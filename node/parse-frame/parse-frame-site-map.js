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

if (typeof define !== 'function') { var define = require('amdefine')(module) }

define([
    "parse-frame/site-parsers/amazon"
], function (
    amazon
) {
    return {
        amazon: amazon
    };
});