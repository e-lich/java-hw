package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
        SingleDocumentModel getCurrentDocument();
        SingleDocumentModel loadDocument(Path path);
        void saveDocument(SingleDocumentModel model, Path newPath) throws IOException;
        void closeDocument(SingleDocumentModel model);
        SingleDocumentModel findForPath(Path path);

        JComponent getVisualComponent();
        SingleDocumentModel createNewDocument() throws IOException;
        void addMultipleDocumentListener(MultipleDocumentListener l);
        void removeMultipleDocumentListener(MultipleDocumentListener l);
        int getNumberOfDocuments();
        SingleDocumentModel getDocument(int index);
        int getIndexOfDocument(SingleDocumentModel model);

}
