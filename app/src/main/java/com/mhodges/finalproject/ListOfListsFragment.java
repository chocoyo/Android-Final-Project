package com.mhodges.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ListOfListsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private List<ItemList> lists;

    public static ListOfListsFragment newInstance() {
        return new ListOfListsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_lists, container, false);
        recyclerView = view.findViewById(R.id.rvDataList);

        updateData();

        return view;
    }


    private Query.Direction getQueryDirection(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (sharedPreferences.getString("sortingDirection", "asc").equals("asc"))
            return Query.Direction.ASCENDING;
        else
            return Query.Direction.DESCENDING;
    }

    public void updateData(){
        lists = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("lists").whereEqualTo("userID", user.getUid()).orderBy("timestamp", getQueryDirection())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemList tempItem = document.toObject(ItemList.class);
                                tempItem.setDocumentId(document.getId());
                                lists.add(tempItem);

                                ListOfListAdapter adapter = new ListOfListAdapter(getContext(), lists);

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        } else {
                            Toast.makeText(getContext(), "Error Retriving Lists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}