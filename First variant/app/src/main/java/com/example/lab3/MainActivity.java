package com.example.lab3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private RandomService randomService;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, RandomService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            RandomService.MainServiceBinder binder = (RandomService.MainServiceBinder) service;
            randomService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText editTextForEnterNumber = findViewById(R.id.EditText_EnterNumber);
        int number = Integer.parseInt(editTextForEnterNumber.getText().toString());
        TextView textViewForOutNumber = findViewById(R.id.TextView_OutNumber);

        switch (item.getItemId()) {
            case R.id.menu_anyRandomFromNumber:
                textViewForOutNumber.setText(Integer.toString(randomService.getAnyRandomFromNumber(number)));
                return true;
            case R.id.menu_randomNumberOfSpecialLength:
                if(number<20){
                    textViewForOutNumber.setText(Long.toString(randomService.getRandomNumberOfSpecifiedLength(number)));
                }
                else{
                    textViewForOutNumber.setText("The entered number is too large.\n Input number must be less than 20!");
                }
                return true;
            case R.id.menu_anyRandomToNumber:
                textViewForOutNumber.setText(Integer.toString(randomService.getAnyRandomToNumber(number)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}