package com.example.takahiro_tsuno.rssreader;

import android.os.Parcel;
import android.os.Parcelable;

public class RssContent implements Parcelable {
    public String title;
    public String url;
    public String description;

    public RssContent(){
    }

    public RssContent(String _title, String _url, String _description){
        title = _title;
        url = _url;
        description = _description;
    }

    public void setTitle(String _title){
        title = _title;
    }

    public void setUrl(String _url){
        url = _url;
    }

    public void setDescription(String _description){
        description = _description;
    }

    // ---------------------------------------------------------------------
    // ここからがParcelableの実装
    // ---------------------------------------------------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    // Parcel → フィールド へのdata移動
    private RssContent(final Parcel in) {
        title = in.readString();
        url = in.readString();
    }

    // フィールド → Parcelへのdata移動
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(description);
    }

    // CREATORは定義が必須のフィールドだが、コンパイルエラーにならない為これが原因でエラーになることが多い
    public static final Parcelable.Creator<RssContent> CREATOR = new Creator<RssContent>() {

        @Override
        public RssContent[] newArray(int size) {
            return new RssContent[size];
        }

        @Override
        public RssContent createFromParcel(Parcel source) {
            return new RssContent(source);
        }
    };
}
