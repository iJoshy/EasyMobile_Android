package com.etisalat.easymobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by portalservices on 23/02/16.
 */
public class WebviewFragment extends Fragment
{

    private WebView myWebView;
    private MaterialDialog dialog;
    private static WebView webView;

    public WebviewFragment()
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
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);


        String strURL = getArguments().getString("FORMURL");

        dialog =  new MaterialDialog.Builder(getContext())
                .title("Loading")
                .content("Please wait ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();

        webView = (WebView) rootView.findViewById(R.id.webView);
        // webView.setInitialScale(1);
        //webView.setWebChromeClient(new WebChromeClient());
        // webView.getSettings().setAllowFileAccess(true);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUseWideViewPort(true);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {

                dialog.dismiss();

            }
        });


        webView.loadUrl(strURL);



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
