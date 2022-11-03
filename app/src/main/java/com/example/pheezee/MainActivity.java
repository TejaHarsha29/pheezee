package com.example.pheezee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pheezee.databinding.ActivityMainBinding;

import java.net.InetSocketAddress;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.editText2.getText().toString().length()!=10){
                    Toast.makeText(MainActivity.this, "Enter a valid Number", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this,OtpActivity.class);
                    intent.putExtra("number",binding.editText2.getText().toString());
                    startActivity(intent);
                    finish();
                }


            }
        });






    }
}