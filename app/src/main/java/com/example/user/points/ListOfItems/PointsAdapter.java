package com.example.user.points.ListOfItems;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private View.OnLongClickListener longClickListener;

    PointsAdapter(List<PointsData> pointsDataList, View.OnLongClickListener longClickListener) {
        this.pointsDataList = pointsDataList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public PointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new PointsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PointsViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM HH:mm", new Locale("ru"));
        format.format(new Date());
        PointsData pointsData = pointsDataList.get(position);
        //Log.d("myLogs", String.valueOf(pointsDataList.get(position).getTimePoint()));
        holder.tvTime.setText(String.valueOf(format.format(pointsData.getTimePoint())));
        holder.tvCat.setText(CategFragment.cat[pointsData.getCat_index()]);
        if (pointsData.getCat_index() == 3) {
            if (pointsData.getCat2_index() == -1) {
                Log.d("test", "getCat2_index = " + pointsData.getCat2_index());
            } else {
                holder.tvCat2.setText(CategFragment.cat_mind[pointsData.getCat2_index()]);
            }

        }
        holder.tvPoint.setText(String.valueOf(pointsData.getPointValue()));
        holder.tvCurPoints.setText(String.valueOf(pointsData.getCurValues()));
        holder.itemView.setTag(pointsData);
        holder.itemView.setOnLongClickListener(longClickListener);
    }

    void addItems(List<PointsData> pointsDataList){
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
        TextView tvCat2;
        TextView tvPoint;
        TextView tvCurPoints;

        PointsViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvCat = itemView.findViewById(R.id.tv_cat_name);
            tvCat2 = itemView.findViewById(R.id.tv_cat2_name);
            tvPoint = itemView.findViewById(R.id.tv_point);
            tvCurPoints = itemView.findViewById(R.id.tv_cur_points);
        }
    }
}
