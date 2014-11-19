package com.magnoliales.annotatedapp.constraint;

import com.magnoliales.annotatedapp.TypeTree;
import com.vaadin.data.Item;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemAdapter;
import info.magnolia.ui.workbench.tree.MoveLocation;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class AnnotatedDropConstraint implements DropConstraint {

    protected TypeTree typeTree;

    public AnnotatedDropConstraint(Class<?> nodeClass) {

        this.typeTree = TypeTree.read(nodeClass);
    }

    private Node getNodeFromItem(Item item) {
        JcrItemAdapter jcrItemAdapter = (JcrItemAdapter) item;
        if(jcrItemAdapter != null && jcrItemAdapter.isNode()){
            return (Node) jcrItemAdapter.getJcrItem();
        }
        return null;
    }

    private String getNodeType(Item item) {
        Node node = getNodeFromItem(item);
        if (node == null) {
            return null;
        }
        try {
            return node.getPrimaryNodeType().getName();
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public boolean isAllowedAt(Item sourceItem, Item targetItem, MoveLocation location) {
        // @todo
        return true;
    }

    @Override
    public boolean allowedAsChild(Item sourceItem, Item targetItem) {
        String sourceType = getNodeType(sourceItem);
        String targetType = getNodeType(targetItem);
        return typeTree.find(targetType).hasChild(sourceType);
    }

    @Override
    public boolean allowedBefore(Item sourceItem, Item targetItem) {
        String sourceNodeType = getNodeType(sourceItem);
        String targetNodeType = getNodeType(targetItem);
        if (sourceNodeType != null && targetNodeType != null) {
            return sourceNodeType.equals(targetNodeType);
        }
        return false;
    }

    @Override
    public boolean allowedAfter(Item sourceItem, Item targetItem) {
        return allowedBefore(sourceItem, targetItem);
    }

    @Override
    public boolean allowedToMove(Item sourceItem) {
        return true;
    }
}
