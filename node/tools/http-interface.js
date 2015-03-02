/*
 * Copyright (C) 2015 Group Kilo (Cambridge Computer Lab)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    var defaultPort = 3000, app, requests = [], unprocessedRequestIndices = [], pendingRequestIndices = [];

    return {
        start: function (paramPort, suppressMessage, onStart) {
            paramPort = paramPort || defaultPort;
            onStart = onStart || _.noop;

            if (app == null) {
                // create instance of the server
                app = express();

                // listen for POST requests
                app.all("/frame-parser", function (req, res) {
                    if (req.method.toLowerCase() === "get") {
                        // index of request to process
                        unprocessedRequestIndices.push(requests.length);

                        // request and response objects to be dealt
                        // with by the read and write methods
                        requests.push({
                            req: req,
                            res: res
                        });
                    } else {
                        // invalid method
                        res.status(405);
                        res.send((new errorMap.PoorRequestPath()).getErrorObj());
                    }
                }); 

                // send 404 error if another route used
                app.use(function(req, res, next) {
                    res.status(404);
                    res.send((new errorMap.PoorRequestPath()).getErrorObj());
                });
                
                // turn the server on
                app.listen(paramPort, function () {
                    if (!suppressMessage) {
                        console.log("Frame parser listening on port " + paramPort);
                    }
                    onStart();
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