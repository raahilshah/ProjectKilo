var _ = require("underscore"),
    spawn = require("child_process").spawn,
    tests = [{
        input: JSON.stringify({hello: 2, goodbye: "hello"}),
        expectedOutput: "[\"This was really good.\",\"This wasn't very good.\"]"
    }, {
        input: JSON.stringify({hello: 2, goodbye: "hello"}),
        expectedOutput: "[\"This was really good.\",\"This wasn't very good.\"]"
    }, {
        input: "this is not valid json",
        expectedOutput: "{\"interfaceError\":true,\"errorMessage\":\"data poorly formatted\",\"errorCode\":100}"
    }],
    onlySourceOutput = false;


_.each(tests, function (curTest, curTestIndex) {
    // set callbacks to read any responses
    var n = spawn("node", [__dirname + "/../frame-parser.js"]),
        inputChunks = [];

    n.stdout.setEncoding("utf8");

    if (onlySourceOutput) {
        // log all of the data passed (inc console.logs if debugging)
        n.stdout.pipe(process.stdout);
    }

    n.stdout.on("data", function(chunk) {
        inputChunks.push(chunk);
    });

    n.stdout.on("end", function() {
        if (!onlySourceOutput) {
            console.log((inputChunks.join("") === curTest.expectedOutput ? "passed test " : "failed test ") + curTestIndex);
        }
    });


    // send something
    n.stdin.write(curTest.input);
    n.stdin.end();
});