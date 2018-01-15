package com.example.midori.phonebookapp;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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
        lvContact.setAdapter(contactAdapter);
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
}
