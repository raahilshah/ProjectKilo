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
| the most likely reason for any issues you may have with this
| is that the amazon link has expired for whatever reason
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "tools/get-url-$body",
    "errors/node-error",
    "errors/error-map"
], function (
    getUrl$body,
    NodeError,
    errorMap
) {
    return function (frameObj, complete) {
        getUrl$body(frameObj.url, function ($, $body) {
            if ($body instanceof NodeError) return complete($body);

            $allLink = $body.find("a").filter(function () {
                return /^[0-9,]+ customer reviews$/i.test($(this).text().trim());
            });

            if ($allLink.length === 0 || !$allLink.attr("href")) return complete(new errorMap.HttpGetFailed());

            getUrl$body($allLink.attr("href"), function ($, $body) {
                var reviews = [];

                $body.find(".reviewText").each(function () {
                    if (reviews.length >= frameObj.maxReviews) return false;

                    reviews.push($(this).text());
                });

                complete(reviews);
            });
        });
    };
});
