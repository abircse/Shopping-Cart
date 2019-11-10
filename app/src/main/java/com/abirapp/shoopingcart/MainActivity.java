package com.abirapp.shoopingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abirapp.shoopingcart.activity.Cart;
import com.abirapp.shoopingcart.adapter.BookAdapter;
import com.abirapp.shoopingcart.model.Book;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        bookList = new ArrayList<>();
        adapter = new BookAdapter(this, bookList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        bookList.add(new Book(0,120,"Introduction To Computer"));
        bookList.add(new Book(1,150,"Software Engineering"));
        bookList.add(new Book(2,100,"Discreat Mathmatics"));
        bookList.add(new Book(3,270,"Electronics Device & Circuits"));

    }

    public void gotocart(View view) {

        startActivity(new Intent(MainActivity.this, Cart.class));
    }
}
