package shavaliev_dinar.studio_colibri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class Full extends Activity
{
    private ArrayList<String> imageAdapArr = new ArrayList<String>(Photo.instArr);


    public int getCount()
    {
        return imageAdapArr.size();
    }

    public Object getItem(int position)
    {
        return imageAdapArr.get(position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        // get intent data
        Intent intent = getIntent();

        // Selected image id
        int position = intent.getExtras().getInt("id");
        Adapter imageAdapter = new Adapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        Log.d("my", imageAdapArr.get(position).toString());
    }

}