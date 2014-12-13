package com.example.takahiro_tsuno.rssreader;

import android.os.Parcel;
import android.os.Parcelable;

public class Content implements Parcelable {

    public String title;
    public String url;

    public Content(String _title, String _url){
        title = _title;
        url = _url;
    }

    /**
     * ここから下はParcelableの挙動
     */

    @Override
    public int describeContents() {
        return 0;
    }

    // フィールド → Parcelへのdata移動
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
    }

    // Parcel → フィールド へのdata移動
    private Content(final Parcel in) {
        title = in.readString();
        url = in.readString();
    }

    // CREATORは定義が必須のフィールドだが、コンパイルエラーにならない為これが原因でエラーになることが多い
    public static final Parcelable.Creator<Content> CREATOR = new Creator<Content>() {

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }

        @Override
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }
    };
}
