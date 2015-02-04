/*
|-------------------------------------------
| requirejs config
|-------------------------------------------
|
| configures requirejs
|
| author:       Josh Bambrick
| version:      0.0.2
| modified:     03/02/15
|
*/

requirejs = require("requirejs");
if (typeof define !== "function") {
	define = require("amdefine")(module);
}

requirejs.config({
    nodeRequire: require,
    baseUrl: __dirname + "/../..",
    deps: ["./lib/requirejs/loader"],
    paths: {
        jquery:                 "node_modules/jquery/jquery",
        underscore:             "node_modules/underscore/underscore",
        underscoreMixins:       "lib/underscore/underscore-mixins"
    }
});