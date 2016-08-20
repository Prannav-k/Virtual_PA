package pk.com.todolist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DisplayTasks extends AppCompatActivity{

    private ArrayList<String> arrayListToDo;
    private ArrayAdapter<String> arrayAdapterToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);



        arrayListToDo=new ArrayList<String>();
        arrayAdapterToDo =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayListToDo);

        final ViewGroup vg=(ViewGroup)findViewById(R.id.listView);
        setContentView(View.inflate(this, R.layout.display, vg));

        final ListView listViewToDo = (ListView) findViewById(R.id.listView);
        listViewToDo.setAdapter(arrayAdapterToDo);
        listViewToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewToDo.showContextMenuForChild(view);
            }
        });
        try {
            Scanner scanner= new Scanner(openFileInput("ToDo.txt"));
            while (scanner.hasNextLine()){
                String toDo= scanner.nextLine();
                arrayAdapterToDo.add(toDo);

            }
            scanner.close();
        }catch (Exception e){
            Log.i("On Create", e.getMessage());
        }
    }

    protected void saveState(){
        try {

            PrintWriter pw= new PrintWriter(openFileOutput("ToDo.txt", Context.MODE_PRIVATE));

            for (String toDo : arrayListToDo) {
                pw.println(toDo);
            }
            pw.close();
        } catch (Exception e)
        {
            Log.i("Back pressed",e.getMessage());
        }
    }
}
