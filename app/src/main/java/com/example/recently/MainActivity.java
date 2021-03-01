package com.example.recently;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.recently.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding root;
    private int STORAGE_PERMISSION_REQUEST_CODE=402;
    private SharedPreferences preferences;
    private boolean Data_In_Local=false;
    private String phoneString;
    private String password;
    private ProgressDialog dialog;
    private AlertDialog.Builder builder1 ;
    private int CAMERA_PERMISSION_REQUEST_CODE = 400;
    private AlertDialog.Builder builder2 ;
    private DatabaseReference rootref;
    private boolean frommainapp=true;
    private boolean remeberlogindata = true;
    private SharedPreferences.Editor editor;
    public static String fragmentphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        if(getIntent().hasExtra("MainApp"))
        {
            frommainapp =getIntent().getExtras().getBoolean("MainApp");
        }
        //check permissions to access Storage
        CheckPermissions(Manifest.permission.INTERNET,STORAGE_PERMISSION_REQUEST_CODE);
        CheckPermissions(Manifest.permission.CAMERA,CAMERA_PERMISSION_REQUEST_CODE);
        init();
        root.Signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });
        root.Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Login();
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
                                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });
        root.remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                remeberlogindata=isChecked;
            }
        });
    }
    public void CheckPermissions(String permission, int requestCode)
    {
        if(ContextCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{permission},requestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if(requestCode==STORAGE_PERMISSION_REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                //Toast.makeText(this,"Storage Permission Granted",Toast.LENGTH_LONG).show();
            }
            else
            {
                //Toast.makeText(this,"Storage Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode==CAMERA_PERMISSION_REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                //Toast.makeText(this,"Camera Permission Granted",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,"Camera Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void init()
    {
        preferences= getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor= preferences.edit();
        rootref = FirebaseDatabase.getInstance().getReference();
        dialog =new ProgressDialog(this);
        dialog.setMessage("Logging in");
        builder1=new AlertDialog.Builder(this);
        builder2=new AlertDialog.Builder(this);
        phoneString="";
        password="";
        if(!preferences.getString("username","N/A").equals("N/A") && !preferences.getString("password","N/A").equals("N/A"))
        {
            phoneString = preferences.getString("phoneString","N/A");
            password =preferences.getString("password","N/A");
        }
        if(!phoneString.equals("N/A") && !password.equals("N/A") && !TextUtils.isEmpty(phoneString) && !TextUtils.isEmpty(password))
        {
            root.PlainUsername.setText(phoneString);
            root.PlainPassword.setText(password);
            if(frommainapp)
            {
                Login();
            }
        }

    }
    private void Login()
    {
        phoneString=root.PlainUsername.getText().toString();
        password=root.PlainPassword.getText().toString();
        dialog.show();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phoneString).getKey().equals(phoneString))
                {
                    if(snapshot.child("Users").child(phoneString).child("password").getValue().toString().equals(password))
                    {
                        Toast.makeText(MainActivity.this,"Logging in successed",Toast.LENGTH_LONG).show();
                        editor.putString("ImageName", snapshot.child("Users").child(phoneString).child("imagename").getValue().toString());
                        if(remeberlogindata)
                        {
                            editor.putString("phoneString",phoneString);
                            editor.putString("password",password);
                            editor.putString("username", snapshot.child("Users").child(phoneString).child("username").getValue().toString());
                            editor.putString("mail", snapshot.child("Users").child(phoneString).child("mail").getValue().toString());
                            fragmentphone=phoneString;
                            editor.apply();
                        }
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this,MainAppActivity.class));
                        finish();
                    }
                    else
                    {
                        dialog.dismiss();
                        builder2.setMessage("Please, Check From the phone and password")
                                .setIcon(R.drawable.ic_baseline_warning_24)
                                .setTitle("Warning")
                                .show();
                    }

                }
                else
                {
                    dialog.dismiss();
                    builder2.setMessage("Please, Check From the phone and password")
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("Warning")
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}