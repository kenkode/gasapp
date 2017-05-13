package com.softark.eddie.gasexpress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softark.eddie.gasexpress.R;
import com.softark.eddie.gasexpress.helpers.Cart;
import com.softark.eddie.gasexpress.models.Service;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Service> services;
    private final LayoutInflater inflater;

    public ServiceAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.service_list, null);
        return new ServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceAdapter.ViewHolder holder, final int position) {
        final Service service = services.get(position);
        holder.name.setText(service.getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView name;
        public final Button add;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.service_name);
            add = (Button) itemView.findViewById(R.id.add_to_cart);
            add.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Cart.getInstance().addService(services.get(getAdapterPosition()));
        }
    }

}
