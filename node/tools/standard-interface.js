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
| version:      0.0.1
| modified:     03/02/15
|
*/

requirejs([
    "underscore"
], function (
    _
) {
    var standardInterface = {
        read: function (callback) {
            var stdin = process.stdin,
                inputChunks = [];

            stdin.setEncoding("utf8");

            stdin.on("readable", function() {
                var curChunk = stdin.read();

                if (curChunk != null) {
                    inputChunks.push(curChunk);
                }
            });

            stdin.on("end", function() {
                callback(JSON.parse(inputChunks.join("")));
            });
        },
        write: function (obj) {
            var stdout = process.stdout;

            stdout.write(JSON.stringify(obj));
            stdout.end("\n");
        }
    };

    return standardInterface;
});