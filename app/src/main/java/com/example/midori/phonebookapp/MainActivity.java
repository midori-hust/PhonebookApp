package com.example.midori.phonebookapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.midori.phonebookapp.adapter.ContactAdapter;
import com.example.midori.phonebookapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContactAdapter contactAdapter;
    private ListView lvContact;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton r_btnMale;
    private RadioButton r_btnFeMale;
    private Button btn_add_phone_number;

    private List<Contact> arrContact = new ArrayList<>();

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_numberphone);
        initWidget();
        arrContact = new ArrayList<>();
        contactAdapter = new ContactAdapter(this,R.layout.activity_add_numberphone,arrContact);
        checkAndRequestPermissions();
        lvContact.setAdapter(contactAdapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
            }
        });
    }

    public void checkAndRequestPermissions(){
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS};
        List<String> listPermissions = new ArrayList<>();
        //端末が許可をあげるかどうか？確認する？
        for (String permission:permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                    listPermissions.add(permission);
            }
        }
        //許可をもらいます？
        if (!listPermissions.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissions.toArray(new String[listPermissions.size()]),1);
        }
    }

    public void initWidget(){
        lvContact = (ListView) findViewById(R.id.list_phone_number);
        r_btnFeMale = (RadioButton) findViewById(R.id.r_btn_gender_famale);
        r_btnMale = (RadioButton) findViewById(R.id.r_btn_gender_male);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        btn_add_phone_number = (Button) findViewById(R.id.btn_add_number_phone);
    }

    public void addContact(View view){
        if(view.getId() == R.id.btn_add_number_phone){
            String name = edtName.getText().toString().trim();
            String number = edtNumber.getText().toString().trim();
            int gender=1;
            if(r_btnFeMale.isChecked()){
                gender=2;
            }
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(number)){
                Toast.makeText(this, "Input miss", Toast.LENGTH_SHORT).show();
            }else {
                Contact contact = new Contact(gender,name,number);
                arrContact.add(contact);
            }
            contactAdapter.notifyDataSetChanged();
        }
    }

    public void showDialogConfirm(final int position){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_customer_dialog);
        Button btn_call = (Button) dialog.findViewById(R.id.btn_call);
        Button btn_messenger = (Button) dialog.findViewById(R.id.btn_messenger);
        dialog.setTitle("Contact now!");
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intentCall(position);
            }
        });

        btn_messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSendMessenger(position);
            }
        });
        dialog.show();
    }

    private void intentSendMessenger(int position){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",arrContact.get(position).getmNumber(),null));
        startActivity(intent);
    }
    private void intentCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+arrContact.get(position).getmNumber()));
        startActivity(intent);
    }

}
