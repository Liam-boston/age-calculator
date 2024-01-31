package edu.psu.sweng888.agecalculator;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AgeCalculator extends AppCompatActivity{
    // declare references to input fields
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText dateOfBirthEditText;
    private TextView dateFormatTextView;
    private Button calculateAgeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);

        // initialize references to input fields
        firstNameEditText = findViewById(R.id.firstNameInput);
        lastNameEditText = findViewById(R.id.lastNameInput);
        dateOfBirthEditText = findViewById(R.id.birthDateInput);
        dateFormatTextView = findViewById(R.id.dateFormat);
        calculateAgeButton = findViewById(R.id.calculateAgeButton);

        // onClick listener for calculateAgeButton
        calculateAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get input from dateOfBirthEditText
                String input = dateOfBirthEditText.getText().toString();

                // determine age
                int age = calculateAge(input);

                // display results
                if (age == -1) {
                    Toast.makeText(getApplicationContext(), ("Unable to calculate age, incorrect date format."), Toast.LENGTH_SHORT).show();
                }  else {
                    Toast.makeText(getApplicationContext(), ("Congratulations! You are " + age + " years old."), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // make dateFormatTextView visible
        dateOfBirthEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dateFormatTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Calculates age based on the date of birth provided and the current year
     *
     * @param input provided date of birth
     * @return calculated age
     */
    private static int calculateAge(String input) {
        // ensure input matches yyyy-MM-dd format
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // if input is in the correct format, calculate age
            if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                // Tue Jun 13 00:00:00 EDT 2000
                Date dob = formatter.parse(input);

                // Tue Jan 30 14:12:12 EST 2024
                Date today = Calendar.getInstance().getTime();

                // calculate age in days
                long difference = Math.abs(today.getTime() - dob.getTime());
                long differenceInDays = difference / (24 * 60 * 60 * 1000);

                // return age in years
                return (int)(differenceInDays / 365);
            }
        }  catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
}
