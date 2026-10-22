package tw.nekomimi.nekogram;

import android.content.SharedPreferences;

import org.telegram.messenger.ApplicationLoader;

/**
 * LinQigram slim config: only the fields used by the ported feature set.
 * Kept under the original Nekogram package/class name so transplanted
 * subsystem code compiles unchanged.
 */
public class NekoConfig {

    public static final int BOOST_NONE = 0;
    public static final int BOOST_AVERAGE = 1;
    public static final int BOOST_EXTREME = 2;

    public static final int ID_TYPE_HIDDEN = 0;
    public static final int ID_TYPE_API = 1;
    public static final int ID_TYPE_BOTAPI = 2;

    private static final String PREFS = "linqi_config";

    public static int downloadSpeedBoost = BOOST_NONE;
    public static int idType = ID_TYPE_API;
    public static int nameOrder = 1;
    public static boolean autoTranslate = true;
    public static boolean mapDriftingFix = false;

    private static SharedPreferences prefs() {
        return ApplicationLoader.applicationContext.getSharedPreferences(PREFS, 0);
    }

    public static void load() {
        try {
            SharedPreferences p = prefs();
            downloadSpeedBoost = p.getInt("downloadSpeedBoost", BOOST_NONE);
            idType = p.getInt("idType", ID_TYPE_API);
            nameOrder = p.getInt("nameOrder", 1);
            autoTranslate = p.getBoolean("autoTranslate", true);
            mapDriftingFix = p.getBoolean("mapDriftingFix", isChinaSim());
        } catch (Exception ignored) {
        }
    }

    private static boolean isChinaSim() {
        try {
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) ApplicationLoader.applicationContext.getSystemService(android.content.Context.TELEPHONY_SERVICE);
            String op = tm != null ? tm.getSimOperator() : null;
            return op != null && op.startsWith("460");
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void setMapDriftingFix(boolean value) {
        mapDriftingFix = value;
        try {
            prefs().edit().putBoolean("mapDriftingFix", value).apply();
        } catch (Exception ignored) {
        }
    }

    public static void setDownloadSpeedBoost(int value) {
        downloadSpeedBoost = value;
        try {
            prefs().edit().putInt("downloadSpeedBoost", value).apply();
        } catch (Exception ignored) {
        }
    }

    public static void setIdType(int value) {
        idType = value;
        try {
            prefs().edit().putInt("idType", value).apply();
        } catch (Exception ignored) {
        }
    }

    public static void setNameOrder(int value) {
        nameOrder = value;
        try {
            prefs().edit().putInt("nameOrder", value).apply();
        } catch (Exception ignored) {
        }
    }

    public static void setAutoTranslate(boolean value) {
        autoTranslate = value;
        try {
            prefs().edit().putBoolean("autoTranslate", value).apply();
        } catch (Exception ignored) {
        }
    }
}
