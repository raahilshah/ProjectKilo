/*
|-------------------------------------------
| parse frame errors
|-------------------------------------------
|
| map of error names to information about those errors
| errors are related to the content requested
|
| type:         object
| author:       Josh Bambrick
| version:      0.0.1
| modified:     09/02/15
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
], function (
) {
    return {
        siteNotFound : {
            errorMessage: "site not found",
            errorCode: 200
        }
    };
});