package com.example.user.points.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity //(tableName = "pointsTable")
public class PointsData {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @TypeConverters(DateConverter.class)
    private Date timePoint;
    private int cat_index;
    private int cat2_index;
    private int pointValue;
    private int curValues;

    public PointsData(Date timePoint, int cat_index, int cat2_index, int pointValue, int curValues) {
        this.timePoint = timePoint;
        this.cat_index = cat_index;
        this.cat2_index = cat2_index;
        this.pointValue = pointValue;
        this.curValues = curValues;
    }

    public int getCurValues() {
        return curValues;
    }

    public int getPointValue() {
        return pointValue;
    }

    public int getCat_index() {
        return cat_index;
    }

    public int getCat2_index() {
        return cat2_index;
    }

    public Date getTimePoint() {
        return timePoint;
    }

}
