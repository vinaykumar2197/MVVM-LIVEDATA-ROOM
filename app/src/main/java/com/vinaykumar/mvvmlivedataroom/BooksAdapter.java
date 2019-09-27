package com.vinaykumar.mvvmlivedataroom;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinaykumar.mvvmlivedataroom.databinding.RowBooksBinding;
import com.vinaykumar.mvvmlivedataroom.model.Book;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private OnItemClickListener listener;

    ArrayList<Book> bookArrayList = new ArrayList<>();

    RowBooksBinding rowBooksBinding;

    public BooksAdapter(ArrayList<Book> bookArrayList) {
        this.bookArrayList = bookArrayList;
    }


    @NonNull
    @Override
    public BooksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rowBooksBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.row_books,viewGroup,false);

        return new MyViewHolder(rowBooksBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.MyViewHolder myViewHolder, int i) {
        Book book = bookArrayList.get(i);
        myViewHolder.rowBooksBinding.setBooks(book);


    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowBooksBinding rowBooksBinding;
        public MyViewHolder(@NonNull RowBooksBinding rowBooksBinding) {
            super(rowBooksBinding.getRoot());
            this.rowBooksBinding = rowBooksBinding;
            rowBooksBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    if(listener!=null && clickedPosition!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(bookArrayList.get(clickedPosition));
                    }
                }
            });
        }
    }



public interface OnItemClickListener{
    void onItemClick(Book book);
}

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

