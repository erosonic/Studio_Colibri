package shavaliev_dinar.studio_colibri;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Adapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PhotoObject> adapterItems;
    private LayoutInflater inflater;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;

    public Adapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(c);
        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    public int getCount() {
        return adapterItems != null ? adapterItems.size() : 0;
    }

    public void setItems(ArrayList<PhotoObject> photosURLsList) {
        this.adapterItems = photosURLsList;
        this.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return adapterItems != null ? adapterItems.get(position) : null;
    }

    @NonNull
    private String getTimeFromMilliseconds(String millisecs) {
        String res = "";
        Date photoDate = new Date(Long.valueOf(millisecs));
        res = mDateFormat.format(photoDate);
        return res;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.photo_grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.setPhotoImage((ImageView) convertView.findViewById(R.id.grid_item_photo));
            viewHolder.setDateText((TextView) convertView.findViewById(R.id.grid_item_date));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.getDateText().setText(
                getTimeFromMilliseconds(adapterItems.get(position).getCreationDate()));

        Glide.with(mContext)
                .load(adapterItems.get(position).getPhotoURL())
                .fitCenter()
                .into(viewHolder.getPhotoImage());

        return convertView;
    }

    private static class ViewHolder {
        private ImageView photoImage;
        private TextView dateText;

        public ViewHolder(ImageView photoImage, TextView dateText) {
            this.photoImage = photoImage;
            this.dateText = dateText;
        }

        public ViewHolder() {
        }

        public ImageView getPhotoImage() {
            return photoImage;
        }

        public void setPhotoImage(ImageView photoImage) {
            this.photoImage = photoImage;
        }

        public TextView getDateText() {
            return dateText;
        }

        public void setDateText(TextView dateText) {
            this.dateText = dateText;
        }
    }
}
