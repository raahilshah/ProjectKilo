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
| frame parser
|-------------------------------------------
|
| gets iframes via stdin, interprets the HTML and
| sends back well-formatted output
|
| type:         void
| author:       Josh Bambrick
|
*/

require("./lib/requirejs/config.js");

requirejs([
    "underscore",
    "tools/http-interface",
    "tools/standard-interface",
    "parse-frame/validate-frame-object",
    "parse-frame/parse-frame",
    "errors/node-error",
    "errors/error-map"
], function (
    _,
    httpInterface,
    standardInterface,
    validateFrameObject,
    parseFrame,
    NodeError,
    errorMap
) {
    var readNextCommand = true,
        readRequest, sendResult,
        readCommand = function () {
            readRequest(function (obj) {
                validateFrameObject(obj, function (obj) {
                    parseFrame(obj, sendResult);
                });
            });
        };

    // determine inteface for communication
    switch (process.argv[2]) {
        case "test":
            // interface using second argument and console

            readRequest = function (callback) {
                try {
                    callback(JSON.parse(process.argv[3]));
                } catch (err) {
                    callback(new errorMap.PoorRequestFormat());
                }
            };

            sendResult = function (res) {
                console.log(res instanceof NodeError ? res.getErrorObj() : res);
            };

            break;
        case "web":
            // interface via a web api using route /frame-parser

            // initiate the RESTful API
            httpInterface.start(process.argv[3]);

            readRequest = httpInterface.read;

            sendResult = function (res) {
                httpInterface.write(res instanceof NodeError ? res.getErrorObj() : res);
                readNextCommand = true;
            };

            break;
        default:
            // use standard in/out

            readRequest = standardInterface.read;
            sendResult = function (res) {
                standardInterface.write(res instanceof NodeError ? res.getErrorObj() : res);
                readNextCommand = true;
            };

            break;
    }

    // repeatedly read commands
    setInterval(function () {
        if (readNextCommand) {
            readNextCommand = false;
            readCommand();
        }
    }, 50);
});