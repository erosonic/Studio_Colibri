package shavaliev_dinar.studio_colibri;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadPhotoAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {
    HttpURLConnection urlConnection;
    BufferedReader reader = null;
    String resultJson = "";
    String requestURI = "";
    DownloadPhotoCallback downloadPhotoListener;

    public DownloadPhotoAsyncTask(String requestURI, DownloadPhotoCallback listener) {
        this.requestURI = requestURI;
        this.downloadPhotoListener = listener;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        URL url;
        ArrayList<String> photosURLS = new ArrayList<>();
        try {
            url = new URL(requestURI);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultJson = buffer.toString();

            JSONObject jsonObject;
            JSONArray jsonArray;
            JSONObject imageJSON;
            String Urlimg;

            try {
                jsonObject = new JSONObject(resultJson);
                jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    imageJSON = jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution");
                    Urlimg = imageJSON.getString("url");
                    Log.d("my", Urlimg);
                    photosURLS.add(Urlimg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photosURLS;
    }

    @Override
    protected void onPostExecute(ArrayList<String> request) {
        downloadPhotoListener.photosDownloaded(request);
    }

}
