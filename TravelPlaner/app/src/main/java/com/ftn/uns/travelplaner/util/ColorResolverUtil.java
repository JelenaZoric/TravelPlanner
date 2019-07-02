package com.ftn.uns.travelplaner.util;

import android.graphics.Color;

public class ColorResolverUtil {

    public static int resolveColour(String colour) {
        int rgbColour = -1;
        
        switch (colour) {
            case "Red":
                rgbColour = Color.RED;
                break;
            case "Blue":
                rgbColour = Color.BLUE;
                break;
            case "Green":
                rgbColour = Color.GREEN;
                break;
            case "Yellow":
                rgbColour = Color.YELLOW;
                break;
            default:
                rgbColour = Color.BLACK;
        }
        
        return rgbColour;
    }
}
