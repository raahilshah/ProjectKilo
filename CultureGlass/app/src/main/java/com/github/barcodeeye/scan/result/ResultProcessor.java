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

package com.github.barcodeeye.scan.result;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.github.barcodeeye.scan.api.CardPresenter;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author javier.romero
 */
public class ResultProcessor<T extends ParsedResult> {
    private final Context mContext;
    private final T mParsedResult;
    private final Result mRawResult;
    private final Uri mPhotoUri;

    public static final HashMap<String, String> PRODUCT_SEARCH_ENDPOINTS = new HashMap<String, String>();

    static {
        PRODUCT_SEARCH_ENDPOINTS.put("CultureGlasses",
                "http://groupkilo.soc.srcf.net/ProjectKiloWebApp/test?barcodeNo={CODE}&barcodeType={TYPE}");
    }

    public ResultProcessor(Context context, T parsedResult,
                           Result result, Uri photoUri) {
        mContext = context;
        mParsedResult = parsedResult;
        mRawResult = result;
        mPhotoUri = photoUri;
    }

    public Context getContext() {
        return mContext;
    }

    public T getParsedResult() {
        return mParsedResult;
    }

    public Result getRawResult() {
        return mRawResult;
    }

    public Uri getPhotoUri() {
        return mPhotoUri;
    }

    public List<CardPresenter> getCardResults() {
        List<CardPresenter> cardPresenters = new ArrayList<CardPresenter>();

        ParsedResult parsedResult = getParsedResult();
        String codeValue = parsedResult.getDisplayResult();

        for (String key : PRODUCT_SEARCH_ENDPOINTS.keySet()) {
            CardPresenter cardPresenter = new CardPresenter();
            cardPresenter.setText("Lookup on " + key).setFooter(codeValue);

            String url = PRODUCT_SEARCH_ENDPOINTS.get(key);
            url = url.replace("{CODE}", codeValue);
            url = url.replace("{TYPE}", mParsedResult.getType().toString());

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            cardPresenter.setPendingIntent(createPendingIntent(getContext(), intent));

            if (getPhotoUri() != null) {
                cardPresenter.addImage(getPhotoUri());
            }

            cardPresenters.add(cardPresenter);
        }

        return cardPresenters;
    }

    public static PendingIntent createPendingIntent(Context context,
                                                    Intent intent) {
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}
