package com.anucana.phoenix.core;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.anucana.phoenix.Activator;


public class ImageCache {

    private static Map<Integer, Image> modifierImageCache = new HashMap<Integer, Image>();

    static {
        modifierImageCache.put(Modifier.PRIVATE, Activator.getImageDescriptor("icons\\private.gif").createImage());
        modifierImageCache.put(Modifier.PROTECTED, Activator.getImageDescriptor("icons\\protected.gif").createImage());
        modifierImageCache.put(Modifier.PUBLIC, Activator.getImageDescriptor("icons\\public.gif").createImage());

        // TODO : to associate these numbers with constants provided in java.lang.reflect.Modifier
        modifierImageCache.put(0, Activator.getImageDescriptor("icons\\default.gif").createImage());

        modifierImageCache.put(9, Activator.getImageDescriptor("icons\\public-static.gif").createImage());
        modifierImageCache.put(12, Activator.getImageDescriptor("icons\\protected-static.gif").createImage());
        modifierImageCache.put(8, Activator.getImageDescriptor("icons\\default-static.gif").createImage());
        modifierImageCache.put(10, Activator.getImageDescriptor("icons\\private-static.gif").createImage());

        modifierImageCache.put(25, Activator.getImageDescriptor("icons\\public-static-final.gif").createImage());
        modifierImageCache.put(28, Activator.getImageDescriptor("icons\\protected-static-final.gif").createImage());
        modifierImageCache.put(24, Activator.getImageDescriptor("icons\\default-static-final.gif").createImage());
        modifierImageCache.put(26, Activator.getImageDescriptor("icons\\private-static-final.gif").createImage());

    }

    public static Image getMethodModifierImage(Integer modifier) {
        return modifierImageCache.get(modifier);
    }

}
