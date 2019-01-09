package com.example.user.points;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.user.points.Database.AppDatabase;
import com.example.user.points.Database.PointsData;

import java.util.List;

public class PointsViewModel extends AndroidViewModel {

    private final LiveData<List<PointsData>> pointsList;
    private final LiveData<List<PointsData>> pointsList1;

    private AppDatabase appDatabase;


    public PointsViewModel(@NonNull Application application) {
        super(application);

       // this.curValues = curValues;
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        pointsList = appDatabase.pointsFromDao().getAllPointsItems();
        pointsList1 = appDatabase.pointsFromDao().getLastItem();
    }

    LiveData<List<PointsData>> getPointsList() {
        return pointsList;
    }
    public LiveData<List<PointsData>> getPointsList1() {
        return pointsList1;
    }


    public void addPointsItem(final PointsData pointsData) {
        new addAsyncTask(appDatabase).execute(pointsData);
    }

    public void deleteItem(PointsData pointsData) {
        new deleteAsyncTask(appDatabase).execute(pointsData);

    }

    void deleteAll() {
        new deleteAllAsyncTask(appDatabase).execute();
        //AppDatabase db;
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


    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private AppDatabase db;

        deleteAllAsyncTask(AppDatabase appDatabase) {
            this.db = appDatabase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.pointsFromDao().deleteAll();
            return null;
        }
    }


}
