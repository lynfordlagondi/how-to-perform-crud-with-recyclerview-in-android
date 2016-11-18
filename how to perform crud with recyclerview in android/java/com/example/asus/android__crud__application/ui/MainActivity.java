package com.example.asus.android__crud__application.ui;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.asus.android__crud__application.Database.db.Functions;
import com.example.asus.android__crud__application.R;
import com.example.asus.android__crud__application.adapter.ItemAdapter;
import com.example.asus.android__crud__application.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<Item>list = new ArrayList<Item>();
    private ItemAdapter adapter;
    private RecyclerView recyclerView;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fetchRecords();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View mView1 = inflater.inflate(R.layout.activity_add,null);

                final EditText input_lastname = (EditText) mView1.findViewById(R.id.Lastname);
                final EditText input_firstname = (EditText) mView1.findViewById(R.id.Firstname);
                final EditText input_middlename = (EditText) mView1.findViewById(R.id.MiddleName);
                final EditText input_contact = (EditText) mView1.findViewById(R.id.Contact);
                final Button btnSave = (Button) mView1.findViewById(R.id.btnSave);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(mView1).setTitle("Add new Record")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.dismiss();
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String lastname = input_lastname.getText().toString();
                        String firstname = input_firstname.getText().toString();
                        String middlename = input_middlename.getText().toString();
                        String contact = input_contact.getText().toString();

                        if (lastname.equals("") && firstname.equals("") && middlename.equals("") && contact.equals("")){
                            Snackbar.make(view,"Field incomplete",Snackbar.LENGTH_SHORT).show();

                        }else {
                            Save(lastname,firstname,middlename,contact);
                            dialog.dismiss();
                            Snackbar.make(view,"Saving",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog = builder.create();
                dialog.show();


            }
        });
    }

    public void fetchRecords() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new ItemAdapter(this,list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        list.clear();

        Functions functions = new Functions(MainActivity.this);

        ArrayList<Item>data = functions.getAllRecords();

        if (data.size()>0){
            for (int i = 0; i<data.size(); i++){

                int id = data.get(i).getId();
                String lname = data.get(i).getLastname();
                String fname = data.get(i).getFirstname();
                String middle = data.get(i).getMiddlename();
                String contact = data.get(i).getContact();

                Item item = new Item();

                item.setId(id);
                item.setLastname(lname);
                item.setFirstname(fname);
                item.setMiddlename(middle);
                item.setContact(contact);
                list.add(item);

            }adapter.notifyDataSetChanged();

        }else {
            Toast.makeText(MainActivity.this, "No Records found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void Save(String lastname, String firstname, String middlename, String contact) {


        Functions functions = new Functions(MainActivity.this);
        Item item = new Item();

        item.setLastname(lastname);
        item.setFirstname(firstname);
        item.setMiddlename(middlename);
        item.setContact(contact);
        functions.Insert(item);
        Toast.makeText(MainActivity.this, "Saved...", Toast.LENGTH_SHORT).show();
        fetchRecords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_all) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("You want to delete all records")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Functions functions = new Functions(MainActivity.this);
                            functions.DeleteAll();
                            fetchRecords();
                            Toast.makeText(MainActivity.this, "Delete Success.", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog = builder.create();
            dialog.show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
