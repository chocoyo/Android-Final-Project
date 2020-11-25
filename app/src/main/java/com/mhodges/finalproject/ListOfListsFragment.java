package com.mhodges.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ListOfListsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db;

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
        View view = inflater.inflate(R.layout.fragment_listoflists, container, false);
        recyclerView = view.findViewById(R.id.rvDataList);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        List<ItemList> lists = new ArrayList<>();

        db.collection("lists").whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lists.add(document.toObject(ItemList.class));

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

        return view;
    }
}