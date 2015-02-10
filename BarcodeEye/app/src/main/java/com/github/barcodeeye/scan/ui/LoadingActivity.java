package com.github.barcodeeye.scan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.github.barcodeeye.migrated.HttpHelper;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.Slider;

import java.io.IOException;

/**
 * Created by ruhatch on 10/02/15.
 */
public class LoadingActivity extends Activity {

    private View mLoading;
    private Slider.Indeterminate mIndeterminate;
    private Slider mSlider;
    private String URI = "http://groupkilo.soc.srcf.net/ProjectKiloWebApp/test?barcodeNo={CODE}&barcodeType={TYPE}";
    private String EXTRA_RESULT = "EXTRA_RESULT";
    private String EXTRA_CODE = "EXTRA_CODE";
    private String EXTRA_TYPE = "EXTRA_TYPE";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mLoading = new CardBuilder(this, CardBuilder.Layout.ALERT).setText("Loading...").getView();
        mSlider = Slider.from(mLoading);

        Intent intent = getIntent();
        String code = "";
        String type = "";

        if (intent != null && intent.getExtras() != null) {

            code = intent.getExtras().getString(EXTRA_CODE, "");
            type = intent.getExtras().getString(EXTRA_TYPE, "");

        }

        System.out.println(code + " " + type);

        URI = URI.replace("{CODE}", code).replace("{TYPE}", type);

        new GetItemInfo().execute(URI);

        setContentView(mLoading);
    }

    private void displayResults(String result) {

        startActivity(new Intent(this, EmbeddedCardLayoutActivity.class).putExtra(EXTRA_RESULT, result));

    }

    private class GetItemInfo extends AsyncTask<String, Void, String> {

        private String getInfo(String uri) {

            CharSequence result = null;

            try {
                result = HttpHelper.downloadViaHttp(uri, HttpHelper.ContentType.HTML);
            } catch (IOException ioe) {

            }
            return result.toString();

        }

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            return getInfo(urls[0]);

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            mIndeterminate.hide();
            displayResults(result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIndeterminate = mSlider.startIndeterminate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIndeterminate.hide();
    }

}
