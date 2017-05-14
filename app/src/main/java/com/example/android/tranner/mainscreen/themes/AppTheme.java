package com.example.android.tranner.mainscreen.themes;

/**
 * Created by Michał on 2017-05-13.
 */

import android.support.annotation.StyleRes;

import com.example.android.tranner.R;

/**
 * Java representation of a Theme
 */
public enum AppTheme {
    DARK(R.style.AppThemeOne, "Pastel"),
    RED(R.style.AppThemeTwo, "Black&Yellow"),
    GREEN(R.style.AppThemeThree, "Softy");

    @StyleRes
    private final int styleResId;
    private final String themeName;

    AppTheme(@StyleRes int styleResId, String themeName) {
        this.styleResId = styleResId;
        this.themeName = themeName;
    }

    @StyleRes
    public int resId() {
        return styleResId;
    }

    public String themeName() {
        return themeName;
    }

    public static AppTheme withName(String themeName) {
        for (AppTheme appTheme : values()) {
            if (appTheme.themeName().equals(themeName)) {
                return appTheme;
            }
        }
        throw new IllegalArgumentException("There is no theme called [" + themeName + "]");
    }
}
