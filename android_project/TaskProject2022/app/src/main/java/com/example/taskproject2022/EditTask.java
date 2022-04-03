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

import java.util.Calendar;

public class EditTask extends Activity implements View.OnClickListener
{
    private String[] priority_level = new String[]{"Low", "Medium", "High"};

    private Button edit_button, delete_button;
    private EditText desc, desc_date;
    private ImageButton date_button, return_home;
    private DatePickerDialog date_picker_dialog;
    private TextView text_view;
    private RadioGroup status;
    private Spinner spinner;

    private int flag_radio_done_choose;
    private String priority_string_choose;
    private long _id;

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_update);

        dbHelper = new DataBaseHelper(this);

        edit_button = (Button)findViewById(R.id.update);
        delete_button = (Button)findViewById(R.id.delete);
        desc = (EditText)findViewById(R.id.edit_description);
        date_button = (ImageButton) findViewById(R.id.button_date);
        text_view = (TextView)findViewById(R.id.selected_date);
        status = (RadioGroup)findViewById(R.id.status_radio_group);
        spinner = (Spinner)findViewById(R.id.priority_to_select);
        return_home = (ImageButton) findViewById(R.id.return_home);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String priority = intent.getStringExtra("priority");
        String done = intent.getStringExtra("done");

        _id = Long.parseLong(id);
        desc.setText(description);
        text_view.setText(date);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priority_level);
        spinner.setAdapter(adapter);

        spinner.setSelection(adapter.getPosition(priority));
        setSelectedRadioBtn(done);

        date_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int mon = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog select_date = new DatePickerDialog(EditTask.this, new DatePickerDialog.OnDateSetListener()
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

        edit_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);
        return_home.setOnClickListener(this);
    }

    private void setSelectedRadioBtn(String done)
    {
        RadioButton rbStart = (RadioButton)findViewById(R.id.start_radio);
        RadioButton rbDone = (RadioButton)findViewById(R.id.done_radio);

        if(done.equals("Start"))
        {
            rbStart.setChecked(true);
            rbDone.setChecked(false);
        }
        else
        {
            rbStart.setChecked(false);
            rbDone.setChecked(true);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.update)
        {
            String description = desc.getText().toString();

            if(desc.getText().toString().isEmpty()){
                description = "new task";
            }

            String date = text_view.getText().toString();
            flag_radio_done_choose = status.getCheckedRadioButtonId();

            RadioButton radio_button = (RadioButton)findViewById(flag_radio_done_choose);
            dbHelper.edit(_id, date, description, priority_string_choose,radio_button.getText().toString());
            Intent intent_save = new Intent(EditTask.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent_save);
        }

        else if (v.getId() == R.id.delete)
        {
            dbHelper.delete(_id);
            Intent intent_save = new Intent(EditTask.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent_save);
        }

        else if (v.getId() == R.id.return_home)
        {
            Intent intent_home = new Intent(EditTask.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_home);
        }

        else if (v.getId() == R.id.choose_date)
        {
            text_view.setText("Chosen Date is: " + desc_date.getText());
        }
    }
}
