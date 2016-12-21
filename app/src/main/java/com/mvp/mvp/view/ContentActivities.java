package com.mvp.mvp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvp.mvp.MainActivity;
import com.mvp.mvp.R;
import com.mvp.mvp.coolapsing.ScrollingActivity;
import com.mvp.mvp.model.pojo.User;


/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public class ContentActivities extends FrameLayout {

    public ViewHolderUser viewHolderUser;
    private Context context;

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
        this.context = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_activities, this);
        viewHolderUser = new ViewHolderUser(this);
    }

    public void setData(final User user) {
        viewHolderUser.username.setText(user.username_user);
        viewHolderUser.fullname.setText(user.fullname_user);
        viewHolderUser.activities.setText(user.activity);

        if (user.is_checked) {
            viewHolderUser.img.setImageResource(R.mipmap.user);
        } else {
            viewHolderUser.img.setImageResource(R.mipmap.ic_launcher);
        }

        viewHolderUser.linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.is_checked) {
                    user.is_checked = false;
                    viewHolderUser.img.setImageResource(R.mipmap.ic_launcher);
                } else {
                    viewHolderUser.img.setImageResource(R.mipmap.user);
                    user.is_checked = true;
                }
                if (MainActivity.mainActivity != null) {
                    MainActivity.mainActivity.adapterUser.notifyDataSetChanged();
                } else if (ScrollingActivity.mainActivity != null) {
                    ScrollingActivity.mainActivity.adapterUser.notifyDataSetChanged();
                }
            }
        });
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder {

        TextView username, activities, fullname;
        LinearLayout linearLayout;
        ImageView img;

        ViewHolderUser(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            activities = (TextView) itemView.findViewById(R.id.activities);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_content_activities);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
