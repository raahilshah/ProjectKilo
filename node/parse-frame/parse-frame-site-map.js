/*
|-------------------------------------------
| parse site frame map
|-------------------------------------------
|
| map of site names to functions used to generate
| reviews based from that site
|
| type:         object
| author:       Josh Bambrick
| version:      0.0.1
| modified:     09/02/15
|
*/

define([
    "parse-frame/site-parsers/amazon"
], function (
    amazon
) {
    return {
        amazon: amazon
    };
});