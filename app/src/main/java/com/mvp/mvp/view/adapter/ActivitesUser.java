package com.mvp.mvp.view.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.mvp.mvp.model.pojo.User;
import com.mvp.mvp.view.ContentActivities;
import com.mvp.mvp.view.HeaderActivities;
import com.mvp.mvp.view.LoadMoreRecycler;

import java.util.List;

/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public class ActivitesUser extends LoadMoreRecycler<User> {

    private List<User> users;
    private ContentActivities contentActivities;
    private HeaderActivities headerActivities;


    public ActivitesUser(RecyclerView recyclerView, List<User> dataSet,
                         OnLoadMoreListener onLoadMoreListener, GridLayoutManager grid, LinearLayoutManager linear) {
        super(recyclerView, dataSet, onLoadMoreListener, grid, linear);

        users = dataSet;

    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        contentActivities = new ContentActivities(parent.getContext());
        return contentActivities.viewHolderUser;
    }


    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        contentActivities.viewHolderUser = (ContentActivities.ViewHolderUser) genericHolder;
        contentActivities.setData(users.get(position));

        if ((users.size() - 1) == position) {
            Log.wtf("test", "test " + users.get(position).id_review);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        headerActivities = new HeaderActivities(parent.getContext());
        return headerActivities.viewHolderHeader;

    }

    @Override
    public void onBindHeaderItemView(RecyclerView.ViewHolder genericHolder, int position) {
        headerActivities.viewHolderHeader = (HeaderActivities.ViewHolderHeader) genericHolder;
        headerActivities.setData("coba headerku");
    }
}
