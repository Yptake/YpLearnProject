package com.yptake.yplearnproject.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoEntity implements Parcelable {

    public String title;
    public String imgCoverUrl;
    public String videoUrl;
    public long playNum;
    public long likeNum;
    public long id;

    public VideoEntity() {
    }

    public VideoEntity(Parcel in) {
        title = in.readString();
        imgCoverUrl = in.readString();
        videoUrl = in.readString();
        playNum = in.readLong();
        likeNum = in.readLong();
        id = in.readLong();
    }

    public static final Creator<VideoEntity> CREATOR = new Creator<VideoEntity>() {
        @Override
        public VideoEntity createFromParcel(Parcel in) {
            return new VideoEntity(in);
        }

        @Override
        public VideoEntity[] newArray(int size) {
            return new VideoEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imgCoverUrl);
        dest.writeString(videoUrl);
        dest.writeLong(playNum);
        dest.writeLong(likeNum);
        dest.writeLong(id);
    }
}
