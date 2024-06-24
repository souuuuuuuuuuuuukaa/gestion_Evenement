package com.example.evenement_app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    // Méthode pour formater la date
    public static String formatDate(Date date) {
        // Format de date souhaité (par exemple, "dd/MM/yyyy")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    // Méthode pour formater l'heure
    public static String formatTime(Date time) {
        // Format de l'heure souhaité (par exemple, "HH:mm")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(time);
    }
}
