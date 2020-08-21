package com.adm.backend.core.cluster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.jgroups.Address;
import org.jgroups.ChannelListener;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterNodeNotificationService implements NotificationService {
    private static final Logger log = LoggerFactory.getLogger(ClusterNodeNotificationService.class);
    private static final String CLUSTER_NODE_NAME = "ADMIN_ClusterNode";
    private static final String DEFAULT_MULTICAST_ADDR = "231.12.21.139";
    private static final String DEFAULT_MULTICAST_PORT = "45567";
    private final Receiver receiverAdapter = new ClusterReceiverAdapter(this);
    private final ChannelListener channelListener = new ClusterChannelListener(this);
    private JChannelExt channel;
    private String multicastAddress;
    private String multicastPort;
    private String bindAddress;
    private String bindPort;
    private String initialHosts;
    private List<ClusterNodeNotificationListener> notificationListeners = new ArrayList<ClusterNodeNotificationListener>();
    private ClusterCommunicationType clusterCommType;

    @PostConstruct
    @Override
    public void start() throws Exception {
        if (this.isActive()) {
            return;
        }
        log.info("Starting a new {} broadcasting listener", (Object)CLUSTER_NODE_NAME);
        this.createJChannel();
        this.channel.connect(CLUSTER_NODE_NAME);
        log.info("[{}] JavaGroups clustering support started successfully", (Object)CLUSTER_NODE_NAME);
    }

    private void createJChannel() throws Exception {
        this.channel = JChannelExt.Builder().setClusterNodeName(CLUSTER_NODE_NAME).setBindAddress(this.getBindAddress()).setBindPort(this.getBindPort()).setMulticastAddress(this.getMulticastAddress()).setMulticastPort(this.getMulticastPort()).setCommType(this.getClusterCommType()).setInitialHost(this.getInitialHosts()).setChannelListener(this.getChannelListener()).setReceiver(this.getReceiverAdapter()).build();
    }

    @Override
    public String getMulticastAddress() {
        return (String)StringUtils.defaultIfBlank((CharSequence)this.multicastAddress, (CharSequence)DEFAULT_MULTICAST_ADDR);
    }

    @Override
    public String getMulticastPort() {
        return (String)StringUtils.defaultIfBlank((CharSequence)this.multicastPort, (CharSequence)DEFAULT_MULTICAST_PORT);
    }

    @Override
    public ClusterCommunicationType getClusterCommType() {
        return this.clusterCommType == null ? DEFAULT_TYPE : this.clusterCommType;
    }

    @Override
    public void handleNotification(Serializable notification) {
        if (!(notification instanceof ClusterNodeNotification)) {
            log.warn("[{}] An unknown cluster notification message received (class={}). Notification ignored.", (Object)this.getClusterNodeName(), (Object)notification.getClass().getName());
            return;
        }
        List<ClusterNodeNotificationListener> list = this.getNotificationListeners();
        for (ClusterNodeNotificationListener listener : list) {
            ((ClusterNodeNotificationService) listener).handleNotification(notification);
        }
    }

    @Override
    public boolean isActive() {
        return this.channel != null;
    }

    @Override
    public List<Address> getMembership() {
        return new ArrayList<Address>(this.getChannel().getView().getMembers());
    }

    @Override
    public void sendNotification(Serializable notification) {
        try {
            Message notificationMessage = new Message(null, Util.objectToByteBuffer((Object)notification));
            this.channel.send(notificationMessage);
        }
        catch (Exception e) {
            log.error("Error sending notification message data", (Throwable)e);
        }
    }

    @Override
    public synchronized boolean isCoordinator() {
        return this.channel.isCoordinator();
    }

    @Override
    public int getMemberCount() {
        return this.channel.getView().getMembers().size();
    }

    @Override
    public void addClusterNodeNotificationListener(ClusterNodeNotificationListener listener) {
        this.notificationListeners.add(listener);
    }

    @Override
    public void removeClusterNodeNotificationListener(ClusterNodeNotificationListener listener) {
        this.notificationListeners.remove(listener);
    }

    @Override
    public String getClusterNodeName() {
        return CLUSTER_NODE_NAME;
    }

    @PreDestroy
    @Override
    public void shutdown() {
        log.info("[{}] : JavaGroups shutting down...", (Object)CLUSTER_NODE_NAME);
        if (this.channel != null) {
            this.channel.close();
            this.channel = null;
        } else {
            log.warn("[{}] : JChannel wasn't initialized or finalize was invoked before!", (Object)CLUSTER_NODE_NAME);
        }
        log.info("[{}] : JavaGroups shutdown complete.", (Object)CLUSTER_NODE_NAME);
    }

    public void setChannel(JChannelExt channel) {
        this.channel = channel;
    }

    @Override
    public void setMulticastAddress(String multicastAddress) {
        this.multicastAddress = multicastAddress;
    }

    @Override
    public void setMulticastPort(String multicastPort) {
        this.multicastPort = multicastPort;
    }

    @Override
    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    @Override
    public void setBindPort(String bindPort) {
        this.bindPort = bindPort;
    }

    @Override
    public void setInitialHosts(String initialHosts) {
        this.initialHosts = initialHosts;
    }

    public void setNotificationListeners(List<ClusterNodeNotificationListener> notificationListeners) {
        this.notificationListeners = notificationListeners;
    }

    @Override
    public void setClusterCommType(ClusterCommunicationType clusterCommType) {
        this.clusterCommType = clusterCommType;
    }

    public Receiver getReceiverAdapter() {
        return this.receiverAdapter;
    }

    public ChannelListener getChannelListener() {
        return this.channelListener;
    }

    @Override
    public JChannelExt getChannel() {
        return this.channel;
    }

    @Override
    public String getBindAddress() {
        return this.bindAddress;
    }

    @Override
    public String getBindPort() {
        return this.bindPort;
    }

    @Override
    public String getInitialHosts() {
        return this.initialHosts;
    }

    public List<ClusterNodeNotificationListener> getNotificationListeners() {
        return this.notificationListeners;
    }
}