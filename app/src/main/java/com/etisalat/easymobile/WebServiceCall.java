
package com.etisalat.easymobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class WebServiceCall extends AsyncTask<String, Void, String>
{

    private String newUrl = "http://41.190.16.170:8080/mobile-gateway/services/1.0/findMobileServicePricingByCategory?category=";
    private String osType = "ANDROID";
    private String originatingMsisdn = "";
    private String versionNo = "3.2";

    private HttpURLConnection conn;
    private JSONObject jsonobject;
    private String displayMessage, responseCode;
    private int requester;
    private MaterialDialog dialog;
    private ProgressDialog progress;
    private Context ctx;


    public WebServiceCall (Context context)
    {
        ctx = context;
    }

    public String categories(String plan)throws InterruptedException, ExecutionException
    {
        newUrl += plan;
        requester = 0;

        return execute(newUrl).get();
    }


    @Override
    protected void onPreExecute()
    {
        progress = ProgressDialog.show(ctx, "", "Please wait...", true);
        super.onPreExecute();
    }

    protected String doInBackground(String... params)
    {
        InputStream is = null;
        OutputStream os = null;
        String result = "";

        try
        {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches(false);
            conn.setDoInput(true);

            // Starts the query
            conn.connect();


            int status = conn.getResponseCode();

            if (status == 200)
            {
                is = conn.getInputStream();
                int len = conn.getContentLength();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                //System.out.println("result ::: "+contentAsString);

                return contentAsString;
            }
            else
            {
                return "request could not be processed at this time, pls try gain later !";
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        finally
        {
            if(conn != null)
            {
                conn.disconnect();
            }
            newUrl = "";
        }

    }

    protected void onPostExecute(String s)
    {
        if (progress.isShowing())
            progress.dismiss();

        super.onPostExecute(s);
    }


    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null)
        {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }

}
