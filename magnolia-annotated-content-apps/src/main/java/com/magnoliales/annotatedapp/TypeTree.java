package com.magnoliales.annotatedapp;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TypeTree implements Serializable {

    private static final long serialVersionUID = 7496221474L;

    private String nodeTypeName;
    private Class<?> rootClass;
    private TypeTree parent;
    private List<TypeTree> children = new ArrayList<TypeTree>();

    private TypeTree(String nodeTypeName, Class<?> rootClass, List<TypeTree> children) {
        this.nodeTypeName = nodeTypeName;
        this.rootClass = rootClass;
        this.children = children;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public static TypeTree read(Class<?> type) {
        if (!type.isAnnotationPresent(Node.class)) {
            return null;
        }
        String nodeType = type.getAnnotation(Node.class).jcrType();
        List<TypeTree> children = new ArrayList<TypeTree>();
        for (java.lang.reflect.Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(UI.ChildNodes.class)) {
                Class<?> subType = field.getAnnotation(UI.ChildNodes.class).childClassName();
                TypeTree child = TypeTree.read(subType);
                if (child != null) {
                    children.add(child);
                }
            }
        }
        TypeTree tree = new TypeTree(nodeType, type, children);
        for (TypeTree child : children) {
            child.setParent(tree);
        }
        return tree;
    }

    public TypeTree find(String nodeTypeName) {
        if (this.nodeTypeName.equals(nodeTypeName)) {
            return this;
        }
        for (TypeTree child : children) {
            TypeTree target = child.find(nodeTypeName);
            if (target != null) {
                return target;
            }
        }
        return null;
    }

    public boolean hasChild(String nodeTypeName) {
        for (TypeTree child : children) {
            if (child.nodeTypeName.equals(nodeTypeName)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getNodeTypeNames() {
        List<String> types = new ArrayList<String>();
        types.add(nodeTypeName);
        for (TypeTree child : children) {
            types.addAll(child.getNodeTypeNames());
        }
        return types;
    }

    public List<TypeTree> toList() {
        List<TypeTree> result = new ArrayList<TypeTree>();
        result.add(this);
        for (TypeTree child : children) {
            result.addAll(child.toList());
        }
        return result;
    }

    public Class<?> getRootType() {
        return rootClass;
    }

    public List<TypeTree> getChildren() {
        return children;
    }

    public Map<String, String> getFieldMapping() {
        Map<String, String> mapping = new HashMap<String, String>();
        java.lang.reflect.Field[] declaredFields = rootClass.getDeclaredFields();
        for (java.lang.reflect.Field field : declaredFields) {
            if (field.isAnnotationPresent(Field.class)) {
                String name = field.getName();
                String jcrName = field.getAnnotation(Field.class).jcrName();
                if (!jcrName.isEmpty()) {
                    mapping.put(name, jcrName);
                } else {
                    mapping.put(name, name);
                }
            }
        }
        for (TypeTree child : children) {
            mapping.putAll(child.getFieldMapping());
        }
        return mapping;
    }

    private void setParent(TypeTree parent) {
        this.parent = parent;
    }

    public TypeTree getParent() {
        return parent;
    }

    public boolean hasParent() {
        return this.parent != null;
    }
}

