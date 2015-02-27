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
| version:      1.3.5
| modified:     24/02/15
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