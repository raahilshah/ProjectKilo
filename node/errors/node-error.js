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
| Node Error
|-------------------------------------------
|
| class of errors that may be encountered on this server
|
| type:         class
| author:       Josh Bambrick
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
], function (
) {
    function NodeError(errObj) {
        this.getMessage = function () {
            return errObj.errorMessage;
        };
        this.getErrorCode = function () {
            return errObj.errorCode;
        };
        this.getErrorObj = function () {
            return errObj;
        };
        this.getErrorJson = function () {
            return JSON.stringify(errObj);
        };
    };

    NodeError.prototype = Error;

    return NodeError;
});