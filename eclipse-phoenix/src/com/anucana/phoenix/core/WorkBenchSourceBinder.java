package com.anucana.phoenix.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;


public class WorkBenchSourceBinder {

    private final IWorkspace workspace = ResourcesPlugin.getWorkspace();
    private final IProject[] projects = workspace.getRoot().getProjects();

    private static Map<String, IFile> sourceMap = new HashMap<String, IFile>();

    public void bindResource(CallSeqModel model) {

        IFile boundSource = sourceMap.get(model.getClassName());
        if (boundSource != null) {
            model.setFile(boundSource);
            return;
        }

        try {

            for (int i = 0; i < projects.length; i++) {
                for (IResource resource : projects[i].members()) {
                    if (resource instanceof IFile && ((IFile) resource).getName().equals(model.getClassName())) {
                        sourceMap.put(model.getClassName(), ((IFile) resource));
                        model.setFile(((IFile) resource));
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
