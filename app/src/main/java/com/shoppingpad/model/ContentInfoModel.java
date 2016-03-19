package com.shoppingpad.model;

import org.json.JSONObject;

/**
 * Created by bridgelabz4 on 6/3/16.
 * <p/>
 * purpose:
 * 1.interact with controller and only store the data objects.
 * 2.it will user getter and setter method.
 */

public class ContentInfoModel
{

    public int     mContent_id;              //content id
    public  String mModified_at;             //info modified Date
    public  String mCreated_at;              //info created date
    public  String mSyncDateTime;            //syncDate time
    public  String mDecription;              //description about content
    public  String mContentLink;             //content image
    public  String mImagesLink;              //it holds content ThumbView link.
    public  String mUrl ;                    //url
    public  String mTitle ;                  //title
    public  String mDisplay_name ;           //it holds content name.
    public  String mContentType ;            //ContentType


    //extract the object here and set ListInformation
    public void setContentInfoData(JSONObject jsonObject)
    {
        mContent_id=jsonObject.optInt("Content_id");
        mModified_at  =jsonObject.optString("modified_at").toString();
        mCreated_at   =jsonObject.optString("created_at").toString();
        mSyncDateTime =jsonObject.optString("syncDateTime").toString();
        mDecription   =jsonObject.optString("decription").toString();
        mContentLink  =jsonObject.optString("contentLink").toString();
        mImagesLink   =jsonObject.optString("imagesLink").toString();
        mUrl          =jsonObject.optString("url").toString();
        mDisplay_name =jsonObject.optString("display_name").toString();
        mTitle        =jsonObject.optString("title").toString();
        mContentType  =jsonObject.optString("contentType").toString();
    }

}
