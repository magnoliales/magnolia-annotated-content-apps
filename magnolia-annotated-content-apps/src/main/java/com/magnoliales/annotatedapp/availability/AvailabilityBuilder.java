package com.magnoliales.annotatedapp.availability;

import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AvailabilityBuilder {

    private boolean root;
    private boolean nodes;
    private List<String> nodeTypes = new ArrayList<>();

    public AvailabilityBuilder setRoot(boolean root) {
        this.root = root;
        return this;
    }

    public AvailabilityBuilder setNodes(boolean nodes) {
        this.nodes = nodes;
        return this;
    }

    public AvailabilityBuilder setNodeTypes(String... nodeTypes) {
        this.nodeTypes.addAll(Arrays.asList(nodeTypes));
        return this;
    }

    public AvailabilityBuilder addNodeType(String nodeType) {
        nodeTypes.add(nodeType);
        return this;
    }

    public AvailabilityDefinition definition() {
        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(root);
        availabilityDefinition.setNodes(nodes);
        availabilityDefinition.setNodeTypes(nodeTypes);
        return availabilityDefinition;
    }
}
