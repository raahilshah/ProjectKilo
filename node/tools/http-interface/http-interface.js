/*
|-------------------------------------------
| http interface
|-------------------------------------------
|
| provides methods to read input from http
| requests and write responses
|
| type:         object
| author:       Josh Bambrick
| version:      0.0.1
| modified:     20/02/15
|
*/

if (typeof define !== "function") { var define = require("amdefine")(module) }

define([
    "underscore",
    "express",
    "body-parser",
    "errors/error-map"
], function (
    _,
    express,
    bodyParser,
    errorMap
) {
    var app, requests = [], port = 3000, unprocessedRequestIndices = [], pendingRequestIndices = [];

    return {
        start: function () {
            if (app == null) {
                app = express();

                // support JSON requests
                app.use(bodyParser.json({
                    limit: "50mb"
                }));

                // listen for POST requests
                app.post("/frame-parser", function (req, res) {
                    unprocessedRequestIndices.push(requests.length);

                    requests.push({
                        req: req,
                        res: res
                    });
                }); 

                app.listen(port, function () {
                    console.log("Frame parser listening on port " + port);
                });

                app.use(function(req, res, next){
                    res.status(404);
                    res.send((new errorMap.PoorRequestPath()).getErrorObj());
                });
            }
        },
        read: function (callback) {
            var read = false,
                checkTimer;

            checkTimer = setInterval(function () {
                if (unprocessedRequestIndices.length > 0) {
                    clearInterval(checkTimer);
                    pendingRequestIndices.push(unprocessedRequestIndices.pop());
                    callback(requests[_.last(pendingRequestIndices)].req.body);
                }
            }, 500);
        },
        write: function (obj) {
            requests[pendingRequestIndices.shift()].res.json(obj);
        }
    }
});