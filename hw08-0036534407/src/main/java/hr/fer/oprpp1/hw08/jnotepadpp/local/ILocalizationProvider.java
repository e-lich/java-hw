package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public interface ILocalizationProvider {

    String getString(String key);
    void addLocalizationListener(ILocalizationListener l);
    void removeLocalizationListener(ILocalizationListener l);
    String getCurrentLanguage();
}
