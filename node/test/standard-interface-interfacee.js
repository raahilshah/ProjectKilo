require("../lib/requirejs/config.js");

requirejs(["tools/standard-interface"], function (standardInterface) {
    // output whatever was sent
    standardInterface.read(function (input) {
        standardInterface.write(input);
    });
});