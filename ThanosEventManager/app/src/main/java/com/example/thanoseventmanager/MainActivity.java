package com.example.thanoseventmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thanoseventmanager.api.GroupeHelper;
import com.example.thanoseventmanager.api.UserHelper;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CONNECTION";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String mVerificationID;
    User userToDelete;

    private boolean test = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickMap(View v) {
        //Launch the map view activity
        Intent intent = new Intent(this, MapViewActivity.class);
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

    // Bouton Se connecter
    public void onClick_Login(View v)
    {
        Log.i(TAG, "click on Se Connecter" + getLocalClassName()) ;
        String phoneNumber = "617205306" ;

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
            /* Gestion appui sur le bouton*/
            if (phoneNumber == "617205306")
            {
                Intent intent_Login = new Intent(this, MapViewActivity.class) ;
                startActivity(intent_Login);
            }
            else {
                // Modifier texte du bouton "button_seConnecter" en valider
                ((Button)findViewById(R.id.button_seConnecter)).setText("VALIDER") ;
                // Clear le champ de texte du phone number
                ((TextView)findViewById(R.id.editTextPhone)).setText("") ;
                ((TextView)findViewById(R.id.textView_33)).setText("") ;
                // Faire l'intent
                Intent intent_Login = new Intent(this, MapViewActivity.class) ;
                startActivity(intent_Login);
            }
        }



        /*if (mAuth.getCurrentUser() != null) {
            UserHelper.getMembreByID(mAuth.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User userFound = documentSnapshot.toObject(User.class);
                    userToDelete = userFound;
                    ((TextView)findViewById(R.id.TestTextView)).setText(userFound.getPseudo());
                    GroupeHelper.createGroupe("1533627384", "Thanos Corp", userFound).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Groupe Created SUCESSSS");
                        }
                    });
                }
            });

        } else {
            ManagePhoneAuthentification();
        }

        /*if (phoneNumber == "+33778798735")
        {
            Intent intent_Login = new Intent(this, MapViewActivity.class) ;
            startActivity(intent_Login);
        }
        else {
            // Modifier texte du bouton "button_seConnecter" en valider
            ((Button)findViewById(R.id.button_seConnecter)).setText("VALIDER") ;
            // Clear le champ de texte du phone number
            ((TextView)findViewById(R.id.editTextPhone)).setText("") ;
            // Faire l'intent
            Intent intent_Login = new Intent(this, MapViewActivity.class) ;
            startActivity(intent_Login);
        }*/

        // C'est pour l'authentification laisse ça en commentaire pour l'instant
        //ManagePhoneAuthentification();
    }

    private void ManagePhoneAuthentification() {
        mAuth = FirebaseAuth.getInstance();
        String phoneNumber = "+33778798735";
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
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "SignInWithCredentials : SUCCESS");
                            FirebaseUser user = task.getResult().getUser();

                            Log.d(TAG, "ID User = " + user.getUid());
                            UserHelper.createUser(user.getUid(), user.getPhoneNumber(), "Benoit").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                        UserHelper.getMembreByID(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                User userFound = documentSnapshot.toObject(User.class);
                                                ((TextView)findViewById(R.id.TestTextView)).setText(userFound.getPseudo());
                                                GroupeHelper.createGroupe("1533627384", "Thanos Corp").addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "Create Groupe Firestore fail");
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
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
        String SMSCode = ((EditText)findViewById(R.id.editSMSCode)).getText().toString();
        //GET EDIT TEXT
        /*String SMSCode = ((EditText)findViewById(R.id.editSMSCode)).getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, SMSCode);
        signInWithPhoneAuthCredential(credential);*/
    }
}