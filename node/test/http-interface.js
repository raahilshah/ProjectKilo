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
var request = require("request"),
    _ = require("underscore"),
    timeout = 5000,
    port = 3000;

requirejs(["tools/http-interface"], function (httpInterface) {
    httpInterface.start(port, true);
});

module.exports = [{
    label: "can receive requests",
    getTestResult: function (complete) {
        complete = _.once(complete);
        setTimeout(_.partial(complete, "timeout"), timeout);

        requirejs(["tools/http-interface"], function (httpInterface) {
            httpInterface.read(function (req) {
                httpInterface.write(req);
            });
        });

        request({
            url: this.testUrl,
            qs: this.input
        }, function (err, res, body) {
            if (err) return complete(err);

            try {
                complete(JSON.parse(body));
            } catch(e) {
                complete(body);
            }
        });
    },
    testUrl: "http://127.0.0.1:" + port + "/frame-parser",
    input: {input: "test input"},
    expectedOutput: {input: "test input"}
}, {
    label: "invalid method",
    getTestResult: function (complete) {
        complete = _.once(complete);
        setTimeout(_.partial(complete, "timeout"), timeout);
        
        request({
            url: this.testUrl,
            method: "POST"
        }, function (err, res, body) {
            if (err) return complete(err);

            complete(res.statusCode);
        });
    },
    testUrl: "http://127.0.0.1:" + port + "/frame-parser",
    expectedOutput: 405
}, {
    label: "wrong uri",
    getTestResult: function (complete) {
        complete = _.once(complete);
        setTimeout(_.partial(complete, "timeout"), timeout);
        
        request(this.testUrl, function (err, res, body) {
            if (err) return complete(err);

            complete(res.statusCode);
        });
    },
    testUrl: "http://127.0.0.1:" + port + "/not-frame-parser",
    expectedOutput: 404
}];