// var spawn = require("child_process").spawn;
var spawn = require("win-spawn");

// var n = cp.fork(__dirname + "/../frame-parser.js", [], {stdio: "pipe"});
var n = spawn("node", [__dirname + "/../frame-parser.js"], {stdio: "pipe"});

// set callbacks to read any responses
var inputChunks = [];

n.stdout.setEncoding("utf8");


n.stdout.on("data", function(chunk) {
    inputChunks.push(chunk);
});

n.stdout.on("end", function() {
    console.log("= test end =")
    // console.log(JSON.parse(inputChunks.join("")));
    console.log(inputChunks.join(""));
});

console.log("= send start =")
// send something
n.stdin.end("hello");
// n.stdin.write("{\"hello\": 2}", "utf8");
// n.stdin.end();
console.log("= send end =")