package com.vinaykumar.mvvmlivedataroom;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vinaykumar.mvvmlivedataroom.databinding.ActivityAddEditBinding;
import com.vinaykumar.mvvmlivedataroom.databinding.ActivityMainBinding;
import com.vinaykumar.mvvmlivedataroom.model.Book;

public class AddEditActivity extends AppCompatActivity {

    public static final String BOOK_ID="bookId";
    public static final String BOOK_NAME="bookName";
    public static final String BOOK_PRICE="unitPrice";

    private ActivityAddEditBinding activityAddEditBinding;
    private AddEditClickHandler addEditClickHandler;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        activityAddEditBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_edit);

        addEditClickHandler = new AddEditClickHandler(this);

        activityAddEditBinding.setClickHandler(addEditClickHandler);

        book = new Book();
        activityAddEditBinding.setBook(book);

        Intent intent = getIntent();
        if(intent.hasExtra(BOOK_ID)){
            setTitle("Edit Book");
            book.setBookName(intent.getStringExtra(BOOK_NAME));
            book.setBookPrice(intent.getStringExtra(BOOK_PRICE));
        }
        else{
            setTitle("Add Book");
        }

    }

    public class AddEditClickHandler{
        Context context;

        public AddEditClickHandler(Context context) {
            this.context = context;
        }

        public void onSubmitButtonClicked(View view){
            if(book.getBookName()==null){
                Toast.makeText(context,"Field can not be empty",Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent();
                intent.putExtra(BOOK_NAME, book.getBookName());
                intent.putExtra(BOOK_PRICE, book.getBookPrice());
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }
}
