package com.etisalat.easymobile;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by portalservices on 23/02/16.
 */

public class PaymentFragment extends Fragment
{

    private MaterialDialog dialog;

    public PaymentFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");
        TextView rechargecodeText = (TextView)rootView.findViewById(R.id.rechargecodeText);
        rechargecodeText.setTypeface(font);

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