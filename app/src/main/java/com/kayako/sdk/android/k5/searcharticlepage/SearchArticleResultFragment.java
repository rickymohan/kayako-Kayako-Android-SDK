package com.kayako.sdk.android.k5.searcharticlepage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.kayako.sdk.android.k5.common.adapter.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleResultFragment extends BaseListFragment implements SearchArticleContract.View, SearchCallback, ListItemRecyclerViewAdapter.OnItemClickListener, EndlessRecyclerViewScrollAdapter.OnLoadMoreListener {

    private SearchArticleContract.Presenter mPresenter;
    private BackgroundTask mSearchTask;
    private BackgroundTask mLoadMoreTask;

    public static SearchArticleResultFragment newInstance() {
        return new SearchArticleResultFragment();
    }

    public SearchArticleResultFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = SearchArticleFactory.getPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelBackgroundTasks();
        mSearchTask = null;
        mLoadMoreTask = null;
    }

    @Override
    protected void reloadPage() {
        mPresenter.reloadPage();
    }

    @Override
    public void showOnlyListView() {
        super.showListViewAndHideOthers();
    }

    @Override
    public void showOnlyEmptyView() {
        super.showEmptyViewAndHideOthers();
    }

    @Override
    public void showOnlyErrorView() {
        super.showErrorViewAndHideOthers();
    }

    @Override
    public void showOnlyLoadingView() {
        super.showLoadingViewAndHideOthers();
    }

    @Override
    public void showBlankView() {
        super.hideEmptyView();
        super.hideLoadingView();
        super.hideErrorView();
        super.hideListView();
    }


    @Override
    public void startSearchTask() {
        // Cancel any previous search task and start a new one
        if (mSearchTask != null) {
            mSearchTask.cancelTask();
        }
        mSearchTask = new BackgroundTask(getActivity()) {
            @Override
            protected boolean performInBackground() {
                return mPresenter.loadDataInBackground();
            }

            @Override
            protected void performOnCompletion(boolean isSuccessful) {
                mPresenter.onDataLoaded(isSuccessful);
            }
        };

        mSearchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void startLoadMoreTask() {
        mLoadMoreTask = new BackgroundTask(getActivity()) {
            @Override
            protected boolean performInBackground() {
                return mPresenter.loadMoreDataInBackground();
            }

            @Override
            protected void performOnCompletion(boolean isSuccessful) {
                mPresenter.onMoreDataLoaded(isSuccessful);
            }
        };

        mLoadMoreTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public void cancelBackgroundTasks() {
        if (mSearchTask != null) {
            mSearchTask.cancelTask();
        }
        if (mLoadMoreTask != null) {
            mLoadMoreTask.cancelTask();
        }
    }

    @Override
    public void setUpList(List<ListItem> items) {
        super.initList(new ListItemRecyclerViewAdapter(items, this), this);
    }

    @Override
    public void addItemsToList(List<ListItem> items) {
        super.addToList(items);
    }

    @Override
    public void showLoadingMoreItemsProgress() {
        super.showLoadMoreProgress();
    }

    @Override
    public void hideLoadingMoreItemsProgress() {
        super.hideLoadMoreProgress();
    }

    @Override
    public void setListHasMoreItems(boolean hasMoreItems) {
        super.setHasMoreItems(hasMoreItems);
    }

    @Override
    public void onItemClick(ListItem listItem) {
        mPresenter.onClickListItem(listItem);
    }

    @Override
    public void loadMoreItems() {
        mPresenter.loadMoreData();
    }

    @Override
    public void showSearchResults(String query) {
        mPresenter.searchArticles(query);
    }
}