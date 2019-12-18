package com.example.signinwithgoogledemo.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.signinwithgoogledemo.R;
import com.example.signinwithgoogledemo.utils.AppConstants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private SignInButton googleSigninButton;
    private GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleSigninButton = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this,gso);

        googleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, AppConstants.REQUEST_CODE);
            }
        });

    }

    @Override
        protected void onStart() {
            super.onStart();

        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);

        if(alreadyloggedAccount != null)
        {
            Toast.makeText(getApplicationContext(), getString(R.string.already_logged_in),Toast.LENGTH_LONG).show();
            onLoggedIn(alreadyloggedAccount);
        }
        else
        {
            Log.e("TAG","Not Logged In...");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case AppConstants.REQUEST_CODE:
                    try {

                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);

                    }
                    catch (ApiException e)
                    {
                        e.printStackTrace();
                        Log.e("TAG","signInResult:failed code "+e.getStatusCode());
                    }

                    break;
            }
        }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount)
    {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(AppConstants.GOOGLE_ACCOUNT,googleSignInAccount);

        startActivity(intent);
        finish();
    }
}
