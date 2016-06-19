package shavaliev_dinar.studio_colibri;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter extends BaseAdapter
{

    private Context mContext;
    private ArrayList<String> imageAdapArr = new ArrayList<String>(Photo.instArr);


    public Context getmContext()
    {
        return mContext;
    }

    public Adapter(Context c)
    {
        mContext = c;
    }
    @Override
    public int getCount()
    {
        return imageAdapArr.size();
    }
    @Override
    public Object getItem(int position)
    {
        return imageAdapArr.get(position);
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        Log.d("my", "getView");
        if (convertView == null)
        {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(120, 110));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext) //передаем контекст приложения
               .load(imageAdapArr.get(position)) //адрес изображения
               .networkPolicy(NetworkPolicy.NO_CACHE)
               .memoryPolicy(MemoryPolicy.NO_CACHE)
               .into(imageView); //ссылка на ImageView


        Log.d("my", imageAdapArr.get(position).toString());
        Log.d("my", "Picasso");
        Log.d("my", position + "");

        return imageView;
    }
}
