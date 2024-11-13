package com.t3h.e_commerce.utils;

import java.util.Date;

public class SlugUtils {
    public static String changeProductNameToSlug(String productName){
        return productName.replaceAll("\\s", "-").toLowerCase() + "-" + new Date().getTime();
    }
}
