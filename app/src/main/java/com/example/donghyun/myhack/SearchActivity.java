package com.example.donghyun.myhack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    static final String[] ories = new String[] {"세종대학교","서울대학교","세종시고려대학교","연세대학교"};

    private ArrayList<OriInfo> ori;

    AdapterView.OnItemClickListener clickListenerActViewExample = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String toastMessage = ((TextView)view).getText().toString() + " is selected. position is"
                    + position + ", and id is " + id;
            Toast.makeText(getApplicationContext(),toastMessage,Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.autocomplete_country);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, ories);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(clickListenerActViewExample);

    }
    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }
    */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
