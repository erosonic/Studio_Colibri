package shavaliev_dinar.studio_colibri;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Full extends Activity {

    public static String SELECTED_PHOTO_URL_KEY = "photo_url";

    private String selectedPhotoURL = "";

    private ImageView fullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        selectedPhotoURL = getIntent().getExtras().getString(SELECTED_PHOTO_URL_KEY);
        fullImage = (ImageView) findViewById(R.id.full_image_view);
        Picasso.with(this).load(selectedPhotoURL).into(fullImage);
    }

}