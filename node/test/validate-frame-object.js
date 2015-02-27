var _ = require("underscore"),
    getResultValid = function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object"], function (validateFrameObject) {
            validateFrameObject(input, function (validated) {
                complete(_.isEqual(validated, input));
            });
        });
    },
    getResultInvalid = function (complete) {
        var input = this.input;

        requirejs(["parse-frame/validate-frame-object", "errors/node-error"], function (validateFrameObject, NodeError) {
            validateFrameObject(input, function (validated) {
                complete(validated instanceof NodeError && validated.getErrorCode() === 100);
            });
        });
    };

module.exports = [{
    label: "nonsense valid object",
    getTestResult: getResultValid,
    input: {site: "", url: "", category: "", maxReviews: 400},
    expectedOutput: true
}, {
    label: "real valid object",
    getTestResult: getResultValid,
    input: {
        site: "amazon",
        url: "http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=1853260002&atag=drupal0a-20&exp=2015-02-13T13%3A05%3A12Z&v=2&sig=f0ix3qz%2FvM2g6eoZcDE38BFOBCbPbaWMDFzAd33niC4%3D",
        maxReviews: 10,
        category: "book"
    },
    expectedOutput: true
}, {
    label: "missing field",
    getTestResult: getResultInvalid,
    input: {
        site: "amazon",
        url: "http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=1853260002&atag=drupal0a-20&exp=2015-02-13T13%3A05%3A12Z&v=2&sig=f0ix3qz%2FvM2g6eoZcDE38BFOBCbPbaWMDFzAd33niC4%3D",
        category: "book"
    },
    expectedOutput: true
}, {
    label: "negative reviews",
    getTestResult: getResultInvalid,
    input: {
        site: "amazon",
        url: "http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=1853260002&atag=drupal0a-20&exp=2015-02-13T13%3A05%3A12Z&v=2&sig=f0ix3qz%2FvM2g6eoZcDE38BFOBCbPbaWMDFzAd33niC4%3D",
        category: "book",
        maxReviews: -10
    },
    expectedOutput: true
}, {
    label: "not object",
    getTestResult: getResultInvalid,
    input: 100,
    expectedOutput: true
}, {
    label: "undefined",
    getTestResult: getResultInvalid,
    input: void 0,
    expectedOutput: true
}, {
    label: "field wrong type",
    getTestResult: getResultInvalid,
    input: {
        site: "amazon",
        url: "http://www.amazon.com/reviews/iframe?akid=AKIAI4LLUAWZMGNUW5NA&alinkCode=xm2&asin=1853260002&atag=drupal0a-20&exp=2015-02-13T13%3A05%3A12Z&v=2&sig=f0ix3qz%2FvM2g6eoZcDE38BFOBCbPbaWMDFzAd33niC4%3D",
        maxReviews: "10",
        category: "book"
    },
    expectedOutput: true
}];