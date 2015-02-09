var _ = require("underscore"),
    spawn = require("child_process").spawn,
    tests = [{
        input: JSON.stringify({site: "amazon", hello: 2, goodbye: "hello"}),
        // response must match this exactly
        expectedOutputObj: [
            "This was really good.",
            "This wasn't very good."
        ]
    }, {
        input: JSON.stringify({site: "amazon", hello: 2, goodbye: "hello"}),
        // response must match this exactly
        expectedOutputObj: [
            "This was really good.",
            "This wasn't very good."
        ]
    }, {
        input: "this is not valid json",
        // response must contain this field
        expectedOutputObjContains: {errorCode: 100}
    }, {
        input: JSON.stringify({site: "not a real site"}),
        // response must contain this field
        expectedOutputObjContains: {errorCode: 200}
    }],
    // pass a parameter to determine whether to display
    // output from the harness or the source
    onlySourceOutput = !!process.argv[2];

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
        var res = JSON.parse(inputChunks.join("")), passed = true;

        if (!onlySourceOutput) {
            passed = _.reduce(curTest, function (curPassed, curTestItem, curTestItemKey) {
                switch (curTestItemKey) {
                    case 'expectedOutputObj':
                        curPassed = curPassed && _.isEqual(res, curTestItem);
                        break;
                    case 'expectedOutputObjContains':
                        curPassed = curPassed && _.matches(curTestItem)(res);
                        break;
                }
                return curPassed
            }, passed);

            console.log((passed ? "passed test " : "failed test ") + curTestIndex);
        }
    });


    // send something
    n.stdin.write(curTest.input);
    n.stdin.end();
});