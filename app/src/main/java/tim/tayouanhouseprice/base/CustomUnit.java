package tim.tayouanhouseprice.base;

import android.util.Log;
import android.util.MonthDisplayHelper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomUnit {
    private static DecimalFormat mDecimalFormat;
    public static DecimalFormat getDecimalInstance() {
        if (mDecimalFormat == null) {
            mDecimalFormat = new DecimalFormat("##.#");
        }
        return mDecimalFormat;
    }
    public static String getHouseAge(String buildingFinishDate) {
        try {
            int buildingFinishAD = Integer.valueOf(buildingFinishDate.substring(0, 3)) + 1911;
            String buildingFinishMonth = (buildingFinishDate.substring(3, 5));
            String buildingFinishDay = buildingFinishDate.substring(5);

            int currentAD = Calendar.getInstance().get(Calendar.YEAR);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            DateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sd.parse(buildingFinishAD + "-" + buildingFinishMonth + "-" + buildingFinishDay);
            Date d2 = sd.parse(currentAD + "-" + currentMonth + "-" + currentDay);
            long diff = d2.getTime() - d1.getTime();
            double years = diff / (1000 * 60 * 60 * 24);

            return getDecimalInstance().format(years / 365);
        } catch (Exception e) {
            Log.d("tag", e.toString());
            return null;
        }
    }

    public static String getFootage(String m2) {
        return getDecimalInstance().format(Double.valueOf(m2) / 3.31);
    }

    public static double getPricePerFootage(String m2, String price) {
        return Integer.valueOf(price) / Double.valueOf(m2);
    }
}
