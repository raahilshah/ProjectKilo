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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.widget.Slider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cam.cl.kilo.NLP.Summary;

/**
 * Creates a card scroll view that displays the summarised information about a product
 * It automatically scrolls through the cards it displays
 *
 * @author groupKilo
 * @author rh572
 */
public final class ResultsCardScrollActivity extends Activity {

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
        mCardScroller.setAdapter(new ResultsCardScrollAdapter(
                this, results.getText(), results.getTitle(), results.getAuthors()));

        mCardScroller.setHorizontalScrollBarEnabled(false);

        MAX_SLIDER_VALUE = mCardScroller.getAdapter().getCount() - 1;

        mSlider = Slider.from(mCardScroller);

        startAnimation();

        setContentView(mCardScroller);
    }

    /**
     * Animates the scrolling between cards
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void startAnimation() {

        //This is to break the loop
        if (!this.isDestroyed()) {

            mDeterminate = mSlider.startDeterminate(MAX_SLIDER_VALUE, 0);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mDeterminate,
                    "position", 0, MAX_SLIDER_VALUE);

            // Hide the slider when the animation stops.
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mDeterminate.hide();
                    mDeterminate = null;
                    if (mCardScroller.getSelectedItemPosition() == MAX_SLIDER_VALUE) {
                        mCardScroller.setSelection(0);
                    } else {
                        issueKey(22);
                    }
                    new AnimationTask().execute("900");
                }
            });

            // Start an animation showing the different positions of the slider.
            animator.setDuration(ANIMATION_DURATION_MILLIS).start();
        }
    }

    /**
     * Asynchronous task that simply sleeps and then performs the animation
     * This is mostly for visual smoothness
     */
    private class AnimationTask extends AsyncTask<String, Void, Void> {

        protected void onPostExecute(Void result) {
            startAnimation();
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                Thread.sleep(Long.parseLong(params[0]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Simulates touch gestures to allow us to use the natural scroll animation
     *
     * @param keyCode the keycode of the gesture to be simulated
     */
    private void issueKey(int keyCode) {
        try {
            Runtime.getRuntime().exec("input keyevent " + String.valueOf(keyCode) + "\n");
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
        this.finish();
        super.onPause();
    }


}
