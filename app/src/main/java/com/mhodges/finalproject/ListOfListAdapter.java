package com.mhodges.finalproject;

import android.app.AppComponentFactory;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListOfListAdapter extends RecyclerView.Adapter<ListOfListAdapter.ViewHolder> {

    List<ItemList> items;
    LayoutInflater layoutInflater;
    private Context context;


    public ListOfListAdapter(Context context, List<ItemList> items){
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_as_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_main_frame_layout, ListOfItemsFragment.newInstance(items.get(getAdapterPosition()))).commit();
                }
            });
        }
    }
}
