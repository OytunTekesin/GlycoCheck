package com.oytuntekesin.authenticationapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.ExerciseTable;
import com.oytuntekesin.authenticationapp.dto.Glyco;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExerciseActivity extends BaseActivity {
    String EXERCISE_HISTORY_ID = "";
    String EXERCISE_ID = "";
    String EXERCISE_NAME = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        _context = AddExerciseActivity.this;

        EditText editTextExerciseName = findViewById(R.id.editTextExerciseName);
        EditText editTextExerciseDuration = findViewById(R.id.editTextExerciseDuration);
        EditText editTextBurnedCalories = findViewById(R.id.editTextBurnedCalories);
        EditText editTextExerciseDescr = findViewById(R.id.editTextExerciseDescr);
        EditText editTextExerciseDateTime = findViewById(R.id.editTextExerciseDateTime);
        Button buttonSave = findViewById(R.id.buttonSaveExercise);

        EXERCISE_HISTORY_ID = "";
        EXERCISE_ID= "";
        String EXERCISE_CALORIES= "";
        String EXERCISE_DATETIME= "";
        String EXERCISE_DESCR= "";
        String EXERCISE_DURATION= "";
        Intent intent = getIntent();
        EXERCISE_ID = intent.getStringExtra("EXERCISE_ID");
        EXERCISE_NAME = intent.getStringExtra("EXERCISE_NAME");
        editTextExerciseName.setText(EXERCISE_NAME);
        if (intent != null) {
            EXERCISE_HISTORY_ID = intent.getStringExtra("EXERCISE_HISTORY_ID");
            EXERCISE_CALORIES = intent.getStringExtra("EXERCISE_CALORIES");
            EXERCISE_DATETIME = intent.getStringExtra("EXERCISE_DATETIME");
            EXERCISE_DESCR = intent.getStringExtra("EXERCISE_DESCR");
            EXERCISE_DURATION = intent.getStringExtra("EXERCISE_DURATION");
            if (EXERCISE_HISTORY_ID != null && !EXERCISE_HISTORY_ID.isEmpty()){
                buttonSave.setText("Düzenle");
                editTextExerciseDuration.setText(EXERCISE_DURATION);
                editTextBurnedCalories.setText(EXERCISE_CALORIES);
                editTextExerciseDescr.setText(EXERCISE_DESCR);
                editTextExerciseDateTime.setText(EXERCISE_DATETIME);
            }
        }
        editTextExerciseDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddExerciseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                                // Update the calendar with the selected date
                                calendar.set(Calendar.YEAR, selectedYear);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create TimePickerDialog to pick the time
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        AddExerciseActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                                // Update the calendar with the selected time
                                                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                                calendar.set(Calendar.MINUTE, selectedMinute);

                                                // Display the selected date and time
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                                                String selectedDateTime = dateFormat.format(calendar.getTime());
                                                editTextExerciseDateTime.setText(selectedDateTime);
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
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Burada gerekli verileri al ve kaydetme işlemini gerçekleştir
                String exerciseDuration = editTextExerciseDuration.getText().toString();
                String burnedCalories = editTextBurnedCalories.getText().toString();
                String exerciseDescr = editTextExerciseDescr.getText().toString();
                String exerciseDateTime = editTextExerciseDateTime.getText().toString();
                ExerciseHistory exercise = new ExerciseHistory();
                exercise.setEXERCISE_HISTORY_ID(EXERCISE_HISTORY_ID == null? "" : EXERCISE_HISTORY_ID);
                exercise.setEXERCISE_ID(EXERCISE_ID);
                exercise.setEXERCISE_DURATION(exerciseDuration);
                exercise.setEXERCISE_CALORIES(burnedCalories);
                exercise.setEXERCISE_DESCR(exerciseDescr);
                exercise.setEXERCISE_DATETIME(exerciseDateTime);
                exercise.setEXERCISE_NAME(EXERCISE_NAME);
                boolean isSuccess = _exerciseBusiness.setExerciseHistoryData(_context, exercise);
                if (isSuccess) {
                    Intent gecisYap = new Intent(getApplicationContext(), MainActivity.class);
                    gecisYap.putExtra("TAB", "1");
                    startActivity(gecisYap);
                }
            }
        });
    }

}