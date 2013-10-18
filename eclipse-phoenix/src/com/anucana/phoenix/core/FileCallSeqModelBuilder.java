package com.anucana.phoenix.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileCallSeqModelBuilder implements ICallSeqModelBuilder {

    public static final String FILE_NAME_SUFFIX = "callseq";
    private final CallSeqLineParser callSeqParser = new CallSeqLineParser();
    private final WorkBenchSourceBinder sourceBinder = new WorkBenchSourceBinder();
    private final UserPreferences userPreferences = new UserPreferences();

    BufferedReader reader = null;
    private final String runtimeOutDir;
    private final List<ISubscriber> subscribers = new ArrayList<ISubscriber>();

    public FileCallSeqModelBuilder(String runtimeOutDir) throws FileNotFoundException {
        this.runtimeOutDir = runtimeOutDir;
        initialiseBuilder();
    }

    private void initialiseBuilder() throws FileNotFoundException {
        File[] files = new File(runtimeOutDir).listFiles();
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No Runtime output file found at location " + runtimeOutDir);
        }

        // files names follow a convention of <number>.callseq. So, file with highest <number> should be our desired file
        long fileNameIndex = 0;
        File callSequenceFile = null;

        for (File file : files) {
            if (file.getName().endsWith(FILE_NAME_SUFFIX)) {

                long currentFileNameIndex = Long.valueOf(file.getName().split("\\.")[0]);
                if (fileNameIndex <= currentFileNameIndex) {
                    fileNameIndex = currentFileNameIndex;
                    callSequenceFile = file;
                }
            }
        }

        if (callSequenceFile == null) {
            throw new FileNotFoundException("No Runtime output file found at location " + runtimeOutDir);
        }

        reader = new BufferedReader(new FileReader(callSequenceFile));
    }

    public void registerSubscribers(ISubscriber s) {
        subscribers.add(s);
    }

    public void removeSubscribers(ISubscriber s) {
        subscribers.remove(s);
    }

    private void publishEvent(CallSeqModel m) {
        for (ISubscriber subscriber : subscribers) {
            subscriber.onCallSeqChange(m);
        }
    }

    public void build() {
        String callSeqLine;
        try {
            while (true) {
                callSeqLine = reader.readLine();
                if (callSeqLine == null) {
                    reader.close();
                    return;
                }

                CallSeqModel model = callSeqParser.parse(callSeqLine);
                // apply user preferences. If user preferences are not satisfied, ignore the call sequence line
                if (!userPreferences.isUserPreferenceSatisfied(model)) {
                    continue;
                }
                // bind the resources
                sourceBinder.bindResource(model);
                // TODO : to run validators
                publishEvent(model);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
