package com.vinaykumar.mvvmlivedataroom.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.vinaykumar.mvvmlivedataroom.model.Book;
import com.vinaykumar.mvvmlivedataroom.model.Category;
import com.vinaykumar.mvvmlivedataroom.model.ShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    ShopRepository shopRepository;
    LiveData<List<Category>> categoryList;
    LiveData<List<Book>> bookList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        shopRepository = new ShopRepository(application);
    }

    public LiveData<List<Category>> getCategoryList(){
        categoryList = shopRepository.getCategories();
        return categoryList;
    }

    public LiveData<List<Book>> getBookListByCategoryId(int categoryId){
        bookList = shopRepository.getBooks(categoryId);
        return bookList;
    }


    public void addBook(Book book){
      shopRepository.insertBook(book);
    }

    public void updateBook(Book book){
        shopRepository.updateBook(book);
    }

    public void deleteBook(Book book){
        shopRepository.deleteBook(book);
    }

}
