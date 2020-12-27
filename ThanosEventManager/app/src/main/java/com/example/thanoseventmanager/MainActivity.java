package com.example.thanoseventmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CONNECTION";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String mVerificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickMap(View v){
        //Launch the map view activity
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }

    // Bouton Se connecter
    public void onClick_Login(View v)
    {
        Log.i(TAG, "click on Se Connecter" + getLocalClassName()) ;

        /* Gestion appui sur le bouton*/
        /* Intent intent_Login = new Intent(this, MainAfterLogin.class) ;
            startActivity(intent_Login) ;
         */
        ManagePhoneAuthentification();
    }

    private void ManagePhoneAuthentification() {
        String phoneNumber = "+33778798735";
        String smsCode = "123456";
        mAuth.setLanguageCode("fr");
        Log.d(TAG, "Manage Authentification");

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                mVerificationID = s;
                                Log.d(TAG, "On Code Sent" + mVerificationID);
                                super.onCodeSent(s, forceResendingToken);
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Log.d(TAG, "On Verification Complete");
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.w(TAG, "onVerificationFailed", e);

                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                    // Invalid request
                                    // ...
                                } else if (e instanceof FirebaseTooManyRequestsException) {
                                    // The SMS quota for the project has been exceeded
                                    // ...
                                }
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "SignInWithCredentials : SUCCESS");
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            Log.w(TAG, "SignInWithCredentials : FAILURE");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.d(TAG, "Code Incorrect");
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void OnClickValidCode(View v) {
        //GET EDIT TEXT
        //String SMSCode = ((EditText)findViewById(R.id.editText_SMSCode)).getText().toString();
        //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, SMSCode);
        //signInWithPhoneAuthCredential(credential);
    }
}