package com.example.lab3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


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

    public int getAnyRandomFromNumber(int number) {
        Random rand = new Random();
        return (int) rand.nextInt(100_000-number) + 1;
    }

    public long getRandomNumberOfSpecifiedLength(int number) {
        return (long)  ThreadLocalRandom.current().nextLong((long)Math.pow(10,number-1),(long)Math.pow(10,number));
    }

    public int getAnyRandomToNumber(int number) {
        Random rand = new Random();
        return (int) rand.nextInt(number-1) + 1;
    }
}
