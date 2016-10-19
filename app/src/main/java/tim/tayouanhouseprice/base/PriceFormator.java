package tim.tayouanhouseprice.base;

import java.text.DecimalFormat;

public class PriceFormator {

    private static DecimalFormat decimalFormat;

    private static void createDecimalFormat () {
        if(decimalFormat != null) {
            return;
        }
        decimalFormat = new DecimalFormat("NT$#,####,###.##");
    }

    public static String getThousandth(String price) {
        createDecimalFormat();
        return decimalFormat.format(price);
    }

    public static String getSimple(String price) {
        return String.valueOf(Integer.valueOf(price) / 10000);
    }
}
