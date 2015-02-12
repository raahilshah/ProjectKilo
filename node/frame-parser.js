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
| version:      0.0.1
| modified:     03/02/15
|
*/

require("./lib/requirejs/config.js");

requirejs([
    "underscore",
    "tools/standard-interface/standard-interface",
    "parse-frame/validate-frame-object",
    "parse-frame/parse-frame",
    "errors/node-error",
    "errors/error-map"
], function (
    _,
    standardInterface,
    validateFrameObject,
    parseFrame,
    NodeError,
    errorMap
) {
    var readNextCommand = true,
        sendResult = function (res) {
            standardInterface.write(res instanceof NodeError ? res.getErrorObj() : res);
            readNextCommand = true;
        },
        readRequest = standardInterface.read,
        readCommand = function () {
            readRequest(function (obj) {
                validateFrameObject(obj, function (obj) {
                    parseFrame(obj, sendResult);
                });
            });
        };

    // alternative means of communicating if this is a test run
    if (process.argv[2] === "test") {
        sendResult = function (res) {
            console.log(res instanceof NodeError ? res.getErrorObj() : res);
        };
        readRequest = function (callback) {
            try {
                callback(JSON.parse(process.argv[3]));
            } catch (err) {
                callback(new errorMap.PoorRequestFormat());
            }
        };
    }

    // repeatedly read in commands from stdin
    // don't read the next if you are currently
    // reading one in/processing one
    // use infinite loop to avoid stack overflow of `readCommand`
    // don't use while (true) as this is FUBAR
    setInterval(function () {
        if (readNextCommand) {
            readNextCommand = false;
            readCommand();
        }
    }, 50);
});