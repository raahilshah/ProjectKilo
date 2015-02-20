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
    "jsdom",
    "lib/jquery/jquery-string",
    "tools/get-url-html",
    "errors/node-error",
    "errors/error-map"
], function (
    jsdom,
    jqString,
    getUrlHtml,
    NodeError,
    errorMap
) {
    return function (frameObj, complete) {
        getUrlHtml(frameObj.url, function (body) {
            if (!(body instanceof NodeError)) {
                jsdom.env({
                    html: body,
                    src: [jqString],
                    done: function (err, window) {
                        var $ = window.jQuery;

                        // jQuery is now loaded on the jsdom window created from 'agent.body'
                        console.log($('body').html());
                    }
                });
                
                // $(body).find("a").filter(function () {
                //     console.log(this.text())
                //     return !!this.text();
                // });
                complete(["This was really good.", "This wasn't very good."]);
            } else {
                complete(body);
            }
        });
    };
});
