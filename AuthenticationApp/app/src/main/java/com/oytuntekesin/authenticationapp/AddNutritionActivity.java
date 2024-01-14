package com.oytuntekesin.authenticationapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.dto.Glyco;
import com.oytuntekesin.authenticationapp.dto.Nutrition;
import com.oytuntekesin.authenticationapp.dto.User;
import com.oytuntekesin.authenticationapp.helpers.NotificationHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddNutritionActivity extends BaseActivity {
    String NUTRITION_ID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nutrition);
        _context = AddNutritionActivity.this;

        EditText editTextMealDuration = findViewById(R.id.editTextMealDuration);
        EditText editTextDietName = findViewById(R.id.editTextDietName);
        EditText editTextDietContent = findViewById(R.id.editTextDietContent);
        Spinner spinNutritionUser = findViewById(R.id.spinNutritionUser);
        Button buttonMealSave = findViewById(R.id.buttonMealSave);
        Button buttonMealDelete = findViewById(R.id.buttonMealDelete);

        List<User> users = new ArrayList<User>();
        _db.collection("USER_TABLE").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User cd = d.toObject(User.class);
                                users.add(cd);
                            }
                            ArrayAdapter<User> adapter = new ArrayAdapter<>(AddNutritionActivity.this, android.R.layout.simple_spinner_dropdown_item, users);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinNutritionUser.setAdapter(adapter);

                            Intent intent = getIntent();
                            String NUTRITION_NAME = "";
                            String NUTRITION_DURATION = "";
                            String NUTRITION_CONTENT = "";
                            String USER_ID = "";

                            if (intent != null) {
                                NUTRITION_ID = intent.getStringExtra("NUTRITION_ID");
                                NUTRITION_NAME = intent.getStringExtra("NUTRITION_NAME");
                                NUTRITION_DURATION = intent.getStringExtra("NUTRITION_DURATION");
                                NUTRITION_CONTENT = intent.getStringExtra("NUTRITION_CONTENT");
                                USER_ID = intent.getStringExtra("USER_ID");
                                if (NUTRITION_ID != null && !NUTRITION_ID.isEmpty()){
                                    editTextDietName.setText(NUTRITION_NAME);
                                    editTextMealDuration.setText(NUTRITION_DURATION);
                                    editTextDietContent.setText(NUTRITION_CONTENT);
                                    for(int i = 0; i < users.size(); i++){
                                        if (users.get(i).getUSER_ID().equals(USER_ID)){
                                            spinNutritionUser.setSelection(i);
                                        }
                                    }

                                }
                            }
                        } else {
                            Toast.makeText(_context, "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(_context, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
        editTextMealDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddNutritionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                                // Update the calendar with the selected date
                                calendar.set(Calendar.YEAR, selectedYear);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create TimePickerDialog to pick the time
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        AddNutritionActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                                // Update the calendar with the selected time
                                                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                                calendar.set(Calendar.MINUTE, selectedMinute);

                                                // Display the selected date and time
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                                                String selectedDateTime = dateFormat.format(calendar.getTime());
                                                editTextMealDuration.setText(selectedDateTime);
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
        // Kaydet butonuna tıklama işlemini ekle
        buttonMealSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Burada gerekli verileri al ve kaydetme işlemini gerçekleştir
                String dietName = editTextDietName.getText().toString();
                String dietContent = editTextDietContent.getText().toString();
                String mealDuration = editTextMealDuration.getText().toString();

                Nutrition nutrition = new Nutrition();
                nutrition.setNUTRITION_ID(NUTRITION_ID == null? "" : NUTRITION_ID);
                nutrition.setNUTRITION_NAME(dietName);
                nutrition.setNUTRITION_DURATION(mealDuration);
                nutrition.setNUTRITION_CONTENT(dietContent);
                User user = (User) spinNutritionUser.getSelectedItem();
                nutrition.setUSER_ID(user.getUSER_ID());
                boolean isSuccess = _nutritionBusiness.setNutritionData(_context, nutrition);
                if (isSuccess) {
                    Intent gecisYap = new Intent(getApplicationContext(), MainActivity.class);
                    gecisYap.putExtra("TAB", "2");
                    startActivity(gecisYap);
                }
            }
        });
        buttonMealDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSuccess = _nutritionBusiness.delNutritionData(_context, NUTRITION_ID);
                if (isSuccess) {
                    Intent gecisYap = new Intent(getApplicationContext(), MainActivity.class);
                    gecisYap.putExtra("TAB", "2");
                    startActivity(gecisYap);
                }
            }
        });
    }

}