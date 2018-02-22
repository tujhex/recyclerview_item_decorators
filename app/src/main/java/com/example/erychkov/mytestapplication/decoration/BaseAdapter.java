package com.example.erychkov.mytestapplication.decoration;

import com.example.erychkov.mytestapplication.CommentAdapterUiModelImpl;
import com.example.erychkov.mytestapplication.R;

import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    public class BaseHolder extends RecyclerView.ViewHolder{

        private final TextView mTime;
        private final TextView mMessage;

        public BaseHolder(View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.message);
            mTime = itemView.findViewById(R.id.time);
        }

        void bind(String message, String time){
            mMessage.setText(message);
            mTime.setText(time);
        }
    }

    private List<CommentAdapterUiModelImpl> mModels;
    private final SimpleDateFormat mSimpleDateFormat;

    public BaseAdapter(){
        mModels = new ArrayList<>();
        mSimpleDateFormat = new SimpleDateFormat("HH:mm");
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BaseHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        CommentAdapterUiModelImpl model = mModels.get(position);
        holder.bind(model.getText(), mSimpleDateFormat.format(model.getCreationDate()));
    }

    public void setData(List<CommentAdapterUiModelImpl> models){
        mModels = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mModels.get(position).isMyComment() ? R.layout.comment_my : R.layout.comment_other;
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }
}
