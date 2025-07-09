package org.jpui.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class JPuiPreferences {

    public static final String THEME = "theme";

    public static Theme getTheme() {
        Preferences node = Preferences.userNodeForPackage(JPuiPreferences.class);
        String theme = node.get(THEME, null);
        if ("light".equals(theme)) {
            return Theme.LIGHT;
        } else if ("dark".equals(theme)) {
            return Theme.DARK;
        }
        return Theme.LIGHT;
    }

    public static void setTheme(Theme theme) {
        Preferences node = Preferences
                .userNodeForPackage(JPuiPreferences.class);

        if (theme == Theme.LIGHT) {
            node.put(THEME, "light");
        } else if (theme == Theme.DARK) {
            node.put(THEME, "dark");
        }
        try {
            node.flush(); // try to update for other instances instantly
        } catch (BackingStoreException e) {
            // Ignore
        }
    }

}
