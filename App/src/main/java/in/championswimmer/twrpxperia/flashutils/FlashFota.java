package in.championswimmer.twrpxperia.flashutils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    private Context c;

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
        c = context;

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
        InputStream is;
        String res = "";
        //the format command
        String[] cmds = new String[]{
                "dd if=/dev/zero of=" + FOTA_PATH};
        try {
            //create a new root shell
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            is = p.getErrorStream();
            //add the commands to the process
            for (String tmpCmd : cmds) {
                Log.d(LOG_TAG, tmpCmd);
                os.writeBytes(tmpCmd + "\n");
            }
            //flush out the process
            os.writeBytes("exit\n");
            byte[] buff = new byte[512];
            is.read(buff);
            res += new String(buff);
            os.flush();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast succeeded = Toast.makeText
                (c, c.getString(R.string.flashfota_format_succeeded),
                        Toast.LENGTH_SHORT);
        Toast failed = Toast.makeText
                (c, c.getString(R.string.flashfota_format_failed),
                        Toast.LENGTH_SHORT);

        if (res.contains("No space left on device"))
            succeeded.show();
        else
            failed.show();
    }

    public void backup() {
        Log.d(LOG_TAG, "preparing to backup fota");
        Process p;
        DataOutputStream os;
        InputStream is;
        String res = "";
        //the backup command
        String[] cmds = new String[]{
                "dd if=" + FOTA_PATH + " of=" + dir.RAW_BACKUP_PATH};
        try {
            //create a new non root shell
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            is = p.getErrorStream();
            //add the commands to the process
            for (String tmpCmd : cmds) {
                Log.d(LOG_TAG, tmpCmd);
                os.writeBytes(tmpCmd + "\n");
            }
            //flush out the process
            os.writeBytes("exit\n");
            byte[] buff = new byte[512];
            is.read(buff);
            res += new String(buff);
            os.flush();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Toast succeeded = Toast.makeText
                (c, c.getString(R.string.flashfota_backup_succeeded),
                        Toast.LENGTH_SHORT);
        Toast failed = Toast.makeText
                (c, c.getString(R.string.flashfota_backup_failed),
                        Toast.LENGTH_SHORT);

        if (res.contains("bytes transferred in"))
            succeeded.show();
        else
            failed.show();
    }

    public void restore() {
        Log.d(LOG_TAG, "preparing to restore fota");
        Process p;
        DataOutputStream os;
        InputStream is;
        String res = "";
        //the format command
        String[] cmds = new String[]{
                "dd if=" + dir.RAW_BACKUP_PATH + " of=" + FOTA_PATH};
        try {
            //create a new root shell
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            is = p.getErrorStream();
            //add the commands to the process
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            //flush out the process
            os.writeBytes("exit\n");
            os.writeBytes("exit\n");
            byte[] buff = new byte[512];
            is.read(buff);
            res += new String(buff);
            os.flush();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast succeeded = Toast.makeText
                (c, c.getString(R.string.flashfota_restore_succeeded),
                        Toast.LENGTH_SHORT);
        Toast failed = Toast.makeText
                (c, c.getString(R.string.flashfota_restore_failed),
                        Toast.LENGTH_SHORT);

        if (res.contains("bytes transferred in"))
            succeeded.show();
        else
            failed.show();
    }

    public void flashimg(String imgPath) {
        Log.d(LOG_TAG, "preparing to flash fota with" + imgPath);
        Process p;
        DataOutputStream os;
        InputStream is;
        String res = "";
        //the format command
        String[] cmds = new String[]{
                "dd if=" + imgPath + " of=" + FOTA_PATH};
        try {
            //create a new root shell
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            is = p.getErrorStream();
            //add the commands to the process
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            //flush out the process
            os.writeBytes("exit\n");
            os.writeBytes("exit\n");
            byte[] buff = new byte[512];
            is.read(buff);
            res += new String(buff);
            os.flush();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Toast succeeded = Toast.makeText
                (c, c.getString(R.string.fotaflash_flash_succeeded),
                        Toast.LENGTH_SHORT);
        Toast failed = Toast.makeText
                (c, c.getString(R.string.fotaflash_flash_failed),
                        Toast.LENGTH_SHORT);

        if (res.contains("bytes transferred in"))
            succeeded.show();
        else
            failed.show();
    }

}
