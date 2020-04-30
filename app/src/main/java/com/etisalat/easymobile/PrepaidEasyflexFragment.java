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

public class PrepaidEasyflexFragment extends Fragment
{

    public PrepaidEasyflexFragment()
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
        View rootView = inflater.inflate(R.layout.fragment_prepaid_easyflex, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        Button easyflexAddBtn = (Button)rootView.findViewById( R.id.easyflexAddBtn );
        Button easyflexDelBtn = (Button)rootView.findViewById( R.id.easyflexDelBtn );
        Button easyflexCheckBtn = (Button)rootView.findViewById( R.id.easyflexCheckBtn );

        easyflexAddBtn.setTypeface(font1);
        easyflexDelBtn.setTypeface(font1);
        easyflexCheckBtn.setTypeface(font1);

        TextView easyflexAddText = (TextView)rootView.findViewById( R.id.easyflexAddText );
        TextView easyflexDelText = (TextView)rootView.findViewById( R.id.easyflexDelText );
        TextView easyflexCheckText = (TextView)rootView.findViewById( R.id.easyflexCheckText );

        easyflexAddText.setTypeface(font2);
        easyflexDelText.setTypeface(font2);
        easyflexCheckText.setTypeface(font2);

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
