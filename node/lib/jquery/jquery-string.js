/*
|-------------------------------------------
| jquery string
|-------------------------------------------
|
| a string containing all the js code in jquery
|
| type:         string
| author:       Josh Bambrick
| version:      0.0.1
| modified:     20/02/15
|
*/

define([
    "fs"
], function (
    fs
) {
    return fs.readFileSync("./lib/jquery/jquery-2.1.3.min.js").toString();
});