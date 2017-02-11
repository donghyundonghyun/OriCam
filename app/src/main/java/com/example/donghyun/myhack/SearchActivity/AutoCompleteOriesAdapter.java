package com.example.donghyun.myhack.SearchActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taewoo on 2017-02-05.
 */

public class AutoCompleteOriesAdapter extends ArrayAdapter<OriInfo>{

    private final List<OriInfo> ories;
    public List<OriInfo> filteredOries = new ArrayList<>();

    public AutoCompleteOriesAdapter(Context context, int textViewResourceId,  ArrayList<OriInfo> ories) {
        super(context, textViewResourceId, ories);
        this.ories = ories;
    }

    @Override
    public int getCount(){
        return filteredOries.size();
    }

    @Override
    public Filter getFilter(){
        return new OriesFilter(this, ories);
    }

    @Override
    public long getItemId(int position){
        return filteredOries.get(position).ID;
    }
    @Override
    public OriInfo getItem(int position){
        return filteredOries.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        if(row == null)
            row = inflater.inflate(R.layout.search_listitem_ori , parent, false);

        OriInfo ori = filteredOries.get(position);

        TextView txt = (TextView) row.findViewById(R.id.ori_name);
        txt.setText(ori.name);

        return  row;
    }

}
