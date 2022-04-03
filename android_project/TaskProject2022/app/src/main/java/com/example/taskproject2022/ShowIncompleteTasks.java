package com.example.taskproject2022;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ShowIncompleteTasks extends Activity implements View.OnClickListener
{
    private DataBaseHelper dbHelper;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    private ListView list_view;
    private ImageButton return_home;

    final String[] from = new String[] {DataBaseHelper._ID, DataBaseHelper.DATE, DataBaseHelper.DESCRIPTION, DataBaseHelper.PRIORITY, DataBaseHelper.DONE};
    final int[] to = new int[] {R.id.id, R.id.date, R.id.description, R.id.priority, R.id.done};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_incomplete_tasks);

        dbHelper = new DataBaseHelper(this);

        list_view = (ListView)findViewById(R.id.list_view);
        return_home = (ImageButton)findViewById(R.id.return_home);

        cursor = dbHelper.chooseIncomplete();
        adapter = new SimpleCursorAdapter(this, R.layout.activity_the_task, cursor, from, to, 0);

        adapter.notifyDataSetChanged();
        list_view.setAdapter(adapter);
        return_home.setOnClickListener(this);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId)
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
        Intent intent_home = new Intent(ShowIncompleteTasks.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_home);
    }

    @Override
    public void onBackPressed() 
    {
        list_view.setVisibility(View.GONE);
    }
}