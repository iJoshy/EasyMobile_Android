package com.etisalat.easymobile;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by portalservices on 9/3/15.
 */
public class SocialFragment extends Fragment
{

    public SocialFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_social, container, false);


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");

        Button facebookBtn = (Button)rootView.findViewById(R.id.facebookBtn);
        Button twitterBtn = (Button)rootView.findViewById(R.id.twitterBtn);
        Button googlePlusBtn = (Button)rootView.findViewById(R.id.googleplusBtn);

        facebookBtn.setTypeface(font1);
        twitterBtn.setTypeface(font1);
        googlePlusBtn.setTypeface(font1);

        TextView socialText = (TextView)rootView.findViewById(R.id.socialText);
        TextView facebookText = (TextView)rootView.findViewById(R.id.facebookText);
        TextView twitterText = (TextView)rootView.findViewById(R.id.twitterText);
        TextView googleplusText = (TextView)rootView.findViewById(R.id.googleplusText);

        socialText.setTypeface(font);
        socialText.setPaintFlags(socialText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        facebookText.setTypeface(font);
        twitterText.setTypeface(font);
        googleplusText.setTypeface(font);


        // Inflate the layout for this fragment
        return rootView;
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

