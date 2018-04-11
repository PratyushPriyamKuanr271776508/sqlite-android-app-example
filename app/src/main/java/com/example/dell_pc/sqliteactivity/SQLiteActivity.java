package com.example.dell_pc.sqliteactivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class SQLiteActivity extends AppCompatActivity {

    SQLiteDatabase db = null;
    Button createDB,addCon,delCon,getCon,delDB;
    EditText nameET,emailET,idET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        createDB = (Button) findViewById(R.id.createDBbutton);
        addCon = (Button) findViewById(R.id.addContacts);
        delCon = (Button) findViewById(R.id.deleteContacts);
        delDB = (Button) findViewById(R.id.deleteDB);
        nameET = (EditText)findViewById(R.id.name);
        emailET = (EditText)findViewById(R.id.email);
        idET = (EditText)findViewById(R.id.id_del);
        getCon = (Button)findViewById(R.id.getContacts);
    }

    public void createDatabase(View view) {
        try{
            db = this.openOrCreateDatabase("MyContacts",MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS contacts"+
            "(id integer primary key,name VARCHAR,email VARCHAR);");
            File database = getApplicationContext().getDatabasePath("db.db");
            if(!database.exists()){
                Toast.makeText(SQLiteActivity.this,"Database Created",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SQLiteActivity.this,"Databasse cannot be created",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        addCon.setClickable(true);
        delCon.setClickable(true);
        delDB.setClickable(true);
        getCon.setClickable(true);
    }

    public void addContacts(View view) {
        String contactName = nameET.getText().toString();
        String email = emailET.getText().toString();
        String identifier = idET.getText().toString();
        int id1 = Integer.valueOf(identifier);
        db.execSQL("INSERT INTO contacts (id,name,email) VALUES ('"+id1+"','"+contactName+"','"+identifier+"');");
    }

    public void deleteConatcts(View view) {
        String id = idET.getText().toString();
        db.execSQL("DELETE FROM contacts WHERE id = " + id + ";");
    }

    public void getContacts(View view) {
        Cursor cursor = db.rawQuery("SELECT * FROM contacts",null);
        int idCol = cursor.getColumnIndex("id");
        int namecol = cursor.getColumnIndex("name");
        int emailCol = cursor.getColumnIndex("email");
        cursor.moveToFirst();
        String contactList = null;
        if(cursor != null && (cursor.getCount() > 0)){
            do{
                int id = cursor.getInt(idCol);
                String name = cursor.getString(namecol);
                String email = cursor.getString(emailCol);
                contactList = contactList + id + " : " + name + " : " + email + "\n" ;
                Toast.makeText(SQLiteActivity.this,contactList,Toast.LENGTH_SHORT).show();
            }while(cursor.moveToNext());
        }else{
            Toast.makeText(SQLiteActivity.this,"no results to show",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDatabase(View view) {
        this.deleteDatabase("db");
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
