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


package com.github.barcodeeye.scan.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.widget.Slider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cam.cl.kilo.NLP.Summary;

/**
 * Creates a card scroll view that shows an example of using a custom embedded layout in a
 * {@code CardBuilder}.
 */
public final class EmbeddedCardLayoutActivity extends Activity {

    private CardScrollView mCardScroller;
    private Summary results;
    private String EXTRA_RESULT_SERIAL = "EXTRA_RESULT_SERIAL";
    private int MAX_SLIDER_VALUE;
    private static final long ANIMATION_DURATION_MILLIS = 5000;
    private Slider mSlider;
    private Slider.Determinate mDeterminate;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            results = (Summary) intent.getExtras().getSerializable(EXTRA_RESULT_SERIAL);

        } else results = null;

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new EmbeddedCardLayoutAdapter(
                this, createItems(), results.getTitle(), results.getAuthors()));

        mCardScroller.setHorizontalScrollBarEnabled(false);

        MAX_SLIDER_VALUE = mCardScroller.getAdapter().getCount() - 1;

        mSlider = Slider.from(mCardScroller);

        startAnimation();

        setContentView(mCardScroller);
    }

    private void startAnimation() {
        mDeterminate = mSlider.startDeterminate(MAX_SLIDER_VALUE, 0);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mDeterminate,
                "position", 0,MAX_SLIDER_VALUE);

        // Hide the slider when the animation stops.
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mDeterminate.hide();
                if (mCardScroller.getSelectedItemPosition() == MAX_SLIDER_VALUE) {
                    mCardScroller.setSelection(0);
                } else {
                    issueKey(22);
                }
                startAnimation();
            }
        });

        // Start an animation showing the different positions of the slider.
        animator.setDuration(ANIMATION_DURATION_MILLIS).start();
    }

    /**
     * Creates some sample items that will be displayed on cards in the card scroll view.
     */
    private List<String> createItems() {
        ArrayList<String> items = new ArrayList<String>();

        for (String s: results.getText()) {
            items.add(s);
        }

        return items;
    }

    private void processSliderRequest(int position) {
        Slider.Scroller scroller = mSlider.startScroller(mCardScroller.getChildCount(), 0);

        // Animate the slider to the next position. The slider
        // automatically hides after the duration has elapsed
        ObjectAnimator.ofFloat(scroller, "position", 0, position)
                .setDuration(ANIMATION_DURATION_MILLIS)
                .start();
    }

    private void issueKey(int keyCode)
    {
        try {
            Process p = Runtime.getRuntime().exec("input keyevent " + String.valueOf(keyCode) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
