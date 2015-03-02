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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.github.barcodeeye.migrated.HttpHelper;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.Slider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import cam.cl.kilo.NLP.Summary;

/**
 * Displays a loading screen while performing the call to our webapp
 *
 * @author groupKilo
 * @author rh572
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

    /**
     * Unpacks the barcode number and type, puts them into the lookup URL and starts the asynchronous task
     *
     * @param bundle
     */
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

        URI = URI.replace("{CODE}", code).replace("{TYPE}", type);

        new GetItemInfo().execute(URI);

        setContentView(mLoading);
    }

    /**
     * Callback from the asynchronous network task to decide which UI
     * to display next
     *
     * @param success result of the lookup
     */
    private void displayResults(Boolean success) {

        if (success) {
            startActivity(new Intent(this, ResultsCardScrollActivity.class).putExtra(EXTRA_RESULT_SERIAL, summary));
        } else {
            startActivity(new Intent(this, ScanFailureActivity.class));
        }

    }

    /**
     * An asynchronous network background task
     */
    private class GetItemInfo extends AsyncTask<String, Void, Boolean> {

        /**
         * Performs the network lookup
         *
         * @param uri the lookup uri of our webapp
         */
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

        /**
         * Decode the Base64 encoded serialised summary object
         *
         * @param s the Base64 encoded summary object
         * @return The Summary object
         * @throws IOException
         * @throws ClassNotFoundException
         */
        private Object fromString(String s) throws IOException, ClassNotFoundException {
            byte[] data = Base64.decode(s, Base64.DEFAULT);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return o;
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            getInfo(urls[0]);
            if (summary != null && summary.getText() != null && !summary.getText().isEmpty()) {
                return true;
            } else {
                return false;
            }

        }

        /**
         * Displays the results of the AsyncTask and stops the loading slider
         *
         * @param result the success of the lookup
         */
        @Override
        protected void onPostExecute(Boolean result) {
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
