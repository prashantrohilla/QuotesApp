package com.social.quotesapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.social.quotesapp.Api.ApiClient;
import com.social.quotesapp.Api.ApiInterface;
import com.social.quotesapp.Models.PoetryModel;
import com.social.quotesapp.R;
import com.social.quotesapp.Response.DeleteResponse;
import com.social.quotesapp.UpdatePoetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder> {
    Context context;
    List<PoetryModel> poetryModel;
    ApiInterface apiInterface;       // we are defining it globally so we can use it anywhere

    public PoetryAdapter(Context context, List<PoetryModel> poetryModel) {
        this.context = context;
        this.poetryModel = poetryModel;
        Retrofit retrofit = ApiClient.getclient();  // we want to initialise api Interface when we call this constructor
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // here we return a new view containing poetry design
        View view = LayoutInflater.from(context).inflate(R.layout.poetry_list_design, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoetryAdapter.ViewHolder holder, int position) { // here we set all views data

        holder.poetName.setText(poetryModel.get(position).getPoet_name());
        holder.poetry.setText(poetryModel.get(position).getPoetry_data());
        holder.date_time.setText(poetryModel.get(position).getDate_time());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deletepoetry(poetryModel.get(position).getId()+"", position);
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, UpdatePoetry.class);
                i.putExtra("p_id",poetryModel.get(position).getId());
                i.putExtra("p_data",poetryModel.get(position).getPoetry_data());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poetryModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // initialisation of components
        TextView poetName, poetry, date_time;
        AppCompatButton update, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poetName = itemView.findViewById(R.id.textview_poetName);
            poetry = itemView.findViewById(R.id.textview_poetryData);
            date_time = itemView.findViewById(R.id.textview_poetryDate);
            update = itemView.findViewById(R.id.update_btn);
            delete = itemView.findViewById(R.id.delete_btn);
        }
    }

    private void deletepoetry(String id, int pos)  // method for delete poetry
    {
        apiInterface.deletepoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                   if(response!=null)
                   {
                       Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_LONG).show();
                       if(response.body().getStatus().equals("1"))
                       {
                          poetryModel.remove(pos);  // after we delete a poetry it should also get remove from our activity
                          notifyDataSetChanged();
                       }
                   }
                } catch (Exception e) {
                    Log.e("failure", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}
