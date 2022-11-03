package com.example.pheezee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.pheezee.databinding.ActivityLoginBinding;
import com.example.pheezee.databinding.ActivityOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    ActivityOtpBinding binding;

    FirebaseAuth mAuth;
    String number;

    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        number = getIntent().getStringExtra("number");

        binding.numberwithtext.setText("We have sent an SMS on +91"+number+" with 4 digit verification code");

        mAuth = FirebaseAuth.getInstance();

        initiateOtp();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.otpEntry.getText().toString().length()!=6){
                    Toast.makeText(OtpActivity.this, "Wrong Otp", Toast.LENGTH_SHORT).show();
                }
                else{
                    verifyCode(binding.otpEntry.getText().toString());
                }
            }
        });







    }

    private void initiateOtp() {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+number)       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId,token);

            mVerificationId = verificationId;


        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        signinByCredential(credential);
    }

    private void signinByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(OtpActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OtpActivity.this,DashBoardActivity.class));
                    finish();
                }
            }
        });

    }
}