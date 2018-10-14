package e.smwhitt.myco2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    ObHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView firstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dbHelper = new ObHelper(this);

        firstTask = (ListView)findViewById(R.id.firstTask);

        loadTaskList();
        setContentView(R.layout.activity_dashboard);

 /*       GraphView graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/
    }


    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if(mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,R.layout.row,R.id.task_title,taskList);
            firstTask.setAdapter(mAdapter);
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.carmenu,menu);

        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        //icon.setColorFilter(getResources().getColor(android.R.color.white, ));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Car")
                        .setMessage("Another car?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask (View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }

}
