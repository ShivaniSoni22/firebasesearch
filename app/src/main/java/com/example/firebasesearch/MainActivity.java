package com.example.firebasesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBtn = (ImageButton) findViewById(R.id.SearchBtn);
        mSearchField = (EditText) findViewById(R.id.searchField);
        mSearchList = (RecyclerView) findViewById(R.id.searchList);

    }
}
