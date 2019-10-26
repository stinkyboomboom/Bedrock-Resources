package com.alexvr.bedres.utils;

import java.text.DecimalFormat;

public class GeneralHelper{

    public static String get2DEcimalPointString(float i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(i);
    }


    public static String get2DEcimalPointString(double i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(i);
    }

}
