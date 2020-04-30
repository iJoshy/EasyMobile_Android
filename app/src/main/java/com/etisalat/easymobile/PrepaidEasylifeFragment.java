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

public class PrepaidEasylifeFragment extends Fragment
{

    public PrepaidEasylifeFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_prepaid_easylife, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        Button easylifeAddBtn = (Button)rootView.findViewById( R.id.easylifeAddBtn );
        Button easylifeDelBtn = (Button)rootView.findViewById( R.id.easylifeDelBtn );
        Button easylifeCheckBtn = (Button)rootView.findViewById( R.id.easylifeCheckBtn );

        easylifeAddBtn.setTypeface(font1);
        easylifeDelBtn.setTypeface(font1);
        easylifeCheckBtn.setTypeface(font1);

        TextView easylifeAddText = (TextView)rootView.findViewById( R.id.easylifeAddText );
        TextView easylifeDelText = (TextView)rootView.findViewById( R.id.easylifeDelText );
        TextView easylifeCheckText = (TextView)rootView.findViewById( R.id.easylifeCheckText );

        easylifeAddText.setTypeface(font2);
        easylifeDelText.setTypeface(font2);
        easylifeCheckText.setTypeface(font2);

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
