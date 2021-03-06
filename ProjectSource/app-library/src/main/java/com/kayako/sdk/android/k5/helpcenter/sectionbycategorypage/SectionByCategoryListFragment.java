package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.android.k5.common.adapter.searchsectionlist.SearchSectionAdapter;
import com.kayako.sdk.android.k5.common.fragments.ActivityNavigationResourceCallback;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.activities.KayakoSearchArticleActivity;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryListFragment extends BaseListFragment implements SectionByCategoryContract.View, ListItemRecyclerViewAdapter.OnListItemClickListener, SearchSectionAdapter.OnSearchClickListener {

    protected SectionByCategoryContract.Presenter mPresenter;
    protected BackgroundTask mBackgroundTask;
    protected SearchSectionAdapter listItemRecyclerViewAdapter;
    protected ActivityNavigationResourceCallback mActivityNavigation;

    public static SectionByCategoryListFragment newInstance() {
        return new SectionByCategoryListFragment();
    }

    public SectionByCategoryListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivityNavigation = ((ActivityNavigationResourceCallback) getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = SectionByCategoryFactory.getPresenter(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDefaultStateViewHelper().setupErrorView(null, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.reloadPage();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    @Override
    public void startBackgroundTask() {
        // Cancel pending tasks before starting new ones
        if (mBackgroundTask != null) {
            mBackgroundTask.cancelTask();
        }

        mBackgroundTask = (BackgroundTask) new BackgroundTask(getActivity()) {
            @Override
            protected boolean performInBackground() {
                return mPresenter.loadDataInBackground();
            }

            @Override
            protected void performOnCompletion(boolean isSuccessful) {
                mPresenter.onDataLoaded(isSuccessful);
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void openArticleListingPage(Section section) {
        mActivityNavigation.openNextPage(section);
    }

    @Override
    public void openSearchPage() {
        startActivity(KayakoSearchArticleActivity.getIntent(getContext()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityNavigation = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelBackgroundTasks();
        mBackgroundTask = null;
    }

    @Override
    public void setUpList(final List<BaseListItem> items) {
        listItemRecyclerViewAdapter = new SearchSectionAdapter(items, this, this);
        initList(listItemRecyclerViewAdapter);
    }

    @Override
    public void showOnlyListView() {
        showListViewAndHideOthers();
    }

    @Override
    public void showOnlyEmptyView() {
        showEmptyViewAndHideOthers();
    }

    @Override
    public void showOnlyErrorView() {
        showErrorViewAndHideOthers();
    }

    @Override
    public void showOnlyLoadingView() {
        showLoadingViewAndHideOthers();
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        mPresenter.onClickListItem(listItem);
    }

    @Override
    public void onClickSearch() {
        mPresenter.onClickSearch();
    }

    protected void cancelBackgroundTasks() {
        if (mBackgroundTask != null) {
            mBackgroundTask.cancelTask();
        }
    }
}
