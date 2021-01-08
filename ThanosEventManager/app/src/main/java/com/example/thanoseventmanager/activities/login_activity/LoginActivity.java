package com.example.thanoseventmanager.activities.login_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanoseventmanager.activities.main_activity.MainActivity;
import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.firebase.UserHelper;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "CONNECTION";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String mVerificationID;

    boolean isCodeSend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (mAuth.getCurrentUser() != null) {
            goToMapView();
        }
    }

    public void onClickMap(View v) {
        //Launch the map view activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "on start" + getLocalClassName()) ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "on stop" + getLocalClassName()) ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "on destroy" + getLocalClassName()) ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "on pause" + getLocalClassName()) ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "on resume" + getLocalClassName()) ;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "on restart" + getLocalClassName()) ;
    }

    private void goToMapView() {
        Intent intent_Login = new Intent(this, MainActivity.class) ;
        startActivity(intent_Login);
    }

    // Bouton Se connecter
    public void onClick_Login(View v) {

        //Shunt pcq Benoit a tout pété
        //goToMapView();

        if (!isCodeSend) {
            SeConnecter();
        } else {
            ValidationCode();
        }

    }

    private void SeConnecter() {
        Log.i(TAG, "click on Se Connecter" + getLocalClassName()) ;
        String phoneNumber = ((EditText)findViewById(R.id.editText_LoginActivity)).getText().toString();

        // Vérification phoneNumber
        if (phoneNumber.length() != 9)
        {
            AlertDialog.Builder ErrorMsg = new AlertDialog.Builder(this);
            ErrorMsg.setMessage("Erreur dans la saisie du numéro de téléphone")
                    .setTitle("Erreur");
            ErrorMsg.create();
            ErrorMsg.show();
        }
        else {
            phoneNumber = "+33" + phoneNumber;
            ManagePhoneAuthentification(phoneNumber);
        }
    }

    private void ValidationCode() {
        Log.i(TAG, "click on Validation Code " + getLocalClassName()) ;
        String SMSCode = ((EditText)findViewById(R.id.editText_LoginActivity)).getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, SMSCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void ManagePhoneAuthentification(String phoneNumber) {
        mAuth = FirebaseAuth.getInstance();
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
                                isCodeSend = true;
                                Log.d(TAG, "On Code Sent" + mVerificationID);
                                Toast.makeText(getApplicationContext(), "Code envoyé !", Toast.LENGTH_LONG).show();
                                updateUI();
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

                            @Override
                            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                Log.d(TAG, "On Code Timeout");
                                super.onCodeAutoRetrievalTimeOut(s);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Context context = this;
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "SignInWithCredentials : SUCCESS");
                            FirebaseUser user = task.getResult().getUser();
                            Log.d(TAG, "ID User = " + user.getUid());

                            UserHelper.getUserByID(user.getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User userFound = documentSnapshot.toObject(User.class);
                                    if (userFound == null) {
                                        Log.d(TAG, "User not found ");
                                        UserHelper.createUser(user.getUid(), user.getPhoneNumber(), "");
                                    } else {
                                        Log.d(TAG, "User found ");
                                    }
                                    isCodeSend = false;
                                    updateUI();
                                }
                            });
                            goToMapView();
                        } else {
                            Log.w(TAG, "SignInWithCredentials : FAILURE");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.d(TAG, "Code Incorrect");
                                // The verification code entered was invalid
                                AlertDialog.Builder ErrorMsg = new AlertDialog.Builder(context);
                                ErrorMsg.setMessage("Code incorrect !")
                                        .setTitle("Erreur");
                                ErrorMsg.create();
                                ErrorMsg.show();
                            }
                        }
                    }
                });
    }

    private void updateUI() {
        if (!isCodeSend) {
            // Modifier texte du bouton "button_seConnecter" en valider
            ((Button)findViewById(R.id.btn_LoginActivity)).setText("SE CONNECTER") ;
            // Clear le champ de texte du phone number
            ((TextView)findViewById(R.id.editText_LoginActivity)).setText("") ;
            ((TextView)findViewById(R.id.phoneIndicator_LoginActivity)).setText("+33") ;
            ((TextView)findViewById(R.id.Title_LoginActivity)).setText("Entrez votre numéro de téléphone");
        } else {
            // Modifier texte du bouton "button_seConnecter" en valider
            ((Button)findViewById(R.id.btn_LoginActivity)).setText("VALIDER") ;
            // Clear le champ de texte du phone number
            ((TextView)findViewById(R.id.editText_LoginActivity)).setText("") ;
            ((TextView)findViewById(R.id.phoneIndicator_LoginActivity)).setText("") ;
            ((TextView)findViewById(R.id.Title_LoginActivity)).setText("Entrez le code reçu par SMS");
        }
    }
}