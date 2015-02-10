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
    "parse-frame/errors"
], function (
    _,
    parseSiteMap,
    errors
) {
    return function (frameObj, complete) {
        var parseSite;

        // fs.writeFileSync("message.txt", JSON.stringify(frameObj));
        if (!frameObj.interfaceError) {
            parseSite = _.find(parseSiteMap, function (curFunc, curSiteKey) {
                return curSiteKey === frameObj.site;
            });
            
            if (parseSite == null) {
                complete(errors.siteNotFound);
            } else {
                parseSite(frameObj, complete);
            }
        } else {
            complete(frameObj);
        }
    };
});