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
| amazon get page reviews
|-------------------------------------------
|
| generates reviews on an item from a single page on amazon and
| recurses to get the from the next page
|
| type:         function
| author:       Josh Bambrick
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