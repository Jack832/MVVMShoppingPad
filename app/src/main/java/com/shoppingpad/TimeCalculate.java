package com.shoppingpad;

import android.util.Log;

/**
 * Created by bridgelabz4 on 31/3/16.
 * purpose:
 * 1. to calculate performance Time of method or class
 *
 * */

public class TimeCalculate
{
    long startTime;
    long endTime;
    double totalTimew;

    //start TimeCalculate
    public void  startTimer(){
        startTime=System.currentTimeMillis();
    }

    //end the timer
    public  void  endTime()
    {
           endTime=System.currentTimeMillis();
    }

    //total time required
    public double timeRequired(){
        totalTimew=endTime-startTime;

        totalTimew=totalTimew/10000;

        return totalTimew;
    }

}
