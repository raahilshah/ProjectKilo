/*
|-------------------------------------------
| get page html
|-------------------------------------------
|
| generates the html returned by a GET HTTP request to a url
|
| type:         function
| author:       Josh Bambrick
| version:      0.0.1
| modified:     09/02/15
|
*/

define([
    "http",
    "url"
], function (
    http,
    urlTools
) {
    return function (url, complete) {
        console.log(urlTools.parse(url), true, true);
        // console.log(url.host)
        // console.log(url.path)
        // var host, path, request;

        // request = http.request({
        //     host: url, // 'www.bulksms.co.uk',
        //     port: 80,
        //     method: 'GET',
        //     path: // '/eapi/submission/send_sms/2/2.0',
        //     // headers: {
        //     //     'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        //     // }
        // });


        // // request.write(postBodyString);

        // request.end();
    };
});
