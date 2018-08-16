package com.example.user.points.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface PointsDao {

    @Insert(onConflict = REPLACE)
    void addPoint(PointsData pointsData);

    @Query("select * from PointsData")
    LiveData<List<PointsData>> getAllPointsItems();

    @Query("select * from PointsData order by id DESC LIMIT 10")
    LiveData<List<PointsData>> getLastItem();

    @Query("select * from PointsData where id = :id")
    PointsData getitembyId(long id);

    @Query("delete from PointsData")
    public void deleteAll();

    @Delete
    void deletePoints(PointsData pointsData);

}
