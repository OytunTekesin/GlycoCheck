package com.oytuntekesin.authenticationapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.oytuntekesin.authenticationapp.dto.Glyco;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddGlycoActivity extends BaseActivity {
    String GLYCO_ID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_glyco);
        _context = AddGlycoActivity.this;

        EditText editTextBloodSugar = findViewById(R.id.editTextBloodSugar);
        RadioGroup radioGroupMealStatus = findViewById(R.id.radioGroupMealStatus);
        RadioButton radioButtonFasting = findViewById(R.id.radioButtonFasting);
        RadioButton radioButtonFeeding = findViewById(R.id.radioButtonFeeding);
        TextView txtMealDuration = findViewById(R.id.textViewMealDuration);
        EditText editTextMealDuration = findViewById(R.id.editTextMealDuration);
        EditText editTextAdditionalNote = findViewById(R.id.editTextAdditionalNote);
        EditText editTextDateTime = findViewById(R.id.editTextDateTime);
        Button buttonSave = findViewById(R.id.buttonSave);

        GLYCO_ID = "";
        String ACIKLAMA= "";
        String ACLIK_SURESI= "";
        String ACLIK_TOKLUK= "";
        String KAN_SEKERI= "";
        String TARIH= "";
        Intent intent = getIntent();
        if (intent != null) {
            GLYCO_ID = intent.getStringExtra("GLYCO_ID");
            ACIKLAMA = intent.getStringExtra("ACIKLAMA");
            ACLIK_SURESI = intent.getStringExtra("ACLIK_SURESI");
            ACLIK_TOKLUK = intent.getStringExtra("ACLIK_TOKLUK");
            KAN_SEKERI = intent.getStringExtra("KAN_SEKERI");
            TARIH = intent.getStringExtra("TARIH");
            if (GLYCO_ID != null && !GLYCO_ID.isEmpty()){
                buttonSave.setText("Düzenle");
                if (ACLIK_TOKLUK.equals(radioButtonFeeding.getText().toString())) {
                    radioButtonFeeding.setChecked(true);
                    txtMealDuration.setVisibility(View.GONE);
                    editTextMealDuration.setVisibility(View.GONE);
                }
                if (ACLIK_TOKLUK.equals(radioButtonFasting.getText().toString())) {
                    radioButtonFasting.setChecked(true);
                    editTextMealDuration.setText(ACLIK_SURESI);
                    txtMealDuration.setVisibility(View.VISIBLE);
                    editTextMealDuration.setVisibility(View.VISIBLE);
                }
                editTextBloodSugar.setText(KAN_SEKERI);
                editTextAdditionalNote.setText(ACIKLAMA);
                editTextDateTime.setText(TARIH);
            }
        }
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
                        AddGlycoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                                // Update the calendar with the selected date
                                calendar.set(Calendar.YEAR, selectedYear);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create TimePickerDialog to pick the time
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        AddGlycoActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                                // Update the calendar with the selected time
                                                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                                calendar.set(Calendar.MINUTE, selectedMinute);

                                                // Display the selected date and time
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                                                String selectedDateTime = dateFormat.format(calendar.getTime());
                                                editTextDateTime.setText(selectedDateTime);
                                            }
                                        },
                                        hour,
                                        minute,
                                        true
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
                String datetime = editTextDateTime.getText().toString();
                Glyco glyco = new Glyco();
                glyco.setID(GLYCO_ID == null? "" : GLYCO_ID);
                glyco.setKAN_SEKERI(bloodSugar);
                glyco.setACLIK_TOKLUK(mealStatus);
                glyco.setACLIK_SURESI(mealDuration);
                glyco.setACIKLAMA(additionalNote);
                glyco.setTARIH(datetime);
                boolean isSuccess = _glycoBusiness.setGlycoData(_context, glyco);
                if (isSuccess) {
                    Intent gecisYap = new Intent(getApplicationContext(), MainActivity.class);
                    gecisYap.putExtra("TAB", "0");
                    startActivity(gecisYap);
                }
            }
        });
    }

}