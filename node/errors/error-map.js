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
| version:      0.0.1
| modified:     11/02/15
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
        errorMessage: "request to invalid location",
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