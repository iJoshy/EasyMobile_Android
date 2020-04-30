package com.etisalat.easymobile;

/**
 * Created by Ravi on 29/07/15.
 */

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;


public class MyAccountFragment extends Fragment
{
    private MaterialDialog dialog;
    private BuildAndSendRequest wbs;

    public MyAccountFragment()
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
        View rootView = inflater.inflate(R.layout.fragment_myaccount, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");
        Typeface font3 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat.TTF");

        Button databalanceBtn = (Button)rootView.findViewById( R.id.databalanceBtn );
        Button rechargeBtn = (Button)rootView.findViewById( R.id.rechargeBtn );
        Button buyDataBtn = (Button)rootView.findViewById( R.id.buyDataBtn );
        Button transferBtn = (Button)rootView.findViewById( R.id.transferBtn );
        Button shownumberBtn = (Button)rootView.findViewById( R.id.shownumberBtn );
        Button rechargeOthersBtn = (Button)rootView.findViewById( R.id.rechargeOthersBtn );

        databalanceBtn.setTypeface(font1);
        rechargeBtn.setTypeface(font1);
        buyDataBtn.setTypeface(font1);
        transferBtn.setTypeface(font1);
        shownumberBtn.setTypeface(font2);
        rechargeOthersBtn.setTypeface(font1);

        TextView airtimebalanceText = (TextView)rootView.findViewById( R.id.airtimebalanceText );
        TextView databalanceText = (TextView)rootView.findViewById( R.id.databalanceText );
        TextView buyDataText = (TextView)rootView.findViewById( R.id.buyDataText );
        TextView rechargeText = (TextView)rootView.findViewById( R.id.rechargeText );
        TextView transferText = (TextView)rootView.findViewById( R.id.transferText );
        TextView rechargeOthersText = (TextView)rootView.findViewById( R.id.rechargeOthersText );
        TextView shownumberText = (TextView)rootView.findViewById( R.id.shownumberText );
        TextView packageText = (TextView)rootView.findViewById( R.id.packageText );

        airtimebalanceText.setTypeface(font2);
        databalanceText.setTypeface(font2);
        buyDataText.setTypeface(font2);
        rechargeText.setTypeface(font2);
        transferText.setTypeface(font2);
        rechargeOthersText.setTypeface(font2);
        shownumberText.setTypeface(font3);
        packageText.setTypeface(font2);



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
