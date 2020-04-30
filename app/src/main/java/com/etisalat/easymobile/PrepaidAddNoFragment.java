package com.etisalat.easymobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
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
 * Created by joshuabalogun on 9/10/15.
 */

public class PrepaidAddNoFragment extends Fragment
{

    private MaterialDialog dialog;

    public PrepaidAddNoFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_prepaid_addno, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");

        TextView easystarterAddText = (TextView)rootView.findViewById(R.id.easystarterAddText);
        EditText easystarterAddEdit = (EditText)rootView.findViewById(R.id.easystarterAddEdit);
        TextView easystarterAddCancel = (TextView)rootView.findViewById(R.id.easystarterAddCancel);

        easystarterAddText.setTypeface(font);
        easystarterAddEdit.setTypeface(font);
        easystarterAddEdit.setGravity(Gravity.CENTER_HORIZONTAL);
        easystarterAddCancel.setTypeface(font);

        easystarterAddEdit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(easystarterAddEdit, InputMethodManager.SHOW_IMPLICIT);


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