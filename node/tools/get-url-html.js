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
            console.log(res.getHeader("Content-length"))
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