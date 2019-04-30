package com.example.queue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.name)).setText("");
            }
        });
        findViewById(R.id.password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.password)).setText("");
            }
        });
        findViewById(R.id.student_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchScreen(((EditText) findViewById(R.id.name)).getText().toString());
            }
        });
        findViewById(R.id.admin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logIn()) {
                    switchScreen("Admin");
                } else {
                    ((EditText) findViewById(R.id.password)).setText("Invalid password");
                }
            }
        });
    }
    private boolean logIn() {
        DataBase.logIn(((EditText) findViewById(R.id.password)).getText().toString());
        return DataBase.isAdmin();
    }
    private void switchScreen(String name) {
        DataBase.setName(name);
        startActivity(new Intent(MainActivity.this, Line.class));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
