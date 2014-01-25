package in.championswimmer.twrpxperia.flashutils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import in.championswimmer.twrpxperia.R;

/**
 * Created by championswimmer on 22/1/14.
 */
public class FlashFota extends AsyncTask<Void, Void, Void> {

    private String FOTA_PATH = "/dev/block/platform/msm_sdcc.1/by-name/";
    private String XPERIA = "yuga";
    private String method;

    public FlashFota (Context context, String flashMethod) {
        method = flashMethod;
        String[] prop = context.getResources().getStringArray(R.array.supported_device_prop);
        String[] codename = context.getResources().getStringArray(R.array.supported_device_codename);
        String[] fotaPath = context.getResources().getStringArray(R.array.fota_path);

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
        if (method.equalsIgnoreCase("format")) {
            return null;
        }
        if (method.equalsIgnoreCase("backup")) {
            return null;
        }
        return null;
    }
}
