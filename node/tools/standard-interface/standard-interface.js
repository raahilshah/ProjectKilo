/*
|-------------------------------------------
| standard interface
|-------------------------------------------
|
| provides `read` and `write methods for standard in/out
| interfaces with JSON, working on JS objects
|
| type:         object
| author:       Josh Bambrick
| version:      0.0.2
| modified:     03/02/15
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "underscore",
    "errors/error-map"
], function (
    _,
    errors
) {
    var standardInterface,
        stdin = process.stdin,
        stdout = process.stdout;

    stdin.setEncoding("utf8");

    // there is a limit to the number of event listeners
    // increase this but keep a limit to prevent it growing uncontrollably
    stdin.setMaxListeners(25);

    standardInterface = {
        read: function (callback) {
            var inputChunks = [],
                readCallback, endCallback;

            readCallback = function(chunk) {
                inputChunks.push(chunk);
            };

            stdin.on("data", readCallback);

            endCallback = function() {
                var obj, response;

                // validate the input, or pass an error object to the handler
                // this may, or may not, eventually get sent back to stdout
                try {
                    obj = JSON.parse(inputChunks.join(""));
                } catch (exception) {
                    obj = _.clone(new errors.PoorRequestFormat());
                }

                callback(obj);

                stdin.removeListener("data", readCallback);
                stdin.removeListener("end", endCallback);
            };

            stdin.on("end", endCallback);
        },
        write: function (obj) {
            // send response to stdout
            stdout.write(JSON.stringify(obj));
            stdout.end();
        }
    };

    return standardInterface;
});