package com.example.recently;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthenticatePhonenumber
{
    private Context mContext;
    private String phonenumber;
    private String verificationCodeBySystem;
    private EditText codeText;
    private AlertDialog.Builder builder;
    public static CheckBox authenticated;
    private ProgressDialog Verifying;
    public AuthenticatePhonenumber(Context mContext, String phonenumber) {
        this.mContext = mContext;
        this.phonenumber = phonenumber;
        builder =  new AlertDialog.Builder(mContext);
        codeText =  new EditText(mContext);
        authenticated= new CheckBox(mContext);
        authenticated.setChecked(false);
        Verifying = new ProgressDialog(mContext);
        Verifying.setMessage("Verifying the phone number");
        Verifying.show();
    }
    public void sendVerificationCodeToUser() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+2" + phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                (Activity) mContext,   // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationCodeBySystem = s;
                    Verifying.dismiss();
                    builder.setMessage("Verify The Code")
                            .setView(codeText)
                            .setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    verifyCode(codeText.getText().toString());
                                }
                            }).show();
                }
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                }
                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            };
    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }
    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                                authenticated.setChecked(true);
                        } else
                        {
                            Toast.makeText(mContext, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
