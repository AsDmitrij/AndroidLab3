package com.example.labservicesb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RandomService randomService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, RandomService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(serviceConnection);
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
        EditText leftBoundNumber = findViewById(R.id.leftBoundNumber);
        EditText rightBoundNumber = findViewById(R.id.rightBoundNumber);
        EditText editArray = findViewById(R.id.editArray);
        editArray.setVisibility(View.INVISIBLE);
        leftBoundNumber.setVisibility(View.INVISIBLE);
        rightBoundNumber.setVisibility(View.INVISIBLE);
        TextView textViewAnswer = findViewById(R.id.textview_first);
        TextView textViewCondition = findViewById(R.id.textView);
        switch (item.getItemId())
        {
            case R.id.menu_any_random_item:
                textViewCondition.setText("You random number");
                textViewAnswer.setText(Integer.toString(randomService.MakeAnyRandom()));
                return true;
            case R.id.menu_random_coin_item:
                textViewCondition.setText("You side of coin");
                textViewAnswer.setText(randomService.MakeCoinRandom());
                return true;
            case R.id.menu_random_range_item:
                int leftValue= (leftBoundNumber.getText() == null)?0:Integer.parseInt(leftBoundNumber.getText().toString());
                int rightValue = (rightBoundNumber.getText() == null)?0:Integer.parseInt(rightBoundNumber.getText().toString());
                if(leftValue>rightValue){
                    int temp=leftValue;
                    leftValue = rightValue;
                    rightValue=temp;
                }
                leftBoundNumber.setVisibility(View.VISIBLE);
                rightBoundNumber.setVisibility(View.VISIBLE);
                textViewCondition.setText("You random number");
                textViewAnswer.setText(Integer.toString(randomService.MakeFromRangeRandom(leftValue,rightValue)));
                return true;
            case R.id.menu_random_list_item:
                editArray.setVisibility(View.VISIBLE);
                String[] choiseValues =  MakeArray();
                textViewAnswer.setText(randomService.MakeFromArrayRandom(choiseValues));
                textViewCondition.setText("You random item");
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
    String[] MakeArray()
    {
        EditText editArray = findViewById(R.id.editArray);
        String stringArray = editArray.getText().toString();
        ArrayList<String> list = new ArrayList<String>();
        for (String getValue : stringArray.split(" ")) {
            list.add(getValue);
        }
        return list.toArray(new String[list.size()]);
    }
}