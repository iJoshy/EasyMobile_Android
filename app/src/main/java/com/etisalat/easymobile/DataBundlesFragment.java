package com.etisalat.easymobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by portalservices on 9/7/15.
 */
public class DataBundlesFragment extends Fragment
{

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private WebServiceCall wbs;

    public DataBundlesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        View rootView = inflater.inflate(R.layout.fragment_data_bundles, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        String strJson = preferences.getString("EASYBLAZEARRAY","0");
        System.out.println("strJson in view :::: "+strJson);

        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            System.out.println("array :::: " + jsonArray);

            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.EasyBlazeLayout);
            layout.setOrientation(LinearLayout.VERTICAL);


            int lengthOfBundles = jsonArray.length();
            int i = 0, btncount = 0;

            while ( i < lengthOfBundles )
            {
                LinearLayout row = new LinearLayout(getActivity());
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                if (btncount % 2 == 0)
                {

                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    Button btn = new Button(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(370, 370, 1.0f);
                    params.setMargins(0, -10, -10, -20);
                    btn.setLayoutParams(params);
                    btn.setTextSize(17);
                    btn.setTypeface(font1);
                    btn.setTextColor(getResources().getColor(R.color.black));
                    btn.getBackground().setColorFilter(0xFFBED308, PorterDuff.Mode.MULTIPLY);
                    btn.setText(jsonobject.getString("displayText").replace("Daily", "Daily\n"));
                    btn.setTransformationMethod(null);
                    btn.setTag(i);
                    btn.setId(i);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            ((MainActivity)getActivity()).buyDataPlanNow(v);
                        }
                    });

                    row.addView(btn);
                    i+=1;


                    if (i == lengthOfBundles)
                    {
                        layout.addView(row);
                        break;
                    }


                    JSONObject jsonobject2 = jsonArray.getJSONObject(i);
                    Button btn2 = new Button(getActivity());
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(370, 370, 1.0f);
                    params2.setMargins(-10, -10, 0, -20);
                    btn2.setLayoutParams(params2);
                    btn2.setTextSize(17);
                    btn2.setTypeface(font1);
                    btn2.setTextColor(getResources().getColor(R.color.white));
                    btn2.getBackground().setColorFilter(0xFF719E19, PorterDuff.Mode.MULTIPLY);
                    btn2.setText(jsonobject2.getString("displayText"));
                    btn2.setTransformationMethod(null);
                    btn2.setTag(i);
                    btn2.setId(i);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) getActivity()).buyDataPlanNow(v);
                        }
                    });

                    row.addView(btn2);
                    i+=1;
                    btncount+=1;

                }
                else
                {
                    LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(370, 370, 1.0f);

                    JSONObject jsonobject3 = jsonArray.getJSONObject(i);
                    Button btn3 = new Button(getActivity());
                    params3.setMargins(0, -10, 0, -20);
                    btn3.setLayoutParams(params3);
                    btn3.setTextSize(17);
                    btn3.setTypeface(font1);
                    btn3.setTextColor(getResources().getColor(R.color.white));
                    btn3.getBackground().setColorFilter(0xFF323E48, PorterDuff.Mode.MULTIPLY);
                    btn3.setText(jsonobject3.getString("displayText"));
                    btn3.setTransformationMethod(null);
                    btn3.setTag(i);
                    btn3.setId(i);
                    btn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) getActivity()).buyDataPlanNow(v);
                        }
                    });

                    row.addView(btn3);
                    i+=1;
                    btncount+=1;

                }

                layout.addView(row);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
