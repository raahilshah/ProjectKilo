var _ = require("underscore"),
    mixins = require("../lib/underscore/underscore-mixins.js"),
    spawn = require("child_process").spawn,
    timeout = 15000;

module.exports = [{
    label: "send and receive same",
    getTestResult: function (complete) {
        var n = spawn("node", [__dirname + "/standard-interface-interfacee.js"]),
            inputChunks = [];

        complete = _.once(complete);
        setTimeout(_.partial(complete, "timeout"), timeout);
        
        n.stdout.setEncoding("utf8");

        n.stdout.on("data", function(chunk) {
            inputChunks.push(chunk);
        });

        // expect the exact same data back
        n.stdout.on("end", function() {
            complete(JSON.parse(inputChunks.join("")));
        });

        // send something
        n.stdin.write(JSON.stringify(this.input));
        n.stdin.end();
    },
    input: {input: "tested data"},
    expectedOutput: {input: "tested data"}
}];