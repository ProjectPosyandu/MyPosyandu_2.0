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

import com.example.myposyandu.R;
import com.example.myposyandu.activity.DetailArtikelActivity;
import com.example.myposyandu.model.ModelDataArtikel;

import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ViewHolder> {

    private Context ctx;
    private List<ModelDataArtikel> ListData;

    public ArtikelAdapter(Context ctx, List<ModelDataArtikel> listData) {
        this.ctx = ctx;
        ListData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desain_layout_artikel, parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelDataArtikel dm = ListData.get(position);
        holder.tvJudul.setText(dm.getJudul_artikel());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailArtikelActivity.class);
                intent.putExtra("id_artikel", dm.getId_artikel());
                intent.putExtra("judul_artikel", dm.getJudul_artikel());
                intent.putExtra("isi_artikel", dm.getIsi_artikel());
                intent.putExtra("penulis", dm.getPenulis());
                ctx.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvJudul;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tvJudul);
            constraintLayout = itemView.findViewById(R.id.constraintLayout2);
        }
    }
}
