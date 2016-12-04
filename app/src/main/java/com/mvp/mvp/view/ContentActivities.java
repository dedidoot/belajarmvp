package com.mvp.mvp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mvp.mvp.R;
import com.mvp.mvp.model.pojo.User;


/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public class ContentActivities extends FrameLayout {

    public ViewHolderUser viewHolderUser;

    public ContentActivities(Context context) {
        super(context);
        init(context);
    }

    public ContentActivities(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContentActivities(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_activities, this);
        viewHolderUser = new ViewHolderUser(this);
    }

    public void setData(User user) {
        viewHolderUser.username.setText(user.username_user);
        viewHolderUser.fullname.setText(user.fullname_user);
        viewHolderUser.activities.setText(user.activity);
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder {

        TextView username, activities, fullname;

        ViewHolderUser(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            activities = (TextView) itemView.findViewById(R.id.activities);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
        }
    }
}
