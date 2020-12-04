package com.example.labservicesb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String PARAM_RESULT = "result";
    public final static String BROADCAST_ACTION = ".RandomService";
    public static String result = "0";
    BroadcastReceiver br;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textview_first);
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent){
                result = intent.getStringExtra(PARAM_RESULT);
                textView.setText(result);
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br,intentFilter);
        stopService(new Intent(MainActivity.this, RandomService.class));

    }
    public void onCreationClicked(View view) {
        Toast.makeText(this,"Enter through spaces",Toast.LENGTH_LONG).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(br);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        EditText leftBoundNumber = findViewById(R.id.leftBoundNumber);
        EditText rightBoundNumber = findViewById(R.id.rightBoundNumber);
        EditText editArray = findViewById(R.id.editArray);
        editArray.setVisibility(View.INVISIBLE);
        leftBoundNumber.setVisibility(View.INVISIBLE);
        rightBoundNumber.setVisibility(View.INVISIBLE);
        switch (id)
        {
            case R.id.menu_any_random_item:
                startService(new Intent(this, RandomService.class).putExtra("typeOperation",1));
                return true;
            case R.id.menu_random_coin_item:
                startService(new Intent(this, RandomService.class).putExtra("typeOperation",2));
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
                startService(new Intent(this, RandomService.class).putExtra("typeOperation",3).putExtra("leftBorder",leftValue).putExtra("rightBorder",rightValue));
                return true;
            case R.id.menu_random_list_item:
                editArray.setVisibility(View.VISIBLE);
                String[] choiseValues =  MakeArray();
                startService(new Intent(this, RandomService.class).putExtra("typeOperation",4).putExtra("choiseValues",choiseValues));
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