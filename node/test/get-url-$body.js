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
module.exports = [{
    label: "valid url",
    getTestResult: function (complete) {
        var testUrl = this.testUrl;

        requirejs(["tools/get-url-$body"], function (getUrl$body) {
            getUrl$body(testUrl, function ($, $body) {
                complete($body.html());
            });
        });
    },
    testUrl: "http://37.122.211.115:3000/frame-parser?site=amazon&url=http%3A%2F%2Fwww.amazon.com%2Freviews%2Fiframe%3Fakid%3DAKIAI4LLUAWZMGNUW5NA%26alinkCode%3Dxm2%26asin%3D1853260002%26atag%3Ddrupal0a-20%26exp%3D2015-02-23T15%253A59%253A03Z%26v%3D2%26sig%3DSr%252B%252Fx6b%252Ft%252BZSdEykxXBZAFHX3iozk3vyoUVkWRPN6sA%253D&maxReviews=1&category=book",
    expectedOutput: "[\"Jane Austen is one of the great masters of the English language, and PRIDE AND PREJUDICE is her great masterpiece, a sharp and witty comedy of manners played out in early 19th Century English society, a world in which men held virtually all the power and women were required to negotiate mine-fields of social status, respectability, wealth, love, and sex in order to marry both to their own liking and to the advantage of their family.  And such is particularly the case of the Bennetts, a family of daughters whose father's estate is entailed to a distant relative, for upon Mr. Bennett's death they will lose home, land, income, everything.  But are the Bennett daughters up to playing a winning hand in this high-stakes matrimonial game without forfeiting their own personal integrity?This battle of the sexes is largely seen through the eyes of second daughter Elizabeth, who possesses a razor-sharp wit and rich sense of humor--and who finds herself hindered by her own addlepated mother, her sister Jane's hopeless love for the wealthy Mr. Bingley, and her sister Lydia's penchant for scandal... not to mention the high-born, formidable, and outrageously proud Mr. Darcy, who seems determined to trump her every card.  But the game of love proves more surprising than either Elizabeth or Mr. Darcy can imagine, and sometimes a seemingly weak hand proves a winning one when all cards are on the table.PRIDE AND PREJUDICE is simply one of the funniest novels ever written, peopled with memorable characters brought vividly to life as they both succeed and fail at the game of life according to the manners of their era.  It is a novel to which I return again and again, enjoying Austen's brillant talent. I have little respect for people who describe it as dull, slow, out of date, for as long as men and women live and fall in love it will never be out of style, always be meaningful, and always be funny.  A masterpiece of wit and style; a timeless novel for the ages.\"]"
}, {
    label: "invalid url",
    getTestResult: function (complete) {
        var testUrl = this.testUrl;

        requirejs(["tools/get-url-$body"], function (getUrl$body) {
            getUrl$body(testUrl, function ($, $body) {
                complete($.getErrorObj());
            });
        });
    },
    testUrl: "invalid url",
    // response must contain this field (http get failed)
    expectedOutput: {errorCode: 300},
    // the actual output object may also contain other fields
    outputExtensible: true
}];