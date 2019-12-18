package com.example.signinwithgoogledemo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.signinwithgoogledemo.R;
import com.example.signinwithgoogledemo.utils.AppConstants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

   // public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView tvProfileName,tvProfileEmail;
    private ImageView imgProfileImage;
    private Button btnSignout;

    private GoogleSignInClient googleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        imgProfileImage = findViewById(R.id.ivProfileImage);
        btnSignout = findViewById(R.id.btnLogout);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this,gso);

        setDataonView();

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }

    private void setDataonView()
    {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(AppConstants.GOOGLE_ACCOUNT);

        Picasso.with(ProfileActivity.this).load(googleSignInAccount.getPhotoUrl()).into(imgProfileImage);
        tvProfileName.setText(googleSignInAccount.getDisplayName());
        tvProfileEmail.setText(googleSignInAccount.getEmail());
    }
}
