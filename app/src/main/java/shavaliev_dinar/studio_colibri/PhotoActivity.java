package shavaliev_dinar.studio_colibri;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

public class PhotoActivity extends Activity implements DownloadPhotoCallback {
    final private String urlGetImg = "https://api.instagram.com/v1/users/self/media/recent/?access_token=";
    final private String token = "3421057307.47efa53.d6f78230ebc94013a4b98f2f30b23915";
    GridView gridview1;
    ProgressView downloadProgress;
    TextView downloadError;
    Adapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        photosAdapter = new Adapter(this);
        gridview1 = (GridView) findViewById(R.id.gridView1);
        downloadProgress = (ProgressView) findViewById(R.id.download_progress);
        downloadError = (TextView) findViewById(R.id.error_text);
        gridview1.setAdapter(photosAdapter);
        gridview1.setOnItemClickListener(gridviewOnItemClickListener);
        new DownloadPhotoAsyncTask(urlGetImg + token, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void photosDownloaded(@NonNull ArrayList<PhotoObject> photosURLsList) {
        downloadError.setVisibility(View.GONE);
        downloadProgress.setVisibility(View.GONE);
        gridview1.setVisibility(View.VISIBLE);
        photosAdapter.setItems(photosURLsList);
    }

    @Override
    public void photosAreLoading() {
        downloadError.setVisibility(View.GONE);
        downloadProgress.setVisibility(View.VISIBLE);
        gridview1.setVisibility(View.GONE);
    }

    @Override
    public void photosLoadingError() {
        downloadError.setVisibility(View.VISIBLE);
        downloadProgress.setVisibility(View.GONE);
        gridview1.setVisibility(View.GONE);
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Intent i = new Intent(getApplicationContext(),
                    Full.class);
            PhotoObject item = (PhotoObject) parent.getItemAtPosition(position);
            // передаем индекс массива
            i.putExtra(Full.SELECTED_PHOTO_URL_KEY, item.getPhotoURL());
            startActivity(i);
        }
    };

    public void onClick(View view) {
        Intent intent = new Intent(PhotoActivity.this, MainActivity.class);
        startActivity(intent);
    }
}