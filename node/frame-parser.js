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
    console.log(arguments)
    while (true) {
        standardInterface.read(_.partial(parseFrame, _, standardInterface.write));
    }
});