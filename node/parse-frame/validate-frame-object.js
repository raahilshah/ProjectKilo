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
| validate frame object
|-------------------------------------------
|
| takes a frame object and ensures that it is valid
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     12/02/15
|
*/

define([
    "underscore",
    "errors/node-error",
    "errors/error-map"
], function (
    _,
    NodeError,
    errors
) {
    var fields = [{
        prop: "site",
        type: "string"
    }, {
        prop: "url",
        type: "string"
    }, {
        prop: "category",
        type: "string"
    }, {
        prop: "maxReviews",
        type: "number",
        nonNegative: true
    }];

    return function (frameObj, complete) {
        if (frameObj instanceof NodeError) return complete(frameObj);

        var isValid = _.reduce(fields, function (curIsValid, curFieldObj) {
            var newIsValid = curIsValid;

            newIsValid = newIsValid && _.has(frameObj, curFieldObj.prop);

            switch (curFieldObj.type) {
                case "string":
                    newIsValid = newIsValid && _.isString(frameObj[curFieldObj.prop]);
                    break;
                case "number":
                    newIsValid = newIsValid && _.isNumber(frameObj[curFieldObj.prop]);
                    newIsValid = newIsValid && (!curFieldObj.nonNegative || frameObj[curFieldObj.prop] >= 0);
                    break;
            }

            return newIsValid;
        }, true);

        if (isValid) {
            complete(frameObj);
        } else {
            complete(new errors.PoorRequestFormat());
        }
    };
});