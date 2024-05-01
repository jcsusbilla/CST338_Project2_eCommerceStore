package com.example.cst338_project2_ecommercestore.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338_project2_ecommercestore.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + eCommerceDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + eCommerceDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + eCommerceDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + eCommerceDatabase.USER_TABLE + " WHERE username == :userId")
    LiveData<User> getUserByUserId(int userId);
}
