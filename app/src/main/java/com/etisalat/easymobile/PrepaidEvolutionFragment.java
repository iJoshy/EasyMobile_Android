package com.etisalat.easymobile;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by portalservices on 9/9/15.
 */

public class PrepaidEvolutionFragment extends Fragment
{

    public PrepaidEvolutionFragment()
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
        View rootView = inflater.inflate(R.layout.fragment_prepaid_evolution, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        Button evolutionDataBtn = (Button)rootView.findViewById( R.id.evolutionDataBtn );
        Button evolutionVoiceBtn = (Button)rootView.findViewById( R.id.evolutionVoiceBtn );
        Button evolutionCheckBtn = (Button)rootView.findViewById( R.id.evolutionCheckBtn );
        Button evolutionCancelBtn = (Button)rootView.findViewById( R.id.evolutionCancelBtn );

        evolutionDataBtn.setTypeface(font1);
        evolutionVoiceBtn.setTypeface(font1);
        evolutionCheckBtn.setTypeface(font1);
        evolutionCancelBtn.setTypeface(font1);

        TextView evolutionDataTxt = (TextView)rootView.findViewById( R.id.evolutionDataTxt );
        TextView evolutionVoiceTxt = (TextView)rootView.findViewById( R.id.evolutionVoiceTxt );
        TextView evolutionCheckTxt = (TextView)rootView.findViewById( R.id.evolutionCheckTxt );
        TextView evolutionCancelTxt = (TextView)rootView.findViewById( R.id.evolutionCancelTxt );

        evolutionDataTxt.setTypeface(font2);
        evolutionVoiceTxt.setTypeface(font2);
        evolutionCheckTxt.setTypeface(font2);
        evolutionCancelTxt.setTypeface(font2);

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

