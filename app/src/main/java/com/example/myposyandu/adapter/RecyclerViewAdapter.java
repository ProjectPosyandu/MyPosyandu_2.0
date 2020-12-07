package com.example.myposyandu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.activity.DetailBayiActivity;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataBayi;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<ModelDataBayi> ListData;

    SharedPrefManager sharedPrefManager;

    public RecyclerViewAdapter(Context ctx, List<ModelDataBayi> listData) {
        this.ctx = ctx;
        ListData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desain_layout_adapter, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelDataBayi dm = ListData.get(position);

        RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.mipmap.ic_launcher_round);
            requestOptions.error(R.mipmap.ic_launcher_round);
            Glide.with(ctx)
                    .load(UtilsApi.BASE_URL_API+dm.getFoto_bayi())
                    .apply(requestOptions)
                    .into(holder.imageView2);

        holder.tvnamaBayi.setText(dm.getNama_bayi());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailBayiActivity.class);
                intent.putExtra("id_bayi", dm.getId_bayi());
                intent.putExtra("nama_bayi", dm.getNama_bayi());
                intent.putExtra("jenis_kelamin", dm.getJenis_kelamin());
                intent.putExtra("tgl_lahir", dm.getTgl_lahir());
                intent.putExtra("id_bayi", dm.getId_bayi());

                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
//        return namaBayi.size();
        return ListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView2;
        TextView tvnamaBayi;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView2 = itemView.findViewById(R.id.imageView2);
            tvnamaBayi = itemView.findViewById(R.id.tvNamaBayi);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
