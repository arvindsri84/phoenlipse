package com.anucana.phoenix.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.anucana.phoenix.Activator;

/**
 * This class represents a preference page that is contributed to the Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we can use the
 * field support built into JFace that allows us to create a page that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main plug-in class. That way, preferences can be
 * accessed directly via the preference store.
 */

public class PhoenixPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    public PhoenixPreferencePage() {
        super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("Setup your preferences for Phoenix");
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common GUI blocks needed to manipulate various types of preferences. Each field editor
     * knows how to save and restore itself.
     */
    @Override
    public void createFieldEditors() {
        addField(new DirectoryFieldEditor(PreferenceConstants.PHOENIX_RT_OUT_DIR, "&Phoenix Runtime Output Directory :", getFieldEditorParent()));
        addField(new RadioGroupFieldEditor(
                PreferenceConstants.REVERSE_ENGINEERING_MODE,
                "Choose the reverse engineering mode",
                1,
                new String[][] { { "&Call Hierarchy", "CallHierarchy" }, { "&Sequence Diagram", "SequenceDiagram" },
                        { "&Communication Diagram", "CommunicationDiagram" } },
                getFieldEditorParent()));
        addField(new StringFieldEditor(PreferenceConstants.EXCLUDE_PACKAGES, "Exclude Packages ( Regex Expressions, comma seperated ):", getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.INCLUDE_PACKAGES,
                "Include Only Packages ( Regex Expressions, comma seperated ):",
                getFieldEditorParent()));
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

}
