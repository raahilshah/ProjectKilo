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
| get url $body
|-------------------------------------------
|
| gets $body object from a url
|
| type:         function
| author:       Josh Bambrick
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
    errorMap
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
