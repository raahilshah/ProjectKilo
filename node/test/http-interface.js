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