package in.championswimmer.twrpxperia;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import in.championswimmer.twrpxperia.flashutils.FlashFota;
import in.championswimmer.twrpxperia.flashutils.GetImg;
import in.championswimmer.twrpxperia.fragment.CwmFragment;
import in.championswimmer.twrpxperia.fragment.FotaFragment;
import in.championswimmer.twrpxperia.fragment.TwrpFragment;

;

public class MainActivity extends Activity
        implements
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        CwmFragment.OnFragmentInteractionListener,
        FotaFragment.OnFragmentInteractionListener,
        TwrpFragment.OnFragmentInteractionListener {

    private String HAS_ROOT_PREF = "hasRoot";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        FlashFota chkRoot = new FlashFota(getApplicationContext());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putBoolean(HAS_ROOT_PREF, chkRoot.hasRoot());
        prefEditor.commit();

        GetImg gi = new GetImg(this);
        if (!gi.isSupported()) {
            AlertDialog.Builder noSupportAlert = new AlertDialog.Builder(this);
            noSupportAlert.setTitle("UNSUPPORTED DEVICE");
            noSupportAlert.setMessage
                    ("This device is not officially supported by the app" +
                    "\n" +
                    "Recoveries downloaded by the app may not be compatible" +
                    "with your device as it has not been tested on it" +
                    "\n" +
                    "Proceed to use this app only if you are sure of what you are doing");
            noSupportAlert.setPositiveButton(
                    "Got it !",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }
            );
            noSupportAlert.setNegativeButton(
                    "Exit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }
            );
            noSupportAlert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            });

            noSupportAlert.show();
        }

        if (!chkRoot.hasRoot()) {
            AlertDialog.Builder noRootAlert = new AlertDialog.Builder(this);
            noRootAlert.setTitle("NO ROOT !! ");
            noRootAlert.setMessage
                    ("Your phone is not rooted or this app does not have Super user access." +
                    "\n" +
                    "Functions like flashing and restoring FOTAKernel will be disabled" +
                    "\n" +
                    "Only downloading recovery images is allowed");
            noRootAlert.setPositiveButton(
                    "Got it !",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }
            );
            noRootAlert.setNegativeButton(
                    "Exit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }
            );

            noRootAlert.show();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        String[] titles = getResources().getStringArray(R.array.section_titles);
        getActionBar().setTitle(titles[position]);
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FotaFragment.newInstance("A", "a"))
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TwrpFragment.newInstance("A", "a"))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CwmFragment.newInstance("A", "a"))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        String[] titles = getResources().getStringArray(R.array.section_titles);
        mTitle = titles[number - 1];
        setTitle(mTitle);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
