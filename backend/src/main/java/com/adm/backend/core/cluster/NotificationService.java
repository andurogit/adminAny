package com.adm.backend.core.cluster;

import java.io.Serializable;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;

public interface NotificationService {
    public static final ClusterCommunicationType DEFAULT_TYPE = ClusterCommunicationType.UDP;

    public void start() throws Exception;

    public void shutdown();

    public boolean isCoordinator();

    public int getMemberCount();

    public void addClusterNodeNotificationListener(ClusterNodeNotificationListener var1);

    public void removeClusterNodeNotificationListener(ClusterNodeNotificationListener var1);

    public boolean isActive();

    public List<Address> getMembership();

    public String getClusterNodeName();

    public void sendNotification(Serializable var1);

    public void setMulticastAddress(String var1);

    public void setMulticastPort(String var1);

    public void setBindAddress(String var1);

    public void setBindPort(String var1);

    public void setInitialHosts(String var1);

    public void setClusterCommType(ClusterCommunicationType var1);

    public String getMulticastAddress();

    public String getMulticastPort();

    public String getBindAddress();

    public String getBindPort();

    public String getInitialHosts();

    public ClusterCommunicationType getClusterCommType();

    public void handleNotification(Serializable var1);

    public JChannel getChannel();
}