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
| error map
|-------------------------------------------
|
| map of error names to information about those errors
| errors are related to the content requested
|
| type:         object
| author:       Josh Bambrick
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "errors/node-error"
], function (
    NodeError
) {
    var PoorRequestFormat = function () {}, PoorRequestPath = function () {}, SiteNotFound = function () {}, HttpGetFailed = function () {};

    PoorRequestFormat.prototype = new NodeError({
        errorMessage: "data poorly formatted",
        errorCode: 100
    });

    PoorRequestPath.prototype = new NodeError({
        errorMessage: "request to invalid frame-parser API route",
        errorCode: 101
    });

    SiteNotFound.prototype = new NodeError({
        errorMessage: "site not found",
        errorCode: 200
    });

    HttpGetFailed.prototype = new NodeError({
        errorMessage: "http request failed",
        errorCode: 300
    });

    return {
        PoorRequestFormat: PoorRequestFormat,
        PoorRequestPath: PoorRequestPath,
        SiteNotFound: SiteNotFound,
        HttpGetFailed: HttpGetFailed
    };
});