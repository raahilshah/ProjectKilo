/*
|-------------------------------------------
| amazon parse frame
|-------------------------------------------
|
| generates reviews on an item from amazon and sends
| them to a callback
|
| type:         function
| author:       Josh Bambrick
| version:      1.2.0
| modified:     20/02/15
|
| the most likely reason for any issues you may have with this
| is that the amazon link has expired for whatever reason
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "underscore",
    "tools/get-url-$body",
    "errors/node-error",
    "errors/error-map",
    "parse-frame/site-parsers/amazon/amazon-get-page-reviews"
], function (
    _,
    getUrl$body,
    NodeError,
    errorMap,
    getPageReviews
) {
    return function (frameObj, complete) {
        getUrl$body(frameObj.url, function ($, $body) {
            if ($ instanceof NodeError) return complete($);

            // get the link to the first page of top rated reviews
            var firstReviewsPageUrl = $body.find("a").filter(function () {
                return /^[0-9,]+ customer reviews$/i.test($(this).text().trim());
            }).attr("href");

            // if this link is bad return an error
            if (!firstReviewsPageUrl) return complete(new errorMap.HttpGetFailed());

            // fetch the first page of top rated reviews
            getUrl$body(firstReviewsPageUrl, _.partial(getPageReviews, 0, [], frameObj.maxReviews, complete));
        });
    };
});