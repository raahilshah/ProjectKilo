var _ = require("underscore");

module.exports = [{
    label: "nonsense valid object",
    getTestResult: function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object"], function (validateFrameObject) {
            validateFrameObject(input, function (validated) {
                complete(_.isEqual(validated, input));
            });
        });
    },
    input: {site: "", url: "", category: "", maxReviews: 400},
    expectedOutput: true
}, {
    label: "real valid object",
    getTestResult: function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object"], function (validateFrameObject) {
            validateFrameObject(input, function (validated) {
                complete(_.isEqual(validated, input));
            });
        });
    },
    input: {
        site: "amazon",
        url: "http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=1853260002&atag=drupal0a-20&exp=2015-02-13T13%3A05%3A12Z&v=2&sig=f0ix3qz%2FvM2g6eoZcDE38BFOBCbPbaWMDFzAd33niC4%3D",
        maxReviews: 10,
        category: "book"
    },
    expectedOutput: true
}, {
    label: "missing field",
    getTestResult: function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object", "errors/node-error"], function (validateFrameObject, NodeError) {
            validateFrameObject(input, function (validated) {
                complete(validated instanceof NodeError && validated.getErrorCode() === 100);
            });
        });
    },
    input: {
        site: "amazon",
        url: "http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=1853260002&atag=drupal0a-20&exp=2015-02-13T13%3A05%3A12Z&v=2&sig=f0ix3qz%2FvM2g6eoZcDE38BFOBCbPbaWMDFzAd33niC4%3D",
        category: "book"
    },
    expectedOutput: true
}, {
    label: "not object",
    getTestResult: function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object", "errors/node-error"], function (validateFrameObject, NodeError) {
            validateFrameObject(input, function (validated) {
                complete(validated instanceof NodeError && validated.getErrorCode() === 100);
            });
        });
    },
    input: 100,
    expectedOutput: true
}, {
    label: "undefined",
    getTestResult: function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object", "errors/node-error"], function (validateFrameObject, NodeError) {
            validateFrameObject(input, function (validated) {
                complete(validated instanceof NodeError && validated.getErrorCode() === 100);
            });
        });
    },
    input: void 0,
    expectedOutput: true
}, {
    label: "field wrong type",
    getTestResult: function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object", "errors/node-error"], function (validateFrameObject, NodeError) {
            validateFrameObject(input, function (validated) {
                complete(validated instanceof NodeError && validated.getErrorCode() === 100);
            });
        });
    },
    input: {
        site: "amazon",
        url: "http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=1853260002&atag=drupal0a-20&exp=2015-02-13T13%3A05%3A12Z&v=2&sig=f0ix3qz%2FvM2g6eoZcDE38BFOBCbPbaWMDFzAd33niC4%3D",
        maxReviews: "10",
        category: "book"
    },
    expectedOutput: true
}];