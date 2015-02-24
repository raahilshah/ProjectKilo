/*
|-------------------------------------------
| get url $body
|-------------------------------------------
|
| gets $body object from a url
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     20/02/15
|
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "jsdom",
    "lib/jquery/jquery-string",
    "request",
    "errors/error-map"
], function (
    jsdom,
    jqString,
    request,
    errors
) {
    return function (url, complete) {
        request(url, function(err, res, body) {
            if (err) return complete(new errorMap.HttpGetFailed());

            // create a dom environment from the html fetched
            jsdom.env({
                html: body,
                // include jquery as a script on the page
                src: [jqString],
                // run once dom environment created
                done: function (err, window) {
                    if (err) return complete(new errorMap.HttpGetFailed());

                    var $ = window.jQuery,
                        $body = $("body");

                    if ($body.length === 0) return complete(new errorMap.HttpGetFailed());

                    complete($, $body);
                }
            });
        });
    }
});
