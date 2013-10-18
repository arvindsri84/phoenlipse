package com.anucana.phoenix.views;


import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import com.anucana.phoenix.views.callhierarchy.TreeNode;
import com.anucana.phoenix.views.callhierarchy.ViewContentProvider;
import com.anucana.phoenix.views.callhierarchy.ViewLabelProvider;


/**
 * This sample class demonstrates how to plug-in a new workbench view. The view shows data obtained from the model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model available either in this or another plug-in (e.g. the workspace). The view is connected to the model
 * using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be presented in the view. Each view can present the same model objects using different
 * labels and icons, if needed. Alternatively, a single label provider can be shared between views in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class PhoenixView extends ViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "com.anucana.phoenix.views.PhoenixView";

    private TreeViewer viewer;
    private Action doubleClickAction;


    /**
     * The constructor.
     */
    public PhoenixView() {
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize it.
     */
    @Override
    public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(new ViewContentProvider(this));
        viewer.setLabelProvider(new ViewLabelProvider());
        viewer.setSorter(null);
        viewer.setInput(getViewSite());

        makeActions();
        hookContextMenu();
        hookDoubleClickAction();
    }

    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        Action refresh = new Action() {

            @Override
            public void run() {
                ((ViewContentProvider) viewer.getContentProvider()).initialize();
                viewer.refresh();
            }
        };
        refresh.setText("Refresh");
        menuMgr.add(refresh);
    }

    private void makeActions() {
        doubleClickAction = new Action() {

            @Override
            public void run() {
                ISelection selection = viewer.getSelection();
                TreeNode tempObj = (TreeNode) ((IStructuredSelection) selection).getFirstElement();

                IFile iFile = tempObj.getModel().getFile();

                IWorkbenchPage dpage = null;
                if (getViewSite().getWorkbenchWindow() != null) {
                    dpage = getViewSite().getWorkbenchWindow().getActivePage();
                }

                if (dpage != null && iFile != null) {
                    try {
                        IDE.openEditor(dpage, iFile, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        };

    }

    private void hookDoubleClickAction() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {

            public void doubleClick(DoubleClickEvent event) {
                doubleClickAction.run();
            }
        });
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }
}
