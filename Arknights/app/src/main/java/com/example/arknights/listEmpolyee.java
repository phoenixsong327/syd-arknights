package com.example.arknights;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class listEmpolyee extends AppCompatActivity {

    private List<employee> employees = new ArrayList<employee>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_empolyee);
        Init();
        EmployAdapter adapter = new EmployAdapter(listEmpolyee.this,R.layout.employ_item,employees);
        ListView listView=(ListView)findViewById(R.id.lvEp);
        listView.setAdapter(adapter);
    }

    public void Init(){
        employees= (List<employee>) getIntent().getSerializableExtra("ANS");
    }

}
