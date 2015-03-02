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
// support requirejs
require("../lib/requirejs/config.js");

var _ = require("underscore"),
    // ensure mixins are added to underscore
    mixins = require("../lib/underscore/underscore-mixins.js"),
    // whether or not to show a full error message
    fullErrorOutput = !!process.argv[2],
    // the string length of a clipped error message
    clippedErrorOutputLength = 100,
    // counters for the tests which have been run
    testsRan = 0, testsPassed = 0, testsFailed = 0,
    // run whenever a test has been completed
    testComplete = function () {
        testsRan += 1;

        if (testsRan === testCount) {
            console.log();
            console.log("================================");
            console.log();
            if (testsPassed === testCount) {
                console.log("PASSED ALL " + testCount + " TESTS");
            } else {
                console.log("FAILED " + testFailed + " OF " + testCount + " TESTS");
            }
        }
    },
    // run whenever a test has passed
    testPassed = function (index, moduleName, label) {
        testsPassed += 1;
        outputTestPass(index, moduleName, label)
        testComplete();
    },
    // run whenever a test has failed
    testFailed = function (index, moduleName, label, res) {
        testsFailed += 1;
        outputTestFail(index, moduleName, label, res)
        testComplete();
    },
    // outputs information relating to the failure of a test
    outputTestFail = function (index, moduleName, label, expected, actual) {
        console.log("FAILED test " + index + ": " + moduleName + "->" + label);
        console.log("   expected:");
        console.log("   " + (fullErrorOutput ? expected : _.stringClip(JSON.stringify(expected), clippedErrorOutputLength, true)));
        console.log("   got:");
        console.log("   " + (fullErrorOutput ? actual : _.stringClip(JSON.stringify(actual), clippedErrorOutputLength, true)));
    },
    // outputs information relating to the success of a test
    outputTestPass = function (index, moduleName, label) {
        console.log("PASSED test " + index + ": " + moduleName + "->" + label);
    },
    determineTestResult = function (test, moduleName, testIndex, res) {
        // decide if match (output may or may not be allowed to contain extra fields)
        var passed = test.outputExtensible ? _.matches(test.expectedOutput)(res) : _.isEqual(res, test.expectedOutput);

        if (passed) {
            testPassed(testIndex, moduleName, test.label)
        } else {
            testFailed(testIndex, moduleName, test.label, test.expectedOutput, res)
        }
    },
    // defines the modules (groups of tests) and the individual tests that make them up
    testModules = [{
        // tests for the full system
        module: "full system",
        tests: require("./full-tests.js")
    }, {
        // tests for getUrl$body
        module: "getUrl$body",
        tests: require("./get-url-$body.js")
    }, {
        // tests for standardInterface
        module: "standardInterface",
        tests: require("./standard-interface.js")
    }, {
        // tests for httpInterface
        module: "httpInterface",
        tests: require("./http-interface.js")
    }, {
        // tests for validateFrameObject
        module: "validateFrameObject",
        tests: require("./validate-frame-object.js")
    }],
    // the total number of tests
    testCount = _.reduce(testModules, function (curCount, curModule) {
        return curModule.tests.length + curCount;
    }, 0),
    // the index of the test currently being run
    curTestIndex = 0;

// run tests
_.each(testModules, function (curModule) {
    _.each(curModule.tests, function (curTest) {
        curTest.getTestResult(_.partial(determineTestResult, curTest, curModule.module, curTestIndex));

        curTestIndex += 1;
    });
});