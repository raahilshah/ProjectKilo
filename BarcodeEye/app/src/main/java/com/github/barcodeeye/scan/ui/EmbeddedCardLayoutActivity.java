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

import uk.ac.cam.cl.kilo.nlp.ItemInfo;

/**
 * Creates a card scroll view that shows an example of using a custom embedded layout in a
 * {@code CardBuilder}.
 */
public final class EmbeddedCardLayoutActivity extends Activity {

    private CardScrollView mCardScroller;
    private ItemInfo results;
    private String EXTRA_RESULT_SERIAL = "EXTRA_RESULT_SERIAL";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            results = (ItemInfo) intent.getExtras().getSerializable(EXTRA_RESULT_SERIAL);

        } else results = null;

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new EmbeddedCardLayoutAdapter(this, createItems(), results.getTitle(), results.getAuthors().get(0)));

        setContentView(mCardScroller);
    }

    /** Creates some sample items that will be displayed on cards in the card scroll view. */
    private List<SimpleTableItem> createItems() {
        ArrayList<SimpleTableItem> items = new ArrayList<SimpleTableItem>();

        for (String d : results.getDescriptions()) {

            items.add(new SimpleTableItem(d));

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
