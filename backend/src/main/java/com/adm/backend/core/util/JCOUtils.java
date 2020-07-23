package com.adm.backend.core.util;

public class JCOUtils {
    private static Boolean supported;

    public static boolean isJCOLibraryAvailable() {
        if (supported == null) {
            try {
                Class.forName("com.sap.conn.jco.JCoDestination");
                supported = true;
            }
            catch (ClassNotFoundException e) {
                supported = false;
            }
        }
        return supported;
    }

    private JCOUtils() {
    }
}