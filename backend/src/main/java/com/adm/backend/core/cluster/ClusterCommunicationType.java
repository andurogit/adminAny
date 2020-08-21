package com.adm.backend.core.cluster;

public enum ClusterCommunicationType {
    UDP("UDP"),
    TCP("TCP");
    
    private String type;

    private ClusterCommunicationType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.type;
    }
}