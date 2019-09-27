package com.vinaykumar.mvvmlivedataroom.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookDAO {

    @Insert
    void addBookInfo(Book book);

    @Update
    void updateBookInfo(Book book);

    @Delete
    void deleteBookInfo(Book book);

    @Query("SELECT * FROM books_table")
    LiveData<List<Book>> getBooksData();

    @Query("SELECT * FROM books_table WHERE category_id==:categoryId")
    LiveData<List<Book>> getBooksByCategoryId(int categoryId);

}
