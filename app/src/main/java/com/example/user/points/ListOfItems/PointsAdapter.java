package com.example.user.points.ListOfItems;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.points.CategFragment;
import com.example.user.points.Database.PointsData;
import com.example.user.points.R;

import java.security.cert.PolicyNode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.PointsViewHolder> {

    private List<PointsData> pointsDataList;

    public PointsAdapter(List<PointsData> pointsDataList) {
        this.pointsDataList = pointsDataList;
    }

    @NonNull
    @Override
    public PointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new PointsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PointsViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));
        format.format(new Date());
        PointsData pointsData = pointsDataList.get(position);
//        holder.tvTime.setText(String.valueOf(format.format(pointsData.getTimePoint())));
        holder.tvCat.setText(CategFragment.cat_rus[pointsData.getCat_index()]);
        holder.tvPoint.setText(String.valueOf(pointsData.getPointValue()));
        holder.tvCurPoints.setText(String.valueOf(pointsData.getCurValues()));
    }

    public void addItems(List<PointsData> pointsDataList){
        this.pointsDataList = pointsDataList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return pointsDataList.size();
    }

    class PointsViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvCat;
        TextView tvPoint;
        TextView tvCurPoints;

        PointsViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvCat = itemView.findViewById(R.id.tv_cat_name);
            tvPoint = itemView.findViewById(R.id.tv_point);
            tvCurPoints = itemView.findViewById(R.id.tv_cur_points);

        }
    }
}
