/*
 * Copyright (C) 2014 The Android Open Source Project
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

package cam.cl.kilo.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;

import java.util.List;

/**
 * Populates views in a {@code CardScrollView} with cards built from summarised sentences
 *
 * @author groupKilo
 * @author rh572
 */
public class ResultsCardScrollAdapter extends CardScrollAdapter {

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
        return mItems.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getPosition(Object item) {
        return AdapterView.INVALID_POSITION;
    }

    /**
     * Returns the view for the relevant position
     * If it is 0 then it is the title card
     * Otherwise it is one of the results
     * If the string for the results is our constant BEGIN_REVIEWS
     * then display the REVIEWS title card
     *
     * @param position position of the View to return in the CardScrollView
     * @param convertView
     * @param parent
     * @return the View to display
     */
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

        return view;
    }
}
