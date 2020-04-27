package com.yptake.yplearnproject.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ToutiaoEntity implements Parcelable {

    /**
     * stat : 1
     * data : [{"uniquekey":"6c4caa0c3ba6e05e2a272892af43c00e","title":"杨幂的发际线再也回不去了么？网友吐槽像半秃","date":"2017-01-05 11:03","category":"yule","author_name":"腾讯娱乐","url":"http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_2_mwpm_03200403.jpeg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_3_mwpm_03200403.jpeg"},"..."]
     */

    public String stat;
    public List<DataBean> data;

    protected ToutiaoEntity(Parcel in) {
        stat = in.readString();
    }

    public static final Creator<ToutiaoEntity> CREATOR = new Creator<ToutiaoEntity>() {
        @Override
        public ToutiaoEntity createFromParcel(Parcel in) {
            return new ToutiaoEntity(in);
        }

        @Override
        public ToutiaoEntity[] newArray(int size) {
            return new ToutiaoEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stat);
    }

    public static class DataBean {
        /**
         * uniquekey : 6c4caa0c3ba6e05e2a272892af43c00e
         * title : 杨幂的发际线再也回不去了么？网友吐槽像半秃
         * date : 2017-01-05 11:03
         * category : yule
         * author_name : 腾讯娱乐
         * url : http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju
         * thumbnail_pic_s : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_1_mwpm_03200403.jpeg
         * thumbnail_pic_s02 : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_2_mwpm_03200403.jpeg
         * thumbnail_pic_s03 : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_3_mwpm_03200403.jpeg
         */

        public String uniquekey;
        public String title;
        public String date;
        public String category;
        public String author_name;
        public String url;
        public String thumbnail_pic_s;
        public String thumbnail_pic_s02;
        public String thumbnail_pic_s03;

    }


}
