package com.oytuntekesin.authenticationapp.adapters;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.dto.Glyco;

import java.util.List;

public class GlycoAdapter extends RecyclerView.Adapter<GlycoAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView circle;
        public TextView toklukTextView;
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView descriptionTextView;


        public ViewHolder(View view) {
            super(view);

            cardView = (CardView)view.findViewById(R.id.gylcoCard);
            circle = (TextView) view.findViewById(R.id.circleView);
            toklukTextView = (TextView)view.findViewById(R.id.toklukTextView);
            dateTextView = (TextView)view.findViewById(R.id.dateTextView);
            timeTextView = (TextView)view.findViewById(R.id.timeTextView);
            descriptionTextView = (TextView)view.findViewById(R.id.descriptionTextView);
        }
    }

    List<Glyco> gylcoList;
    public GlycoAdapter(List<Glyco> gylcoList) {

        this.gylcoList = gylcoList;
    }
    @Override
    public GlycoAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.glyco_card, parent, false);
        final GlycoAdapter.ViewHolder view_holder = new GlycoAdapter.ViewHolder(v);
        v.setTag(view_holder.getPosition());
        return view_holder;
    }


    @Override
    public void onBindViewHolder(final GlycoAdapter.ViewHolder holder, final int position) {
        holder.circle.setText(gylcoList.get(position).getGlikozDegeri());
        holder.toklukTextView.setText(gylcoList.get(position).getOlcumTuru());
        holder.dateTextView.setText(gylcoList.get(position).getTarih().split(" ")[0]);
        holder.timeTextView.setText(gylcoList.get(position).getTarih().split(" ")[1]);
        holder.descriptionTextView.setText(gylcoList.get(position).getAciklama());

    }

    @Override
    public int getItemCount() {
        return gylcoList.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}