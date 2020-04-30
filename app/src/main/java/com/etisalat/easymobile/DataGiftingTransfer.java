package com.etisalat.easymobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by joshuabalogun on 9/7/15.
 */

public class DataGiftingTransfer extends Fragment
{
    private MaterialDialog dialog;
    private WebserviceGift wbsGifting;
    private Typeface font;
    private String bundles = null;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public DataGiftingTransfer()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        View rootView = inflater.inflate(R.layout.fragment_data_gifting, container, false);

        font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");
        Typeface font1 = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

        TextView rechargecodeText = (TextView)rootView.findViewById(R.id.rechargecodeText);
        EditText rechargecodeEdit = (EditText)rootView.findViewById(R.id.rechargecodeEdit);
        TextView receiverNoText = (TextView)rootView.findViewById(R.id.receiverNoText);
        EditText receiverNoEdit = (EditText)rootView.findViewById(R.id.receiverNoEdit);
        TextView rechargecodeCancel = (TextView)rootView.findViewById(R.id.rechargecodeCancel);

        rechargecodeText.setTypeface(font);
        rechargecodeEdit.setTypeface(font1);
        receiverNoText.setTypeface(font);
        receiverNoEdit.setTypeface(font);
        rechargecodeCancel.setTypeface(font);


        try
        {
            wbsGifting = new WebserviceGift(getActivity());
            bundles = wbsGifting.gifting();

            //System.out.println("array in home :::: "+bundles);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bundles != null)
            {
                editor.putString("GIFTINGARRAY", bundles);
                editor.commit();
            }
        }


        setSpinnerContent(rootView );

        // Inflate the layout for this fragment
        return rootView;
    }


    private void setSpinnerContent( View view )
    {

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<String>();

        try
        {

            JSONArray jsonArray = new JSONArray(bundles);
            if (jsonArray != null)
            {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++)
                {
                    JSONObject jsonobject2 = jsonArray.getJSONObject(i);
                    list.add(jsonobject2.getString("name"));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        ArrayAdapter<CharSequence> adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}