package com.github.barcodeeye.scan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.github.barcodeeye.migrated.HttpHelper;
import com.github.barcodeeye.scan.ScanFailureActivity;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.Slider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import cam.cl.kilo.nlp.Summary;

/**
 * Created by ruhatch on 10/02/15.
 */
public class LoadingActivity extends Activity {

    private View mLoading;
    private Slider.Indeterminate mIndeterminate;
    private Slider mSlider;
    private String URI = "http://groupkilo.soc.srcf.net/ProjectKiloWebApp/barcode?barcodeNo={CODE}&barcodeType={TYPE}";
    private String EXTRA_RESULT_SERIAL = "EXTRA_RESULT_SERIAL";
    private String EXTRA_CODE = "EXTRA_CODE";
    private String EXTRA_TYPE = "EXTRA_TYPE";
    private Summary summary;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mLoading = new CardBuilder(this, CardBuilder.Layout.MENU).setText("Loading...").getView();
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

        if (result.equals("success")) {
            startActivity(new Intent(this, EmbeddedCardLayoutActivity.class).putExtra(EXTRA_RESULT_SERIAL, summary));
        } else {
            startActivity(new Intent(this, ScanFailureActivity.class)); //failure activity!
        }

    }

    private class GetItemInfo extends AsyncTask<String, Void, String> {

        private void getInfo(String uri) {

            try {
                CharSequence result = HttpHelper.downloadViaHttp(uri, HttpHelper.ContentType.HTML);
                summary = (Summary) fromString(result.toString());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            }

        }

        private Object fromString(String s) throws IOException, ClassNotFoundException {
            byte[] data = Base64.decode(s, Base64.DEFAULT);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return o;
        }

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            getInfo(urls[0]);
            if (summary != null && summary.getText() != null && !summary.getText().isEmpty()) {
                return "success";
            } else {
                return "failure";
            }

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
        finish();
    }

}
