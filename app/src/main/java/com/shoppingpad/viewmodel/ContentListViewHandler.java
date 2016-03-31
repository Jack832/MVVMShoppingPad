package com.shoppingpad.viewmodel;

import android.content.Context;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.controller.ContentListViewController;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* purpose:
 *
 * 1.This class join the required Attribute from List.
 * 2.it call controller and take the data lists
 * *
 */

public class ContentListViewHandler
{
    //unit test
    final static boolean            mPERFORM_UNIT_TEST = false;
    //hold the content list
    public List<ContentViewModel>    mContentList;
    //controller object;
    public ContentListViewController mContentListViewController;
    //contentViewModel Object;
    ContentViewModel mContentViewData;
    //
    Pattern pattern;
    Matcher matcher;
    String Pattern_Image="(bmp|jpg|gif|png)";


    //populating dummy data , checking Unit test Condition
    //calling Controller
    public ContentListViewHandler(Context context)
    {
        mContentList = new ArrayList<>();
        if (mPERFORM_UNIT_TEST)
        {
            mContentList = populateDummyData();
        }
        else
        {
            mContentList= new ArrayList<>();
            mContentListViewController = new ContentListViewController(context);
         }
    }

    //initialize the data with required attribute
    public List<ContentViewModel> populateDummyData()
    {
        int icon[]         = {R.drawable.lego, R.drawable.lego};
        String title[]     = {"Sham", "sam"};
        String lastSeen[]  = {"12/8/16", "30/1/16"};
        int participants[] = {45,52};
        int noofviews[]    = {51,12};

        for (int i = 0; i < icon.length && i < title.length; i++)
        {
            ContentViewModel contentViewData  = new ContentViewModel();
            contentViewData.mDisplayName      = title[i];
            contentViewData.mLastViewDateTime = lastSeen[i];
            mContentList.add(contentViewData);
        }
        return mContentList;
    }

     //this will call controller and ask for particular dataLists and
    //it also set the data with ViewModelController attribute
    public List<ContentViewModel> getContentViewList()
    {
        List<ContentInfoModel> contentInfoList=mContentListViewController.
                                                   getJsonInfoData();
        List<ViewModel> contentViewList=mContentListViewController.
                                                  getJsonViewData();
       for(int i=0;i<contentInfoList.size();i++)
       {
           mContentViewData = new ContentViewModel();
           boolean imageLink=ImageVerification(contentInfoList.get(i).mImagesLink);
           if(imageLink)
           {
               mContentViewData.setmDisplayImage(contentInfoList.get(i).mImagesLink);
           }
           else
           {
               String image = "https://account.socialbakers.com/default_user.png";
               mContentViewData.setmDisplayImage(image);
           }
            mContentViewData.setmLastViewDateTime(contentViewList.get(i).mLastViewedDateTime);
            mContentViewData.setmNumberofParticipants(contentViewList.get(i).mParticipants);
            mContentViewData.setmNumberofViews(contentViewList.get(i).mNumberOfViews);
            mContentViewData.setmDisplayName(contentInfoList.get(i).mDisplay_name);
            mContentList.add(mContentViewData);
       }
        return mContentList;
    }

    private boolean ImageVerification(String mImagesLink)
    {
        pattern=Pattern.compile(Pattern_Image);
        matcher=pattern.matcher(mImagesLink);
        return matcher.find();
    }

    //pass the data for required position
    public ContentViewModel getContentInfoPosition(int position)
    {
        return mContentList.get(position);
    }

    //using size() to get size of data
    public int getContentSize()
    {
        return mContentList.size();
    }
}
