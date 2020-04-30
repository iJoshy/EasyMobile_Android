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
 * Created by joshuabalogun on 9/7/15.
 */

public class DataTransferFragment extends Fragment
{
    private MaterialDialog dialog;

    public DataTransferFragment()
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
        View rootView = inflater.inflate(R.layout.fragment_data_transfer, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");

        TextView securityCodeText = (TextView)rootView.findViewById(R.id.securityCodeText);
        EditText securityCodeEdit = (EditText)rootView.findViewById(R.id.securityCodeEdit);
        TextView receiverNoText = (TextView)rootView.findViewById(R.id.receiverNoText);
        EditText receiverNoEdit = (EditText)rootView.findViewById(R.id.receiverNoEdit);
        TextView amountText = (TextView)rootView.findViewById(R.id.amountText);
        EditText amountEdit = (EditText)rootView.findViewById(R.id.amountEdit);
        TextView rechargecodeCancel = (TextView)rootView.findViewById(R.id.rechargecodeCancel);

        securityCodeText.setTypeface(font);
        securityCodeEdit.setTypeface(font);
        receiverNoText.setTypeface(font);
        receiverNoEdit.setTypeface(font);
        amountText.setTypeface(font);
        receiverNoEdit.setTypeface(font);
        amountEdit.setGravity(Gravity.CENTER_HORIZONTAL);
        rechargecodeCancel.setTypeface(font);

        securityCodeEdit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(securityCodeEdit, InputMethodManager.SHOW_IMPLICIT);


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
