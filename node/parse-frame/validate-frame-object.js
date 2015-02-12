/*
|-------------------------------------------
| validate frame object
|-------------------------------------------
|
| takes a frame object and ensures that it is valid
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     12/02/15
|
*/

define([
    "underscore",
    "errors/node-error",
    "errors/error-map"
], function (
    _,
    NodeError,
    errors
) {
    var fields = [{
        prop: "site",
        type: "string"
    }, {
        prop: "url",
        type: "string"
    }, {
        prop: "category",
        type: "string"
    }, {
        prop: "maxReviews",
        type: "number"
    }];

    return function (frameObj, complete) {
        if (frameObj instanceof NodeError) return complete(frameObj);

        var isValid = _.reduce(fields, function (curIsValid, curFieldObj) {
            var newIsValid = curIsValid;

            newIsValid = newIsValid && _.has(frameObj, curFieldObj.prop);

            switch (curFieldObj.type) {
                case "string":
                    newIsValid = newIsValid && _.isString(frameObj[curFieldObj.prop]);
                    break;
                case "number":
                    newIsValid = newIsValid && _.isNumber(frameObj[curFieldObj.prop]);
                    break;
            }

            return newIsValid;
        }, true);

        if (isValid) {
            complete(frameObj);
        } else {
            complete(new errors.PoorRequestFormat());
        }
    };
});