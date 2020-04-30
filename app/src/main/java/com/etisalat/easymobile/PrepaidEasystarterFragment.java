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

public class PrepaidEasystarterFragment extends Fragment
{

    public PrepaidEasystarterFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_prepaid_easystarter, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        Button easystarterOptinBtn = (Button)rootView.findViewById( R.id.easystarterOptinBtn );
        Button easystarterAddBtn = (Button)rootView.findViewById( R.id.easystarterAddBtn );
        Button easystarterDelBtn = (Button)rootView.findViewById( R.id.easystarterDelBtn );
        Button easystarterCheckBtn = (Button)rootView.findViewById( R.id.easystarterCheckBtn );

        easystarterOptinBtn.setTypeface(font1);
        easystarterAddBtn.setTypeface(font1);
        easystarterDelBtn.setTypeface(font1);
        easystarterCheckBtn.setTypeface(font1);

        TextView easystarterOptinText = (TextView)rootView.findViewById( R.id.easystarterOptinText );
        TextView easystarterAddText = (TextView)rootView.findViewById( R.id.easystarterAddText );
        TextView easystarterDelText = (TextView)rootView.findViewById( R.id.easystarterDelText );
        TextView easystarterCheckText = (TextView)rootView.findViewById( R.id.easystarterCheckText );
        TextView easystarterReplaceText = (TextView)rootView.findViewById( R.id.easystarterReplaceText );

        easystarterOptinText.setTypeface(font2);
        easystarterAddText.setTypeface(font2);
        easystarterDelText.setTypeface(font2);
        easystarterCheckText.setTypeface(font2);
        easystarterReplaceText.setTypeface(font2);

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