package com.example.myposyandu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myposyandu.R;
import com.example.myposyandu.activity.DetailJadwalActivity;
import com.example.myposyandu.model.ModelDataJadwal;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    private Context ctx;
    private List<ModelDataJadwal> ListData;

    public JadwalAdapter(Context ctx, List<ModelDataJadwal> listData) {
        this.ctx = ctx;
        ListData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desain_layout_jadwal, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelDataJadwal dm = ListData.get(position);
        holder.tvNama.setText("Jadwal Imunisasi "+dm.getNama_imunisasi());
        holder.tvTanggal.setText("Tanggal :"+dm.getTgl_imunisasi()+", Pukul : "+dm.getWaktu());
        if (dm.getStatus().equals("sudah")){
            holder.layout.setBackgroundResource(R.drawable.style_list);
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailJadwalActivity.class);
                intent.putExtra("id_jadwal", dm.getId_jadwal());
                intent.putExtra("nama_imunisasi", dm.getNama_imunisasi());
                intent.putExtra("tanggal", dm.getTgl_imunisasi());
                intent.putExtra("waktu", dm.getWaktu());
                intent.putExtra("status", dm.getStatus());
                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNama, tvTanggal;
        ConstraintLayout constraintLayout;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.namaImunisasi);
            tvTanggal = itemView.findViewById(R.id.tglImunisasi);
            constraintLayout = itemView.findViewById(R.id.constraintLayout3);
            layout = itemView.findViewById(R.id.layoutJadwal);
        }
    }
}
