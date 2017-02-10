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
    public String tel;
    public String addr;
    public String info;
    public String etc;
    public int facility;
    public double distance;
    public int near;

    public OriInfo(int ID, double lon, double lat, double alt, String name, String tel, String addr, String info, String etc, int facility, double distance, int near) {
        this.ID = ID;
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
        this.name = name;
        this.tel = tel;
        this.addr = addr;
        this.info = info;
        this.etc = etc;
        this.facility = facility;
        this.distance = distance;
        this.near = near;
    }

    protected OriInfo(Parcel in) {
        ID = in.readInt();
        lon = in.readDouble();
        lat = in.readDouble();
        alt = in.readDouble();
        name = in.readString();
        tel = in.readString();
        addr = in.readString();
        info = in.readString();
        etc = in.readString();
        facility = in.readInt();
        distance = in.readDouble();
        near = in.readInt();
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
        dest.writeString(tel);
        dest.writeString(addr);
        dest.writeString(info);
        dest.writeString(etc);
        dest.writeInt(facility);
        dest.writeDouble(distance);
        dest.writeInt(near);
    }
}
