package in.championswimmer.twrpxperia.flashutils;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import in.championswimmer.twrpxperia.R;

/**
 * Created by championswimmer on 26/1/14.
 */
public class GetImg {
    private String LOG_TAG = "XRM";

    private static String TWRP_BASE_URL = "http://files.championswimmer.in/championswimmer/recovery/twrp/";
    private static String CWM_BASE_URL = "http://files.championswimmer.in/championswimmer/recovery/cwm/";
    private static String PHILZ_BASE_URL = "http://files.championswimmer.in/championswimmer/recovery/philz/";
    private static String CM_BASE_URL = "http://files.championswimmer.in/championswimmer/recovery/cm/";


    private String[] codenames, props;
    private String deviceName;
    private SaveDir dir;

    private Boolean supported = false;

    private Context c;

    public GetImg(Context context) {
        c = context;
        codenames = context.getResources().getStringArray(R.array.supported_device_codename);
        props = context.getResources().getStringArray(R.array.supported_device_prop);
        dir = new SaveDir();

        for (int i = 0; i < props.length; i++) {
            if (props[i].equalsIgnoreCase(Build.DEVICE)) {
                deviceName = codenames[i];
                supported = true;
            }
        }
        Log.d(LOG_TAG, "prepare a download method for " + deviceName);
    }

    public Boolean isSupported() {
        return supported;
    }

    public void downloadTWRP() {
        String url = TWRP_BASE_URL + deviceName + "/recovery.img";
        Log.d(LOG_TAG, "downloading " + url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("TWRP recovery for " + deviceName);
        request.setTitle("twrp.img");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        final File twrp = new File(dir.twrpPath());
        Log.d(LOG_TAG, "Saving at " + dir.twrpPath());
        if (twrp.exists() || twrp.isFile()){
            AlertDialog.Builder tex = new AlertDialog.Builder(c);
            tex.setTitle("FILE EXISTS");
            tex.setMessage(c.getString(R.string.alert_exists_twrp_image));
            tex.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    twrp.delete();
                }
            });
            tex.show();
        }
        request.setDestinationUri(Uri.parse("file://" + dir.twrpPath()));
        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast done = Toast.makeText(c,
                "TWRP Recovery image downloading to \n"
                + twrp.getPath(),
                Toast.LENGTH_LONG);
        done.show();
    }

    public void downloadCWM() {
        String url = CWM_BASE_URL + deviceName + "/recovery.img";
        Log.d(LOG_TAG, "downloading " + url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("CWM recovery for " + deviceName);
        request.setTitle("cwm.img");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        final File cwm = new File(dir.twrpPath());
        Log.d(LOG_TAG, "Saving at " + dir.cwmPath());
        if (cwm.exists() || cwm.isFile()) {
            AlertDialog.Builder cex = new AlertDialog.Builder(c);
            cex.setTitle("FILE EXISTS");
            cex.setMessage(c.getString(R.string.alert_exists_cwm_image));
            cex.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cwm.delete();
                }
            });
            cex.show();
        }
        request.setDestinationUri(Uri.parse("file://" + dir.cwmPath()));
        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast done = Toast.makeText(c,
                "CWM Recovery image downloading to \n"
                        + cwm.getPath(),
                Toast.LENGTH_LONG);
        done.show();
    }


    public void downloadPhilz() {
        String url = PHILZ_BASE_URL + deviceName + "/recovery.img";
        Log.d(LOG_TAG, "downloading " + url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Philz-Touch recovery for " + deviceName);
        request.setTitle("philz.img");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        final File philz = new File(dir.philzPath());
        Log.d(LOG_TAG, "Saving at " + dir.philzPath());
        if (philz.exists() || philz.isFile()) {
            AlertDialog.Builder cex = new AlertDialog.Builder(c);
            cex.setTitle("FILE EXISTS");
            cex.setMessage(c.getString(R.string.alert_exists_philz_image));
            cex.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    philz.delete();
                }
            });
            cex.show();
        }
        request.setDestinationUri(Uri.parse("file://" + dir.philzPath()));
        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast done = Toast.makeText(c,
                "Philz-Touch Recovery image downloading to \n"
                        + philz.getPath(),
                Toast.LENGTH_LONG);
        done.show();
    }

    public void downloadCm() {
        String url = CM_BASE_URL + deviceName + "/recovery.img";
        Log.d(LOG_TAG, "downloading " + url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("CM Simple recovery for " + deviceName);
        request.setTitle("cm.img");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        final File cm = new File(dir.cmPath());
        Log.d(LOG_TAG, "Saving at " + dir.cmPath());
        if (cm.exists() || cm.isFile()) {
            AlertDialog.Builder cex = new AlertDialog.Builder(c);
            cex.setTitle("FILE EXISTS");
            cex.setMessage(c.getString(R.string.alert_exists_cm_image));
            cex.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cm.delete();
                }
            });
            cex.show();
        }
        request.setDestinationUri(Uri.parse("file://" + dir.cmPath()));
        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast done = Toast.makeText(c,
                "CM Simple Recovery image downloading to \n"
                        + cm.getPath(),
                Toast.LENGTH_LONG);
        done.show();
    }
}
