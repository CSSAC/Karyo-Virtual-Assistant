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
import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;

/**
 * Created by prrateekk on 26/4/18.
 */

public class AnalyticsTagsAdapter extends BaseAdapter{

    private final Context mContext;
    private final String[] tags;

    UserInformation userInformation;

    @Override
    public int getCount() {
        return tags.length;
    }

    public AnalyticsTagsAdapter(Context mContext, UserInformation userInformation) {
        this.mContext = mContext;
        this.userInformation = userInformation;
        this.tags = Tags.TAGS;
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
            convertView = layoutInflater.inflate(R.layout.analytics_tag_layout, null);
        }

        final TextView nameTextView = (TextView)convertView.findViewById(R.id.tag_title);
        final TextView levelView = (TextView)convertView.findViewById(R.id.tag_level);
        final TextView attemptsView = (TextView)convertView.findViewById(R.id.tag_correct_attempts);
        final TextView accuracyView = (TextView)convertView.findViewById(R.id.tag_accuracy);
        final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.tag_icon);

        int dr = mContext.getResources().getIdentifier("drawable/" + tags[idx] + "_tag", null, mContext.getPackageName());
        Drawable drawable = mContext.getResources().getDrawable(dr);
        imageViewFavorite.setImageDrawable(drawable);

        nameTextView.setText(tags[idx]);

        String levelText = String.format("%d out of %d", userInformation.level.get(tags[idx]), userInformation.maxLevel.get(tags[idx]));
        String attemptsText = String.format("%d out of %d", userInformation.correctAttempts.get(tags[idx]), userInformation.attempts.get(tags[idx]));

        levelView.setText(levelText);
        attemptsView.setText(attemptsText);
        int accuracy = userInformation.attempts.get(tags[idx])==0?0:(userInformation.correctAttempts.get(tags[idx])*100)/userInformation.attempts.get(tags[idx]);
        accuracyView.setText(String.format("%d%", accuracy));

        return convertView;
    }
}