package com.oytuntekesin.authenticationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NutritionContentActivity extends BaseActivity {

    TextView textViewDietName, textViewEndDate, textViewDietContent;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_content);
        _context = NutritionContentActivity.this;

        textViewDietName = findViewById(R.id.textViewDietName);
        textViewEndDate = findViewById(R.id.textViewEndDate);
        textViewDietContent = findViewById(R.id.textViewDietContent);
        Intent intent = getIntent();
        String NUTRITION_ID = "";
        String NUTRITION_NAME = "";
        String NUTRITION_DURATION = "";
        String NUTRITION_CONTENT = "";
        if (intent != null) {
            NUTRITION_ID = intent.getStringExtra("NUTRITION_ID");
            NUTRITION_NAME = intent.getStringExtra("NUTRITION_NAME");
            NUTRITION_DURATION = intent.getStringExtra("NUTRITION_DURATION");
            NUTRITION_CONTENT = intent.getStringExtra("NUTRITION_CONTENT");
            if (NUTRITION_ID != null && !NUTRITION_ID.isEmpty()){
                textViewDietName.setText("Diyet Başlığı: " + NUTRITION_NAME);
                textViewEndDate.setText("Bitiş Tarihi: " +NUTRITION_DURATION);
                textViewDietContent.setText(NUTRITION_CONTENT);
            }
        }
    }
}