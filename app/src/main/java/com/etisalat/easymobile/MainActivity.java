package com.etisalat.easymobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.etisalat.thirdparty.AnalyticsApplication;
import com.etisalat.thirdparty.EventsData;
import com.etisalat.thirdparty.FragmentDrawer;
import com.etisalat.thirdparty.RoundedImageView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by portalservices on 8/31/15.
 */

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener
{

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    TextView toolbarTitle;
    private TextView titleSettings;
    private Typeface myTypeface;

    private BuildAndSendRequest bnsr = null;
    private MaterialDialog dialog;

    private String dataplancode, dataamount, prodCode, dataplan;
    private int whichOpt;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EventsData events;

    static final int SELECT_PHOTO = 100;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    RoundedImageView ivImage;
    private WebServiceCall wbsCategories;
    private DrawerLayout mDrawer;

    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        myTypeface = Typeface.createFromAsset(getAssets(), "etisalat2.TTF");
        titleSettings = (TextView) findViewById(R.id.shareText);
        titleSettings.setTypeface(myTypeface);
        toolbarTitle.setTypeface(myTypeface);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        // display the first navigation drawer view on app launch
        displayView(0);

        preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        editor.putString("LASTFUNCTION", "HOME");
        editor.commit();

        ivImage = (RoundedImageView) findViewById(R.id.profileImg);

        String mypic = "", firsttime = "";
        mypic = preferences.getString("MYPIC","0");
        if (!mypic.equals("0"))
        {
            loadpicture(mypic);
            System.out.println("mypic path :::: "+mypic);
        }


        firsttime = preferences.getString("FIRSTTIME","0");
        if (firsttime.equals("1"))
        {
            initialLoading();
        }

    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }


    public void loadpicture(String mypic)
    {
        String selectedImagePath = mypic;

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        editor.putString("MYPIC", selectedImagePath);
        editor.commit();

        ivImage.setImageBitmap(bm);
    }


    public void initialLoading()
    {
        bnsr = new BuildAndSendRequest(this);
        bnsr.validation();

        editor.putString("FIRSTTIME", "0");
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logo)
        {
            displayView(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position)
    {
        displayView(position);
    }

    private void displayView(int position)
    {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        int level = 0;

        switch (position)
        {
            case 0:
                //Toast.makeText(getApplicationContext(), "Show My Account", Toast.LENGTH_SHORT).show();
                fragment = new MyAccountFragment();
                title = getString(R.string.title_myaccount);
                level = 0;
                break;
            case 1:
                //Toast.makeText(getApplicationContext(), "Prepaid", Toast.LENGTH_SHORT).show();
                fragment = new PrepaidFragment();
                title = getString(R.string.title_prepaid);
                level = 1;
                break;
            case 2:
                //Toast.makeText(getApplicationContext(), "Data Services", Toast.LENGTH_SHORT).show();
                fragment = new DataServicesFragment();
                title = getString(R.string.title_dataservices);
                level = 2;
                break;
            case 3:
                //Toast.makeText(getApplicationContext(), "Migration", Toast.LENGTH_SHORT).show();
                fragment = new StoresFragment();
                title = getString(R.string.title_stores);
                level = 3;
                break;
            case 4:
                //Toast.makeText(getApplicationContext(), "Migration", Toast.LENGTH_SHORT).show();
                fragment = new SupportFragment();
                title = "support";
                level = 4;
                break;
            case 5:
                //Toast.makeText(getApplicationContext(), "Migration", Toast.LENGTH_SHORT).show();
                fragment = new SocialFragment();
                title = "share";
                level = 5;
                break;
            default:
                break;
        }


        getFragment(fragment, title, level);

    }

    public void getFragment(Fragment fragment, String title, int level)
    {
        if (fragment != null)
        {
            String screenname = "";
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (level)
            {
                case 0:
                    screenname = "MyAccount";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 1:
                    screenname = "Prepaid";
                    //fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 2:
                    screenname = "DataServices";
                    //fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 3:
                    screenname = "Etisalat Stores";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 4:
                    screenname = "Support";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 5:
                    screenname = "Share";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                default:
                    break;
            }

            mTracker.setScreenName(screenname);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // set the toolbar title
            toolbarTitle.setText(title);
        }
    }


    public void profileImg(View v)
    {
        final CharSequence[] items = { "take photo", "choose from library", "cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("change profile photo !");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (items[item].equals("take photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[item].equals("choose from library"))
                {
                    Intent intent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
                else if (items[item].equals("cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data)
    {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try
        {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        editor.putString("MYPIC", selectedImagePath);
        editor.commit();

        ivImage.setImageBitmap(bm);
    }


    /******************
     *                *
     *  My Account    *
     *                *
     ******************/


    //1. Check Balance
    public void showBalance(View v)
    {
        mTracker.setScreenName("Airtime Balance");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.mainbalance();
    }


    //2. Data Balance
    public void showDataBalance(View v)
    {
        mTracker.setScreenName("Data Balance");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.databalance();
    }


    //3. Recharge Account

    public void showPaymentOptionScreen(View v)
    {
        mTracker.setScreenName("Payment Option");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PaymentFragment();
        String title = "recharge options";
        getFragment(fragment, title, 0);
    }

    public void showRechargeScreen(View v)
    {
        mTracker.setScreenName("Airtime Recharge");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new AirtimeRechargeFragment();
        String title = getString(R.string.title_recharge);
        getFragment(fragment, title, 0);
    }

    public void goRechargeNow(View v)
    {
        EditText rechargeEdit = (EditText) findViewById(R.id.rechargecodeEdit);
        final String rechargeNo = rechargeEdit.getText().toString();
        String displayMessage = "";

        bnsr = new BuildAndSendRequest(this);
        bnsr.recharge(rechargeNo);
    }


    public void goRechargeNowQT(View v)
    {
        mTracker.setScreenName("Airtime Recharge via Quickteller");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        Bundle bundle = new Bundle();
        bundle.putString("FORMURL", "https://paywith.quickteller.com/?paymentCode=90806&ispopup=true&site=etisalat.com.ng");

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);

        getFragment(fragment, "PayWithQuickteller", 0);
    }


    //4. Credit Transfer
    public void showTransferScreen(View v)
    {
        mTracker.setScreenName("Airtime Transfer");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new AirtimeTransferFragment();
        String title = getString(R.string.title_transfer);
        getFragment(fragment, title, 0);
    }

    public void goTransferNow(View v)
    {
        EditText securityCodeEdit = (EditText) findViewById(R.id.securityCodeEdit);
        final String securitycode = securityCodeEdit.getText().toString();

        EditText receiverNoEdit = (EditText) findViewById(R.id.receiverNoEdit);
        final String receiverNo = receiverNoEdit.getText().toString();

        EditText amountEdit = (EditText) findViewById(R.id.amountEdit);
        final String amountText = amountEdit.getText().toString();

        bnsr = new BuildAndSendRequest(this);
        bnsr.transfer(securitycode, receiverNo, amountText);

    }


    //5. Show Number
    public void showNumber(View v)
    {
        mTracker.setScreenName("Show Number");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.shownumber();
    }


    //6. Recharge Others
    public void showRechargeOthersScreen(View v)
    {

        mTracker.setScreenName("Recharge Others");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new AirtimeRechargeOthersFragment();
        String title = getString(R.string.title_recharge_others);

        getFragment(fragment, title, 0);

    }

    public void goRechargeOthersNow(View v)
    {
        EditText rechargecodeEdit = (EditText) findViewById(R.id.rechargecodeEdit);
        final String rechargecode = rechargecodeEdit.getText().toString();

        EditText receiverNoEdit = (EditText) findViewById(R.id.receiverNoEdit);
        final String receiverNo = receiverNoEdit.getText().toString();

        bnsr = new BuildAndSendRequest(this);
        bnsr.rechargeother(rechargecode, receiverNo);
    }


    //7. Show Package
    public void showPackage(View v)
    {
        mTracker.setScreenName("Show Package");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.checkpackage();
    }


    // Show My Account Screen
    public void showMyaccountScreen(View v)
    {
        displayView(0);
    }

    // Show Prepaid Screen
    public void showPrepaidScreen(View v)
    {
        displayView(1);
    }

    // Show Data Services Screen
    public void showDataServiceScreen(View v)
    {
        displayView(2);
    }



    /********************
     *                  *
     *     Prepaid      *
     *                  *
     ********************/


    //0. Evolution
    public void showEvolution(View v)
    {
        mTracker.setScreenName("easyflex evolution");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidEvolutionFragment();
        String title = "easyflex evolution";
        getFragment(fragment, title, 1);
    }


    // Buy Evolution Data
    public void buyEvoultionData(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYFLEXEVOLUTIONDATA");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if( ! (result == null || result == ""))
            {
                editor.putString("EASYFLEXEVOLUTIONDATAARRAY", result);
                editor.commit();

                mTracker.setScreenName("EasyFlex Evolution Data");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                Fragment fragment = new PrepaidEvolutionDataFragment();
                String title = "data +";
                getFragment(fragment, title, 2);
            }
        }

    }

    public void buyEvoultionDataNow(View v)
    {

        Button buttonPressImg = (Button)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        int which = Integer.parseInt(buttonCode);
        String displayMessage = "";
        dataplancode = "";

        String strJson = preferences.getString("EASYFLEXEVOLUTIONDATAARRAY","0");
        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            //System.out.println("array :::: " + jsonArray);
            JSONObject jsonobject;


            for (int i = 0; i < jsonArray.length(); i++)
            {
                if (which == i)
                {
                    jsonobject = jsonArray.getJSONObject(i);
                    displayMessage = jsonobject.getString("popupMsg");
                    dataamount = jsonobject.getString("amount");
                    dataplancode = jsonobject.getString("serviceFlag");
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        goBuyEvoultionNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

        mTracker.setScreenName("EasyFlex Evolution Data " + dataamount);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void goBuyEvoultionNow()
    {
        bnsr = new BuildAndSendRequest(this);
        bnsr.buyevolution(dataplancode, dataamount);
    }



    // Buy Evolution Voice
    public void buyEvoultionVoice(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYFLEXEVOLUTIONVOICE");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if( ! (result == null || result == ""))
            {
                editor.putString("EASYFLEXEVOLUTIONVOICEARRAY", result);
                editor.commit();

                mTracker.setScreenName("EasyFlex Evolution Voice");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                Fragment fragment = new PrepaidEvolutionVoiceFragment();
                String title = "voice +";
                getFragment(fragment, title, 2);
            }
        }

    }

    public void buyEvoultionVoiceNow(View v)
    {

        Button buttonPressImg = (Button)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        int which = Integer.parseInt(buttonCode);
        String displayMessage = "";
        dataplancode = "";

        String strJson = preferences.getString("EASYFLEXEVOLUTIONVOICEARRAY","0");
        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            //System.out.println("array :::: " + jsonArray);
            JSONObject jsonobject;


            for (int i = 0; i < jsonArray.length(); i++)
            {
                if (which == i)
                {
                    jsonobject = jsonArray.getJSONObject(i);
                    displayMessage = jsonobject.getString("popupMsg");
                    dataamount = jsonobject.getString("amount");
                    dataplancode = jsonobject.getString("serviceFlag");
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        goBuyEvoultionNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

        mTracker.setScreenName("EasyFlex Evolution Voice " + dataamount);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


    // Check Bundle
    public void evolutionCheckbundle(View v)
    {
        mTracker.setScreenName("EasyFlex Evolution Check Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.queryevolution();
    }

    // Cancel Bundle
    public void evolutionCancelbundle(View v)
    {
        mTracker.setScreenName("EasyFlex Evolution Cancel Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.cancelevolution();
    }


    //1. Easystarter
    public void showEasystarter(View v)
    {
        mTracker.setScreenName("EasyStarter");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidEasystarterFragment();
        String title = "easystarter";
        getFragment(fragment, title, 1);
    }

    // AddNo
    public void showEasystarterOptin(View v)
    {
        toEasystarter(v);
    }

    // AddNo
    public void showEasystarterAddNo(View v)
    {
        mTracker.setScreenName("EasyStarter AddNo");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidAddNoFragment();
        String title = "add number";
        getFragment(fragment, title, 1);
    }

    public void goAddNow(View v)
    {
        EditText easystarterAddEdit = (EditText) findViewById(R.id.easystarterAddEdit);
        final String younmeaddno = easystarterAddEdit.getText().toString();

        bnsr = new BuildAndSendRequest(this);
        bnsr.younmeaddno(younmeaddno);
    }

    // Delet No
    public void showEasystarterDeleteNo(View v)
    {
        mTracker.setScreenName("EasyStarter DeleteNo");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidDeleteNoFragment();
        String title = "delete number";
        getFragment(fragment, title, 1);
    }

    public void goDeleteNow(View v)
    {
        EditText easystarterDeleteEdit = (EditText) findViewById(R.id.easystarterDeleteEdit);
        final String younmedeleteno = easystarterDeleteEdit.getText().toString();

        bnsr = new BuildAndSendRequest(this);
        bnsr.younmedeleteno(younmedeleteno);
    }

    // Check No
    public void showEasystarterCheckNo(View v)
    {
        mTracker.setScreenName("EasyStarter CheckNo");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.younmequeryno();
    }

    // Replace No
    public void showEasystarterReplaceNo(View v)
    {
        mTracker.setScreenName("EasyStarter ReplaceNo");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidReplacenoFragment();
        String title = "replace number";
        getFragment(fragment, title, 1);
    }

    public void goReplaceNow(View v) {
        EditText easystarterReplaceEdit = (EditText) findViewById(R.id.easystarterReplaceEdit);
        final String younmereplaceno1 = easystarterReplaceEdit.getText().toString();

        EditText easystarterReplaceWithEdit = (EditText) findViewById(R.id.easystarterReplaceWithEdit);
        final String younmereplaceno2 = easystarterReplaceWithEdit.getText().toString();

        bnsr = new BuildAndSendRequest(this);
        bnsr.younmereplaceno(younmereplaceno1, younmereplaceno2);
    }



    //2. Easycliq
    public void showEasycliq(View v)
    {
        mTracker.setScreenName("EasyCliq");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidEasycliqFragment();
        String title = "easycliq";
        getFragment(fragment, title, 1);
    }

    // OPT In
    public void easycliqOptin(View v)
    {

        mTracker.setScreenName("EasyCliq Optin");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content("You will be charged N100 for migration to this plan. Do you wish to continue?")
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        easycliqOptinNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();
    }

    public void easycliqOptinNow()
    {
        bnsr = new BuildAndSendRequest(this);
        bnsr.cliqoptin();
    }

    // OPT Out
    public void easycliqOptout(View v)
    {
        mTracker.setScreenName("EasyCliq Optout");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidFragment();
        String title = "prepaid";
        getFragment(fragment, title, 1);

        Toast.makeText(MainActivity.this, (String) "To opt-out, select any other package and opt-in to it. This action will automatically opt you out.", Toast.LENGTH_LONG).show();

    }

    // Check Status
    public void easycliqCheckstatus(View v)
    {
        mTracker.setScreenName("EasyCliq CheckStatus");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.cliqoptquery();
    }


    //3. EasyFlex
    public void showEasyflex(View v)
    {
        mTracker.setScreenName("EasyFlex");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidEasyflexFragment();
        String title = "easyflex";
        getFragment(fragment, title, 1);
    }

    // Buy EasyFlex
    public void buyEasyFlex(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYFLEX");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if( ! (result == null || result == ""))
            {
                editor.putString("EASYFLEXARRAY", result);
                editor.commit();

                mTracker.setScreenName("EasyFlex Bundles");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                Fragment fragment = new PrepaidEasyflexBundlesFragment();
                String title = "easyflex bundles";
                getFragment(fragment, title, 2);
            }
        }

    }

    public void buyEasyFlexNow(View v)
    {

        Button buttonPressImg = (Button)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        int which = Integer.parseInt(buttonCode);
        String displayMessage = "";
        dataplancode = "";

        String strJson = preferences.getString("EASYFLEXARRAY","0");
        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            //System.out.println("array :::: " + jsonArray);
            JSONObject jsonobject;


            for (int i = 0; i < jsonArray.length(); i++)
            {
                if (which == i)
                {
                    jsonobject = jsonArray.getJSONObject(i);
                    displayMessage = jsonobject.getString("popupMsg");
                    dataamount = jsonobject.getString("amount");
                    dataplancode = jsonobject.getString("serviceFlag");
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        goBuyEasyFlexNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

        mTracker.setScreenName("EasyFlex " + dataamount + " Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void goBuyEasyFlexNow()
    {
        bnsr = new BuildAndSendRequest(this);
        bnsr.buyeasyflex(dataplancode, dataamount);
    }


    // Check Bundle
    public void easyFlexCheckbundle(View v)
    {
        mTracker.setScreenName("EasyFlex Check Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.queryeasyflex();
    }

    // Cancel Bundle
    public void easyFlexCancelbundle(View v)
    {
        mTracker.setScreenName("EasyFlex Cancel Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.canceleasyflex();
    }


    //4. Easylife

    public void  showEasylifeOptin(View v)
    {
        mTracker.setScreenName("EasyLife OptIn");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.easylifeOptin();
    }

    public void showEasylife(View v)
    {
        mTracker.setScreenName("EasyLife");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidEasylifeFragment();
        String title = "easylife";
        getFragment(fragment, title, 1);
    }

    public void showEasylifebundle(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYLIFE");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if( ! (result == null || result == ""))
            {
                editor.putString("EASYLIFEARRAY", result);
                editor.commit();

                mTracker.setScreenName("EasyLife Bundles");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                Fragment fragment = new PrepaidEasylifeBundlesFragment();
                String title = "easylife";
                getFragment(fragment, title, 1);
            }

        }

    }

    public void buyEasylifeNow(View v)
    {
        Button buttonPressImg = (Button)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        int which = Integer.parseInt(buttonCode);
        String displayMessage = "";
        dataplancode = "";

        String strJson = preferences.getString("EASYLIFEARRAY","0");
        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            //System.out.println("array :::: " + jsonArray);
            JSONObject jsonobject;


            for (int i = 0; i < jsonArray.length(); i++)
            {
                if (which == i)
                {
                    jsonobject = jsonArray.getJSONObject(i);
                    displayMessage = jsonobject.getString("popupMsg");
                    dataamount = jsonobject.getString("amount");
                    dataplancode = jsonobject.getString("serviceFlag");
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        goBuyEasylifeNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

        mTracker.setScreenName("EasyLife "+dataamount+" Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void goBuyEasylifeNow()
    {
        bnsr = new BuildAndSendRequest(this);
        bnsr.buyeasylife(dataplancode, dataamount);
    }

    // OPT Out
    public void easylifeOptout(View v)
    {
        mTracker.setScreenName("EasyLife Optout");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.canceleasylife();
    }

    // Check Status
    public void easylifeCheckstatus(View v)
    {
        mTracker.setScreenName("EasyLife CheckStatus");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        bnsr = new BuildAndSendRequest(this);
        bnsr.queryeasylife();
    }


    //5. Business
    public void showBusiness(View v)
    {
        mTracker.setScreenName("EasyBusiness");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new PrepaidBusinessFragment();
        String title = "easyBusiness";
        getFragment(fragment, title, 1);
    }


    // EasyExecutive
    public void showEasyExecutive(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYBUSINESS-2.0");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if( ! (result == null || result == ""))
            {
                editor.putString("EASYEXECUTIVEARRAY", result);
                editor.commit();

                mTracker.setScreenName("EasyBusiness Executive");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                Fragment fragment = new PrepaidEasyExecutiveFragment();
                String title = "easybusiness executive";
                getFragment(fragment, title, 1);
            }

        }

    }

    public void buyEasyexecutiveNow(View v)
    {

        Button buttonPressImg = (Button)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        int which = Integer.parseInt(buttonCode);
        String displayMessage = "";
        dataplancode = "";
        prodCode = "";

        String strJson = preferences.getString("EASYEXECUTIVEARRAY","0");
        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            //System.out.println("array :::: " + jsonArray);
            JSONObject jsonobject;


            for (int i = 0; i < jsonArray.length(); i++)
            {
                if (which == i)
                {
                    jsonobject = jsonArray.getJSONObject(i);
                    displayMessage = jsonobject.getString("popupMsg");
                    dataamount = jsonobject.getString("amount");
                    dataplancode = jsonobject.getString("serviceFlag");
                    prodCode = (i+1) + "";
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        goBuyEasyexecutiveNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

        mTracker.setScreenName("EasyBusiness "+dataamount+ " Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void goBuyEasyexecutiveNow()
    {
        bnsr = new BuildAndSendRequest(this);
        bnsr.buyeasybexec(dataplancode, prodCode, dataamount);
    }


    // EasyBusiness
    public void showEasybusiness(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYBUSINESS");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if( ! (result == null || result == ""))
            {
                editor.putString("EASYBUSINESSARRAY", result);
                editor.commit();

                mTracker.setScreenName("EasyBusiness Classic");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                Fragment fragment = new PrepaidEasybusinessFragment();
                String title = "easybusiness classic";
                getFragment(fragment, title, 1);
            }

        }

    }

    public void buyEasybusinessNow(View v)
    {

        Button buttonPressImg = (Button)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        int which = Integer.parseInt(buttonCode);
        String displayMessage = "";
        dataplancode = "";
        prodCode = "";

        String strJson = preferences.getString("EASYBUSINESSARRAY","0");
        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            //System.out.println("array :::: " + jsonArray);
            JSONObject jsonobject;


            for (int i = 0; i < jsonArray.length(); i++)
            {
                if (which == i)
                {
                    jsonobject = jsonArray.getJSONObject(i);
                    displayMessage = jsonobject.getString("popupMsg");
                    dataamount = jsonobject.getString("amount");
                    dataplancode = jsonobject.getString("serviceFlag");
                    prodCode = (i+1) + "";
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        goBuyEasybusinessNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

        mTracker.setScreenName("EasyBusiness "+dataamount+ " Bundle");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void goBuyEasybusinessNow()
    {
        bnsr = new BuildAndSendRequest(this);
        bnsr.buyeasybiz(dataplancode, prodCode, dataamount);
    }


    /********************
     *                  *
     *  Data Services   *
     *                  *
     ********************/


    //1. Data Balance
    public void dataBalance(View v)
    {
        //System.out.println("1 ....");
        showDataBalance(v);
    }



    public void showBuydataScreen(View v)
    {
        mTracker.setScreenName("Buy Data Options");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new BuydataFragment();
        String title = "data purchase options";
        getFragment(fragment, title, 2);

    }


    //2. Buy Data Plan
    public void buyDataPlan(View v)
    {

        BootstrapButton buttonPressImg = (BootstrapButton)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        whichOpt = Integer.parseInt(buttonCode);

        String resultOld = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            resultOld = wbsCategories.categories("EASYBLAZE");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if( ! (resultOld == null || resultOld == ""))
            {
                String result = "";

                if (whichOpt == 223)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(resultOld);
                        JSONObject jsonobject;
                        JSONArray newja = new JSONArray();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonobject = jsonArray.getJSONObject(i);
                            String iswCode = jsonobject.getString("interswitchDataCode");

                            if (!iswCode.equals("N/A"))
                                newja.put(jsonobject);
                        }

                        result = newja.toString();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (whichOpt == 222)
                {
                    result = resultOld;
                }

                editor.putString("EASYBLAZEARRAY", result);
                editor.commit();

                mTracker.setScreenName("EasyBlaze Bundles");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                Fragment fragment = new DataBundlesFragment();
                String title = getString(R.string.title_databundles);
                getFragment(fragment, title, 2);
            }
        }

    }

    public void buyDataPlanNow(View v)
    {

        Button buttonPressImg = (Button)findViewById(v.getId());
        String buttonCode = buttonPressImg.getTag().toString();
        int which = Integer.parseInt(buttonCode);

        String displayMessage = "";
        dataplancode = "";
        dataplan = "";
        dataamount = "";
        prodCode = "";

        String strJson = preferences.getString("EASYBLAZEARRAY","0");
        try
        {
            JSONArray jsonArray = new JSONArray(strJson);
            //System.out.println("array :::: " + jsonArray);
            JSONObject jsonobject;


            for (int i = 0; i < jsonArray.length(); i++)
            {
                if (which == i)
                {
                    jsonobject = jsonArray.getJSONObject(which);
                    displayMessage = jsonobject.getString("popupMsg");
                    dataamount = jsonobject.getString("amount");
                    dataplancode = jsonobject.getString("serviceFlag");
                    dataplan = jsonobject.getString("type");
                    prodCode = jsonobject.getString("interswitchDataCode");
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        if (whichOpt == 222)
        {

            mTracker.setScreenName("EasyBlaze " + dataplan + " Bundle purchase with Airtime");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            new MaterialDialog.Builder(this)
                    .iconRes(R.drawable.logo)
                    .limitIconToDefaultSize()
                    .title("etisalat")
                    .content(displayMessage)
                    .positiveText("ok")
                    .negativeText("cancel")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            goBuyDataPlanNow();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            //
                        }
                    })
                    .show();
        }
        else if (whichOpt == 223)
        {
            goBuyDataPlanNowQT();
        }

    }


    public void goBuyDataPlanNow()
    {

        bnsr = new BuildAndSendRequest(this);
        bnsr.databundles(dataplancode, dataamount, dataplan);
    }


    public void goBuyDataPlanNowQT()
    {

        String iswURL = "https://paywith.quickteller.com/?paymentCode="+ prodCode +"&amount="+ dataamount +"&ispopup=true&site=etisalat.com.ng";

        Bundle bundle = new Bundle();
        bundle.putString("FORMURL", iswURL);

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);

        getFragment(fragment, "PayWithQuickteller", 2);
    }


    //3. Data Transfer
    public void showDataTransferScreen(View v)
    {
        mTracker.setScreenName("Data Transfer");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new DataTransferFragment();
        String title = getString(R.string.title_data_transfer);
        getFragment(fragment, title, 2);
    }

    public void dataTransferNow(View v)
    {
        EditText securityCodeEdit = (EditText) findViewById(R.id.securityCodeEdit);
        final String securitycode = securityCodeEdit.getText().toString();

        EditText receiverNoEdit = (EditText) findViewById(R.id.receiverNoEdit);
        final String receiverNo = receiverNoEdit.getText().toString();

        EditText amountEdit = (EditText) findViewById(R.id.amountEdit);
        final String amountText = amountEdit.getText().toString();

        bnsr = new BuildAndSendRequest(this);
        bnsr.datatransfer(securitycode, receiverNo, amountText);
    }


    //4. Data Gifting
    public void showDataGiftingScreen(View v)
    {
        mTracker.setScreenName("Data Gifting");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment fragment = new DataGiftingTransfer();
        String title = getString(R.string.title_data_gifting);
        getFragment(fragment, title, 2);
    }

    public void dataGiftingNow(View v)
    {

        EditText receiverNoEdit = (EditText) findViewById(R.id.receiverNoEdit);
        final String receiverNo = receiverNoEdit.getText().toString();

        if ( receiverNo.length() == 11  )
        {
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            int code = spinner.getSelectedItemPosition();

            String displayMessage = "You will be charged N";

            String strJson = preferences.getString("GIFTINGARRAY", "0");
            try
            {
                JSONArray jsonArray = new JSONArray(strJson);
                //System.out.println("array :::: " + jsonArray);
                JSONObject jsonobject;


                for (int i = 0; i < jsonArray.length(); i++)
                {
                    if (code == i)
                    {
                        jsonobject = jsonArray.getJSONObject(i);
                        dataamount = jsonobject.getString("cost");
                        dataplancode = jsonobject.getString("wsCode");
                    }
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            int amtInt = Integer.parseInt(dataamount) / 100;
            dataamount = amtInt + "";

            displayMessage += dataamount + " for this plan. Do you wish to continue?";

            new MaterialDialog.Builder(this)
                    .iconRes(R.drawable.logo)
                    .limitIconToDefaultSize()
                    .title("etisalat")
                    .content(displayMessage)
                    .positiveText("ok")
                    .negativeText("cancel")
                    .callback(new MaterialDialog.ButtonCallback()
                    {
                        @Override
                        public void onPositive(MaterialDialog dialog)
                        {
                            goDataGiftingNow();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog)
                        {
                            //
                        }
                    })
                    .show();

            mTracker.setScreenName("DataGifting "+dataamount);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
        else
        {
            new MaterialDialog.Builder(this)
                    .iconRes(R.drawable.logo)
                    .limitIconToDefaultSize()
                    .title("etisalat")
                    .content("enter an 11 digit receiver number")
                    .positiveText("ok")
                    .callback(new MaterialDialog.ButtonCallback()
                    {
                        @Override
                        public void onPositive(MaterialDialog dialog)
                        {
                            //
                        }
                    })
                    .show();
        }

    }


    public void goDataGiftingNow()
    {
        EditText receiverNoEdit = (EditText) findViewById(R.id.receiverNoEdit);
        final String receiverNo = receiverNoEdit.getText().toString();

        bnsr = new BuildAndSendRequest(this);
        bnsr.datagifting(dataplancode, receiverNo);
    }


    //5. Opt Out
    public void optout(View v)
    {
        mTracker.setScreenName("Dataplan Optout");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        String displayMessage = "Are you sure you want to opt-out of your current data plan?";

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        goToOptOutNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

    }


    public void goToOptOutNow()
    {

        bnsr = new BuildAndSendRequest(this);
        bnsr.optout();
    }



    /********************
     *                  *
     *    Migration     *
     *                  *
     ********************/


    //1. To EasyStarter
    public void toEasystarter(View v)
    {
        mTracker.setScreenName("EasyStarter OPtIn");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        String displayMessage = "You will be charged N100 for this plan. Do you wish to continue?";

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        toEasystarterNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

    }


    public void toEasystarterNow()
    {

        bnsr = new BuildAndSendRequest(this);
        bnsr.younmemigrate();
    }

    //2. To EasyCliq
    public void toEasycliq(View v)
    {

        mTracker.setScreenName("EasyCliq Migration");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        String displayMessage = "You will be charged N100 for this plan. Do you wish to continue?";

        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .limitIconToDefaultSize()
                .title("etisalat")
                .content(displayMessage)
                .positiveText("ok")
                .negativeText("cancel")
                .callback(new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        toEasycliqNow();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        //
                    }
                })
                .show();

    }


    public void toEasycliqNow()
    {

        bnsr = new BuildAndSendRequest(this);
        bnsr.cliqmigrate();
    }

    //3. To EasyFlex
    public void toEasyflex(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYFLEX");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

            editor.putString("EASYFLEXARRAY", result);
            editor.commit();

            mTracker.setScreenName("EasyFlex Migration");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            Fragment fragment = new PrepaidEasyflexBundlesFragment();
            String title = "migration";
            getFragment(fragment, title, 3);
        }

    }


    //4. To Easylife
    public void toEasylife(View v)
    {
        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYLIFE");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

            editor.putString("EASYLIFEARRAY", result);
            editor.commit();

            mTracker.setScreenName("EasyLife Migration");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            Fragment fragment = new PrepaidEasylifeBundlesFragment();
            String title = "migration";
            getFragment(fragment, title, 3);
        }

    }


    //5. To Easybusiness
    public void toEasybusiness(View v)
    {

        String result = "";

        try
        {
            wbsCategories = new WebServiceCall(this);
            result = wbsCategories.categories("EASYBUSINESS");

            //System.out.println("array in home :::: "+result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

            editor.putString("EASYBUSINESSARRAY", result);
            editor.commit();

            mTracker.setScreenName("EasyBusiness Migration");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            Fragment fragment = new PrepaidEasybusinessFragment();
            String title = "migration";
            getFragment(fragment, title, 3);
        }

    }


    /********************
     *                  *
     *     Support      *
     *                  *
     ********************/


    //1. Support Website
    public void showWebsiteScreen(View v)
    {
        mTracker.setScreenName("Website Support");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Bundle bundle = new Bundle();
        bundle.putString("FORMURL", "http://www.etisalat.com.ng");

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);

        getFragment(fragment, "etisalat website", 4);

    }

    //2. Support Email
    public void showEmailScreen(View v)
    {

        mTracker.setScreenName("Email Support");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"care@etisalat.com.ng"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "EasyMobile_Support");
        startActivity(Intent.createChooser(emailIntent, "EasyMobile_Support"));
    }

    //3. Support Chat
    public void showChatScreen(View v)
    {
        mTracker.setScreenName("Chat Support");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        startActivity(new Intent(this, ZopimChatActivity.class));
    }

    //4. Support Facebook
    public void showFacebookScreen(View v)
    {
        mTracker.setScreenName("Facebook Support");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        Bundle bundle = new Bundle();
        bundle.putString("FORMURL", "https://www.facebook.com/etisalatnigeria");

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);

        getFragment(fragment, "facebook support", 4);
    }

    //5. Support Twitter
    public void showTwitterScreen(View v)
    {
        mTracker.setScreenName("Twitter Support");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Bundle bundle = new Bundle();
        bundle.putString("FORMURL", "https://twitter.com/0809ja_support");

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);

        getFragment(fragment, "twitter support", 4);
    }



    /********************
     *                  *
     *     Social       *
     *                  *
     ********************/


    public void shareTapped(View v)
    {

        mDrawer.closeDrawers();

        Fragment fragment = new SocialFragment();
        String title = "social media";
        getFragment(fragment, title, 5);
    }


    public void facebookClicked(View v)
    {

        final String shareMsg = "I use EasyMobile App to manage my Etisalat account & its awesome! You can download using this link: https://goo.gl/hIqLW8";

        Intent normalIntent = new Intent(Intent.ACTION_SEND);
        normalIntent.setType("text/plain");
        normalIntent.setPackage("com.facebook.katana"); // I just know the package of Facebook, the rest you will have to search for or use my method.
        normalIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
        startActivity(Intent.createChooser(normalIntent, "Facebook Share"));
    }


    public void twitterClicked(View v)
    {

        final String shareMsg = "I use EasyMobile App to manage my Etisalat account & its awesome! You can download using this link: https://goo.gl/hIqLW8";

        Intent normalIntent = new Intent(Intent.ACTION_SEND);
        normalIntent.setType("text/plain");
        normalIntent.setPackage("com.twitter.android"); // I just know the package of Facebook, the rest you will have to search for or use my method.
        normalIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
        startActivity(Intent.createChooser(normalIntent, "Twitter Share"));
    }


    public void gplusClicked(View v)
    {

        final String shareMsg = "I use EasyMobile App to manage my Etisalat account & its awesome! You can download using this link: https://goo.gl/hIqLW8";

        Intent normalIntent = new Intent(Intent.ACTION_SEND);
        normalIntent.setType("text/plain");
        normalIntent.setPackage("com.google.android.apps.plus"); // I just know the package of Facebook, the rest you will have to search for or use my method.
        normalIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
        startActivity(Intent.createChooser(normalIntent, "Googleplus Share"));
    }


}
