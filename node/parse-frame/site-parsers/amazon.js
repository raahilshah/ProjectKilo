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

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "jquery",
    "tools/getUrlHtml",
    "errors/node-error",
    "errors/error-map"
], function (
    $,
    getUrlHtml,
    NodeError,
    errorMap
) {
    return function (frameObj, complete) {
        getUrlHtml(frameObj.url, function (body) {
            if (!(body instanceof NodeError)) {
                $(body).find("a").filter(function () {
                    console.log(this.text())
                    return !!this.text();
                });
                complete(["This was really good.", "This wasn't very good."]);
            } else {
                complete(body);
            }
        });
    };
});
