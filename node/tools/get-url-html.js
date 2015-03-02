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
| get page html
|-------------------------------------------
|
| generates the html returned by a GET HTTP request to a url
|
| type:         function
| author:       Josh Bambrick
|
*/

if (typeof define !== "function") { var define = require("amdefine")(module) }

define([
    "http",
    "errors/error-map"
], function (
    http,
    errors
) {
    return function (url, complete) {
        http.get(url, function (res) {
            var chunks = [];
            
            res.setEncoding("utf8");
            
            res.on("data", function (chunk) {
                chunks.push(chunk);
            });

            res.on("end", function (chunk) {
                complete(chunks.join(""));
            });
        }).on("error", function () {
            complete(new errors.HttpGetFailed());
        });
    };
});
