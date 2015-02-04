var cp = require("child_process");

// var n = cp.fork(__dirname + "/../frame-parser.js", [], {stdio: "pipe"});
var n = cp.spawn("node", [__dirname + "/../frame-parser.js"], {stdio: "pipe"});

// set callbacks to read any responses
var inputChunks = [];

n.stdout.on("readable", function() {
    var curChunk = n.stdout.read();


    console.log(curChunk)

    if (curChunk != null) {
        inputChunks.push(curChunk);
    }
});

n.stdout.on("end", function() {
    console.log(JSON.parse(inputChunks.join("")));
});
setTimeout(function () {
	// send something
	n.stdin.end("{\"hello\": 2}");
	// n.stdin.write("{\"hello\": 2}", "utf8", function () {
	// });
	
}, 200)