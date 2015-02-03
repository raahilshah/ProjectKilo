(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['underscore'], factory);
    } else {
        // Browser globals
        factory(this._);
    }
}(function (_) {
    _.mixin({
        capitalise: function(string) {
            return string.charAt(0).toUpperCase() + string.substring(1).toLowerCase();
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