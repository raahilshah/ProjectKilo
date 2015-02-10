/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.github.barcodeeye.scan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.github.barcodeeye.R;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a card scroll view that shows an example of using a custom embedded layout in a
 * {@code CardBuilder}.
 */
public final class EmbeddedCardLayoutActivity extends Activity {

    private CardScrollView mCardScroller;
    private String[] results;
    private int authorNo;
    private String[] authors;
    private String title;
    private int sentenceNo;
    private String[] sentences;
    private String EXTRA_RESULT = "EXTRA_RESULT";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            results = intent.getExtras().getString(EXTRA_RESULT, "No barcode found").split("~");
            for (int i = 0; i < results.length; i++) {

                System.out.println(results[i]);

            }
            title = results[0];
            authorNo = Integer.parseInt(results[1]);
            authors = new String[authorNo];
            for (int i = 0; i < authorNo; i++) {
                authors[i] = results[i+2];
            }
            sentenceNo = results.length - authorNo - 2;
            sentences = new String[sentenceNo];
            for (int i = 0; i < sentenceNo; i++) {
                sentences[i] = results[i + authorNo + 2];
            }

        } else results = null;

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new EmbeddedCardLayoutAdapter(this, createItems()));

        setContentView(mCardScroller);
    }

    /** Creates some sample items that will be displayed on cards in the card scroll view. */
    private List<SimpleTableItem> createItems() {
        ArrayList<SimpleTableItem> items = new ArrayList<SimpleTableItem>();

        for (int i = 0; i < sentenceNo; i++) {

            items.add(new SimpleTableItem(R.drawable.ic_circle_blue, sentences[i]));

        }

        return items;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }
}
