package com.abirapp.shoopingcart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abirapp.shoopingcart.R;
import com.abirapp.shoopingcart.adapter.CartAdapter;
import com.abirapp.shoopingcart.model.Book;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Cart extends AppCompatActivity {

    private Realm realm;
    private TextView Texttotalprice;
    private TextView noBookText;
    private Button checkout;
    private Button orderMore;
    private Book bookmodel;

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Book> cartbookList;


    private String orderBookNames;
    private String orderedBookQuantities;
    private String orderedBookPrice;
    private String orderedBookID;
    double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        realm = Realm.getDefaultInstance();

        Texttotalprice = findViewById(R.id.totalPrice);
        noBookText = findViewById(R.id.warning);
        checkout = findViewById(R.id.checkout);
        orderMore = findViewById(R.id.gotostore);
        recyclerView = findViewById(R.id.cart_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        // load book  from cart
        cartbookList = new ArrayList<>();
        cartbookList= realm.where(Book.class).findAll();

        // call for alltime Update price
        calculateTotalPrice();

        if (cartbookList.isEmpty())
        {
            noBookText.setVisibility(View.VISIBLE);
        }
        else
        {
            noBookText.setVisibility(View.GONE);
        }

        // set to adapter
        cartAdapter = new CartAdapter(this,cartbookList);
        recyclerView.setAdapter(cartAdapter);

        // adapter callback for delete item
        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int objectID) {

                final RealmResults<Book> realmResults= realm.where(Book.class).equalTo("id",objectID).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realmResults.deleteAllFromRealm();
                        calculateTotalPrice();
                    }
                });

                cartAdapter.notifyDataSetChanged();
            }
        });


    }

    private void calculateTotalPrice() {

        List<Book> allbooks= realm.where(Book.class).findAll();

        if (allbooks.isEmpty())
        {
            noBookText.setVisibility(View.VISIBLE);
        }

        orderedBookPrice = "";
        orderedBookQuantities = "";
        orderBookNames = "";
        totalPrice = 0;

        for(Book b: allbooks){

            orderedBookPrice+=b.getPrice();
            orderedBookQuantities+=b.getBook_quantity();
            orderBookNames+=b.getBook_name();
            totalPrice+= b.getPrice()*b.getBook_quantity();
        }
        Texttotalprice.setText("Total: " + totalPrice);


    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
