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
        getUrlHtml("http://amazon.com/my/product?sogood=true");
        complete(["This was really good.", "This wasn't very good."]);
    };
});
