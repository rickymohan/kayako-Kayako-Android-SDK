package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.Map;

public class AttachmentMessageContinuedSelfListItem extends BaseDataListItem {

    private long time;
    private Attachment attachment;
    private DeliveryIndicator deliveryIndicator;

    public AttachmentMessageContinuedSelfListItem(@Nullable Long id, @NonNull Attachment attachment, @Nullable long time, @Nullable DeliveryIndicator deliveryIndicator, @Nullable Map<String, Object> data) {
        super(MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_SELF, id, data);
        this.attachment = attachment;
        this.time = time;
        this.deliveryIndicator = deliveryIndicator;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public long getTime() {
        return time;
    }

    public DeliveryIndicator getDeliveryIndicator() {
        return deliveryIndicator;
    }
}
