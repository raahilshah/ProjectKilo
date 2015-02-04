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
    "tools/standard-interface",
    "parse-frame/parse-frame"
], function (
    _,
    standardInterface,
    parseFrame
) {
    var readNextCommand = true,
        readCommand = function () {
            standardInterface.read(function (obj) {
                parseFrame(obj, function (res) {
                    standardInterface.write(res);
                    readNextCommand = true;
                });
            });
        };

    // repeatedly read in commands from stdin
    // don't read the next if you are currently
    // reading one in/processing one
    // use infinite loop to avoid stack overflow of `readCommand`
    while (true) {
        if (readNextCommand) {
            readNextCommand = false;
            readCommand();
        }
    }
});