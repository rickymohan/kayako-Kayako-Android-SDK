package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.MessengerListFragment;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.List;

public class MessageListFragment extends MessengerListFragment implements MessageListContract.View, MessageListContract.ConfigureView {

    private MessengerAdapter.OnItemClickListener mOnItemClickListener;
    private OnListPageStateChangeListener mOnListPageStateChangeListener;
    private MessageListContract.OnErrorListener mErrorListener;
    private EndlessRecyclerViewScrollAdapter.OnLoadMoreListener mLoadMoreListener;
    private OnScrollListListener mOnScrollListener;
    private boolean mIsListAlreadyInitialized;
    private ListPageState mListPageState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPageStateListener();

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().getClass() != KayakoSelectConversationActivity.class) {
            throw new AssertionError("This fragment was intended to be used with KayakoSelectConversationActivity!");
        }

        triggerPageStateCallback();
    }

    private void setPageStateListener() {
        super.setOnListPageChangeStateListener(new OnListPageStateChangeListener() {
            @Override
            public void onListPageStateChanged(ListPageState state) {
                mListPageState = state;
                if (mOnListPageStateChangeListener != null) {
                    mOnListPageStateChangeListener.onListPageStateChanged(state);
                }
            }
        });
    }

    private void triggerPageStateCallback() {
        if (mOnListPageStateChangeListener != null && mListPageState != null) {
            mOnListPageStateChangeListener.onListPageStateChanged(mListPageState);
        }
    }

    private boolean hasPageLoaded() {
        return isAdded();
    }

    @Override
    public void setupList(List<BaseListItem> messageList) {
        if (!hasPageLoaded()) {
            return;
        }

        if (messageList == null) {
            throw new IllegalStateException("Null argument unacceptable!");
        }

        if (mIsListAlreadyInitialized) {
            replaceMessengerList(messageList);
        } else {
            initMessengerList(messageList);
            if (mOnItemClickListener != null) {
                super.setOnItemClickListener(mOnItemClickListener);
            }
            if (mOnScrollListener != null) {
                super.addScrollListListener(mOnScrollListener);
            }
            if (mLoadMoreListener != null) {
                super.setLoadMoreListener(mLoadMoreListener);
            }

            mIsListAlreadyInitialized = true;
        }

        super.scrollToNewMessagesIfNearby();
        showListViewAndHideOthers();
    }

    @Override
    public void setHasMoreItemsToLoad(boolean hasMoreItems) {
        if (!hasPageLoaded()) {
            return;
        }

        super.setHasMoreItems(hasMoreItems);
    }

    @Override
    public void showLoadMoreView() {
        if (!hasPageLoaded()) {
            return;
        }

        super.showLoadMoreProgress();

    }

    @Override
    public void hideLoadMoreView() {
        if (!hasPageLoaded()) {
            return;
        }

        super.hideLoadMoreProgress();
    }

    @Override
    public void scrollToBottomOfList() {
        if (!hasPageLoaded()) {
            return;
        }

        super.scrollToEndOfList();
    }

    @Override
    public boolean isNearBottomOfList() {
        if (!hasPageLoaded()) {
            return false;
        }

        return super.isNearEndOfList();
    }

    @Override
    public boolean hasUserInteractedWithList() {
        if (!hasPageLoaded()) {
            return false;
        }

        return super.hasUserTouchedRecyclerView();
    }


    @Override
    public void showEmptyView() {
        if (!hasPageLoaded()) {
            return;
        }

        showEmptyViewAndHideOthers();
    }

    @Override
    public void showErrorView() {
        if (!hasPageLoaded()) {
            return;
        }

        Context context = Kayako.getApplicationContext();

        if (mErrorListener != null) {
            getDefaultStateViewHelper().setupErrorView(context.getResources().getString(R.string.ko__label_error_view_title), context.getResources().getString(R.string.ko__label_error_view_description), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mErrorListener.onClickRetry();
                }
            });
        }
        showErrorViewAndHideOthers();
    }

    @Override
    public void showLoadingView() {
        if (!hasPageLoaded()) {
            return;
        }

        showLoadingViewAndHideOthers();
    }

    @Override
    public void setOnListPageStateChangeListener(OnListPageStateChangeListener listener) {
        mOnListPageStateChangeListener = listener;
        if (hasPageLoaded()) {
            setPageStateListener();
            triggerPageStateCallback();
        }
    }

    @Override
    public void setOnListScrollListener(OnScrollListListener listener) {
        mOnScrollListener = listener;
    }

    @Override
    public void setOnListAttachmentClickListener(MessengerAdapter.OnAttachmentClickListener listener) {
        super.setOnListAttachmentClickListener(listener);
    }

    @Override
    public void setOnListItemClickListener(MessengerAdapter.OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    public void setListOnLoadMoreListener(EndlessRecyclerViewScrollAdapter.OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    @Override
    public void setOnErrorListener(MessageListContract.OnErrorListener listener) {
        mErrorListener = listener;
    }

}
