 package com.example.sqlliteproject1;
import com.example.sqlliteproject1.database.myDatapaseHelper;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlliteproject1.models.Note;

import java.util.ArrayList;

 public class ListViewActivity extends AppCompatActivity {

 ListView listView;
     myDatapaseHelper myDatapaseHelper ;
 Note[] notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = findViewById(R.id.listview);
        myDatapaseHelper =new myDatapaseHelper(ListViewActivity.this);

        ArrayList allNotes = myDatapaseHelper.getAllNotesAsArrayList();
        Toast.makeText(this,allNotes+"this is content",Toast.LENGTH_LONG).show();

        ArrayAdapter arrayAdapter =new ArrayAdapter<>(this, R.layout.item_view,allNotes);

        Add add =new Add(allNotes);
        listView.setAdapter(add);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }
     class Add extends BaseAdapter {
         ArrayList<Note> arrayList =new ArrayList<Note>();
         public Add(ArrayList<Note> v){
             this.arrayList =v;
         }

         @Override
         public int getCount() {
             return arrayList.size();
         }

         @Override
         public Object getItem(int position) {
             return arrayList.get(position);
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             LayoutInflater ly =getLayoutInflater();

             View view =ly.inflate(R.layout.item_view,null);
             TextView title = view.findViewById(R.id.title);
             TextView content = view.findViewById(R.id.content);
             TextView date = view.findViewById(R.id.date);

             title.setText(arrayList.get(position).getTitle());
             content.setText(arrayList.get(position).getContent());
             date.setText(arrayList.get(position).getTimeAsString());
             return view;
         }
     }
}



