package com.example.recently;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import android.service.autofill.UserData;
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
import com.example.recently.databinding.RegistrationBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    RegistrationBinding root;
    private String username;
    private String mail;
    private String password1;
    private String password2;
    private String phoneString;
    private Long phone;
    private SharedPreferences userdata;
    private SharedPreferences.Editor editor;
    private AlertDialog.Builder builder1 ;
    private AlertDialog.Builder builder2 ;
    private Boolean imagecheck=false;
    private String imageencoded;
    private ProgressDialog waiting;
    private Uri image;
    private StorageReference reference;

    private boolean uploaded=false;
    private  DatabaseReference rootref;
    private SimpleDateFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = RegistrationBinding.inflate(getLayoutInflater());
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
            public void onClick(View v) {
                username =root.usernameregitration.getText().toString().trim();
                password1=root.passwordregistration.getText().toString().trim();
                password2=root.password2registration.getText().toString().trim();
                phoneString=root.phonenumber.getText().toString().trim();
                mail=root.mailregistration.getText().toString().trim();
                Boolean isVaild = EmailValid(mail);
                Boolean isValid_0=PhoneValid(phoneString);
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(mail)&& ! TextUtils.isEmpty(password1)
                && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(phoneString) && password1.equals(password2) && isVaild && isValid_0)
                {

                    phone = Long.parseLong(phoneString);
                    //need to sign in with google first to get mail
                    if(imagecheck) {
                        //uploadDataToDateBaseusingAysncTask();
                        try {
                            //up.execute(username, password1, phoneString, mail, imageencoded);
                            AuthenticatePhonenumber a = new AuthenticatePhonenumber(Registration.this,phoneString);
                            a.sendVerificationCodeToUser();
                            AuthenticatePhonenumber.authenticated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    addfirebaseusers();
                                }
                            });

                        }
                        catch (Exception exp)
                        {
                            exp.printStackTrace();
                            builder2.setMessage("SomeThing Error please try Later.")
                                    .setIcon(R.drawable.ic_baseline_warning_24)
                                    .setTitle("Warning")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(Registration.this,Registration.class));
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                    }
                    else
                    {
                        builder2.setMessage("Please Select the Image")
                                .setIcon(R.drawable.ic_baseline_warning_24)
                                .setTitle("Warning")
                                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AddAvater();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        imagecheck=false;
                                    }
                                })
                                .show();
                        root.Register.setEnabled(true);
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
    public Boolean EmailValid (String mail)
    {
        //this function to test the mail is written in correct shape or not
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (mail == null)
            return false;
        return pat.matcher(mail).matches();
    }
    public Boolean PhoneValid (String phone)
    {
        // this function to test the phone number is written in correct shape or not
        Pattern p = Pattern.compile("(01)[0-9]{9}");
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }
    public void init()
    {
        rootref = FirebaseDatabase.getInstance().getReference();
        userdata = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = userdata.edit();
        reference= FirebaseStorage.getInstance().getReference();
        builder1=new AlertDialog.Builder(this);
        builder2=new AlertDialog.Builder(this);
        waiting=new ProgressDialog(this);
        waiting.setMessage("we are Doing Registration");
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
            root.userimage.setImageURI(image);
            imagecheck=true;
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                imageencoded=encodeImage(bitmap);
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
    public  void addfirebaseusers()
    {
        waiting.show();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(phoneString).exists())) {
                    String FinalDate  = format.format(Calendar.getInstance().getTimeInMillis());
                    Map<String, Object> usermap = new HashMap<>();
                    usermap.put("phone", phone);
                    usermap.put("username", username);
                    usermap.put("mail", mail);
                    usermap.put("password", password1);
                    usermap.put("RegDate",FinalDate);
                    uploadimage(image,usermap);
                }
                else
                {
                    Toast.makeText(Registration.this, "This Phone is already exists", Toast.LENGTH_LONG).show();
                    waiting.dismiss();
                    Toast.makeText(Registration.this, "Please try again with another phone number", Toast.LENGTH_LONG).show();


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
                        editor.putString("ImageString", imageencoded);
                        editor.putString("ImageName", (String) usermap.get("imagename"));
                        editor.apply();
                        root.usernameregitration.setText("");
                        root.passwordregistration.setText("");
                        root.password2registration.setText("");
                        root.mailregistration.setText("");
                        root.phonenumber.setText("");
                        startActivity(new Intent(Registration.this, MainActivity.class));
                        finish();
                        Toast.makeText(Registration.this, "Congratulations, your account has been created", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(Registration.this, " There is error on network, try again later", Toast.LENGTH_LONG).show();

                    }
                    waiting.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(Registration.this, "There is error on uploading the image", Toast.LENGTH_LONG).show();
            waiting.dismiss();
        }

    }
}


