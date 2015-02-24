/*
|-------------------------------------------
| amazon get page reviews
|-------------------------------------------
|
| generates reviews on an item from a single page on amazon and
| recurses to get the from the next page
|
| type:         function
| author:       Josh Bambrick
| version:      0.2.0
| modified:     22/02/15
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "underscore",
    "tools/get-url-$body",
    "errors/node-error"
], function (
    _,
    getUrl$body,
    NodeError
) {
    var getPageReviews = function (
        // total max number of reviews to get
        maxReviews,
        // callback for completion
        complete,
        // jquery of the page
        $,
        // jquery object of the page's body
        $body
    ) {
        if ($ instanceof NodeError) return complete($);

        var reviews = [];

        // get the text for each of these reviews
        $body.find(".reviewText").each(function () {
            if (reviews.length >= maxReviews) return false;

            reviews.push($(this).text());
        });

        complete(reviews);
    };

    return getPageReviews;
});