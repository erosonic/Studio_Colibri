package shavaliev_dinar.studio_colibri;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> imageAdapArr;

    public Adapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return imageAdapArr != null ? imageAdapArr.size() : 0;
    }

    public void setItems(ArrayList<String> photosURLsList) {
        this.imageAdapArr = photosURLsList;
        this.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return imageAdapArr != null ? imageAdapArr.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            Picasso.with(mContext) //передаем контекст приложения
                    .load(imageAdapArr.get(position)) //адрес изображения
                    .into(imageView); //ссылка на ImageView
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }
}
