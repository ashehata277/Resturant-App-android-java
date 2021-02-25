package com.example.recently;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class profileFragment extends Fragment {
    private TextView username;
    private TextView mail;
    private TextView phone;
    private DatabaseReference database;
    private ImageView userimage;
    private String imagename;
    private StorageReference reference;
    private TextView update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        username = (TextView) root.findViewById(R.id.UserNameProfile);
        mail = (TextView) root.findViewById(R.id.mailprofile);
        phone = (TextView) root.findViewById(R.id.phoneprofile);
        userimage = (ImageView) root.findViewById(R.id.userimageprofile);
        update =(TextView) root.findViewById(R.id.textView);
        database = FirebaseDatabase.getInstance().getReference();
        reference= FirebaseStorage.getInstance().getReference();
        updateData();
        SpannableString sString =  new SpannableString("if you want to update your data press: Update");
        ClickableSpan clickableSpan =  new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
            startActivity(new Intent(getActivity(),UpdateData.class));
            }
        };
        sString.setSpan(clickableSpan,39,45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        update.append(sString);
        update.setMovementMethod(LinkMovementMethod.getInstance());
        return root;
    }
    private void updateData()
    {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.child("Users").child(MainActivity.fragmentphone).exists())
                {
                   mail.setText(snapshot.child("Users").child(MainActivity.fragmentphone).child("mail").getValue().toString());
                   phone.setText(snapshot.child("Users").child(MainActivity.fragmentphone).getKey());
                   username.setText(snapshot.child("Users").child(MainActivity.fragmentphone).child("username").getValue().toString());
                   imagename = snapshot.child("Users").child(MainActivity.fragmentphone).child("imagename").getValue().toString();
                    Log.i("name",reference.child("Photos").child(imagename+".jpeg").getPath());
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/chatroom-b54ff.appspot.com/o/Photos%2F"+imagename+"?alt=media").into(userimage);
                   //DownloadImage(imagename);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void DownloadImage(String name)
    {

        StorageReference storageReference = reference.child("Photos").child(name+".jpeg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //do your stuff- uri.toString() will give you download URL\\
                Log.i("Success","Success");
            }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("Failure","Failure");
                    int errorCode = ((StorageException) e).getErrorCode();
                    String errorMessage = e.getMessage();

                }
            });
        }

    }

