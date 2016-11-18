package com.example.asus.android__crud__application.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.android__crud__application.Database.db.Functions;
import com.example.asus.android__crud__application.R;
import com.example.asus.android__crud__application.model.Item;
import com.example.asus.android__crud__application.ui.MainActivity;

import java.util.ArrayList;

/**
 * Created by asus on 11/18/2016.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {


    private Activity activity;
    private ArrayList<Item>list;
    private AlertDialog dialog;

    public ItemAdapter(Activity activity, ArrayList<Item> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Item item = list.get(position);

        holder.lastname.setText("Last Name: " + item.getLastname());
        holder.firstname.setText("First Name: " + item.getFirstname());
        holder.middlename.setText("Middle Name: " + item.getMiddlename());
        holder.contact.setText("Contact:" + item.getContact());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int id = item.getId();


                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.activity_edit,null);

                final EditText input_lastname = (EditText) view1.findViewById(R.id.Lastname);
                final EditText input_firstname = (EditText) view1.findViewById(R.id.Firstname);
                final EditText input_middlename = (EditText) view1.findViewById(R.id.MiddleName);
                final EditText input_contact = (EditText) view1.findViewById(R.id.Contact);
                final Button btnSave = (Button) view1.findViewById(R.id.btnSave);


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setView(view1).setTitle("Edit Records").setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.dismiss();
                    }
                });

                final Functions functions = new Functions(activity);
                final Item _items = functions.getSingleItem(id);
                input_lastname.setText(_items.getLastname());
                input_firstname.setText(_items.getFirstname());
                input_middlename.setText(_items.getMiddlename());
                input_contact.setText(_items.getContact());

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String lastname = input_lastname.getText().toString();
                        String firstname = input_firstname.getText().toString();
                        String middlename = input_middlename.getText().toString();
                        String contact = input_contact.getText().toString();

                        _items.setLastname(lastname);
                        _items.setFirstname(firstname);
                        _items.setMiddlename(middlename);
                        _items.setContact(contact);

                        functions.Update(_items);

                        Toast.makeText(activity, lastname + " updated.", Toast.LENGTH_SHORT).show();
                        ((MainActivity)activity).fetchRecords();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();


            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int id = item.getId();
                final String last = item.getLastname();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Delete").setMessage("Are you sure you want to delete " + last+"?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Functions functions = new Functions(activity);
                                functions.DeleteItem(id);
                                ((MainActivity)activity).fetchRecords();

                                Toast.makeText(activity,last + " deleted.",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.dismiss();
                    }
                });

                dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lastname, firstname,  middlename,  contact;
        TextView btnEdit,btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            lastname = (TextView) itemView.findViewById(R.id.lastname);
            firstname = (TextView) itemView.findViewById(R.id.firstname);
            middlename = (TextView) itemView.findViewById(R.id.middlename);
            contact = (TextView) itemView.findViewById(R.id.contact);

            btnDelete = (TextView) itemView.findViewById(R.id.btnDelete);
            btnEdit = (TextView) itemView.findViewById(R.id.btnEdit);
        }
    }
}
