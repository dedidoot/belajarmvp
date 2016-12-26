package com.mvp.mvp.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mvp.mvp.R;


/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public class HeaderActivities extends FrameLayout {

    public ViewHolderHeader viewHolderHeader;

    public HeaderActivities(Context context) {
        super(context);
        init(context);
    }

    public HeaderActivities(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeaderActivities(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.header_activities, this);
        viewHolderHeader = new ViewHolderHeader(this);
    }

    public void setData(String a) {
        viewHolderHeader.username.setText(a);
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView username;

        ViewHolderHeader(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.name_header);
        }
    }
}
