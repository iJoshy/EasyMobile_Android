package com.etisalat.easymobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by joshuabalogun on 8/31/15.
 */

public class AirtimeRechargeFragment extends Fragment
{

    private MaterialDialog dialog;

    public AirtimeRechargeFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_airtime_recharge, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");

        TextView rechargecodeText = (TextView)rootView.findViewById(R.id.rechargecodeText);
        EditText rechargecodeEdit = (EditText)rootView.findViewById(R.id.rechargecodeEdit);
        TextView rechargecodeCancel = (TextView)rootView.findViewById(R.id.rechargecodeCancel);

        rechargecodeText.setTypeface(font);
        rechargecodeEdit.setTypeface(font);
        rechargecodeEdit.setGravity(Gravity.CENTER_HORIZONTAL);
        rechargecodeCancel.setTypeface(font);

        rechargecodeEdit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(rechargecodeEdit, InputMethodManager.SHOW_IMPLICIT);


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