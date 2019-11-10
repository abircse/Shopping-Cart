package com.abirapp.shoopingcart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abirapp.shoopingcart.R;
import com.abirapp.shoopingcart.model.Book;

import io.realm.Realm;

public class BookDetails extends AppCompatActivity {


    private Realm realm;
    private TextView name;
    private TextView price;
    private TextView quantity;
    private Button addtoCart;
    private Button minus_Book;
    private Button plus_book;
    private Book bookmodel;
    private int CurrentQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        realm = Realm.getDefaultInstance();
        name = findViewById(R.id.textView1);
        price = findViewById(R.id.textView2);
        quantity = findViewById(R.id.book_quantity);
        minus_Book = findViewById(R.id.book_minus);
        plus_book = findViewById(R.id.book_plus);
        addtoCart = findViewById(R.id.cart);

        // receive complete model using percelable
        bookmodel= (Book) getIntent().getSerializableExtra("BookModel");

        // set data to view
        if (bookmodel != null) {

            name.setText(bookmodel.getBook_name());
            price.setText("Price: "+ bookmodel.getPrice());
        }

        // without increase or decrease book quantity get default qtn to model
        CurrentQuantity = Integer.parseInt(quantity.getText().toString());
        bookmodel.setBook_quantity(CurrentQuantity);

        // book minus operation
        minus_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get book quantity from text view
                CurrentQuantity = Integer.parseInt(quantity.getText().toString());
                // Check book qtn is one or not
                if (CurrentQuantity == 1)
                {
                    Toast.makeText(BookDetails.this, "Quantity must be atlist 1", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CurrentQuantity--;
                    // set letest book quantity to model for all use purpose
                    bookmodel.setBook_quantity(CurrentQuantity);
                    // set latest quantity to textview in view
                    quantity.setText(String.valueOf(CurrentQuantity));
                }

            }
        });
        // book plus operation
        plus_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get book quantity from text view
                CurrentQuantity = Integer.parseInt(quantity.getText().toString());
                CurrentQuantity++;
                // set letest book quantity to model for all use purpose
                bookmodel.setBook_quantity(CurrentQuantity);
                // set latest quantity to textview in view
                quantity.setText(String.valueOf(CurrentQuantity));

            }
        });


        // add book to cart
        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {

                        ////////// add data to cart////////////
                        try {

                            Book book = realm.createObject(Book.class,bookmodel.getId());
                            book.setBook_name(bookmodel.getBook_name());
                            book.setBook_quantity(bookmodel.getBook_quantity());
                            book.setPrice(bookmodel.getPrice());
                            Toast.makeText(BookDetails.this, "Book Added To Cart", Toast.LENGTH_SHORT).show();

                        }
                        catch (io.realm.exceptions.RealmPrimaryKeyConstraintException e)
                        {
                            // check same book exist in cart or not
                            Book book = realm.where(Book.class).equalTo("id",bookmodel.getId()).findFirst();
                            Toast.makeText(BookDetails.this, "Book Already Added to Cart", Toast.LENGTH_SHORT).show();
                            // update quantity only which book already added into cart
                            book.setBook_quantity(Integer.valueOf(quantity.getText().toString()));
                            realm.copyToRealmOrUpdate(book);
                            Toast.makeText(BookDetails.this, "Quatity Updated", Toast.LENGTH_SHORT).show();

                        }
                        //////////// End/////////////
                    }
                });

            }
        });








    }
}
