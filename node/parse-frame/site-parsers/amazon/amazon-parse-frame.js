/*
 * Copyright (C) 2015 Group Kilo (Cambridge Computer Lab)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "underscore",
    "url",
    "tools/get-url-$body",
    "errors/node-error",
    "errors/error-map",
    "parse-frame/site-parsers/amazon/amazon-get-page-reviews"
], function (
    _,
    url,
    getUrl$body,
    NodeError,
    errorMap,
    getPageReviews
) {
    var reviewsPerPage = 10,
        reviewsUrlTemplate = _.template("http://www.amazon.com/product-reviews/<%= asin %>/sortBy=byRankDescending&pageNumber=<%= pageNumber %>");

    return function (frameObj, complete) {
        var asin = url.parse(frameObj.url, true).query.asin,
            // the total number of reviews pages to make requests to
            pagesToTest = Math.ceil(frameObj.maxReviews / reviewsPerPage),
            // the total number of reviews to fetch from the last page
            lastPageReviewsToFetch = frameObj.maxReviews % reviewsPerPage,
            // maps page index to the an array of the reviews for that page
            pageIndexReviews = [],
            // a function to create a callback to be run once a request is complete
            // once all these callbacks have been called, the function passed to
            // _.all will then be called (this can only happen once)
            getNewCompleteCallback = _.all(function () {
                // find if any of the page requests created an error
                var err = _.find(pageIndexReviews, function (curPageReviews) {
                    return curPageReviews instanceof NodeError;pagesToTest
                });

                // there was at least one error, so return that
                if (err) return complete(err);

                // return all the reviews
                complete(_.flatten(pageIndexReviews));
            }, true);

        // if this is 0, take it to mean `reviewsPerPage`
        lastPageReviewsToFetch = lastPageReviewsToFetch === 0 ? reviewsPerPage : lastPageReviewsToFetch;

        // asin must be a number that converts to a string
        if (!_.isString(asin) || isNaN(asin)) return complete(new errorMap.HttpGetFailed());

        // create requests to the pages
        _.times(pagesToTest, function (curPageIndex) {
            var curReviewsPageUrl = reviewsUrlTemplate({asin: asin, pageNumber: curPageIndex + 1}),
                curReviewsPageMaxReviews = curPageIndex + 1 === pagesToTest ? lastPageReviewsToFetch : reviewsPerPage,
                curPageCompleteCallback = getNewCompleteCallback();

            getUrl$body(curReviewsPageUrl, _.partial(getPageReviews, curReviewsPageMaxReviews, function (reviews) {
                pageIndexReviews[curPageIndex] = reviews;
                curPageCompleteCallback();
            }));
        });

        // make sure that the `complete` callback actually runs
        // even if no reveiws to fetch, return the result (no reviews)
        getNewCompleteCallback(true);
    };
});