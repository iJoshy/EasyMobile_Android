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


public class PrepaidDeleteNoFragment extends Fragment
{

    private MaterialDialog dialog;

    public PrepaidDeleteNoFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_prepaid_deleteno, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");

        TextView easystarterDeleteText = (TextView)rootView.findViewById(R.id.easystarterDeleteText);
        EditText easystarterDeleteEdit = (EditText)rootView.findViewById(R.id.easystarterDeleteEdit);
        TextView easystarterDeleteCancel = (TextView)rootView.findViewById(R.id.easystarterDeleteCancel);

        easystarterDeleteText.setTypeface(font);
        easystarterDeleteEdit.setTypeface(font);
        easystarterDeleteEdit.setGravity(Gravity.CENTER_HORIZONTAL);
        easystarterDeleteCancel.setTypeface(font);

        easystarterDeleteEdit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(easystarterDeleteEdit, InputMethodManager.SHOW_IMPLICIT);

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
