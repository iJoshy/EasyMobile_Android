
package com.etisalat.easymobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by joshuabalogun on 9/10/15.
 */


public class PrepaidEvolutionVoiceFragment extends Fragment
{

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private WebServiceCall wbs;

    public PrepaidEvolutionVoiceFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_prepaid_evolution_voice, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        String strJson = preferences.getString("EASYFLEXEVOLUTIONVOICEARRAY","0");
        System.out.println("strJson in view :::: "+strJson);

        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            System.out.println("array :::: " + jsonArray);

            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.EvolutionVoiceLayout);
            layout.setOrientation(LinearLayout.VERTICAL);


            int lengthOfBundles = jsonArray.length();
            int i = 0, btncount = 0;

            while ( i < lengthOfBundles )
            {

                LinearLayout row = new LinearLayout(getActivity());
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(370, 437, 2.0f);

                JSONObject jsonobject = jsonArray.getJSONObject(i);
                Button btn = new Button(getActivity());
                params.setMargins(0, -10, 0, -20);
                btn.setGravity(Gravity.CENTER);
                btn.setLayoutParams(params);
                btn.setTextSize(17);
                btn.setTypeface(font1);
                btn.setTextColor(getResources().getColor(R.color.white));
                btn.getBackground().setColorFilter(0xFF719E19, PorterDuff.Mode.MULTIPLY);
                btn.setText(jsonobject.getString("displayText"));
                btn.setTransformationMethod(null);
                btn.setTag(i);
                btn.setId(i);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).buyEvoultionVoiceNow(v);
                    }
                });

                row.addView(btn);
                i+=1;
                btncount+=1;

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
