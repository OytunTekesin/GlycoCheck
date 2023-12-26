package com.oytuntekesin.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DegerEkleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deger_ekle);

        // Gerekli kontrolleri bul
        EditText editTextBloodSugar = findViewById(R.id.editTextBloodSugar);
        RadioGroup radioGroupMealStatus = findViewById(R.id.radioGroupMealStatus);
        TextView txtMealDuration = findViewById(R.id.textViewMealDuration);
        EditText editTextMealDuration = findViewById(R.id.editTextMealDuration);
        EditText editTextAdditionalNote = findViewById(R.id.editTextAdditionalNote);
        EditText editTextDateTime = findViewById(R.id.editTextDateTime);
        Button buttonSave = findViewById(R.id.buttonSave);

        editTextDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DegerEkleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                                // Update the calendar with the selected date
                                calendar.set(Calendar.YEAR, selectedYear);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create TimePickerDialog to pick the time
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        DegerEkleActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                                // Update the calendar with the selected time
                                                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                                calendar.set(Calendar.MINUTE, selectedMinute);

                                                // Display the selected date and time
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                                String selectedDateTime = dateFormat.format(calendar.getTime());
                                                editTextDateTime.setText(selectedDateTime);
                                            }
                                        },
                                        hour,
                                        minute,
                                        false
                                );

                                // Show the TimePickerDialog
                                timePickerDialog.show();
                            }
                        },
                        year,
                        month,
                        day
                );

                // Show the DatePickerDialog
                datePickerDialog.show();

                datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", datePickerDialog);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePickerDialog);
                datePickerDialog.show();
            }
        });
        radioGroupMealStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButtonFasting) {
                    editTextMealDuration.setVisibility(View.VISIBLE);
                    txtMealDuration.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioButtonFeeding) {
                    editTextMealDuration.setVisibility(View.GONE);
                    txtMealDuration.setVisibility(View.GONE);
                }
            }
        });
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
    }

    private void kaydet(String bloodSugar, String mealStatus, String mealDuration, String additionalNote) {
        // Burada verileri kaydetme işlemini gerçekleştir
        // Verileri örneğin bir veritabanına veya başka bir depolama yöntemine kaydedebilirsiniz
        finish();
    }

}