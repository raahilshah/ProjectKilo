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
    "errors/error-map"
], function (
    _,
    express,
    errorMap
) {
    var port = 3000, app, requests = [], unprocessedRequestIndices = [], pendingRequestIndices = [];

    return {
        start: function (paramPort) {
            paramPort = paramPort == null ? port : paramPort;

            if (app == null) {
                // create instance of the server
                app = express();

                // listen for POST requests
                app.get("/frame-parser", function (req, res) {
                    // index of request to process
                    unprocessedRequestIndices.push(requests.length);

                    // request and response objects to be dealt
                    // with by the read and write methods
                    requests.push({
                        req: req,
                        res: res
                    });
                }); 

                // send 404 error if another route used
                app.use(function(req, res, next) {
                    res.status(404);
                    res.send((new errorMap.PoorRequestPath()).getErrorObj());
                });

                // turn the server on
                app.listen(paramPort, function () {
                    console.log("Frame parser listening on port " + paramPort);
                });
            }
        },
        read: function (callback) {
            var read = false,
                checkTimerId;

            // keep checking until the next request has come in
            // there will usually already be one
            checkTimerId = setInterval(function () {
                var query;

                if (unprocessedRequestIndices.length > 0) {
                    // stop checking
                    clearInterval(checkTimerId);


                    pendingRequestIndices.push(unprocessedRequestIndices.pop());

                    // determine the query (and convert numbers to actual numbers)
                    query = requests[_.last(pendingRequestIndices)].req.query;

                    _.each(query, function (curVal, curProp) {
                        query[curProp] = isNaN(curVal) ? curVal : +curVal;
                    });

                    callback(query);
                }
            }, 500);
        },
        write: function (obj) {
            requests[pendingRequestIndices.shift()].res.json(obj);
        }
    }
});