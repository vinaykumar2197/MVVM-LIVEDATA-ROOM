package com.vinaykumar.mvvmlivedataroom.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void addCategoryData(Category category);

    @Update
    void updateCategoryData(Category category);

    @Delete
    void deleteCategoryData(Category category);

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getCategoryList();

}
