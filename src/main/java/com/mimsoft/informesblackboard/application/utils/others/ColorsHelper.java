package com.mimsoft.informesblackboard.application.utils.others;

import java.awt.*;

public class ColorsHelper {
    public static Color HexToColor(String hex) {
        hex = hex.toUpperCase().replaceAll("#", "");
        if (hex.length() != 6) return Color.WHITE;
        String r = hex.substring(0, 2);
        String g = hex.substring(2, 4);
        String b = hex.substring(4, 6);
        return new Color(Integer.parseInt(r, 16), Integer.parseInt(g, 16), Integer.parseInt(b, 16));
    }
}
