package com.example.android.tranner.mainscreen.themes;

/**
 * Created by Michał on 2017-05-13.
 */

import android.support.annotation.StyleRes;

import com.example.android.tranner.R;

import java.io.Serializable;

/**
 * Java representation of a Theme
 */
public enum AppTheme {
    PASTEL(R.style.AppThemeOne, "Pastel"),
    YELLOW(R.style.AppThemeTwo, "Purple"),
    SOFT(R.style.AppThemeThree, "Brown");

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
