package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
    String language;
    ResourceBundle bundle;
    private static LocalizationProvider instance = new LocalizationProvider();

    private LocalizationProvider() {
        language = "en";
        setLanguage(language);
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

    public static LocalizationProvider getInstance() {
        return instance;
    }

    public void setLanguage(String language) {
        bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.translation", Locale.forLanguageTag(language));
        this.language = language;
    }
}
