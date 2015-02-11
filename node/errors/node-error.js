/*
|-------------------------------------------
| Node Error
|-------------------------------------------
|
| class of errors that may be encountered on this server
|
| type:         class
| author:       Josh Bambrick
| version:      0.0.1
| modified:     11/02/15
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