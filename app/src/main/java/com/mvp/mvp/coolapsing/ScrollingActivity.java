package com.mvp.mvp.coolapsing;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;

import com.mvp.mvp.R;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mvp.mvp.helper.Utils;
import com.mvp.mvp.model.api.RequestInterface;
import com.mvp.mvp.model.api.RestClient;
import com.mvp.mvp.model.pojo.Rintik;
import com.mvp.mvp.model.pojo.ShowHeaderUser;
import com.mvp.mvp.model.pojo.User;
import com.mvp.mvp.view.LoadMoreRecycler;
import com.mvp.mvp.view.adapter.ActivitesUser;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ScrollingActivity extends AppCompatActivity implements LoadMoreRecycler.OnLoadMoreListener {

    public ActivitesUser adapterUser;
    private List<User> datausers = new ArrayList<>();
    private int batas = 0;
    public static ScrollingActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mainActivity = this;

        setContentView(R.layout.activity_scrolling2);

        CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar_layout.setTitleEnabled(true);
        toolbar_layout.setTitle("Title");

        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScrollingActivity.this, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ScrollingActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        if (recycler_view != null) {
            recycler_view.setLayoutManager(gridLayoutManager);
            //recycler_view.setLayoutManager(layoutManager);
        }

        datausers.add(new ShowHeaderUser());

        adapterUser = new ActivitesUser(recycler_view, datausers, this, gridLayoutManager, null);
        //adapterUser = new ActivitesUser(recycler_view, datausers, this, null, layoutManager);

        if (recycler_view != null) {
            recycler_view.setAdapter(adapterUser);

        }


        getDataAct("0");

    }

    private void getDataAct(final String pagination) {
        final RequestInterface request = RestClient.createService(RequestInterface.class);
        request.rintik()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Rintik>() {
                    @Override
                    public void onCompleted() {
                        Log.wtf("onCompleted", "=> ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("onError", "=> " + e.getMessage());
                    }

                    @Override
                    public void onNext(Rintik rintik) {
                        String key = Utils.md5("u_act" + rintik.waktunya);
                        request.getUserActivities(key, rintik.waktunya, "0", pagination)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<List<User>>() {
                                    @Override
                                    public void onCompleted() {
                                        adapterUser.stopLoading();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(List<User> user) {

                                        /* konversi dari pojo to json string */
                                        Gson gson = new GsonBuilder().create();
                                        String json = gson.toJson(user);
                                        Utils.LogNp("data", "=> " + json);

                                        if (user.size() < 15) {
                                            adapterUser.setEnableLoadMore(false);
                                        }
                                        adapterUser.addItems(user);
                                    }
                                });
                    }
                });
    }

    @Override
    public void onLoadMore() {
        Log.wtf("load more", "on the way");
        batas = batas + 15;
        getDataAct(batas + "");
    }
}

