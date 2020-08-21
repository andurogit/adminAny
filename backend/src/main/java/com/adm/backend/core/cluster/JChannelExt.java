package com.adm.backend.core.cluster;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.jgroups.ChannelListener;
import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.jgroups.conf.ConfiguratorFactory;
import org.jgroups.conf.ProtocolConfiguration;
import org.jgroups.conf.ProtocolStackConfigurator;
import org.jgroups.protocols.TP;
import org.jgroups.stack.Protocol;
import org.jgroups.util.DefaultThreadFactory;
import org.jgroups.util.ThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JChannelExt extends JChannel {
    private static final Logger log = LoggerFactory.getLogger(JChannelExt.class);

    public JChannelExt(@NotNull ProtocolStackConfigurator configurator) throws Exception {
        super(configurator);
    }

    public boolean isCoordinator() {
        return this.getView().getCoord().equals((Object)this.getAddress());
    }

    public static JChannelBuilder Builder() {
        return new JChannelBuilder();
    }
    
    public static class JChannelBuilder {
        private String clusterNodeName;
        private String multicastPort;
        private String multicastAddress;
        private String bindAddress;
        private String bindPort;
        private String initialHosts;
        private String customProperties;
        private ChannelListener channelListener;
        private Receiver receiver;
        private ClusterCommunicationType commType = ClusterCommunicationType.UDP;

        public JChannelBuilder setClusterNodeName(String clusterNodeName) {
            this.clusterNodeName = clusterNodeName;
            return this;
        }

        public JChannelBuilder setMulticastPort(@NotNull String multicastPort) {
            this.multicastPort = multicastPort;
            return this;
        }

        public JChannelBuilder setMulticastAddress(@NotNull String multicastAddress) {
            this.multicastAddress = multicastAddress;
            return this;
        }

        public JChannelBuilder setBindAddress(@NotNull String bindAddress) {
            this.bindAddress = bindAddress;
            return this;
        }

        public JChannelBuilder setBindPort(@NotNull String bindPort) {
            this.bindPort = bindPort;
            return this;
        }

        public JChannelBuilder setInitialHost(@NotNull String initialHosts) {
            this.initialHosts = initialHosts;
            return this;
        }

        public JChannelBuilder setChannelListener(@NotNull ChannelListener channelListener) {
            this.channelListener = channelListener;
            return this;
        }

        public JChannelBuilder setReceiver(@NotNull Receiver receiver) {
            this.receiver = receiver;
            return this;
        }

        public JChannelBuilder setCommType(@NotNull ClusterCommunicationType commType) {
            this.commType = commType;
            return this;
        }

        public JChannelBuilder setCustomProperties(String customProperties) {
            this.customProperties = customProperties;
            return this;
        }

        public JChannelExt build() throws Exception {
            this.checkBuildAvailable();
            String configuration = StringUtils.isNotBlank((CharSequence)this.customProperties) ? this.customProperties : "jgroups-" + StringUtils.lowerCase((String)this.commType.getName()) + ".xml";
            ProtocolStackConfigurator stackConfigurator = ConfiguratorFactory.getStackConfigurator((String)configuration);
            List<ProtocolConfiguration> protocolConfigurationList = stackConfigurator.getProtocolStack();
            for (ProtocolConfiguration config : protocolConfigurationList) {
                String protoName = StringUtils.upperCase((String) config.getProtocolName());
                if (protoName.equals(StringUtils.upperCase((String) this.commType.getName()))) {
                    switch (this.commType) {
                        case UDP: {
                            config.getProperties().put("mcast_addr", this.multicastAddress);
                            config.getProperties().put("mcast_port", this.multicastPort);
                            if (!StringUtils.isNotBlank((CharSequence)this.bindAddress)) break;
                            config.getProperties().put("bind_addr", this.bindAddress);
                            break;
                        }
                        case TCP: {
                            config.getProperties().put("bind_addr", this.bindAddress);
                            config.getProperties().put("bind_port", this.bindPort);
                        }
                    }
                }
                if (!protoName.equals("TCPPING") || !this.commType.equals((Object)ClusterCommunicationType.TCP)) continue;
                config.getProperties().put("initial_hosts", this.initialHosts);
            }
            JChannelExt channel = new JChannelExt(stackConfigurator);
            channel.addChannelListener(this.channelListener);
            channel.setReceiver(this.receiver);
            channel.setDiscardOwnMessages(true);
            this.setupThreadFactoryToDaemonThread(channel);
            log.debug("[{}] Created new JChannel with properties={}", (Object)this.clusterNodeName, (Object)stackConfigurator.getProtocolStackString());
            return channel;
        }

        private void checkBuildAvailable() throws IllegalArgumentException {
            if (this.commType == null) {
                throw new IllegalArgumentException("jGroups communication type must not be null");
            }
            if (this.channelListener == null) {
                throw new IllegalArgumentException("jGroups channel listener must not be null");
            }
            if (this.receiver == null) {
                throw new IllegalArgumentException("jGroups receiver adapter must not be null");
            }
            switch (this.commType) {
                case UDP: {
                    if (!StringUtils.isAnyBlank((CharSequence[])new CharSequence[]{this.multicastAddress, this.multicastPort})) break;
                    throw new IllegalArgumentException("Multicast address(mcast_addr) and multicast port(mcast_port) must not be null : mcast_addr : " + this.multicastAddress + ", mcast_port : " + this.multicastPort);
                }
                case TCP: {
                    if (StringUtils.isAnyBlank((CharSequence[])new CharSequence[]{this.bindAddress, this.bindPort})) {
                        throw new IllegalArgumentException("Bind address(bind_addr) and bind port(bind_port) must not be null : bind_addr : " + this.bindAddress + ", bind_port : " + this.bindPort);
                    }
                    if (!StringUtils.isBlank((CharSequence)this.initialHosts)) break;
                    throw new IllegalArgumentException("TCPPING initial_hosts must not be null : " + this.initialHosts);
                }
            }
        }

        private void setupThreadFactoryToDaemonThread(JChannel channel) {
            for (Protocol protocol : channel.getProtocolStack().getProtocols()) {
                if (!(protocol instanceof TP)) continue;
                TP tp = (TP)protocol;
                if (tp.getThreadFactory() instanceof DefaultThreadFactory) {
                    tp.setThreadFactory((ThreadFactory)new DefaultThreadFactory("", true));
                }
                if (tp.getThreadPoolThreadFactory() instanceof DefaultThreadFactory) {
                    tp.setThreadPoolThreadFactory((ThreadFactory)new DefaultThreadFactory("Incoming", true, true));
                }
                if (tp.getThreadPoolThreadFactory() instanceof DefaultThreadFactory) {
                    tp.setThreadPoolThreadFactory((ThreadFactory)new DefaultThreadFactory("OOB", true, true));
                }
                if (!(tp.getInternalThreadPoolThreadFactory() instanceof DefaultThreadFactory)) continue;
                tp.setInternalThreadPoolThreadFactory((ThreadFactory)new DefaultThreadFactory("INT", true, true));
            }
        }

        private JChannelBuilder() {}
    }
}