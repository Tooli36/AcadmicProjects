package com.example.taskproject2022;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Calendar;

public class ShowByDate extends Activity implements View.OnClickListener
{
    private DataBaseHelper dbHelper;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    private Button date_button;
    private EditText edit_text;
    private ImageButton return_home;
    private DatePickerDialog selected_date;
    private TextView text_view;
    private ListView list_view;

    final String[] from = new String[] {DataBaseHelper._ID, DataBaseHelper.DATE, DataBaseHelper.DESCRIPTION, DataBaseHelper.PRIORITY, DataBaseHelper.DONE};
    final int[] to = new int[] {R.id.id, R.id.date, R.id.description, R.id.priority, R.id.done};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tasks_by_date);

        dbHelper = new DataBaseHelper(this);

        date_button = (Button)findViewById(R.id.choose_date);
        edit_text = (EditText)findViewById(R.id.date_edit);
        edit_text.setInputType(InputType.TYPE_NULL);
        return_home = (ImageButton)findViewById(R.id.return_home);
        text_view=(TextView)findViewById(R.id.text_view);
        list_view = (ListView)findViewById(R.id.list_view);
        list_view.setEmptyView(findViewById(R.id.empty));

        edit_text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int mon = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                selected_date = new DatePickerDialog(ShowByDate.this, R.style.Widget_AppCompat_ActionBar, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        edit_text.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                    }
                }, year, mon, day);
                selected_date.show();
            }
        });
        date_button.setOnClickListener(this);
        return_home.setOnClickListener(this);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView id_text_view = (TextView) view.findViewById(R.id.id);
                TextView date_text_view = (TextView) view.findViewById(R.id.date);
                TextView desc_text_view = (TextView) view.findViewById(R.id.description);
                TextView priority_text_view = (TextView) view.findViewById(R.id.priority);
                TextView done_text_view = (TextView) view.findViewById(R.id.done);

                String idd = id_text_view.getText().toString();
                String date = date_text_view.getText().toString();
                String desc = desc_text_view.getText().toString();
                String priority = priority_text_view.getText().toString();
                String done = done_text_view.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), EditTask.class);
                modify_intent.putExtra("id", idd);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("description", desc);
                modify_intent.putExtra("priority", priority);
                modify_intent.putExtra("done", done);

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.choose_date)
        {
            text_view.setText("Chosen date is: " + edit_text.getText());
            String date_str = edit_text.getText().toString();

            cursor = dbHelper.findBydate(date_str);
            adapter = new SimpleCursorAdapter(this, R.layout.activity_the_task, cursor, from, to, 0);
            adapter.notifyDataSetChanged();
            list_view.setAdapter(adapter);
        }

        else if (v.getId() == R.id.return_home)
        {
            Intent intent_home = new Intent(ShowByDate.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_home);
        }
    }
}