package com.example.donghyun.myhack;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DH on 2017-01-15.
 */

public class OriInfo implements Parcelable{
    public int ID;
    public double lon;
    public double lat;
    public double alt;
    public String name;
    public String desc;

    public OriInfo(int ID, double lon, double lat, double alt, String name, String desc) {
        this.ID = ID;
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
        this.name = name;
        this.desc = desc;
    }

    protected OriInfo(Parcel in) {
        ID = in.readInt();
        lon = in.readDouble();
        lat = in.readDouble();
        alt = in.readDouble();
        name = in.readString();
        desc = in.readString();
    }

    public static final Creator<OriInfo> CREATOR = new Creator<OriInfo>() {
        @Override
        public OriInfo createFromParcel(Parcel in) {
            return new OriInfo(in);
        }

        @Override
        public OriInfo[] newArray(int size) {
            return new OriInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeDouble(lon);
        dest.writeDouble(lat);
        dest.writeDouble(alt);
        dest.writeString(name);
        dest.writeString(desc);
    }
}
