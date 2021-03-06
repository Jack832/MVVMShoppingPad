package com.shoppingpad.controller;


import android.content.Context;
import android.support.v4.util.LogWriter;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.TimeCalculate;
import com.shoppingpad.localdatabase.ContentListDBHandler;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ViewModel;
import com.shoppingpad.restservice.ContentListServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgelabz4 on 6/3/16.
 *
 * purpose:
 *
 * 1.It is data controller in MVVM.
 * 2.interact with local db and cloud interaction.
 * 3.it provide interface for VM to interact with DbController,it abstract the
 * DBHandler,model and service.
 * 4.Controller controls the flow of data between the models,DB handler and
 * services.it will acts as manager.
 * 5.controller will get the data from server then controller will handle database call .
 * then controller will call database and give required attributes to viewModel.
 *
 */

public class ContentListViewController
{
    final static boolean mPERFORM_UNIT_TEST = false;

    //ContentServiceHandler Object
    ContentListServiceHandler mContentListServiceHandler;
    //list which will store the name and Image
    ContentListDBHandler mContentListDBHandler;
    //content List Model
    ContentInfoModel mContentInfoModel;
    //content List view Model
    ViewModel mViewModel;
    //creating Info list for REST
    List<ContentInfoModel> mInfoDataListFromREST;
    //creating View list Rest
    public static List<ViewModel> mViewDataListFromREST;
    //timer object
    TimeCalculate time;
    double totalTime;


    //calling the serviceHandler to generate dummy Data
    public ContentListViewController(Context context)
    {
        mInfoDataListFromREST = new ArrayList<>();
        mViewDataListFromREST = new ArrayList<>();
        time= new TimeCalculate();
        // mContentInfoModel= new ContentInfoModel();

        if (mPERFORM_UNIT_TEST)
        {
            populateDummyData();
        } else
        {
           mContentListDBHandler      = new ContentListDBHandler(context);
           mContentListServiceHandler = new ContentListServiceHandler();
       }
    }

    //creating method which will create dummy for ContentInfo and ContentViews
    private void populateDummyData()
    {
        populateContentInfoDummyData();
        populateContentViewDummyData();
    }

    //Dummy data for ContentViews
    private void populateContentViewDummyData()
    {
        String lastSeen[] = {"12/8/16", "30/1/16"};
        int participants[] = {45, 52};
        int noofviews[] = {51, 12};
        int contentId1[] = {0, 1};
        for (int i = 0; i < lastSeen.length; i++)
        {
            ControllerModel controllerModel = new ControllerModel();
            controllerModel.mLastViewDateTimeController = lastSeen[i];
            controllerModel.mNumberofViewsController = noofviews[i];
            controllerModel.mNumberofParticipantsController = participants[i];
            controllerModel.mContentId = contentId1[i];
        }
    }

    //Dummy Data for ContentInfo
    private void populateContentInfoDummyData()
    {
        String title1[] = {"Sham", "Sharma"};
        int icon[] = {R.drawable.lego, R.drawable.lego};
        int contentId[] = {1, 0};
        for (int i = 0; i < title1.length && i < icon.length && i < contentId.length; i++)
        {
            ControllerModel controllerModelData1 = new ControllerModel();
            // controllerModelData1.mDisplayImageController = icon[i];
            controllerModelData1.mDisplayNameContoller = title1[i];
            controllerModelData1.mContentId = contentId[i];
            //mControllerModelsInfo.add(controllerModelData1);
        }
    }

    //reading json and passing Object in InfoList
    public List<ContentInfoModel> getJsonInfoData()
    {
        JSONArray jsonArrayInfo = mContentListServiceHandler.getJsonArrayOfInfo();
        for (int i = 0; i < jsonArrayInfo.length(); i++)
        {
            try
            {
                mContentInfoModel= new ContentInfoModel();
                JSONObject jsonObject1 = jsonArrayInfo.getJSONObject(i);
                mContentInfoModel.setContentInfoData(jsonObject1);
                mInfoDataListFromREST.add(mContentInfoModel);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        time.startTimer();
        insertInfoDataIntoTables();
        time.endTime();
        totalTime=time.timeRequired();
        Log.w("DataBase","InfoData insert:"+totalTime);
        return mInfoDataListFromREST;
    }

    //to read and save Json ViewList
    public List<ViewModel> getJsonViewData()
    {
        JSONArray jsonArrayView = mContentListServiceHandler.getJsonArrayOfView();
        for (int i = 0; i < jsonArrayView.length(); i++)
        {
            try
            {
                mViewModel = new ViewModel();
                JSONObject jsonObject1 = jsonArrayView.getJSONObject(i);
                mViewModel.setContentViewData(jsonObject1);
                mViewDataListFromREST.add(mViewModel);       //data size 10
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        time.startTimer();
        insertViewDataIntoTables();
        time.endTime();
        totalTime=time.timeRequired();
        Log.w("DataBase", "viewData insert "+totalTime);
        return mViewDataListFromREST;
    }

    //set the database by calling database Info method
    public void insertInfoDataIntoTables()
    {
        for (int i = 0; i <mInfoDataListFromREST.size();i++)
        {
            mContentListDBHandler.setInfoDataInDB(mInfoDataListFromREST.get(i));
        }
    }

    //set the database by calling database View method
    public void insertViewDataIntoTables()
    {
        for (int i = 0; i <mViewDataListFromREST.size();i++)
        {
            mContentListDBHandler.setViewDataInDB(mViewDataListFromREST.get(i));
        }
    }
}
