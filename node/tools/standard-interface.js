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

define([
    "underscore"
], function (
    _
) {
    var fs = require("fs");
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
                fs.writeFileSync("message.txt", chunk);

                
                inputChunks.push(chunk);
            };

            stdin.on("data", readCallback);

            endCallback = function() {
                stdout.end("remove");
                callback(JSON.parse(inputChunks.join("")));
                stdin.removeListener("data", readCallback);
                stdin.removeListener("end", endCallback);
            };

            stdin.on("end", endCallback);
        },
        write: function (obj) {
            stdout.write(JSON.stringify(obj));
            // stdout.end("\n");
            stdout.end();
        }
    };

    return standardInterface;
});