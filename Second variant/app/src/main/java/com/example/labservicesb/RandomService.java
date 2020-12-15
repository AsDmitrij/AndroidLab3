package com.example.labservicesb;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class RandomService extends Service {

    public class MainServiceBinder extends Binder {
        public RandomService getService(){
            return RandomService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MainServiceBinder();
    }
    public int MakeAnyRandom()
    {
        Random rand = new Random();
        int randNum = rand.nextInt(100_000 - 1) + 1;
        return randNum;
    }
    public String MakeCoinRandom()
    {
        Random rand = new Random();
        return (rand.nextBoolean())?"Heads":"Tails";
    }
    public int MakeFromRangeRandom(int leftBound, int rightBound)
    {
        Random rand = new Random();
        int randNum = rand.nextInt(rightBound - leftBound+1) + leftBound;
        return randNum;
    }
    public String MakeFromArrayRandom(String[] choiseArray)
    {
        Random rand = new Random();
        int randNum = rand.nextInt((choiseArray.length) - 0) + 0;
        String answer = choiseArray[randNum];
        return answer;
    }
}
