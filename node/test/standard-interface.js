/*
 * Copyright (C) 2015 Group Kilo (Cambridge Computer Lab)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
|
| type:         function
| author:       Josh Bambrick
|
*/
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