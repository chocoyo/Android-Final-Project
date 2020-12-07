package com.mhodges.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Adapter for showing items in grid
public class ListOfItemAdapter extends RecyclerView.Adapter<ListOfItemAdapter.ViewHolder> {

    private List<Item> items;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListOfItemAdapter(Context context, List<Item> items){
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getName());
        holder.price.setText(String.valueOf(items.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.txtPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_main_frame_layout, ItemFragment.newInstance(items.get(getAdapterPosition()))).commit();
                }
            });
        }
    }
}
