/*
|-------------------------------------------
| parse frame
|-------------------------------------------
|
| takes a frame object and sends processed output to a callback
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     03/02/15
|
*/

requirejs([
    "underscore",
    "tools/standard-interface",
    "parse-frame/parse-frame"
], function (
    _
) {
    return function (frameObj, complete) {
        complete(["This was really good.", "This wasn't very good."]);
    };
});