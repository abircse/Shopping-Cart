package com.abirapp.shoopingcart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abirapp.shoopingcart.R;
import com.abirapp.shoopingcart.activity.BookDetails;
import com.abirapp.shoopingcart.model.Book;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_layout, viewGroup, false);
        return new BookAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, final int i) {

        holder.name.setText(bookList.get(i).getBook_name());
        holder.price.setText("Price: "+ bookList.get(i).getPrice());

    }

    @Override
    public int getItemCount() {

        if (bookList == null) {
            return 0;
        }
        return bookList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,quantity;
        MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.textView1);
            price=view.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position= getAdapterPosition();
                    Book book= bookList.get(position);
                    Intent i= new Intent(context, BookDetails.class);
                    i.putExtra("BookModel",book);
                    context.startActivity(i);

                }
            });

        }
    }


}
