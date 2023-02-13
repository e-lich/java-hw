package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.ArrayList;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
    private ArrayList<ILocalizationListener> listeners = new ArrayList<>();
    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    private void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }
}
