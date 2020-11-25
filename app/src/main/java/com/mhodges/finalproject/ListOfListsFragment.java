package com.mhodges.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ListOfListsFragment extends Fragment {
    private RecyclerView recyclerView;

    public static ListOfListsFragment newInstance() {
        return new ListOfListsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listoflists, container, false);
        recyclerView = view.findViewById(R.id.rvDataList);

        List<ItemList> test = new ArrayList<>();

        ItemList it = new ItemList();
        it.setName("New Name");

        ItemList it2 = new ItemList();
        it2.setName("New Name2");

        ItemList it3 = new ItemList();
        it3.setName("New Name3");

        test.add(it);
        test.add(it3);
        test.add(it2);




        ListOfListAdapter adapter = new ListOfListAdapter(getContext(), test);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}