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
        // 0-indexed current page number
        curPageIndex,
        // string of the reviews
        reviews,
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

        // the url of the next page of reviews to process (if necessary)
        var nextPageUrl;

        // get the text for each of these reviews
        $body.find(".reviewText").each(function () {
            if (reviews.length >= maxReviews) return false;

            reviews.push($(this).text());
        });

        // you have found all the requested reviews
        if (reviews.length === maxReviews) {
            complete(reviews);
        } else {
            nextPageUrl = $(".paging").first().find("a").filter(function () {
                // zero-based index of link text is that of the next page
                return (+$(this).text()) - 1 === curPageIndex + 1;
            }).attr("href");
                
            // you have already fetched all the reviews
            if (!nextPageUrl) {
                complete(reviews);
            } else {
                // recurse to process next page
                getUrl$body(nextPageUrl, _.partial(getPageReviews, curPageIndex + 1, reviews, maxReviews, complete));
            }
        }
    };

    return getPageReviews;
});