package com.social.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.social.quotesapp.Api.ApiClient;
import com.social.quotesapp.Api.ApiInterface;
import com.social.quotesapp.Response.DeleteResponse;
import com.social.quotesapp.databinding.ActivityUpdatePoetryBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {
    ActivityUpdatePoetryBinding binding;
    String poetryData;
    int poetryId;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdatePoetryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit= ApiClient.getclient();
        apiInterface=retrofit.create(ApiInterface.class);

        poetryId=getIntent().getIntExtra("p_id",0); // 0 is default value is value is null
        poetryData=getIntent().getStringExtra("p_data");

        binding.addUpdatedPoetryEdittext.setText(poetryData); // showing data

        binding.clearField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addUpdatedPoetryEdittext.setText(""); // clearing data
            }
        });

        binding.updatebackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.updateSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_data=binding.addUpdatedPoetryEdittext.getText().toString();
                if(p_data.isEmpty())
                {
                    binding.addUpdatedPoetryEdittext.setError("Field is empty.");
                }
                 callApi(p_data,poetryId+"");
            }
        });
    }

    private void callApi(String p_data, String p_id)
    {
        apiInterface.updatepoetry(p_data, p_id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
              try{
                  if(response.body().getStatus().equals("1"))
                  {
                      Toast.makeText(UpdatePoetry.this,"Data updated", Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                      Toast.makeText(UpdatePoetry.this,"Data not updated", Toast.LENGTH_SHORT).show();
                  }
              }catch (Exception e)
              {
                  Log.e("Update Exp",e.getLocalizedMessage());
              }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("Update Failed",t.getLocalizedMessage());
            }
        });

    }
}