package com.anucana.phoenix.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;

import com.anucana.phoenix.Activator;
import com.anucana.phoenix.preferences.PreferenceConstants;


public class UserPreferences {

    private final List<Pattern> includeClassPatterns = new ArrayList<Pattern>();
    private final List<Pattern> excludeClassPatterns = new ArrayList<Pattern>();

    private final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();

    public UserPreferences() {
        String includeClassPref = prefStore.getString(PreferenceConstants.INCLUDE_PACKAGES);

        if (includeClassPref != null && includeClassPref.trim().length() != 0) {
            String[] includeRegexes = includeClassPref.split(",");
            if (includeRegexes != null) {
                for (String regex : includeRegexes) {
                    includeClassPatterns.add(Pattern.compile(regex));
                }
            }
        }

        String excludeClassPref = prefStore.getString(PreferenceConstants.EXCLUDE_PACKAGES);

        if (excludeClassPref != null && excludeClassPref.trim().length() != 0) {
            String[] excludeRegexes = excludeClassPref.split(",");
            if (excludeRegexes != null) {
                for (String regex : excludeRegexes) {
                    excludeClassPatterns.add(Pattern.compile(regex));
                }
            }
        }

    }

    public boolean isUserPreferenceSatisfied(CallSeqModel model) {
        Matcher matcher = null;
        for (Pattern pattern : excludeClassPatterns) {
            matcher = pattern.matcher(model.getClassName());
            // if match is found, it means class is in exclude list
            if (matcher.find()) {
                return false;
            }
        }

        // if include pattern is empty, every is to include
        if (includeClassPatterns.isEmpty()) {
            return true;
        }

        for (Pattern pattern : includeClassPatterns) {
            matcher = pattern.matcher(model.getClassName());
            // if match is found, it means annotation is in exclude list
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }


}
