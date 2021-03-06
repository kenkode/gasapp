package com.softark.eddie.gasexpress.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softark.eddie.gasexpress.Constants;
import com.softark.eddie.gasexpress.R;
import com.softark.eddie.gasexpress.activities.PriceActivity;

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.ViewHolder> {

    private final int[] sizes;
    private final Context context;
    private final LayoutInflater inflater;

    public DistributorAdapter(int[] sizes, Context context) {
        this.sizes = sizes;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return sizes.length;
    }

    @Override
    public void onBindViewHolder(DistributorAdapter.ViewHolder holder, int position) {
        int size = sizes[position];
        holder.gasSize.setText(String.valueOf(size));
    }

    @Override
    public DistributorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.distributor_list, null);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView gasSize;
        public final View view;
//        public final ImageButton moreInfo;
        public final View mainView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            gasSize = (TextView) itemView.findViewById(R.id.gas_size);
            mainView = itemView;
            mainView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int size = sizes[getAdapterPosition()];
            Intent intent = new Intent(context, PriceActivity.class);
            intent.putExtra(Constants.SIZE, size);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

}
