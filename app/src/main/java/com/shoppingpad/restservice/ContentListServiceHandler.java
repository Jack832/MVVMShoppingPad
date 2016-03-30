package com.shoppingpad.restservice;

import android.content.Context;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.controller.ControllerModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgelabz4 on 6/3/16.
 *
 * purpose:
 * 1.this will call the RestService which is server .
 * 2.it will fetch the data from server
 * the data from server is given to controller.
 */

public class ContentListServiceHandler
{
    public static final boolean mPERFORM_UNIT_TEST = false;
    //url of Json
    String ContentInfoUrl = "http://54.86.64.100:3000/api/v1/content/content-info";
    String ContentViewUrl = "http://54.86.64.100:3000/api/v1/content/user-content-view";
    static JSONArray jsonArray = null;
    //using JsonArray to load the data from Url
    public JSONArray jsonArrayView;
    public JSONArray jsonArrayInfo;

    //generating Dummy data here.
    public ContentListServiceHandler() {
        if (mPERFORM_UNIT_TEST)
        {
            populateDummyData();
        } else
        {
            jsonArrayView = new JSONArray();
            jsonArrayInfo = new JSONArray();
        }
    }

    //creating method which will create dummy for ContentInfo and ContentViews
    private void populateDummyData() {
        // populateContentInfoDummyData();
        // populateContentViewDummyData();
    }

    //reading Url to get the data of ViewList
    public JSONArray getJsonArrayOfView()
    {
        jsonArrayView = getJsonData(ContentViewUrl);
        return jsonArrayView;
    }

    //reading Url to get the data of InfoList
    public JSONArray getJsonArrayOfInfo()
    {
        JSONArray jsonArrayInfo = getJsonData(ContentInfoUrl);
        return jsonArrayInfo;
    }


    //this method is called by getJsonArrayOfInfo & View
    // the url call and json reading which returns JsonArray
    public JSONArray getJsonData(String url)
    {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int Statuscode = statusLine.getStatusCode();
            if (Statuscode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                InputStream content = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                String Line;
                while ((Line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(Line);
                }
            } else
            {
                Log.e("d==", "download not completed");
            }
        }
        catch (ClientProtocolException eb)
        {
            eb.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            jsonArray = new JSONArray(stringBuilder.toString());
        }
        catch (JSONException e)
        {
            Log.e("json", "Error" + e.toString());
        }
        return jsonArray;
    }
}



