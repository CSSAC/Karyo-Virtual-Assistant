package org.cssac.karyovirtualassistantforautistickids.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.cssac.karyovirtualassistantforautistickids.R;
import org.cssac.karyovirtualassistantforautistickids.constants.Tags;

/**
 * Created by prrateekk on 17/4/18.
 */

public class TagsAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] tags;

    public TagsAdapter(Context mContext, String[] tags) {
        this.mContext = mContext;
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.length;
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
            convertView = layoutInflater.inflate(R.layout.tag_layout, null);
        }

        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);
        final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_book_author);
        final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.imageview_favorite);

        int dr = mContext.getResources().getIdentifier("drawable/" + Tags.TAGS[idx], null, mContext.getPackageName());
        Drawable drawable = mContext.getResources().getDrawable(dr);
        imageViewFavorite.setImageDrawable(drawable);

        nameTextView.setText(Tags.TAGS[idx]);
        authorTextView.setText("Book Author");

        return convertView;
    }
}
