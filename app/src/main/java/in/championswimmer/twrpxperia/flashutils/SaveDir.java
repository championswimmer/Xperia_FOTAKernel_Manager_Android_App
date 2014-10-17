package in.championswimmer.twrpxperia.flashutils;

import android.os.Environment;

import java.io.File;

/**
 * Created by championswimmer on 23/1/14.
 */
public class SaveDir {

    private String LOG_TAG = "XRM";

    public static String STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/TWRPXperia/";
    public static String RAW_CWM_PATH = "/sdcard/TWRPXperia/cwm.img";
    public static String RAW_TWRP_PATH = "/sdcard/TWRPXperia/twrp.img";
    public static String RAW_PHILZ_PATH = "/sdcard/TWRPXperia/philz.img";
    public static String RAW_BACKUP_PATH = "/sdcard/TWRPXperia/fotabackup.img";

    public SaveDir() {
        File valid = new File(STORAGE_DIRECTORY + "valid.txt");
        if (!valid.exists()) {
            valid.mkdirs();
        }
    }

    public Boolean existsCwmImage() {
        File cwm = new File(STORAGE_DIRECTORY + "cwm.img");
        return cwm.exists();
    }

    public Boolean existsTwrpImage() {
        File twrp = new File(STORAGE_DIRECTORY + "twrp.img");
        return twrp.exists();
    }

    public Boolean existsPhilzImage() {
        File twrp = new File(STORAGE_DIRECTORY + "philz.img");
        return twrp.exists();
    }

    public Boolean existsFotaBackup() {
        File backup = new File(STORAGE_DIRECTORY + "fotabackup.img");
        return backup.exists();
    }

    public String cwmPath() {
        return STORAGE_DIRECTORY + "cwm.img";
    }

    public String twrpPath() {
        return STORAGE_DIRECTORY + "twrp.img";
    }

    public String philzPath() {
        return STORAGE_DIRECTORY + "philz.img";
    }


    public String backupBath() {
        return STORAGE_DIRECTORY + "fotabackup.img";
    }

    public Boolean validCwm(){
        File f = new File(cwmPath());
        return (((f.length() / 1024) / 1024) > 5);
    }
    public Boolean validTwrp() {
        File f = new File(twrpPath());
        return (((f.length() / 1024) / 1024) > 5);
    }
    public Boolean validPhilz() {
        File f = new File(philzPath());
        return (((f.length() / 1024) / 1024) > 5);
    }
}
