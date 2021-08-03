package com.social.quotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.social.quotesapp.Adapter.PoetryAdapter;
import com.social.quotesapp.Api.ApiClient;
import com.social.quotesapp.Api.ApiInterface;
import com.social.quotesapp.Models.PoetryModel;
import com.social.quotesapp.Response.GetPoetryResponse;
import com.social.quotesapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    PoetryAdapter poetryAdapter;      // taking object of adapter
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);  // using our customised toolbar

        // we will call our api here
        Retrofit retrofit = ApiClient.getclient();  // we will call our static method here
        apiInterface = retrofit.create(ApiInterface.class); // we have initialised api interface
        getData();

    }

    private void SetAdapter(List<PoetryModel> poetryModels) {
        poetryAdapter = new PoetryAdapter(this, poetryModels); // passing context and list. to adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.poetryRecyclerview.setLayoutManager(linearLayoutManager);
        binding.poetryRecyclerview.setAdapter(poetryAdapter);
    }

    private void getData() // for api calling
    {
        // getpoetry defined in api interface
        apiInterface.getpoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {
                try {

                    if (response != null) {
                        if (response.body().getStatus().equals("1")) {  // data is coming
                            SetAdapter(response.body().getData());
                        } else {  // failure case
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "Serve is offline. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_property:
                Intent i=new Intent(MainActivity.this,AddPoetry.class);
                startActivity(i);
              //  Toast.makeText(this, "Add poetry clicked", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}