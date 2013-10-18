package com.anucana.phoenix.views.callhierarchy;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.anucana.phoenix.core.CallSeqModel;
import com.anucana.phoenix.core.ICallSeqModelBuilder;
import com.anucana.phoenix.core.ISubscriber;
import com.anucana.phoenix.core.ModelBuilderFactory;
import com.anucana.phoenix.views.PhoenixView;

public class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider, ISubscriber {

    private final PhoenixView phoenixView;
    private TreeNode invisibleRoot;
    // keeping track of last node reduces number of iteration
    private TreeNode lastNode;

    /**
     * @param phoenixView
     */
    public ViewContentProvider(PhoenixView phoenixView) {
        this.phoenixView = phoenixView;
    }


    public void inputChanged(Viewer v, Object oldInput, Object newInput) {
    }

    public void dispose() {
    }

    public Object[] getElements(Object parent) {
        if (parent.equals(this.phoenixView.getViewSite())) {
            if (invisibleRoot == null) {
                initialize();
            }
            return getChildren(invisibleRoot);
        }
        return getChildren(parent);
    }

    public Object getParent(Object child) {
        if (child instanceof TreeLeaf) {
            return ((TreeLeaf) child).getParent();
        }
        return null;
    }

    public Object[] getChildren(Object parent) {
        if (parent instanceof TreeNode) {
            return ((TreeNode) parent).getChildren();
        }
        return new Object[0];
    }

    public boolean hasChildren(Object parent) {
        if (parent instanceof TreeNode)
            return ((TreeNode) parent).hasChildren();
        return false;
    }

    public void initialize() {
        lastNode = null;
        // Create a dummy invisible root
        invisibleRoot = new TreeNode("", new CallSeqModel(-1, null, 1, null, null, null, 0));
        lastNode = invisibleRoot;

        // Model builder
        ICallSeqModelBuilder modelBuilder = ModelBuilderFactory.getInstance();
        modelBuilder.registerSubscribers(this);
        modelBuilder.build();
    }

    public void onCallSeqChange(CallSeqModel m) {
        if (m.getArgumentVal() != null && m.getReturnVal() != null) {
            updateTreeNode(m, lastNode);
        } else {
            addTreeNode(m, lastNode);
        }
    }


    private void addTreeNode(CallSeqModel m, TreeNode n) {
        // if last node is the root node, add a child node
        if (n.getParent() == null || n.getModel().compareTo(m) <= 0) {
            TreeNode n1 = new TreeNode(m.getClassName() + m.getLineNumber(), m);
            if (n.getParent() != null && n.getModel().compareTo(m) == 0) {
                n.getParent().addChild(n1);
            } else {
                n.addChild(n1);
            }
            lastNode = n1;
        } else {
            addTreeNode(m, n.getParent());
        }


    }

    private void updateTreeNode(CallSeqModel m, TreeNode n) {
        if (n.getModel().equals(m)) {

            n.getModel().setArgumentVal(m.getArgumentVal());
            n.getModel().setReturnVal(m.getReturnVal());

            return;
        } else {
            updateTreeNode(m, n.getParent());
        }

    }
}
