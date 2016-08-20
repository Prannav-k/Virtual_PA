package pk.com.todolist;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by prannav on 28-Oct-15.
 */
public class CreateTask extends AppCompatActivity{
    private ArrayList<String> arrayListToDo;
    private ArrayAdapter<String> arrayAdapterToDo;
    public Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
       arrayListToDo=new ArrayList<String>();
        arrayAdapterToDo =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayListToDo);
        final ListView listViewToDo = (ListView)findViewById(R.id.listView3);
        listViewToDo.setAdapter(arrayAdapterToDo);

        b1=(Button)findViewById (R.id.buttonToDo);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextDo = (EditText) findViewById(R.id.editTextToDo);
                String toDo = editTextDo.getText().toString().trim();

                if (toDo.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter any task", Toast.LENGTH_SHORT).show();
                    return;
                }
                arrayAdapterToDo.add(toDo);
                Toast.makeText(getApplicationContext(), "Task Added Successfully", Toast.LENGTH_SHORT).show();
                editTextDo.setText("");
                saveState();
            }
        });

        b2=(Button)findViewById (R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveState();
                finish();
            }
        });


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

        }
    }

}
