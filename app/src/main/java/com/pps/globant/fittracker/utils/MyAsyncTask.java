package com.pps.globant.fittracker.utils;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.pps.globant.fittracker.mvp.model.InstagramLoginModel;
import com.squareup.otto.Bus;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.pps.globant.fittracker.utils.Constants.CLIENT_ID;
import static com.pps.globant.fittracker.utils.Constants.CLIENT_SECRET;
import static com.pps.globant.fittracker.utils.Constants.REDIRECT_URI;
import static com.pps.globant.fittracker.utils.Constants.SP_CODE;
import static com.pps.globant.fittracker.utils.Constants.SP_NAME;
import static com.pps.globant.fittracker.utils.Constants.SP_TOKEN;
import static com.pps.globant.fittracker.utils.Constants.USER_ID;

public class MyAsyncTask extends AsyncTask<URL, Void, Void> {
    private String code;
    private String accessTokenString;
    private String fullName;
    private String id;
    public SharedPreferences spUser;
    public SharedPreferences.Editor spEdit;
    public Bus bus;

    public MyAsyncTask(String code, Bus bus, SharedPreferences spUser) {
        this.code = code;
        this.bus = bus;
        this.spUser = spUser;
    }

    protected Void doInBackground(URL... urls) {
        try {
            URL url = new URL(InstagramLoginModel.TOKEN_URL_FULL);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
            outputStreamWriter.write("client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET +
                    "&grant_type=authorization_code" +
                    "&redirect_uri=" + REDIRECT_URI +
                    "&code=" + code);

            outputStreamWriter.flush();
            String response = StreamToString.streamToString(httpsURLConnection.getInputStream());
            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
            accessTokenString = jsonObject.getString("access_token");
            fullName = jsonObject.getJSONObject("user").getString("full_name");
            id = jsonObject.getJSONObject("user").getString("id");
            spEdit = spUser.edit();
            spEdit.putString(SP_TOKEN, accessTokenString);
            spEdit.putString(SP_NAME, fullName);
            spEdit.putString(USER_ID,id);
            spEdit.putString(SP_CODE, code);
            spEdit.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        bus.post(new PostReadyInformation(fullName,id));
    }

    public static class PostReadyInformation {
        public final String name;
        public final String id;

        public PostReadyInformation(String name,String id) {
            this.name = name;
            this.id = id;
        }
    }


}
