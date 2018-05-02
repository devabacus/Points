package com.example.user.points;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.user.points.Database.AppDatabase;
import com.example.user.points.Database.PointsData;

import java.util.List;

public class PointsViewModel extends AndroidViewModel {

    private final LiveData<List<PointsData>> pointsList;

    private AppDatabase appDatabase;


    public PointsViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        pointsList = appDatabase.pointsFromDao().getAllPointsItems();
    }

    public LiveData<List<PointsData>> getPointsList() {
        return pointsList;
    }


    public void addPointsItem(final PointsData pointsData) {
        new addAsyncTask(appDatabase).execute(pointsData);
    }

    public void deleteItem(PointsData pointsData) {
        new deleteAsyncTask(appDatabase).execute(pointsData);
    }

    private static class addAsyncTask extends AsyncTask<PointsData, Void, Void> {
        private AppDatabase db;

        addAsyncTask(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(PointsData... pointsData) {
            db.pointsFromDao().addPoint(pointsData[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<PointsData, Void, Void> {
        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            this.db = appDatabase;
        }

        @Override
        protected Void doInBackground(PointsData... pointsData) {
            db.pointsFromDao().deletePoints(pointsData[0]);
            return null;
        }
    }


}
