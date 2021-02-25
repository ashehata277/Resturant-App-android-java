package com.example.recently;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.recently.databinding.ActivityUpdateDataBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateData extends AppCompatActivity {
    ActivityUpdateDataBinding root;
    private String username;
    private String mail;
    private String password1;
    private String password2;
    private String phoneString;
    private Long phone;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private AlertDialog.Builder builder1 ;
    private AlertDialog.Builder builder2 ;
    private Boolean imagecheck=false;
    private String imageencoded;
    private ProgressDialog waiting;
    private StorageReference reference;
    private DatabaseReference rootref;
    private Uri image;
    private boolean uploaded=false;
    private SimpleDateFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root= ActivityUpdateDataBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        init();
        root.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAvater();
            }
        });
        root.Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                username =root.usernameregitration.getText().toString().trim();
                password1=root.passwordregistration.getText().toString().trim();
                password2=root.password2registration.getText().toString().trim();
                phoneString=root.phonenumber.getText().toString().trim();
                mail=root.mailregistration.getText().toString().trim();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(mail)&& ! TextUtils.isEmpty(password1)
                        && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(phoneString) && password1.equals(password2))
                {

                    phone = Long.parseLong(phoneString);
                    try
                    {
                        AuthenticatePhonenumber a = new AuthenticatePhonenumber(UpdateData.this,phoneString);
                        a.sendVerificationCodeToUser();
                        AuthenticatePhonenumber.authenticated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                UpdateDatafireBaseUser();
                            }
                        });
                    }
                    catch (Exception exp)
                    {
                        exp.printStackTrace();
                        builder1.setMessage("SomeThing Error please try Later.")
                                .setIcon(R.drawable.ic_baseline_warning_24)
                                .setTitle("Warning")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(UpdateData.this,UpdateData.class));
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
                else
                {
                    builder1.setMessage("Please Complete the Registration Data.")
                            .setTitle("Warning")
                            .setIcon(R.drawable.ic_baseline_warning_24).show();
                    root.Register.setEnabled(true);
                }
            }
        });

    }
    public void init()
    {
        rootref = FirebaseDatabase.getInstance().getReference();
        reference= FirebaseStorage.getInstance().getReference();
        preferences= getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                root.mailregistration.setText(snapshot.child("Users").child(MainActivity.fragmentphone).child("mail").getValue().toString());
                root.phonenumber.setText(snapshot.child("Users").child(MainActivity.fragmentphone).getKey());
                root.usernameregitration.setText(snapshot.child("Users").child(MainActivity.fragmentphone).child("username").getValue().toString());
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/chatroom-b54ff.appspot.com/o/Photos%2F"+snapshot.child("Users").child(MainActivity.fragmentphone).child("imagename").getValue().toString()+"?alt=media").into(root.userimage);
                Log.i("Loading","successfully");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        builder1=new AlertDialog.Builder(this);
        builder2=new AlertDialog.Builder(this);
        root.mailregistration.setEnabled(false);
        waiting=new ProgressDialog(this);
        waiting.setMessage("Updating your Data");
        format =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
    }
    public void AddAvater()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(gallery.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(gallery,100);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==100 && resultCode==RESULT_OK)
        {
            image = data.getData();
            //root.userimage.setImageURI(image);
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                imageencoded=encodeImage(bitmap);
                imagecheck=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //need to decode the image to string and undecode
        }
    }
    public String encodeImage(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = image;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
    public Bitmap decode (String strimage)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(strimage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }
    public void UpdateDatafireBaseUser()
    {
        waiting.show();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.child("Users").child(phoneString).exists())) {
                    String FinalDate  = format.format(Calendar.getInstance().getTimeInMillis());
                    Map<String, Object> usermap = new HashMap<>();
                    usermap.put("phone", phone);
                    usermap.put("username", username);
                    usermap.put("mail", mail);
                    usermap.put("password", password1);
                    usermap.put("LastUpdateDate", FinalDate);
                    if(imagecheck)
                    {
                        uploadimage(image,usermap);
                    }
                    else
                    {
                        uploaded=true;
                        uploadData(usermap);
                    }

                }
                else
                {
                    Toast.makeText(UpdateData.this, "This Phone is not exists", Toast.LENGTH_LONG).show();
                    waiting.dismiss();
                    Toast.makeText(UpdateData.this, "Please try again after check phone number", Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void uploadimage(Uri data,Map<String,Object> usermap)
    {
        Calendar calender = Calendar.getInstance();
        String ImageName="img_"+phoneString+"_"+calender.getTimeInMillis();
        StorageReference filepath = reference.child("Photos").child(ImageName);
        filepath.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploaded=true;
                usermap.put("imagename", ImageName);
                uploadData(usermap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Registration.this,"There is error on network please try again later",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                uploaded=false;
            }
        });



    }
    private void uploadData(Map<String,Object> usermap)
    {
        if (uploaded)
        {
            rootref.child("Users").child(phoneString).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        editor.putString("username", username);
                        editor.putString("mail", mail);
                        editor.putString("password", password1);
                        editor.putLong("phone", phone);
                        editor.putString("phoneString",phoneString);
                        editor.putString("ImageName", (String) usermap.get("imagename"));
                        if(imagecheck)
                        {
                            editor.putString("ImageString", imageencoded);
                        }
                        editor.apply();
                        root.usernameregitration.setText("");
                        root.passwordregistration.setText("");
                        root.password2registration.setText("");
                        root.mailregistration.setText("");
                        root.phonenumber.setText("");
                        startActivity(new Intent(UpdateData.this, MainActivity.class));
                        finish();
                        Toast.makeText(UpdateData.this, "Congratulations, your account has been updated", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(UpdateData.this, " There is error on network, try again later", Toast.LENGTH_LONG).show();

                    }
                    waiting.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(UpdateData.this, "There is error on uploading the image", Toast.LENGTH_LONG).show();
            waiting.dismiss();
        }

    }

}