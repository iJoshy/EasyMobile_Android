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
 * Created by portalservices on 9/3/15.
 */
public class SupportFragment extends Fragment
{
    public SupportFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_support, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        Button websiteBtn = (Button)rootView.findViewById( R.id.websiteBtn );
        Button emailBtn = (Button)rootView.findViewById( R.id.emailBtn );
        Button chatBtn = (Button)rootView.findViewById( R.id.chatBtn );
        Button facebookBtn = (Button)rootView.findViewById( R.id.facebookBtn );
        Button twitterBtn = (Button)rootView.findViewById( R.id.twitterBtn );


        websiteBtn.setTypeface(font1);
        emailBtn.setTypeface(font1);
        chatBtn.setTypeface(font1);
        facebookBtn.setTypeface(font1);
        twitterBtn.setTypeface(font1);


        TextView websiteText = (TextView)rootView.findViewById( R.id.websiteText );
        TextView emailText = (TextView)rootView.findViewById( R.id.emailText );
        TextView chatText = (TextView)rootView.findViewById( R.id.chatText );
        TextView facebookText = (TextView)rootView.findViewById( R.id.facebookText );
        TextView twitterText = (TextView)rootView.findViewById( R.id.twitterText );

        websiteText.setTypeface(font2);
        emailText.setTypeface(font2);
        chatText.setTypeface(font2);
        facebookText.setTypeface(font2);
        twitterText.setTypeface(font2);

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

