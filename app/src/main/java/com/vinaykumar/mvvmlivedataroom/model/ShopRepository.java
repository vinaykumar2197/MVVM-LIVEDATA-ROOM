package com.vinaykumar.mvvmlivedataroom.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ShopRepository {


    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;
    private LiveData<List<Book>> books;
    private LiveData<List<Category>> categories;

    public ShopRepository(Application application){
        BooksDatabase booksDatabase = BooksDatabase.getInstance(application);
        categoryDAO = booksDatabase.getCategoryDAO();
        bookDAO = booksDatabase.getBooksDAO();
    }


    public LiveData<List<Category>> getCategories(){
        return categoryDAO.getCategoryList();
    }

    public LiveData<List<Book>> getBooks(int categoryId){
        return bookDAO.getBooksByCategoryId(categoryId);
    }

    public void insertCategory(Category category){
         new insertCategoryAsync(categoryDAO).execute(category);
    }


    public void updateCategory(Category category){
        new updateCategoryAsync(categoryDAO).execute(category);
    }

    public void deleteCategory(Category category){
        new deleteCategoryAsync(categoryDAO).execute(category);
    }

    public void insertBook(Book book){
        new insertBookAsync(bookDAO).execute(book);
    }

    public void updateBook(Book book){
        new updateBookAsync(bookDAO).execute(book);
    }

    public void deleteBook(Book book){
        new deleteBookAsync(bookDAO).execute(book);
    }


    public static  class insertCategoryAsync extends AsyncTask<Category,Void,Void>{

        private CategoryDAO categoryDAO;

        public insertCategoryAsync(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }


        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.addCategoryData(categories[0]);
            return null;
        }

    }


    public static class updateCategoryAsync extends AsyncTask<Category,Void,Void>{

        private CategoryDAO categoryDAO;

        public updateCategoryAsync(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.updateCategoryData(categories[0]);
            return null;
        }
    }


    public static class deleteCategoryAsync extends AsyncTask<Category,Void,Void>{

        private CategoryDAO categoryDAO;

        public deleteCategoryAsync(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }


        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.deleteCategoryData(categories[0]);
            return null;
        }
    }


    public static class insertBookAsync extends AsyncTask<Book,Void,Void>{
        private BookDAO bookDAO;

        public insertBookAsync(BookDAO bookDAO) {
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.addBookInfo(books[0]);
            return null;
        }
    }


    public static class updateBookAsync extends AsyncTask<Book,Void,Void>{

        private BookDAO bookDAO;

        public updateBookAsync(BookDAO bookDAO) {
            this.bookDAO = bookDAO;
        }


        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.updateBookInfo(books[0]);
            return null;
        }
    }


    public static class deleteBookAsync extends AsyncTask<Book,Void,Void>{

        private BookDAO bookDAO;

        public deleteBookAsync(BookDAO bookDAO) {
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.deleteBookInfo(books[0]);
            return null;
        }
    }



}
