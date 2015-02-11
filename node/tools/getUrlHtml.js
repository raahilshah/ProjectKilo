/*
|-------------------------------------------
| get page html
|-------------------------------------------
|
| generates the html returned by a GET HTTP request to a url
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     09/02/15
|
*/

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "http",
    "errors/error-map"
], function (
    http,
    errors
) {
    return function (url, complete) {
        http.get(url, function (res) {
            console.log('STATUS: ' + res.statusCode);
            console.log('HEADERS: ' + JSON.stringify(res.headers));
            res.setEncoding('utf8');
            res.on('data', function (chunk) {
                console.log('BODY: ' + chunk);
            });
        }).on("error", function () {
            complete(new errors.HttpGetFailed());
        });
    };
});