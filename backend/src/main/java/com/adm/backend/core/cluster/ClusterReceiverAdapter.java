package com.adm.backend.core.cluster;

import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;

/**
 * ClusterReceiverAdapter
 */
public class ClusterReceiverAdapter extends ReceiverAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClusterReceiverAdapter.class);
    @NonNull
    private final NotificationService notificationService;
    private final List<Address> members = new ArrayList<Address>();

    public void receive(Message message) {
        this.notificationService.handleNotification((Serializable)message.getObject());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void viewAccepted(View view) {
        ArrayList<Address> joinedMembers = new ArrayList<Address>();
        ArrayList<Address> leftMembers = null;
        if (view == null) {
            return;
        }
        
        JChannel currentChannel = this.notificationService.getChannel();
        List<Address> tempMembers = view.getMembers();
        List<?> var6 = this.members;

        synchronized (var6) {
            if (this.members.isEmpty()) {
                joinedMembers.add(currentChannel.getAddress());
            } else {
                for (Address member : tempMembers) {
                    if (currentChannel.getAddress().equals((Object)member) || this.members.contains((Object)member)) continue;
                    joinedMembers.add(member);
                }
                leftMembers = new ArrayList<Address>();
                for (Address member : this.members) {
                    if (tempMembers.contains((Object)member)) continue;
                    leftMembers.add(member);
                }
            }
            this.members.clear();
            this.members.addAll(tempMembers);
        }
        if (!joinedMembers.isEmpty()) {
            for (Address joinedMember : joinedMembers) {
                this.memberJoined(joinedMember);
            }
        }
        if (leftMembers != null && !leftMembers.isEmpty()) {
            for (Address leftMember : leftMembers) {
                this.memberLeft(leftMember);
            }
        }
    }

    private void memberJoined(Address address) {
        log.info("A new member at address '{}' has joined the {}. Current coordinator is {}", new Object[]{address, this.notificationService.getClusterNodeName(), this.notificationService.getChannel().getView().getCoord()});
    }

    private void memberLeft(Address address) {
        String coordinatorMsg = "";
        if (this.notificationService.isCoordinator()) {
            coordinatorMsg = "Now this machine is coordinator.";
        }
        log.info("Member at address '{}' left the {}. Current coordinator is {}. {}", new Object[]{address, this.notificationService.getClusterNodeName(), this.notificationService.getChannel().getView().getCoord(), coordinatorMsg});
    }

    public ClusterReceiverAdapter(@NonNull NotificationService notificationService) {
        if (notificationService == null) {
            throw new NullPointerException("notificationService is marked @NonNull but is null");
        }
        this.notificationService = notificationService;
    }
}