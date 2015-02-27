(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['underscore'], factory);
    } else if (typeof require === 'function') {
        factory(require("underscore"));
    } else {
        // Browser globals
        factory(this._);
    }
}(function (_) {
    _.mixin({
        // take a function as a parameter and returns a `getNewCallback` function
        // `getNewCallback` returns a callback function and starts tracking it
        // once all functions created using the `getNewCallback` function have run, the original input function will be called
        // can pass 'true' into one of the callbacks to call it immediately (so that if this is the only callback created, `func` will run)
        all: function (func, onceOnly) {
            var getNewCallback, requiredCallbackIds = [], hasBeenCalled = false, idCounter = 0;

            getNewCallback = function (callImmediately) {
                var newCallbackId = idCounter++, newCallback;

                requiredCallbackIds.push(newCallbackId);

                newCallback = function () {
                    // NOTE: this will do nothing if `newCallbackId` isn't in the array
                    requiredCallbackIds.splice(_.indexOf(requiredCallbackIds, newCallbackId), 1);

                    if (requiredCallbackIds.length === 0 && (!onceOnly || hasBeenCalled === false)) {
                        hasBeenCalled = true;
                        func();
                    }
                };

                return callImmediately ? newCallback() : newCallback;
            };

            return getNewCallback;
        },
        capitalise: function(string) {
            return string.charAt(0).toUpperCase() + string.substring(1).toLowerCase();
        },
        stringClip: function (str, length, addEllipsis) {
            var clipped = str.substr(0, length - (addEllipsis ? 3 : 0));
            return clipped + ((addEllipsis && clipped !== str) ? "..." : "");
        },
        round: function (number, decimalPlaces, toString) {
            var power = Math.pow(10, decimalPlaces || 0),
                roundedNumber = Math.round(number * power)/power;

            return !toString ? roundedNumber : roundedNumber.toFixed(decimalPlaces || 0);
        },
        // a replacement version of `bind` to support the `passDefaultContext` param (but no pre-filled arguments)
        proxy: function (callback, context, passDefaultContext) {
            return function () {
                var args = arguments;

                if (passDefaultContext) {
                    args = _.toArray(args);
                    args.unshift(this);
                }

                return callback.apply(context, args);
            };
        },
        // inserts a new element into a sorted array (at the correct location)
        insert: function (originArr, el) {
            var arr = _.clone(originArr);
            arr.splice(_.sortedIndex(arr, el), 0, el);
            return arr;
        },
        // prefills the arguments of a function
        // more arguments passed are added at the end (or optionally at the start)
        prefill: function (func, args, before, context) {
            args = !_.isArray(args) ? _.toArray(args) : args;

            return function () {
                var newArgs = _.toArray(arguments);
                return func.apply(context || this, before ? newArgs.concat(args) : args.concat(newArgs));
            };
        },
        mean: function (arr) {
            return _.reduce(arr, function(memo, num) {
                return memo + num;
            }, 0) / arr.length;
        },
        replaceArgs: function (func) {
            // the function itself is not included
            var args = _.toArray(arguments).slice(1);
            
            return function () {
                // maintain `this` value
                return func.apply(this, args);
            };
        },
        render: function (template, params) {
            return (_.template(template))(params);
        },
        trim: function (text) {
            // includes trim BOM and NBSP
            return text == null ? '' : (text + '').replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
        },
        bindDefer: function () {
            _.defer(_.bind.apply(_, arguments));
        }
    });
}));