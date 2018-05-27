package org.cssac.karyovirtualassistantforautistickids.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.cssac.karyovirtualassistantforautistickids.R;

/**
 * Created by prrateekk on 1/5/18.
 */

public class BubbleAdapter extends BaseAdapter {

    private final Context mContext;

    public BubbleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 55;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int idx, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.bubble_layout, null);
        }
        final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.imageview_favorite);

        int dr = mContext.getResources().getIdentifier("drawable/bubble_small", null, mContext.getPackageName());
        Drawable drawable = mContext.getResources().getDrawable(dr);
        imageViewFavorite.setImageDrawable(drawable);

        return convertView;
    }
}
