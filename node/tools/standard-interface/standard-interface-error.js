/*
|-------------------------------------------
| standard interface error
|-------------------------------------------
|
| represents an error thrown which may be sent via standard out
|
| type:         constructor
| author:       Josh Bambrick
| version:      0.0.1
| modified:     10/02/15
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
], function (
) {
    return function StandardInterfaceError(messageObj) {
        this.getMessage = function () {
            return messageObj;
        };
        this.getMessageJson = function () {
            return JSON.stringify(messageObj);
        };
    };
});