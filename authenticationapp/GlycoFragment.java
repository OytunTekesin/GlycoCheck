package com.oytuntekesin.authenticationapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
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

        // Gerekli kontrolleri bul
        EditText editTextBloodSugar = rootView.findViewById(R.id.editTextBloodSugar);
        RadioGroup radioGroupMealStatus = rootView.findViewById(R.id.radioGroupMealStatus);
        EditText editTextMealDuration = rootView.findViewById(R.id.editTextMealDuration);
        EditText editTextAdditionalNote = rootView.findViewById(R.id.editTextAdditionalNote);
        Button buttonSave = rootView.findViewById(R.id.buttonSave);

        // Kaydet butonuna tıklama işlemini ekle
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Burada gerekli verileri al ve kaydetme işlemini gerçekleştir
                String bloodSugar = editTextBloodSugar.getText().toString();
                int mealStatusId = radioGroupMealStatus.getCheckedRadioButtonId();
                String mealStatus = (mealStatusId == R.id.radioButtonFasting) ? "Açlık" : "Tokluk";
                String mealDuration = editTextMealDuration.getText().toString();
                String additionalNote = editTextAdditionalNote.getText().toString();

                // Verileri kullanarak kaydetme işlemini gerçekleştir
                kaydet(bloodSugar, mealStatus, mealDuration, additionalNote);
            }
        });

        return rootView;
    }

    private void kaydet(String bloodSugar, String mealStatus, String mealDuration, String additionalNote) {
        // Burada verileri kaydetme işlemini gerçekleştir
        // Verileri örneğin bir veritabanına veya başka bir depolama yöntemine kaydedebilirsiniz
    }

}
