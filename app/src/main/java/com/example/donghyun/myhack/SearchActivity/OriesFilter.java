package com.example.donghyun.myhack.SearchActivity;

import android.widget.Filter;

import com.example.donghyun.myhack.OriInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taewoo on 2017-02-05.
 */

public class OriesFilter extends Filter {

    AutoCompleteOriesAdapter adapter;
    List<OriInfo> originalList;
    List<OriInfo> filteredList;

    public OriesFilter(AutoCompleteOriesAdapter adapter, List<OriInfo> originalList){
        super();
        this.adapter = adapter;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if(constraint == null || constraint.length() == 0){
            filteredList.addAll(originalList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            //my filtering logic
            for(final OriInfo ori : originalList){
                if(ori.name.toLowerCase().contains(filterPattern)){
                    filteredList.add(ori);
                }
            }
        }

        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.filteredOries.clear();
        if(results.values != null)
            adapter.filteredOries.addAll((List)results.values);
        adapter.notifyDataSetChanged();
    }
}
