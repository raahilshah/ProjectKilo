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
    var standardInterface,
        stdin = process.stdin;

    stdin.setEncoding("utf8");

    // there is a limit to the number of event listeners
    // increase this but keep a limit to prevent it growing uncontrollably
    stdin.setMaxListeners(25);

    standardInterface = {
        read: function (callback) {
            console.log(100)
            var inputChunks = [],
                readCallback, endCallback;


            readCallback = function() {
                console.log(200)
                var curChunk = stdin.read();

                if (curChunk != null) {
                    inputChunks.push(curChunk);
                }
            };

            stdin.on("readable", readCallback);

            endCallback = function() {
                console.log("sldfkj")
                callback(JSON.parse(inputChunks.join("")));
                stdin.removeListener("readable", readCallback);
                stdin.removeListener("end", endCallback);
            };

            stdin.on("end", endCallback);
        },
        write: function (obj) {
            var stdout = process.stdout;

            stdout.write(JSON.stringify(obj));
            stdout.end("\n");
        }
    };

    return standardInterface;
});