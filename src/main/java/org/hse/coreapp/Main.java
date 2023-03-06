package org.hse.coreapp;

import com.itextpdf.text.DocumentException;
import org.hse.mainbuilder.MainBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException {
        MainBuilder runner = new MainBuilder("/Users/borismikhaylov/Downloads/sampleDiploma.xlsx");
        runner.createPdfs();
    }
}
