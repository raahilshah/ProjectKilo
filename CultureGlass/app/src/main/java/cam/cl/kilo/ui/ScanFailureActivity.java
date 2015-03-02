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

package cam.cl.kilo.ui;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.Slider;

/**
 * Alerts the user that the scan has failed and then returns to the scanning activity (by killing itself)
 *
 * @author groupKilo
 * @author rh572
 */
public class ScanFailureActivity extends Activity {

    private Slider.GracePeriod mGracePeriod;
    private View mScanFailed;
    private Slider mSlider;

    /**
     * The listener for the GracePeriod Slider that handles its completion
     */
    private final Slider.GracePeriod.Listener mGracePeriodListener =
            new Slider.GracePeriod.Listener() {

                @Override
                public void onGracePeriodEnd() {
                    // Play a SUCCESS sound to indicate the end of the grace period.
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.playSoundEffect(Sounds.SUCCESS);
                    mGracePeriod = null;
                    finish();
                }

                @Override
                public void onGracePeriodCancel() {
                    // Play a DIMISS sound to indicate the cancellation of the grace period.
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.playSoundEffect(Sounds.DISMISSED);
                    mGracePeriod = null;
                    finish();
                }
            };

    /**
     * Sets up the View and initialises the Slider
     *
     * @param bundle
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Create the cards for the view
        mScanFailed = new CardBuilder(this, CardBuilder.Layout.MENU).setText("Scan Failed").setFootnote("Returning to Scanner").getView();
        mSlider = Slider.from(mScanFailed);

        setContentView(mScanFailed);
    }

    @Override
    public void onBackPressed() {
        // If the Grace Period is running,
        // cancel it instead of finishing the Activity.
        if (mGracePeriod != null) {
            mGracePeriod.cancel();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Starts the GracePeriod Slider
     */
    @Override
    protected void onResume() {
        super.onResume();
        mGracePeriod = mSlider.startGracePeriod(mGracePeriodListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
