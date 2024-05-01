package com.example.cst338_project2_ecommercestore.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cst338_project2_ecommercestore.database.entities.User;
import com.example.cst338_project2_ecommercestore.database.entities.eCommerce;
import com.example.cst338_project2_ecommercestore.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class eCommerceRepository {
    //attributes
    private final eCommerceDAO ecommerceDAO;
    private final UserDAO userDAO;
    private ArrayList<eCommerce> allCarts;
    private static eCommerceRepository repository;

    //constructor
    private eCommerceRepository(Application application){
        eCommerceDatabase db = eCommerceDatabase.getDatabase(application);
        this.ecommerceDAO = db.ecommerceDAO();
        this.userDAO = db.userDAO();
        this.allCarts = (ArrayList<eCommerce>) this.ecommerceDAO.getAllRecords(); //cast into ArrayList
    }

    //methods

    public static eCommerceRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<eCommerceRepository> future = eCommerceDatabase.databaseWriteExecutor.submit(
                new Callable<eCommerceRepository>() {
                    @Override
                    public eCommerceRepository call() throws Exception {
                        return new eCommerceRepository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting eCommerceRepository, thread error");
        }
        return null;
    }

    public ArrayList<eCommerce> getAllCarts() {
        Future<ArrayList<eCommerce>> future = eCommerceDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<eCommerce>>() {
                    @Override
                    public ArrayList<eCommerce> call() throws Exception {
                        return (ArrayList<eCommerce>) ecommerceDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all in repository");
        }
        return null;
    }

    public void insertECommerce(eCommerce ecommerce){
        eCommerceDatabase.databaseWriteExecutor.execute(()->
        {
            ecommerceDAO.insert(ecommerce);
        });
    }

    public void insertUser(User... user){
        eCommerceDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {          //LiveData is automatically muiltithreaded
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {          //LiveData is automatically muiltithreaded
        return userDAO.getUserByUserId(userId);
    }

//    public LiveData<List<eCommerce>> getAllCartsByUserId(int userId){
//        return eCommerce.getAllCartsByUserId(userId);
//    }
}
