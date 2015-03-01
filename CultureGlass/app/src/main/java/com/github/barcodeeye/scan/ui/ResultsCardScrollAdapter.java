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

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;

import java.util.List;

/**
 * Populates views in a {@code CardScrollView} with cards built from custom embedded layouts to
 * represent items in a simple table.
 */
public class ResultsCardScrollAdapter extends CardScrollAdapter {

    /**
     * The maximum number of items that fit on a card.
     */
    private static final int ITEMS_PER_CARD = 1;

    /**
     * Index of the {@link android.widget.TextView} containing the primary text in a table row.
     */
    private static final int PRIMARY_TEXT_VIEW_INDEX = 0;

    private final Context mContext;
    private final List<String> mItems;
    private final String mTitle;
    private final String mAuthor;

    /**
     * Initializes a new adapter with the specified context and list of items.
     */
    public ResultsCardScrollAdapter(Context context, List<String> items, String title, String author) {
        mContext = context;
        mItems = items;
        mTitle = title;
        mAuthor = author;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        // Compute the number of cards needed to display the items with 4 per card (rounding up to
        // capture the remainder).
        return (int) Math.ceil((double) mItems.size() / ITEMS_PER_CARD);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getPosition(Object item) {
        return AdapterView.INVALID_POSITION;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardBuilder card;
        if (position == 0) {
            card = new CardBuilder(mContext, CardBuilder.Layout.MENU)
                    .setText(mTitle)
                    .setFootnote("by " + mAuthor);
        } else if (mItems.get(position - 1).equals("BEGIN_REVIEWS")) {
            card = new CardBuilder(mContext, CardBuilder.Layout.MENU)
                    .setText("REVIEWS");
        } else {
            card = new CardBuilder(mContext, CardBuilder.Layout.TEXT)
                    .setText(mItems.get(position - 1))
                    .setFootnote(mTitle)
                    .setTimestamp(mAuthor);
        }
        View view = card.getView(convertView, parent);

        // Get a reference to an embedded view from the custom layout and then manipulate it.
        //ViewGroup tableView = (ViewGroup) view.findViewById(R.id.simple_table);
       // populateTableRows(position, tableView);

        return view;
    }

    /**
     * Populates all of the rows in the card at the specified position.
     */
    /*private void populateTableRows(int position, ViewGroup tableView) {
        int startItemIndex = position * ITEMS_PER_CARD;
        int endItemIndex = Math.min(startItemIndex + ITEMS_PER_CARD, mItems.size());

        for (int i = 0; i < ITEMS_PER_CARD; i++) {
            int itemIndex = startItemIndex + i;
            ViewGroup rowView = (ViewGroup) tableView.getChildAt(i);

            // The layout contains four fixed rows, so we need to hide the later ones if there are
            // not four items on this card. We need to make sure to update the visibility in both
            // cases though if the card has been recycled.
            if (itemIndex < endItemIndex) {
                SimpleTableItem item = mItems.get(itemIndex);
                populateTableRow(item, rowView);
                rowView.setVisibility(View.VISIBLE);
            } else {
                rowView.setVisibility(View.INVISIBLE);
            }
        }
    }*/

    /**
     * Populates a row in the table with the specified item data.
     */
    private void populateTableRow(SimpleTableItem item, ViewGroup rowView) {
        TextView primaryTextView = (TextView) rowView.getChildAt(PRIMARY_TEXT_VIEW_INDEX);
        primaryTextView.setText(item.primaryText);
    }
}
