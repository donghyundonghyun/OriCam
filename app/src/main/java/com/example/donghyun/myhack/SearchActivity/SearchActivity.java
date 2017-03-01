package com.example.donghyun.myhack.SearchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donghyun.myhack.InfoActivity;
import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<OriInfo> ories;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ories = getIntent().getParcelableArrayListExtra("ories");

        final AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.autocomplete_ories);
        final AutoCompleteOriesAdapter adapter = new AutoCompleteOriesAdapter(this, R.layout.activity_search, ories);

        AdapterView.OnItemClickListener clickListenerActViewExample = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AutoCompleteOriesAdapter adapter1 = (AutoCompleteOriesAdapter)parent.getAdapter();
//                OriInfo ori = adapter1.getItem(position);
                OriInfo ori = ((AutoCompleteOriesAdapter)parent.getAdapter()).getItem(position);
                textView.setText(ori.name);

                //Test
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("ID", ori.ID);
                startActivity(intent);
            }
        };

        textView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        textView.setInputType(InputType.TYPE_CLASS_TEXT);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:
                        //검색 버튼 클릭시 이벤트 발생
                        Toast.makeText(getApplicationContext(),"검색", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(clickListenerActViewExample);

    }
}