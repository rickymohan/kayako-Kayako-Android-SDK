package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

import java.util.List;

public class MessageListContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<MessageListContract.View> {

        void initPage();

        void closePage();

    }

    interface ConfigureView {

        void setupList(List<BaseListItem> conversation);

        void showEmptyView();

        void showErrorView();

        void showLoadingView();

        void setOnErrorListener(MessageListContract.OnErrorListener listener);

        void setOnListPageStateChangeListener(OnListPageStateChangeListener listener);

        void setOnListScrollListener(OnScrollListListener listener);

        void setOnListItemClickListener(MessengerAdapter.OnItemClickListener listener);
    }

    public interface OnErrorListener {
        void onClickRetry();
    }
}
