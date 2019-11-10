package com.abirapp.shoopingcart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abirapp.shoopingcart.R;
import com.abirapp.shoopingcart.activity.BookDetails;
import com.abirapp.shoopingcart.model.Book;

import java.util.List;

import io.realm.Realm;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private List<Book> orderbookList;
    private OnItemClickListener mListener;

    ///-------interface---------//
    public interface OnItemClickListener{
        void OnItemClick(int objectID);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    public CartAdapter(Context context, List<Book> orderbookList) {
        this.context = context;
        this.orderbookList = orderbookList;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_cart, viewGroup, false);
        return new CartAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, final int i) {

        holder.name.setText(orderbookList.get(i).getBook_name());
        holder.price.setText("Price: "+ orderbookList.get(i).getPrice());
        holder.quantity.setText("Quantity: "+ orderbookList.get(i).getBook_quantity());
    }

    @Override
    public int getItemCount() {

        if (orderbookList == null) {
            return 0;
        }
        return orderbookList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        Realm realm;
        TextView name,price,quantity;
        ImageView deletebook;

        MyViewHolder(View view) {
            super(view);

            realm= Realm.getDefaultInstance();

            name=view.findViewById(R.id.each_cart_book_name);
            price=view.findViewById(R.id.each_cart_book_price);
            quantity=view.findViewById(R.id.each_cart_book_quantity);
            deletebook=view.findViewById(R.id.each_cart_remove);

            deletebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int id= orderbookList.get(getAdapterPosition()).getId();
                    mListener.OnItemClick(id);
                }
            });

        }
    }


}
