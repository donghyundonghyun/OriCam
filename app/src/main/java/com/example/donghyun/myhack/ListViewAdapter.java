package com.example.donghyun.myhack;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by limjeonghyun on 2017. 2. 12..
 */

public class ListViewAdapter extends BaseAdapter {

    //private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    public ListViewAdapter() {

    }
    @Override
    public int getCount() {
       // return listViewItemList.size() ;
        return 0;
    }

//    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;

//        ListViewItem listViewItem = listViewItemList.get(position);
//        iconImageView.setImageDrawable(listViewItem.getIcon());
//        titleTextView.setText(listViewItem.getTitle());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        //return listViewItemList.get(position) ;
        return 0;
    }

    public void addItem(Drawable icon, String title) {
//        ListViewItem item = new ListViewItem();
//
//        item.setIcon(icon);
//        item.setTitle(title);

       // listViewItemList.add(item);
    }
}