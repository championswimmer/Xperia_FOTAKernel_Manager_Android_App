package in.championswimmer.twrpxperia.flashutils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

import in.championswimmer.twrpxperia.R;

/**
 * Created by championswimmer on 22/1/14.
 */
public class FlashFota extends AsyncTask<Void, Void, Void> {

    private String FOTA_PATH = "/dev/block/platform/msm_sdcc.1/by-name/";
    private String XPERIA = "yuga";
    private String method;
    Process p = null;
    String[] cmds = null;
    SaveDir dir = new SaveDir();

    public FlashFota (Context context, String flashMethod) {
        method = flashMethod;
        String[] prop = context.getResources().getStringArray(R.array.supported_device_prop);
        String[] codename = context.getResources().getStringArray(R.array.supported_device_codename);
        String[] fotaPath = context.getResources().getStringArray(R.array.fota_path);

        Log.d("XFM", "new object to "+flashMethod+" fota created");

        for (int i = 0; i < prop.length; i++) {
            //Log.d("XFM", "checking "+ Build.DEVICE + " against " + prop[i]);
            if (prop[i].equalsIgnoreCase(Build.DEVICE)) {
                XPERIA = codename[i]; //Log.d("XFM", "xperia " + XPERIA);
                FOTA_PATH = FOTA_PATH + fotaPath[i]; //Log.d("XFM", "fotapath " + FOTA_PATH);
            }
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            p = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            //TODO:
            // add method to handle root not present situation
            e.printStackTrace();
        }
        DataOutputStream os = new DataOutputStream(p.getOutputStream());


        if (method.equalsIgnoreCase("format")) {
            cmds = new String[]{
                    "dd if=/dev/zero of=" + FOTA_PATH};
            Log.d("XFM", "preparing to format fota");

        }


        if (method.equalsIgnoreCase("backup")) {
        }


        for (String tmpCmd : cmds) {
            try {
                os.writeBytes(tmpCmd+"\n");
            } catch (IOException e) {
                //TODO:
                // handle operation failure here
                e.printStackTrace();
            }
        }
        try {
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            //TODO:
            // handle operation failure here
            e.printStackTrace();
        }
        return null;
    }
}
