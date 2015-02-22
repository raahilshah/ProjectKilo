package com.github.barcodeeye.scan;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.Slider;

/**
 * Created by ruhatch on 11/02/15.
 */
public class ScanFailureActivity extends Activity {

    private Slider.GracePeriod mGracePeriod;
    private View mScanFailed;
    private Slider mSlider;

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
