package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.Map;

public class AttachmentMessageSelfListItem extends BaseDataListItem {

    private long time;
    private Attachment attachment;
    private DeliveryIndicator deliveryIndicator;

    public AttachmentMessageSelfListItem(@Nullable Long id, @Nullable DeliveryIndicator deliveryIndicator, @NonNull Attachment attachment, @Nullable long time, @Nullable Map<String, Object> data) {
        super(MessengerListType.ATTACHMENT_MESSAGE_SELF, id, data);
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

    public void setTime(long time) {
        this.time = time;
    }

    public DeliveryIndicator getDeliveryIndicator() {
        return deliveryIndicator;
    }
}
