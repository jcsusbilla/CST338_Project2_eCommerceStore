package com.example.cst338_project2_ecommercestore.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.cst338_project2_ecommercestore.database.entities.eCommerce;
import java.util.List;

//represents queries run in database
@Dao
public interface eCommerceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(eCommerce ecommerce);

    @Query("SELECT * FROM " + eCommerceDatabase.eCommerceTable + " ORDER BY date DESC")
    List<eCommerce> getAllRecords();

    @Query("SELECT * FROM " + eCommerceDatabase.eCommerceTable + " WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<eCommerce>> getAllCartsByUserId(int userId);
}
