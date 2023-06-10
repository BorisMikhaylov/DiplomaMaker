package org.hse.mainbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface MainInterface {
    boolean loadSheet(File sheet) throws IOException;
    boolean loadSubstrate(File substrate);
    boolean editTemplate();
    boolean makeDiplomas();
    List<File> uploadDiplomas();
}
