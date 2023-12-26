package com.oytuntekesin.authenticationapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oytuntekesin.authenticationapp.adapters.GlycoAdapter;
import com.oytuntekesin.authenticationapp.dto.Glyco;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GlycoFragment extends Fragment {
    public GlycoFragment() {
        // Boş kurucu metod gerekli
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment'ın görünümünü oluştur
        View rootView = inflater.inflate(R.layout.fragment_glyco, container, false);

        FloatingActionButton btnPetAdd;
        btnPetAdd = rootView.findViewById(R.id.pet_add);
        ArrayList<Glyco> allGlycoList = new ArrayList<Glyco>();

        Glyco glyco = new Glyco();
        glyco.setTarih("23.12.2023 18:30");
        glyco.setAciklama("Milkshake içtikten yarım saat sonra ölçtüm");
        glyco.setOlcumTuru("Açlık");
        glyco.setGlikozDegeri("135");
        allGlycoList.add(glyco);

        GlycoAdapter adapter_items = new GlycoAdapter(allGlycoList);

        RecyclerView recycler_view = rootView.findViewById(R.id.glycoItemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setAdapter(adapter_items);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        btnPetAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(getActivity().getApplicationContext(), DegerEkleActivity.class);
                startActivity(gecisYap);
            }
        });

        return rootView;
    }

}
