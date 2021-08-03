package com.social.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.social.quotesapp.Api.ApiClient;
import com.social.quotesapp.Api.ApiInterface;
import com.social.quotesapp.Response.DeleteResponse;
import com.social.quotesapp.databinding.ActivityAddPoetryBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {
    ActivityAddPoetryBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddPoetryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit= ApiClient.getclient(); // get client is a method from ApiClient class which will return a retrofit object , we will store
        apiInterface=retrofit.create(ApiInterface.class);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.submitDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryData = binding.addPoetryEdittext.getText().toString();
                String poetName = binding.addPoetNameEdittext.getText().toString();

                if(poetryData.isEmpty())
                {
                    binding.addPoetryEdittext.setError("Field is Empty");
                    return;
                }
                if(poetName.isEmpty())
                {
                    binding.addPoetNameEdittext.setError("Field is Empty");
                    return;
                }

                callApi(poetryData, poetName);
                //Toast.makeText(AddPoetry.this,"",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private  void callApi(String poetryData, String poetName)
    {
        apiInterface.addpoetry(poetryData,poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
              try{
                  if(response.body().getStatus().equals("1"))
                  {
                      Toast.makeText(AddPoetry.this,"Added Successfully.",Toast.LENGTH_SHORT).show();

                  }
                  else
                  {
                      Toast.makeText(AddPoetry.this,"Not Added Successfully.",Toast.LENGTH_SHORT).show();

                  }
              }
              catch(Exception e)
              {
                  Log.e("failure", ""+e.getLocalizedMessage());
              }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failure", ""+t.getLocalizedMessage());
            }
        });
    }
}