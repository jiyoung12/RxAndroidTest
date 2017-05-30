package com.jiyoung.rxandroidexamples;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jiyoung on 2017. 5. 30..
 */

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> {

    private Context context;
    private List<ExampleActivityAndName> mExamples;
    private ClickListener clickListener = null;

    public ExampleAdapter(Context context, List<ExampleActivityAndName> mExamples) {
        this.context = context;
        this.mExamples = mExamples;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tv_name.setText(mExamples.get(position).mActivityName);

    }

    @Override
    public int getItemCount() {
        return mExamples.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            tv_name = (TextView) itemView.findViewById(R.id.tv_menu_name);
        }

        @Override
        public void onClick(View v){
            if (clickListener != null){
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }
}
