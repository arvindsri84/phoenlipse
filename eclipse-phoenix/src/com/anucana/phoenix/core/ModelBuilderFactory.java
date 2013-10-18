package com.anucana.phoenix.core;

import java.io.FileNotFoundException;

import com.anucana.phoenix.Activator;
import com.anucana.phoenix.preferences.PreferenceConstants;


public class ModelBuilderFactory {

    public static ICallSeqModelBuilder getInstance() {

        String runtimeOutDir = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.PHOENIX_RT_OUT_DIR);
        if (runtimeOutDir != null && runtimeOutDir.trim().length() != 0) {
            try {
                return new FileCallSeqModelBuilder(runtimeOutDir);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new DummyCallSeqModelBuilder();
            }
        } else {
            // Check for socket ip and port
            return new SocketCallSeqModelBuilder();
        }
    }
}
