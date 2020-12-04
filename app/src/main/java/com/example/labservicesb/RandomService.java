package com.example.labservicesb;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class RandomService extends Service {
    public RandomService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }
    public int onStartCommand(Intent intent,int flags,int startId){
        int typeRandom = intent.getIntExtra("typeOperation", 0 );
        String resultNumber="";
        switch (typeRandom)
        {
            case 1: resultNumber=String.valueOf(MakeAnyRandom());break;
            case 2: resultNumber=String.valueOf(MakeCoinRandom());break;
            case 3:
                int leftRandomBorder = intent.getIntExtra("leftBorder",0 );
                int rightRandomBorder = intent.getIntExtra("rightBorder",0 );
                resultNumber=String.valueOf(MakeFromRangeRandom(leftRandomBorder,rightRandomBorder));
                break;
            case 4:
                String[] choiseValues = intent.getStringArrayExtra("choiseValues" );
                resultNumber=MakeFromArrayRandom(choiseValues);
                break;
            default:break;
        }
        Intent newIntent = new Intent(MainActivity.BROADCAST_ACTION);
        PendingIntent pi=null;
        newIntent.putExtra("result",resultNumber);
        sendBroadcast(newIntent);
        stopSelf();
        return super.onStartCommand(intent,flags,startId);
    }
    public void onDestroy(){

        super.onDestroy();
        Log.d(String.valueOf(1), "TimeService.onDestroy");
    }
    int MakeAnyRandom()
    {
        Random rand = new Random();
        int randNum = rand.nextInt(100_000 - 1) + 1;
        return randNum;
    }
    int MakeCoinRandom()
    {
        Random rand = new Random();
        int randNum = rand.nextInt(2 - 1) + 1;
        return randNum;
    }
    int MakeFromRangeRandom(int leftBound, int rightBound)
    {
        Random rand = new Random();
        int randNum = rand.nextInt(rightBound - leftBound+1) + leftBound;
        return randNum;
    }
    String MakeFromArrayRandom(String[] choiseArray)
    {
        Random rand = new Random();
        int randNum = rand.nextInt((choiseArray.length) - 0) + 0;
        String answer = choiseArray[randNum];
        return answer;
    }
}
