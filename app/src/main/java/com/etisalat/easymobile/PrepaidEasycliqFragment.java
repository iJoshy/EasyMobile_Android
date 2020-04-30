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

public class PrepaidEasycliqFragment extends Fragment
{

    public PrepaidEasycliqFragment()
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
        View rootView = inflater.inflate(R.layout.fragment_prepaid_easycliq, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        Button easycliqAddBtn = (Button)rootView.findViewById( R.id.easycliqAddBtn );
        Button easycliqCheckBtn = (Button)rootView.findViewById( R.id.easycliqCheckBtn );

        easycliqAddBtn.setTypeface(font1);
        easycliqCheckBtn.setTypeface(font1);

        TextView easycliqAddText = (TextView)rootView.findViewById( R.id.easycliqAddText );
        TextView easycliqCheckText = (TextView)rootView.findViewById( R.id.easycliqCheckText );

        easycliqAddText.setTypeface(font2);
        easycliqCheckText.setTypeface(font2);

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