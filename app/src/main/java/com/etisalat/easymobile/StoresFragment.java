package com.etisalat.easymobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.etisalat.thirdparty.HandleXML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by portalservices on 9/3/15.
 */
public class StoresFragment extends Fragment
{

    private String newUrl = "http://etisalat.com.ng/wp-content/themes/etisalat/stores/data/stores.xml";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MaterialDialog dialog;

    public StoresFragment()
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

        View rootView = inflater.inflate(R.layout.fragment_stores, container, false);

        /* Create and customize RecyclerView. */
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        new AsyncHttpTask().execute(newUrl);


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


    /* Create RecylcerView Adapter. */
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        private Activity _context;
        private ArrayList mDataset = new ArrayList();

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public TextView title;
            public TextView descr;
            public ItemClickSupport mListener;

            public ViewHolder(View v, ItemClickSupport listener)
            {
                super(v);
                mListener = listener;
                title = (TextView) v.findViewById(R.id.card_title);
                descr = (TextView) v.findViewById(R.id.card_descr);

                title.setOnClickListener(this);
                descr.setOnClickListener(this);
            }

            @Override
            public void onClick(View v)
            {
                mListener.onText((TextView) v);
            }

            public static interface ItemClickSupport
            {
                public void onText(TextView callerText);
            }

        }

        public MyAdapter(ArrayList myDataset, Activity c)
        {
            mDataset = myDataset;
            _context = c;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

            ViewHolder vh = new ViewHolder(cardview, new ViewHolder.ItemClickSupport()
            {
                public void onText(TextView callerText)
                {
                    gotoDetail(callerText);
                }
            });

            return vh;
        }


        public void gotoDetail(View fromView)
        {
            int position = Integer.parseInt(fromView.getTag().toString());

            Dictionary access = (Dictionary)mDataset.get(position);
            String cord = access.get("cordinates").toString();
            String cordAr[]  = cord.split(",");

            double longi = Double.parseDouble(cordAr[0]);
            double lati = Double.parseDouble(cordAr[1]);

            System.out.println("longi is " + longi);
            System.out.println("lati is " + lati);

            //Uri gmmIntentUri = Uri.parse("geo:"+lati+","+longi);
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lati+","+longi);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            ActivityCompat.startActivity(_context, mapIntent, null);
            _context.overridePendingTransition(R.anim.slide_in_right, R.anim.scale_down);

        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            final Typeface font2 = Typeface.createFromAsset(_context.getAssets(), "etisalat.TTF");

            String descr = "";
            String title = "";

            Dictionary access = (Dictionary)mDataset.get(position);
            title = access.get("name").toString();
            descr = (access.get("description") == null) ? "N/A" : access.get("description").toString();

            holder.title.setText(title);
            holder.descr.setText(descr);

            holder.title.setTypeface(font2);
            holder.descr.setTypeface(font2);

            holder.title.setTag(position);
            holder.descr.setTag(position);
        }


        @Override
        public int getItemCount()
        {
            return mDataset.size();
        }

    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer>
    {

        ArrayList al = new ArrayList();

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // Showing progress dialog
            dialog =  new MaterialDialog.Builder(getContext())
                    .title("Loading")
                    .content("Please wait ...")
                    .progress(true, 0)
                    .progressIndeterminateStyle(true)
                    .show();
        }

        @Override
        protected Integer doInBackground(String... params)
        {
            Integer result = 0;
            HttpURLConnection urlConnection;
            HandleXML obj;

            try
            {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();
                //System.out.print("statuscode  :::: "+statusCode);

                // 200 represents HTTP OK
                if (statusCode == 200)
                {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null)
                    {
                        response.append(line);
                    }

                    //parseResult(response.toString());
                    obj = new HandleXML(response.toString());
                    al = obj.fetchXML();

                    result = 1; // Successful
                }
                else
                {
                    result = 0; //"Failed to fetch data!";
                }
            }
            catch (Exception e)
            {
                //Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result)
        {

            if (result == 1)
            {

                MyAdapter adapter = new MyAdapter(al, getActivity());
                recyclerView.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();

        }

    }

}

