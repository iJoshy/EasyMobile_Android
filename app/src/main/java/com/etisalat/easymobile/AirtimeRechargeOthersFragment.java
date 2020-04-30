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

public class AirtimeRechargeOthersFragment extends Fragment
{
    private MaterialDialog dialog;

    public AirtimeRechargeOthersFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_airtime_rechargeothers, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "etisalat.TTF");

        TextView rechargecodeText = (TextView)rootView.findViewById(R.id.rechargecodeText);
        EditText rechargecodeEdit = (EditText)rootView.findViewById(R.id.rechargecodeEdit);
        TextView receiverNoText = (TextView)rootView.findViewById(R.id.receiverNoText);
        EditText receiverNoEdit = (EditText)rootView.findViewById(R.id.receiverNoEdit);
        TextView rechargecodeCancel = (TextView)rootView.findViewById(R.id.rechargecodeCancel);

        rechargecodeText.setTypeface(font);
        rechargecodeEdit.setTypeface(font);
        receiverNoText.setTypeface(font);
        receiverNoEdit.setTypeface(font);
        rechargecodeEdit.setGravity(Gravity.CENTER_HORIZONTAL);
        rechargecodeCancel.setTypeface(font);

        rechargecodeEdit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(rechargecodeEdit, InputMethodManager.SHOW_IMPLICIT);


        /* Floating Action Button.
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Do something awesome?", Snackbar.LENGTH_LONG).show();
                getBalance();
            }
        });
        */

        // Inflate the layout for this fragment
        return rootView;
    }

    /*
    public void getBalance()
    {
        //System.out.println("1 ....");
        dialog =  new MaterialDialog.Builder(getActivity())
                .title("Processing")
                .content("Please wait ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //System.out.println("2 ....");
                dialog.dismiss();
                //System.out.println("3 ....");
                dialog =  new MaterialDialog.Builder(getActivity())
                        .iconRes(R.drawable.logo)
                        .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                        .title("etisalat")
                        .content("Your balance is N2000. Dial *200# to access our list of services.\nThank you !")
                        .positiveText("ok")
                        .show();
            }
        }, 5000);

    }
    */

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}