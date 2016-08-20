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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by C5215239 on 28-Oct-15.
 */
public class DeleteTask extends AppCompatActivity {
private ArrayList<String> arrayListToDo;
private ArrayAdapter<String> arrayAdapterToDo;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        arrayListToDo=new ArrayList<String>();
        arrayAdapterToDo =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayListToDo);
       final ViewGroup vg=(ViewGroup)findViewById(R.id.listView2);
        setContentView(View.inflate(this, R.layout.layout,vg));

        final ListView listViewToDo = (ListView)findViewById(R.id.listView2);
        listViewToDo.setAdapter(arrayAdapterToDo);
        registerForContextMenu(listViewToDo);
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
@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() != R.id.listView2){
        return;
        }
        menu.setHeaderTitle("What would you like to do");
        String[] options= {"Delete Task","Back"};

        for(String option : options){
        menu.add(option);
        }
        }




@Override
public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int selectedIndex = info.position;

        if (item.getTitle().equals("Delete Task")){
        arrayListToDo.remove(selectedIndex);
        Toast.makeText(getApplicationContext(), "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
        arrayAdapterToDo.notifyDataSetChanged();
        saveState();
        }

        return true;
        }




protected void saveState(){
        try {
        Log.i("On back pressed", "You have pressed back");
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
@Override
public void onBackPressed() {
        saveState();
        finish();
        }
        }
