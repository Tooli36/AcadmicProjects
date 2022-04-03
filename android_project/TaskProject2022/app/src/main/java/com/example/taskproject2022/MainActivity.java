package com.example.taskproject2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private ListView list_view;
    private Button add_task_button;
    private DataBaseHelper dbHelper;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[] {DataBaseHelper._ID, DataBaseHelper.DATE, DataBaseHelper.DESCRIPTION, DataBaseHelper.PRIORITY, DataBaseHelper.DONE};
    final int[] to = new int[] {R.id.id, R.id.date, R.id.description, R.id.priority, R.id.done};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        Cursor cursor = dbHelper.fetch();
        list_view = (ListView)findViewById(R.id.list_show_tasks);
        add_task_button = (Button)findViewById(R.id.add);

        adapter = new SimpleCursorAdapter(this, R.layout.activity_the_task, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        list_view.setAdapter(adapter);

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

        add_task_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CreateTask.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_tasks_views, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Toast.makeText(this, "Chosen: " + item.getTitle(), Toast.LENGTH_SHORT).show();

        if (item.getItemId() == R.id.completed_tasks)
        {
            Intent intent_completed = new Intent(MainActivity.this, ShowCompletedTasks.class);
            startActivity(intent_completed);
            return true;
        }

        if (item.getItemId() == R.id.incomplete_tasks)
        {
            Intent intent_incomplete = new Intent(MainActivity.this, ShowIncompleteTasks.class);
            startActivity(intent_incomplete);
            return true;
        }

        if (item.getItemId() == R.id.find_tasks_by_date)
        {
            Intent intent_date = new Intent(MainActivity.this, ShowByDate.class);
            startActivity(intent_date);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
