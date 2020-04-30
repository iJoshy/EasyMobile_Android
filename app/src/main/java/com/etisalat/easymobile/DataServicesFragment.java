package com.etisalat.easymobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

/**
 * Created by portalservices on 8/31/15.
 */

public class DataServicesFragment extends Fragment
{

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private MaterialDialog dialog;

    public DataServicesFragment()
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

        preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        View rootView = inflater.inflate(R.layout.fragment_data_services, container, false);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "etisalat2.TTF");

        Button buyDataBtn = (Button)rootView.findViewById( R.id.buyDataBtn );
        Button dataTransferBtn = (Button)rootView.findViewById( R.id.dataTransferBtn );
        Button dataGiftingBtn = (Button)rootView.findViewById( R.id.dataGiftingBtn );
        Button optoutBtn = (Button)rootView.findViewById( R.id.optoutBtn );

        buyDataBtn.setTypeface(font1);
        dataTransferBtn.setTypeface(font1);
        dataGiftingBtn.setTypeface(font1);
        optoutBtn.setTypeface(font1);

        TextView databalanceText = (TextView)rootView.findViewById( R.id.databalanceText );
        TextView dataTransferText = (TextView)rootView.findViewById( R.id.dataTransferText );
        TextView buyDataText = (TextView)rootView.findViewById( R.id.buyDataText );
        TextView dataGiftingText = (TextView)rootView.findViewById( R.id.dataGiftingText );
        TextView optoutText = (TextView)rootView.findViewById( R.id.optoutText );

        databalanceText.setTypeface(font2);
        dataTransferText.setTypeface(font2);
        buyDataText.setTypeface(font2);
        dataGiftingText.setTypeface(font2);
        optoutText.setTypeface(font2);

        final ImageView imageViewBan = (ImageView)rootView.findViewById(R.id.AdvertBannerBtn);
        //System.out.println("strJson in view :::: " + strJson);

        rootView.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {

                    final WebServiceAD wbsAds = new WebServiceAD(getActivity());
                    final String strJson = wbsAds.promotion();
                    JSONObject jsonobject = new JSONObject(strJson);
                    //System.out.println("array :::: " + jsonobject);

                    String encodedImage = jsonobject.getString("imageByte");
                    final String url = jsonobject.getString("url");

                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    imageViewBan.setImageBitmap(decodedByte);

                    imageViewBan.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            //System.out.println(" ::: image clicked :::: ");

                            Bundle bundle = new Bundle();
                            bundle.putString("FORMURL", url);

                            WebviewFragment fragment = new WebviewFragment();
                            fragment.setArguments(bundle);

                            ((MainActivity)getActivity()).getFragment(fragment, "etisalat promo", 2);

                            return false;
                        }
                    });

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



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
