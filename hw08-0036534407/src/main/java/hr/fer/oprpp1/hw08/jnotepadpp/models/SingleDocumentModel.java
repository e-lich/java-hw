package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.awt.event.WindowListener;
import java.nio.file.Path;

public interface SingleDocumentModel {

        JTextArea getTextComponent();

        Path getFilePath();
        void setFilePath(Path path);

        boolean isModified();
        void setModified(boolean modified);

        void addSingleDocumentListener(SingleDocumentListener l);

        void removeSingleDocumentListener(SingleDocumentListener l);

}
