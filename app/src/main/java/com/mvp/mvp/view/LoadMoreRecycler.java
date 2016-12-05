package com.mvp.mvp.view;

/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mvp.mvp.R;

import java.util.List;

/**
 * Created by mSobhy on 7/22/15 from Gist https://gist.github.com/mSobhy90/cf7fa98803a0d7716a4a#file-abstractrecyclerviewfooteradapter-java
 */
public abstract class LoadMoreRecycler<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VISIBLE_THRESHOLD = 1;

    private final int ITEM_VIEW_TYPE_BASIC = 1;
    private final int ITEM_VIEW_TYPE_FOOTER = 2;

    private List<T> dataSet;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean loading = true;
    private boolean enableLoadMore = true;
    public GridLayoutManager gridLayoutManager;
    public LinearLayoutManager linearLayoutManager;

    public LoadMoreRecycler(final RecyclerView recyclerView, List<T> dataSet,
                            final OnLoadMoreListener onLoadMoreListener,
                            final GridLayoutManager grid, LinearLayoutManager linear) {

        this.dataSet = dataSet;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager && linear != null) {

            linearLayoutManager = linear;

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recycler, int dx, int dy) {
                    if (!enableLoadMore) return;

                    totalItemCount = linearLayoutManager.getItemCount();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                        addItem(null);
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = false;
                    }
                }
            });
        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager && grid != null) {

            gridLayoutManager = grid;

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {/** ## basically getSpanSize return default 1 (selain 1 baklan crash) if item ## **/
                    if (getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER) {
                        return ITEM_VIEW_TYPE_FOOTER;
                    } else {
                        return ITEM_VIEW_TYPE_BASIC;
                    }

                }
            });

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recycler, int dx, int dy) {
                    if (!enableLoadMore) return;

                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                    boolean isLoad = (visibleItemCount + firstVisibleItem) == totalItemCount;

                    if (loading && isLoad) {
                        addItem(null);
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = false;
                    }
                }
            });
        }
    }

    public int getFirstVisibleItem() {
        return firstVisibleItem;
    }

    public void resetItems(@NonNull List<T> newDataSet) {
        loading = true;
        firstVisibleItem = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
        previousTotal = 0;

        dataSet.clear();
        addItems(newDataSet);
    }


    public void stopLoading() {
        if (!loading) {
            removeItem(null);
            loading = true;
        }
    }

    public void setEnableLoadMore(boolean enable) {
        if (enable) {
            loading = true;
            enableLoadMore = true;
        } else {
            enableLoadMore = false;
        }
    }

    public void addItems(@NonNull List<T> newDataSetItems) {
        dataSet.addAll(newDataSetItems);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (!dataSet.contains(item)) {
            dataSet.add(item);
            notifyItemInserted(dataSet.size() - 1);
        }
    }


    public void removeItem(T item) {
        int indexOfItem = dataSet.indexOf(item);
        if (indexOfItem != -1) {
            this.dataSet.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    public T getItem(int index) {
        if (dataSet != null && dataSet.get(index) != null) {
            return dataSet.get(index);
        } else {
            throw new IllegalArgumentException("Item with index " + index + " doesn't exist, dataSet is " + dataSet);
        }
    }

    public List<T> getDataSet() {
        return dataSet;
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            return onCreateBasicItemViewHolder(parent, viewType);
        } else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        } else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_BASIC) {
            onBindBasicItemView(genericHolder, position);
        } else {
            onBindFooterView(genericHolder, position);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position);

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        //noinspection ConstantConditions
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_more_footer, parent, false);
        return new ProgressViewHolder(v);
    }

    public void onBindFooterView(RecyclerView.ViewHolder genericHolder, int position) {
        ((ProgressViewHolder) genericHolder).progressBar.setIndeterminate(true);
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.load_more_progressBar);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


}