package in.championswimmer.twrpxperia.flashutils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

import in.championswimmer.twrpxperia.R;

/**
 * Created by championswimmer on 22/1/14.
 */
public class FlashFota {

    private String LOG_TAG = "XRM FlashFota";


    private String FOTA_PATH = "/dev/block/platform/msm_sdcc.1/by-name/";
    private String XPERIA = "yuga";
    private String method;
    SaveDir dir = new SaveDir();

    public Boolean hasRoot() {
        Process root;
        DataOutputStream os;
        try {
            root = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(root.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            return ((root.waitFor()) == 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public FlashFota(Context context) {
        String[] prop = context.getResources().getStringArray(R.array.supported_device_prop);
        String[] codename = context.getResources().getStringArray(R.array.supported_device_codename);
        String[] fotaPath = context.getResources().getStringArray(R.array.fota_path);

        Log.d(LOG_TAG, "new object to manipulate fota created");

        for (int i = 0; i < prop.length; i++) {
            //Log.d(LOG_TAG, "checking "+ Build.DEVICE + " against " + prop[i]);
            if (prop[i].equalsIgnoreCase(Build.DEVICE)) {
                XPERIA = codename[i]; //Log.d(LOG_TAG, "xperia " + XPERIA);
                FOTA_PATH = FOTA_PATH + fotaPath[i]; //Log.d(LOG_TAG, "fotapath " + FOTA_PATH);
            }
        }
    }

    public void format() {
        Log.d(LOG_TAG, "preparing to format fota");
        Process p;
        DataOutputStream os;
        //the format command
        String[] cmds = new String[]{
                "dd if=/dev/zero of=" + FOTA_PATH};
        try {
            //create a new root shell
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            //add the commands to the process
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            //flush out the process
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backup() {
        Log.d(LOG_TAG, "preparing to backup fota");
        Process p;
        DataOutputStream os;
        //the backup command
        String[] cmds = new String[]{
                "dd if=" + FOTA_PATH + " of=" + dir.backupBath()};
        try {
            //create a new non root shell
            p = Runtime.getRuntime().exec("sh");
            os = new DataOutputStream(p.getOutputStream());
            //add the commands to the process
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            //flush out the process
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void restore() {
        Log.d(LOG_TAG, "preparing to restore fota");
        Process p;
        DataOutputStream os;
        //the format command
        String[] cmds = new String[]{
                "dd if=" + dir.backupBath() + " of=" + FOTA_PATH};
        try {
            //create a new root shell
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            //add the commands to the process
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            //flush out the process
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
