package com.example.taskproject2022;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.Calendar;

public class CreateTask extends Activity implements OnClickListener
{
    private String[] priority_level = new String[]{"Low", "Medium", "High"};

    private Button create_button;
    private EditText desc;
    private EditText desc_date;
    private ImageButton date_button;
    private DatePickerDialog date_picker_dialog;
    private TextView text_view;
    private RadioGroup status;
    private Spinner spinner;
    private ImageButton return_home;

    private DataBaseHelper dbHelper;

    private int flag_radio_done_choose;
    private String priority_string_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        dbHelper = new DataBaseHelper(this);

        create_button = (Button)findViewById(R.id.create);
        desc = (EditText)findViewById(R.id.edit_description);
        date_button = (ImageButton) findViewById(R.id.button_date);
        text_view = (TextView)findViewById(R.id.selected_date);
        status = (RadioGroup)findViewById(R.id.status_radio_group);
        spinner = (Spinner)findViewById(R.id.priority_to_select);
        return_home = (ImageButton) findViewById(R.id.return_home);

        date_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int mon = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog select_date = new DatePickerDialog(CreateTask.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        text_view.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, year, mon, day);

                select_date.show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                priority_string_choose = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priority_level);
        spinner.setAdapter(adapter);

        create_button.setOnClickListener(this);
        return_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.create)
        {
            String description = desc.getText().toString();

            if(desc.getText().toString().isEmpty())
            {
                description = "new task";
            }

            String date = text_view.getText().toString();
            flag_radio_done_choose = status.getCheckedRadioButtonId();

            RadioButton radio_button = (RadioButton)findViewById(flag_radio_done_choose);
            dbHelper.insertTasks(date, description, priority_string_choose, radio_button.getText().toString());
            Intent intent_save = new Intent(CreateTask.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_save);
        }

        else if (v.getId() == R.id.return_home)
        {
            Intent intent_home = new Intent(CreateTask.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_home);
        }

        else if (v.getId() == R.id.button_date)
        {
            text_view.setText("Chosen Date is: " + desc_date.getText());
        }
    }
}
