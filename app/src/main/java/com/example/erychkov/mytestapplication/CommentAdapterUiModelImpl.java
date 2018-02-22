package com.example.erychkov.mytestapplication;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public class CommentAdapterUiModelImpl implements Serializable {

    private final String mUuid;
    private final String mAuthorTitle;
    private final String mMessage;
    private final Date mCreationDate;
    private final boolean mPrivate;
    private final boolean mMyComment;
    private boolean mSelected = false;

    public CommentAdapterUiModelImpl(String uuid, String authorTitle, String message, Date creationDate, boolean privat, boolean my) {
        mUuid = uuid;
        mAuthorTitle = authorTitle;
        mMessage = message;
        mCreationDate = creationDate;
        mPrivate = privat;
        mMyComment = my;
    }

    public String getAuthor() {
        return mAuthorTitle;
    }

    public String getText() {
        return mMessage;
    }

    public boolean isPrivate() {
        return mPrivate;
    }

    public boolean isMyComment() {
        return mMyComment;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public String getUuid() {
        return mUuid;
    }

    @Override
    public String toString() {
        return "CommentAdapterUiModelImpl{" +
            "authorTitle='" + mAuthorTitle + '\'' +
            ", uuid='" + mUuid + '\'' +
            ", message='" + mMessage + '\'' +
            ", creationDate='" + mCreationDate + '\'' +
            ", private='" + mPrivate + '\'' +
            ", selected='" + mSelected + '\'' +
            '}';
    }
}

