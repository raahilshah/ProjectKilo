/*
|-------------------------------------------
| amazon frame parser
|-------------------------------------------
|
| generates reviews on an item from amazon and sends
| them to a callback
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     09/02/15
|
*/

define([
    "tools/getUrlHtml"
], function (
    getUrlHtml
) {
    return function (frameObj, complete) {
        // getUrlHtml("http://user:pass@host.com:8080/p/a/t/h?query=string#hash");
        complete(["This was really good.", "This wasn't very good."]);
    };
});
