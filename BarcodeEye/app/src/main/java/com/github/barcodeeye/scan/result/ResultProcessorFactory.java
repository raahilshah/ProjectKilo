package com.github.barcodeeye.scan.result;

import android.content.Context;
import android.net.Uri;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

public final class ResultProcessorFactory {

    public static ResultProcessor<? extends ParsedResult> makeResultProcessor(
            Context context, Result result, Uri photoUri) {

        ParsedResult parsedResult = ResultParser.parseResult(result);
        return new ResultProcessor<ParsedResult>(context, parsedResult, result, photoUri);

    }
}