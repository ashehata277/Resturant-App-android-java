package com.example.recently;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
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
    private int EXTERNAL_PERMISSION_REQUEST_CODE= 404;
    private int FINE_LOCATION_CODE=406;
    private int COARSOR_LOCATION_CODE=408;
    private AlertDialog.Builder builder2 ;
    private DatabaseReference rootref;
    private boolean frommainapp=true;
    private boolean remeberlogindata = true;
    private SharedPreferences.Editor editor;
    public static String fragmentphone;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN=10;
    private FirebaseAuth mAuth;
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
        CheckPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,EXTERNAL_PERMISSION_REQUEST_CODE);
        CheckPermissions(Manifest.permission.ACCESS_FINE_LOCATION,FINE_LOCATION_CODE);
        CheckPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,COARSOR_LOCATION_CODE);
        init();
        root.signinwithgmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               Toast.makeText(MainActivity.this,"Still under working for visitors",Toast.LENGTH_SHORT).show();
                // StartAuthentication();
            }
        });
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
        if(requestCode==FINE_LOCATION_CODE)
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
        if(requestCode==COARSOR_LOCATION_CODE)
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
        if(requestCode==EXTERNAL_PERMISSION_REQUEST_CODE)
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
        //this part of code need to optimize, we can make this task with on condition
        if(!preferences.getString("username","N/A").equals("N/A") && !preferences.getString("password","N/A").equals("N/A") && !preferences.getString("password","N/A").isEmpty() && !preferences.getString("username","N/A").isEmpty())
        {
            phoneString = preferences.getString("phoneString","N/A");
            password =preferences.getString("password","N/A");
            root.PlainUsername.setText(phoneString);
            root.PlainPassword.setText(password);
            if(frommainapp)
            {
                Login();
            }
        }
        /*if(!phoneString.equals("N/A") && !password.equals("N/A") && !TextUtils.isEmpty(phoneString) && !TextUtils.isEmpty(password))
        {
            root.PlainUsername.setText(phoneString);
            root.PlainPassword.setText(password);
            if(frommainapp)
            {
                Login();
            }
        }*/
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }
    private void Login()
    {
        phoneString=root.PlainUsername.getText().toString();
        password=root.PlainPassword.getText().toString();
        dialog.show();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phoneString).getKey().equals(phoneString)
                && snapshot.child("Users").child(phoneString).child("password").getValue().toString().equals(password))
                {
                        /* Map<String,Object> active = new HashMap<String,Object>();
                        active.put("activeNow",true);
                        rootref.child("Users").child(phoneString).updateChildren(active);*/
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
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void StartAuthentication()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN && resultCode==RESULT_OK )
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            dialog.show();
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //root.mailregistration.setEnabled(true); // we should make enable to this after get google mail
            // Signed in successfully, show authenticated UI.
            Firebase_Authentication(account.getIdToken(),account.getDisplayName());
        }
        catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG"", "signInResult:failed code=" + e.getStatusCode());
            e.printStackTrace();
        }
    }
    private void Firebase_Authentication(String idToken,String name)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            dialog.dismiss();
                            //need to start MainApp Activity

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
}