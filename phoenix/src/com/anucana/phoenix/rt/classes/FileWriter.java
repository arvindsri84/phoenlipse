package com.anucana.phoenix.rt.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;


public class FileWriter implements ICallSeqWriter {

    private final String FILE_NAME_SUFFIX = "callseq";
    private BufferedWriter writer = null;

    public FileWriter(String filePath) throws IOException {
        File parentFolder = new File(filePath);

        if (parentFolder.isDirectory() && parentFolder.canWrite()) {
            File outFile = new File(parentFolder, "" + (new Date()).getTime() + "." + FILE_NAME_SUFFIX);
            // Create a new file
            outFile.createNewFile();
            writer = new BufferedWriter(new java.io.FileWriter(outFile));
        }

    }

    public void write(ICallSeqFormatter formatter) {
        if (writer != null) {
            try {
                writer.write(formatter.formatCallSeq().toString());
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
