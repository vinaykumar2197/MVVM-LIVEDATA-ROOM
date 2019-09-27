package com.vinaykumar.mvvmlivedataroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.vinaykumar.mvvmlivedataroom.model.Book;
import com.vinaykumar.mvvmlivedataroom.model.Category;
import com.vinaykumar.mvvmlivedataroom.databinding.ActivityMainBinding;
import com.vinaykumar.mvvmlivedataroom.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.vinaykumar.mvvmlivedataroom.AddEditActivity.BOOK_NAME;
import static com.vinaykumar.mvvmlivedataroom.AddEditActivity.BOOK_PRICE;

public class MainActivity extends AppCompatActivity {


    public static int ADD_BOOK_REQUEST_CODE = 111;
    public static int EDIT_BOOK_REQUEST_CODE = 444;

    MainActivityClickHandlers mainActivityClickHandlers;

    ActivityMainBinding activityMainBinding;

    MainActivityViewModel mainActivityViewModel;

    private Category selectedCategory;

    private ArrayList<Book> bookArrayList;
    private ArrayList<Category> categoryArrayList;

    private RecyclerView booksRecyclerView;

    private int selectedBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        mainActivityClickHandlers = new MainActivityClickHandlers();

        activityMainBinding.setClickHandlers(mainActivityClickHandlers);


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                categoryArrayList = (ArrayList<Category>) categories;

//                for (Category c : categories) {
//
//                    Log.i("MyTAG", c.getCategoryName());
//                }

//                for(int i = 0 ; i<categoryArrayList.size();i++){
//                    Log.i("MyTag",categoryArrayList.get(i).getCategoryName());
//                }

                showOnSpinner();
            }
        });


//        mainActivityViewModel.getBookListByCategoryId().observe(this, new Observer<List<Book>>() {
//            @Override
//            public void onChanged(@Nullable List<Book> books) {
//
//            }
//        });

    }

    public void showOnSpinner(){
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_dropdown_item,categoryArrayList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }


    public void loadBooksArrayList(int categoryId){
        mainActivityViewModel.getBookListByCategoryId(categoryId).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                bookArrayList =(ArrayList<Book>) books;
                loadRecyclerView();
            }
        });
    }


    public void loadRecyclerView(){

        booksRecyclerView = activityMainBinding.secondaryLayout.recyclerView;
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setItemAnimator(new DefaultItemAnimator());

        BooksAdapter booksAdapter = new BooksAdapter(bookArrayList);
        booksRecyclerView.setAdapter(booksAdapter);

        booksAdapter.setListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                selectedBookId = book.getBookId();

                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.BOOK_ID, selectedBookId);
                intent.putExtra(BOOK_NAME, book.getBookName());
                intent.putExtra(AddEditActivity.BOOK_PRICE, book.getBookPrice());
                startActivityForResult(intent, EDIT_BOOK_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Book bookToDelete = bookArrayList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteBook(bookToDelete);
            }
        }).attachToRecyclerView(booksRecyclerView);

    }


    public class  MainActivityClickHandlers{

        public void onSelectItem(AdapterView<?> parent , View view,int pos , long id){
            selectedCategory = (Category) parent.getItemAtPosition(pos);

            loadBooksArrayList(selectedCategory.getCategoryId());
        }

        public void onFabClick(View view){
            Intent intent = new Intent(MainActivity.this,AddEditActivity.class);
            startActivityForResult(intent,ADD_BOOK_REQUEST_CODE);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedCategoryId = selectedCategory.getCategoryId();

        if(requestCode==ADD_BOOK_REQUEST_CODE && resultCode==RESULT_OK){
            Book book = new Book();
            book.setCategoryId(selectedCategoryId);
            book.setBookName(data.getStringExtra(BOOK_NAME));
            book.setBookPrice(data.getStringExtra(BOOK_PRICE));
            mainActivityViewModel.addBook(book);

        }
        else  if(requestCode==EDIT_BOOK_REQUEST_CODE && resultCode==RESULT_OK){
            Book book = new Book();
            book.setCategoryId(selectedCategoryId);
            book.setBookName(data.getStringExtra(BOOK_NAME));
            book.setBookPrice(data.getStringExtra(BOOK_PRICE));
            book.setBookId(selectedBookId);
            mainActivityViewModel.updateBook(book);

        }
    }
}
