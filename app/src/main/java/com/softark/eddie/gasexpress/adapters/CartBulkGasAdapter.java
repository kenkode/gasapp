package com.softark.eddie.gasexpress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.softark.eddie.gasexpress.R;
import com.softark.eddie.gasexpress.helpers.Cart;
import com.softark.eddie.gasexpress.models.BulkGas;

import java.util.ArrayList;

public class CartBulkGasAdapter extends RecyclerView.Adapter<CartBulkGasAdapter.ViewHolder> {

    private final ArrayList<BulkGas> items;
    private final LayoutInflater inflater;
    private final TextView totalPrice;

    public CartBulkGasAdapter(Context context, ArrayList<BulkGas> bulkGases, TextView totalPrice) {
        this.items = bulkGases;
        inflater = LayoutInflater.from(context);
        this.totalPrice = totalPrice;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CartBulkGasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_list, null);
        return new CartBulkGasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartBulkGasAdapter.ViewHolder holder, int position) {
        BulkGas bulkGas = items.get(position);
        holder.price.setText(String.valueOf(bulkGas.getPrice()));
        holder.name.setText(bulkGas.getName());
        holder.quantitySelect.setValue(bulkGas.getQuantity());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, NumberPicker.OnScrollListener, NumberPicker.OnValueChangeListener {
        public final TextView name;
        public final TextView price;
        public final Button remove;
        public final NumberPicker quantitySelect;
        private int scrollState = 0;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.cart_item_name);
            price = (TextView) itemView.findViewById(R.id.cart_item_price);
            remove = (Button) itemView.findViewById(R.id.remove_from_cart);
            remove.setOnClickListener(this);
            quantitySelect = (NumberPicker) itemView.findViewById(R.id.quantity_select);
            quantitySelect.setMinValue(1);
            quantitySelect.setMaxValue(100000);
            quantitySelect.setWrapSelectorWheel(false);
            quantitySelect.setOnValueChangedListener(this);
            quantitySelect.setOnScrollListener(this);
        }

        @Override
        public void onClick(View v) {
            BulkGas bulkGas = items.get(getAdapterPosition());
            Cart.removeBulkGas(bulkGas);
            totalPrice.setText(String.valueOf(Cart.totalPrice));
            items.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), items.size());
            notifyDataSetChanged();
        }

        @Override
        public void onScrollStateChange(NumberPicker view, int scrollState) {
            this.scrollState = scrollState;
            if(scrollState == SCROLL_STATE_IDLE) {
                BulkGas gas = items.get(getAdapterPosition());
                Cart.updateCartItem(gas, Cart.BULK_GAS, view.getValue());
                gas.setQuantity(view.getValue());
                totalPrice.setText(String.valueOf(Cart.totalPrice));
            }
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if(scrollState == SCROLL_STATE_IDLE) {
                BulkGas gas = items.get(getAdapterPosition());
                Cart.updateCartItem(gas, Cart.BULK_GAS, picker.getValue());
                gas.setQuantity(picker.getValue());
                totalPrice.setText(String.valueOf(Cart.totalPrice));
            }
        }
    }
}
