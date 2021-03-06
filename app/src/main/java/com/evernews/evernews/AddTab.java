package com.evernews.evernews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.net.SocketTimeoutException;

public class AddTab extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Context context;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    TabLayout tabLayout;
    LinearLayout tabStrip;
    View virtualView;

    private static SharedPreferences sharedpreferences;
    private boolean initialTabs(){
        try {
            int i=0;
            if (i == 0) {
                View v = View.inflate(getBaseContext(), R.layout.layout_tab_2, null);
                TextView tvt = (TextView) v.findViewById(R.id.tab_tv_2);
                tvt.setText("Featured");
                tabLayout.getTabAt(i).setCustomView(v);
                tabLayout.getTabAt(i).getCustomView().setBackgroundResource(R.drawable.tab_color2);
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_2_color));
                tabLayout.setSelectedTabIndicatorHeight(2);
            }
            i=1;
            if (i == 1) {
                View v = View.inflate(getApplicationContext(), R.layout.layout_tab_3, null);
                TextView tvt = (TextView) v.findViewById(R.id.tab_tv_3);
                tvt.setText("Popular");
                tabLayout.getTabAt(i).setCustomView(v);
                tabLayout.getTabAt(i).getCustomView().setBackgroundResource(R.drawable.tab_color3);
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_3_color));
                tabLayout.setSelectedTabIndicatorHeight(2);
            }
            i=2;
            if (i == 2) {
                View v = View.inflate(getApplicationContext(), R.layout.layout_tab_4, null);
                TextView tvt = (TextView) v.findViewById(R.id.tab_tv_4);
                tvt.setText("Categories");
                tabLayout.getTabAt(i).setCustomView(v);
                tabLayout.getTabAt(i).getCustomView().setBackgroundResource(R.drawable.tab_color4);
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_4_color));
                tabLayout.setSelectedTabIndicatorHeight(2);
            }
        }catch (Exception e){
            return(false);
        }
        return(true);
    }

    public void resetOtherTabs(int skipTab){
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            if(i==skipTab) {
                continue;
            }
            else{
                tabStrip.getChildAt(i).setBackgroundResource(R.drawable.tab_color1_rised_uncolored);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        /**RESTORE BRIGHTNESS**/
        {
            SharedPreferences sharedpreferences = getSharedPreferences(Main.USERLOGINDETAILS, Context.MODE_PRIVATE);
            float arg1=sharedpreferences.getFloat(Main.SLIDERCURRENT,250);
            float BackLightValue = (float)arg1/100;
            int curBrightnessValue=0;
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes(); // Get Params
            layoutParams.screenBrightness = BackLightValue; // Set Value
            getWindow().setAttributes(layoutParams); // Set params
        }
        /**END**/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setPageTransformer(true, new AccordionTransformer());

        context=this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_backpng);
        getSupportActionBar().setTitle("");

        sharedpreferences = getSharedPreferences(Main.USERLOGINDETAILS, Context.MODE_PRIVATE);
        if(sharedpreferences.getString(Main.APPLICATIONORIENTATION,"A").compareTo("L")==0){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else if(sharedpreferences.getString(Main.APPLICATIONORIENTATION,"A").compareTo("P")==0){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //if(Main.validCategory==false)
            //new GetCategoryList().execute();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        initialTabs();

        virtualView=(View) findViewById(R.id.virtual_tab_addtab);
        virtualView.setBackgroundColor(getResources().getColor(R.color.tab_2_color));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                int x = tab.getPosition();
                if ((x) == 0) {
                    int i=x;
                    tabStrip.getChildAt(i).setBackgroundResource(R.drawable.tab_color2);
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_2_color));
                    virtualView.setBackgroundColor(getResources().getColor(R.color.tab_2_color));
                    tabLayout.setSelectedTabIndicatorHeight(4);
                }
                if ((x)== 1) {
                    int i=x;
                    tabStrip.getChildAt(i).setBackgroundResource(R.drawable.tab_color3);
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_3_color));
                    virtualView.setBackgroundColor(getResources().getColor(R.color.tab_3_color));
                    tabLayout.setSelectedTabIndicatorHeight(4);
                }
                if ((x)== 2) {
                    int i=x;
                    tabStrip.getChildAt(i).setBackgroundResource(R.drawable.tab_color4);
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_4_color));
                    virtualView.setBackgroundColor(getResources().getColor(R.color.tab_4_color));
                    tabLayout.setSelectedTabIndicatorHeight(4);
                }
                resetOtherTabs(x);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabStrip = (LinearLayout) tabLayout.getChildAt(0);
        int tabPos=tabLayout.getSelectedTabPosition();
        tabStrip.getChildAt(tabPos).setBackgroundResource(R.drawable.tab_color2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_tab, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Bundle extras = getIntent().getExtras();
        String value="";
        if (extras != null) {
            value = extras.getString("CALLER");
            if ((keyCode == KeyEvent.KEYCODE_BACK) && value.compareTo("MAIN") == 0) {
                //Intent intent = new Intent(AddTab.this, Main.class);
                //startActivity(intent);
                finish();
                return (true);
            }
            else if ((keyCode == KeyEvent.KEYCODE_BACK) && value.compareTo("SETTINGS") == 0) {
                Intent intent = new Intent(AddTab.this, Settings.class);
                startActivity(intent);
                finish();
                return (true);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==android.R.id.home){
            //Intent intent=new Intent(AddTab.this,Main.class);
            //startActivity(intent);
            finish();
            return (true);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_tab, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return NewsAddListFragment.newInstanceREE(position, "Featured");
            }
            else if(position==1){
                return NewsAddListFragment.newInstanceREE(position, "Popular");
            }else{
                return ExpandCategoryView.newInstance(position, "Categories");
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Featured";
                case 1:
                    return "Popular";
                case 2:
                    return "Categories";
            }
            return null;
        }
    }

    class GetCategoryList extends AsyncTask<Void,Integer,Void>
    {
        ProgressDialog progressDlg;
        String content;
        int ExceptionCode=0;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            if(progress[0]==1){
                progressDlg.setTitle("Downloading");
                progressDlg.setMessage("Downloading data...");
            }
            if(progress[0]==2){
                progressDlg.setTitle("Formatting");
                progressDlg.setMessage("Formatting data...");
            }
        }

        @Override
        protected void onPreExecute()
        {
            progressDlg = ProgressDialog.show(context, "Connecting", "Please wait while we connect to our servers", true);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                publishProgress(1);
                String fetchLink="http://rssapi.psweb.in/everapi.asmx/GetNewsChannelList";//Over ride but should be Main.androidId
                content= Jsoup.connect(fetchLink).ignoreContentType(true).timeout(Initilization.timeout).execute().body();
                if(content.length()>50)
                    publishProgress(2);
            }
            catch(Exception e)
            {
                if(e instanceof SocketTimeoutException) {
                    ExceptionCode=1;
                    return null;
                }
                if(e instanceof HttpStatusException) {
                    ExceptionCode=2;
                    return null;
                }
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(content!=null)
            {
                String result = content.toString().replaceAll("&lt;", "<").replaceAll("&gt;",">").replaceAll("&amp;","&");
                // Log.d("response", result);
                //after getting the response we have to parse it
                parseResultsList(result);
            }
            progressDlg.dismiss();
            super.onPostExecute(aVoid);
        }
    }

    public void parseResultsList(String response)
    {

        org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(response, "", org.jsoup.parser.Parser.xmlParser());
        try {
            for (int i = 0; i < 15; i++) {
                if (i == 0) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("RSSUrlId")) {
                        Main.catListArray[index][0] = e.text();
                        index++;
                    }
                }
                if (i == 1) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("RSSURL")) {
                        Main.catListArray[index][1] = e.text();
                        index++;
                    }
                }
                if (i == 2) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("RSSTitle")) {
                        Main.catListArray[index][2] = e.text();
                        index++;
                    }
                }
                if (i == 3) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("Image")) {
                        Main.catListArray[index][3] = e.text();
                        index++;
                    }
                }
                if (i == 4) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("Detail")) {
                        Main.catListArray[index][4] = e.text();
                        index++;
                    }
                }
                if (i == 5) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("Comment")) {
                        Main.catListArray[index][5] = e.text();
                        index++;
                    }
                }
                if (i == 6) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("MediaHouse")) {
                        Main.catListArray[index][6] = e.text();
                        index++;
                    }
                }
                if (i == 7) {
                    int index = 0;
                    for (org.jsoup.nodes.Element e : jsoupDoc.select("NewsType")) {
                        Main.catListArray[index][7] = e.text();
                        index++;
                    }
                }
            }
            Main.validCategory=true;
        }catch (Exception e){
            Main.validCategory=false;
        }
    }
}
