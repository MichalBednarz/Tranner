package com.example.android.tranner.mainscreen.themes;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.tranner.R;

/**
 * Created by Michał on 2017-05-14.
 */

public class ThemeOptionViewHolder extends RecyclerView.ViewHolder {

    private final AttributeExtractor extractor;
    private final Context context;
    private final Button themeNameView;

    public static ThemeOptionViewHolder createFor(ViewGroup parent, AttributeExtractor extractor) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_theme_button, parent, false);
        return new ThemeOptionViewHolder(view, extractor);
    }

    public ThemeOptionViewHolder(View itemView, AttributeExtractor extractor) {
        super(itemView);
        this.context = itemView.getContext();
        this.extractor = extractor;
        this.themeNameView = (Button) itemView.findViewById(R.id.theme_name);
    }

    public void bind(final AppTheme appTheme, final OnThemeSelectedListener onThemeSelectedListener) {
        themeNameView.setText(appTheme.themeName());
        ContextThemeWrapper themedContext = new ContextThemeWrapper(this.context, appTheme.resId());

        int primaryColor = extractor.extractPrimaryColorFrom(themedContext);
        themeNameView.getBackground().setColorFilter(primaryColor, PorterDuff.Mode.MULTIPLY);

        themeNameView.setOnClickListener(v -> onThemeSelectedListener.onThemeSelected(appTheme));

    }
}
