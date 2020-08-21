package com.adm.backend.core.cluster;

import org.jgroups.ChannelListener;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.NonNull;

public class ClusterChannelListener implements ChannelListener {
    private static final Logger log = LoggerFactory.getLogger(ClusterChannelListener.class);
    @NonNull
    private NotificationService notificationService;

    public void channelConnected(JChannel channel) {
        String coordinatorMsg = "";
        if (channel.getView().getCoord().equals((Object)channel.getAddress())) {
            coordinatorMsg = "Now this machine is coordinator.";
        }
        log.info("[{}] Channel connected. Name : {} | Address : {} | Coordinator : {}. {}", new Object[]{this.notificationService.getClusterNodeName(), channel.getName(), channel.getAddress(), channel.getView().getCoord(), coordinatorMsg});
    }

    public void channelDisconnected(JChannel channel) {
        log.info("[{}] Channel disconnected. Name : {} | Address : {}", new Object[]{this.notificationService.getClusterNodeName(), channel.getName(), channel.getAddress()});
    }

    public void channelClosed(JChannel channel) {
        log.info("[{}] Channel closed. Name : {} | Address : {}", new Object[]{this.notificationService.getClusterNodeName(), channel.getName(), channel.getAddress()});
    }

    public ClusterChannelListener(@NonNull NotificationService notificationService) {
        if (notificationService == null) {
            throw new NullPointerException("notificationService is marked @NonNull but is null");
        }
        this.notificationService = notificationService;
    }
}