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


public class PrepaidReplacenoFragment extends Fragment
{

    private MaterialDialog dialog;

    public PrepaidReplacenoFragment()
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
        View rootView = inflater.inflate(R.layout.fragment_prepaid_replaceno, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");

        TextView easystarterReplaceText = (TextView)rootView.findViewById(R.id.easystarterReplaceText);
        EditText easystarterReplaceEdit = (EditText)rootView.findViewById(R.id.easystarterReplaceEdit);
        TextView easystarterReplaceWithText = (TextView)rootView.findViewById(R.id.easystarterReplaceWithText);
        EditText easystarterReplaceWithEdit = (EditText)rootView.findViewById(R.id.easystarterReplaceWithEdit);
        TextView easystarterReplaceCancel = (TextView)rootView.findViewById(R.id.easystarterReplaceCancel);

        easystarterReplaceText.setTypeface(font);
        easystarterReplaceEdit.setTypeface(font);
        easystarterReplaceWithText.setTypeface(font);
        easystarterReplaceWithEdit.setTypeface(font);
        easystarterReplaceEdit.setGravity(Gravity.CENTER_HORIZONTAL);
        easystarterReplaceCancel.setTypeface(font);

        easystarterReplaceEdit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(easystarterReplaceEdit, InputMethodManager.SHOW_IMPLICIT);

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
