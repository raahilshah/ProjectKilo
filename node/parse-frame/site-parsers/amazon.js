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
    "tools/getUrlHtml"
], function (
    getUrlHtml
) {
    return function (frameObj, complete) {
        getUrlHtml("http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=052156543X&atag=drupal0a-20&exp=2015-02-04T12%3A34%3A32Z&v=2&sig=ktGPAyJ4NeysiOSgUUX0YwBZhCEr8%2BS2cCjxjbwBDcw%3D");
        complete(["This was really good.", "This wasn't very good."]);
    };
});
