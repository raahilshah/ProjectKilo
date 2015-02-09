// var spawn = require("child_process").spawn;
var spawn = require("win-spawn");

// var n = cp.fork(__dirname + "/../frame-parser.js", [], {stdio: "pipe"});
var n = spawn("node", [__dirname + "/../frame-parser.js"]);

// set callbacks to read any responses
var inputChunks = [];

n.stdout.setEncoding("utf8");


n.stdout.on("data", function(chunk) {
    inputChunks.push(chunk);
});

n.stdout.on("end", function() {
    console.log(JSON.parse(inputChunks.join("")));
});


// write all child stdout to this process' stdout
n.stdout.pipe(process.stdout);

// send something
n.stdin.write("{\"hello\": 2}");
n.stdin.end();