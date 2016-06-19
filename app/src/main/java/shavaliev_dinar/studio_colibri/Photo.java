package shavaliev_dinar.studio_colibri;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class Photo extends Activity
{
    final  private  String urlGetImg="https://api.instagram.com/v1/users/self/media/recent/?access_token=";
    final  private  String token="3421057307.47efa53.d6f78230ebc94013a4b98f2f30b23915";
    GridView gridview1;

    public static ArrayList<String> instArr = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);

        instArr = new ArrayList<String>();
        gridview1 = (GridView) findViewById(R.id.gridView1);
        new GetIntFoto().execute();

        gridview1.setOnItemClickListener(gridviewOnItemClickListener);
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        {
            Intent i = new Intent(getApplicationContext(),
                    Full.class);
            // передаем индекс массива
            i.putExtra("id", position);
            startActivity(i);
        }
    };

    public void onClick(View view)
    {
        Intent intent = new Intent(Photo.this, MainActivity.class);
        startActivity(intent);
    }

    private  class GetIntFoto extends AsyncTask<Void,Void,String> {
        HttpURLConnection urlConnection;
        BufferedReader reader = null;
        String resultJson = "";
        @Override
        protected String doInBackground(Void... params)
        {
            URL url = null;
            try {
                url = new URL(urlGetImg+token);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
                resultJson = buffer.toString();

            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (ProtocolException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;
            JSONArray jsonArray;
            JSONObject imageJSON;
            String Urlimg;

            try
            {
                jsonObject=new JSONObject(s);
                jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i <jsonArray.length() ; i++)
                {
                    imageJSON= jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution");
                    Urlimg=imageJSON.getString("url");
                    Log.d("my", Urlimg);
                    instArr.add(Urlimg);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            gridview1.setAdapter(new Adapter(Photo.this));

        }
    }
}