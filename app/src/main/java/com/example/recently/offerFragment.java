package com.example.recently;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class offerFragment extends Fragment
{
    private RecyclerView recyclerView;
    private ArrayList<offeritem> content= new ArrayList<offeritem>();
    private RecycleViewAdapter recycleViewAdapter;
    private DatabaseReference reference ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_offer, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.RecycleView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2); // to make View Horizontal can replace grid layout to ---->LinearLayoutManager(Context,LinearLayoutManager.Horizontal,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recycleViewAdapter = new RecycleViewAdapter(getActivity(),content);
        recyclerView.setAdapter(recycleViewAdapter);
        reference = FirebaseDatabase.getInstance().getReference();
        ReadOffersData();
        return root;
    }
    private void ReadOffersData()
    {
        reference.child("Offers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child :snapshot.getChildren())
                {
                    content.add(new offeritem((String)child.child("link").getValue(),
                            (String)child.child("name").getValue(),
                            (String)child.child("description").getValue(),
                            (Long) child.child("price").getValue(),0));
                }
                recycleViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}