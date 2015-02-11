/*
|-------------------------------------------
| parse frame
|-------------------------------------------
|
| takes a frame object and sends processed output to a callback
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     03/02/15
|
*/

define([
    "underscore",
    "parse-frame/parse-frame-site-map",
    "errors/node-error",
    "errors/error-map"
], function (
    _,
    parseSiteMap,
    NodeError,
    errors
) {
    return function (frameObj, complete) {
        var parseSite;

        // fs.writeFileSync("message.txt", JSON.stringify(frameObj));
        if (!(frameObj instanceof NodeError)) {
            parseSite = _.find(parseSiteMap, function (curFunc, curSiteKey) {
                return curSiteKey === frameObj.site;
            });
            
            if (parseSite == null) {
                complete(new errors.SiteNotFound());
            } else {
                parseSite(frameObj, complete);
            }
        } else {
            complete(frameObj);
        }
    };
});