package com.oytuntekesin.authenticationapp.adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.oytuntekesin.authenticationapp.AddGlycoActivity;
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
    int p = 0;

    @Override
    public void onBindViewHolder(final GlycoAdapter.ViewHolder holder, final int position) {
        String dbTarih = gylcoList.get(position).getTARIH().split(" ")[0];
        String[] tarihs = dbTarih.split("\\.");
        String tarih = "";
        switch (tarihs[1]){
            case "01":
                tarih = tarihs[0] + " Ocak " + tarihs[2];
                break;
            case "02":
                tarih = tarihs[0] + " Şubat "+ tarihs[2];
                break;
            case "03":
                tarih = tarihs[0] + " Mart "+ tarihs[2];
                break;
            case "04":
                tarih = tarihs[0] + " Nisan "+ tarihs[2];
                break;
            case "05":
                tarih = tarihs[0] + " Mayıs "+ tarihs[2];
                break;
            case "06":
                tarih = tarihs[0] + " Haziran "+ tarihs[2];
                break;
            case "07":
                tarih = tarihs[0] + " Temmuz "+ tarihs[2];
                break;
            case "08":
                tarih = tarihs[0] + " Ağustos "+ tarihs[2];
                break;
            case "09":
                tarih = tarihs[0] + " Eylül "+ tarihs[2];
                break;
            case "10":
                tarih = tarihs[0] + " Ekim "+ tarihs[2];
                break;
            case "11":
                tarih = tarihs[0] + " Kasım "+ tarihs[2];
                break;
            case "12":
                tarih = tarihs[0] + " Aralık "+ tarihs[2];
                break;
            default:
                tarih = dbTarih;
                break;
        }
        holder.circle.setText(gylcoList.get(position).getKAN_SEKERI());
        Drawable drawable = ContextCompat.getDrawable(holder.circle.getContext(), R.drawable.circle_green_background);
        if (80 <= Integer.parseInt(gylcoList.get(position).getKAN_SEKERI()) && Integer.parseInt(gylcoList.get(position).getKAN_SEKERI()) <= 120){
            drawable = ContextCompat.getDrawable(holder.circle.getContext(), R.drawable.circle_green_background);
        }else if ((70 <= Integer.parseInt(gylcoList.get(position).getKAN_SEKERI()) && Integer.parseInt(gylcoList.get(position).getKAN_SEKERI()) <= 80)|| (120 < Integer.parseInt(gylcoList.get(position).getKAN_SEKERI()) && Integer.parseInt(gylcoList.get(position).getKAN_SEKERI()) < 140)){
            drawable = ContextCompat.getDrawable(holder.circle.getContext(), R.drawable.circle_orange_background);
        }else {
            drawable = ContextCompat.getDrawable(holder.circle.getContext(), R.drawable.circle_red_background);
        }
        holder.circle.setBackground(drawable);
        holder.toklukTextView.setText(gylcoList.get(position).getACLIK_TOKLUK());
        holder.dateTextView.setText(tarih);
        holder.timeTextView.setText(gylcoList.get(position).getTARIH().split(" ")[1]);
        holder.descriptionTextView.setText(gylcoList.get(position).getACIKLAMA());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.cardView.getContext(), AddGlycoActivity.class);
                intent.putExtra("GLYCO_ID", gylcoList.get(position).getID());
                intent.putExtra("ACIKLAMA", gylcoList.get(position).getACIKLAMA());
                intent.putExtra("ACLIK_SURESI", gylcoList.get(position).getACLIK_SURESI());
                intent.putExtra("ACLIK_TOKLUK", gylcoList.get(position).getACLIK_TOKLUK());
                intent.putExtra("KAN_SEKERI", gylcoList.get(position).getKAN_SEKERI());
                intent.putExtra("TARIH", gylcoList.get(position).getTARIH());
                holder.cardView.getContext().startActivity(intent);
            }
        });
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