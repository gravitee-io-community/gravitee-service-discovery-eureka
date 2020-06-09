/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.discovery.eureka.spring;

import com.netflix.appinfo.EurekaAccept;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EurekaClientConfigBean implements EurekaClientConfig {

    private final EurekaTransportConfig transportConfig;

    private final ConfigurableEnvironment environment;

    private int registryFetchIntervalSeconds;
    private int instanceInfoReplicationIntervalSeconds;
    private int initialInstanceInfoReplicationIntervalSeconds = 40;
    private int eurekaServiceUrlPollIntervalSeconds;
    private String proxyPort;
    private String proxyHost;
    private String proxyUserName;
    private String proxyPassword;
    private int eurekaServerReadTimeoutSeconds;
    private int eurekaServerConnectTimeoutSeconds;
    private String backupRegistryImpl;
    private int eurekaServerTotalConnections;
    private int eurekaServerTotalConnectionsPerHost;
    private String eurekaServerURLContext;
    private String eurekaServerPort;
    private String eurekaServerDNSName;
    private String region;
    private int eurekaConnectionIdleTimeoutSeconds;
    private String registryRefreshSingleVipAddress;
    private int heartbeatExecutorThreadPoolSize;
    private int heartbeatExecutorExponentialBackOffBound;
    private int cacheRefreshExecutorThreadPoolSize;
    private int cacheRefreshExecutorExponentialBackOffBound;
    private boolean gZipContent;
    private boolean useDnsForFetchingServiceUrls;
    private boolean registerWithEureka = false;
    private boolean preferSameZoneEureka;
    private boolean logDeltaDiff;
    private boolean disableDelta;
    private String fetchRemoteRegionsRegistry;
    private boolean filterOnlyUpInstances;
    private boolean fetchRegistry;
    private String dollarReplacement;
    private String escapeCharReplacement;
    private boolean allowRedirects;
    private boolean onDemandUpdateStatusChange;
    private String encoderName;
    private String decoderName;
    private String clientDataAccept;
    private boolean shouldUnregisterOnShutdown;
    private boolean shouldEnforceRegistrationAtInit;

    public EurekaClientConfigBean(ConfigurableEnvironment environment, EurekaTransportConfig eurekaTransportConfigBean) {
        this.environment = environment;
        this.transportConfig = eurekaTransportConfigBean;
        this.registryFetchIntervalSeconds = Integer.parseInt(environment.getProperty("service-discovery.eureka.client.refresh.interval", "30"));
        this.instanceInfoReplicationIntervalSeconds = Integer.parseInt(environment.getProperty("service-discovery.eureka.appinfo.replicate.interval", "30"));
        this.initialInstanceInfoReplicationIntervalSeconds = Integer.parseInt(environment.getProperty("service-discovery.eureka.appinfo.initial.replicate.time", "40"));
        this.eurekaServiceUrlPollIntervalSeconds = Integer.parseInt(environment.getProperty("service-discovery.eureka.serviceUrlPollIntervalMs", "300"));
        this.proxyPort = environment.getProperty("service-discovery.eureka.eurekaServer.proxyPort");
        this.proxyHost = environment.getProperty("service-discovery.eureka.eurekaServer.proxyHost");
        this.proxyUserName = environment.getProperty("service-discovery.eureka.eurekaServer.proxyUserName");
        this.proxyPassword = environment.getProperty("service-discovery.eureka.eurekaServer.proxyPassword");
        this.eurekaServerReadTimeoutSeconds = Integer.parseInt(environment.getProperty("service-discovery.eureka.eurekaServer.readTimeout", "8"));
        this.eurekaServerConnectTimeoutSeconds = Integer.parseInt(environment.getProperty("service-discovery.eureka.eurekaServer.connectTimeout", "5"));
        this.backupRegistryImpl = environment.getProperty("service-discovery.eureka.eurekaServer.backupregistry");
        this.eurekaServerTotalConnections = Integer.parseInt(environment.getProperty("service-discovery.eureka.eurekaServer.maxTotalConnections", "200"));
        this.eurekaServerTotalConnectionsPerHost = Integer.parseInt(environment.getProperty("service-discovery.eureka.eurekaServer.maxConnectionsPerHost", "50"));
        this.eurekaServerURLContext = environment.getProperty("service-discovery.eureka.eurekaServer.context");
        this.eurekaServerPort =  environment.getProperty("service-discovery.eureka.eurekaServer.port");
        this.eurekaServerDNSName = environment.getProperty("service-discovery.eureka.eurekaServer.domainName");
        this.region = environment.getProperty("service-discovery.eureka.region", "us-east-1");
        this.eurekaConnectionIdleTimeoutSeconds = Integer.parseInt(environment.getProperty("service-discovery.eureka.eurekaserver.connectionIdleTimeoutInSeconds", "30"));
        this.registryRefreshSingleVipAddress = environment.getProperty("service-discovery.eureka.registryRefreshSingleVipAddress");
        this.heartbeatExecutorThreadPoolSize = Integer.parseInt(environment.getProperty("service-discovery.eureka.client.heartbeat.threadPoolSize", "5"));
        this.heartbeatExecutorExponentialBackOffBound = Integer.parseInt(environment.getProperty("service-discovery.eureka.client.heartbeat.exponentialBackOffBound", "10"));
        this.cacheRefreshExecutorThreadPoolSize = Integer.parseInt(environment.getProperty("service-discovery.eureka.client.cacheRefresh.threadPoolSize", "5"));
        this.cacheRefreshExecutorExponentialBackOffBound = Integer.parseInt(environment.getProperty("service-discovery.eureka.client.cacheRefresh.exponentialBackOffBound", "10"));
        this.gZipContent = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.eurekaServer.gzipContent", "true"));
        this.useDnsForFetchingServiceUrls = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.shouldUseDns", "false"));
        this.preferSameZoneEureka = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.preferSameZone", "true"));
        this.logDeltaDiff = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.printDeltaFullDiff", "false"));
        this.disableDelta = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.disableDelta", "false"));
        this.fetchRemoteRegionsRegistry = environment.getProperty("service-discovery.eureka.fetchRemoteRegionsRegistry");
        this.filterOnlyUpInstances = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.shouldFilterOnlyUpInstances", "true"));
        this.fetchRegistry = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.shouldFetchRegistry", "true"));
        this.dollarReplacement = environment.getProperty("service-discovery.eureka.dollarReplacement", "_-");
        this.escapeCharReplacement = environment.getProperty("service-discovery.eureka.escapeCharReplacement", "__");
        this.allowRedirects = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.allowRedirects", "false"));
        this.onDemandUpdateStatusChange = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.shouldOnDemandUpdateStatusChange", "true"));
        this.encoderName = environment.getProperty("service-discovery.eureka.encoderName");
        this.decoderName = environment.getProperty("service-discovery.eureka.decoderName");
        this.clientDataAccept = environment.getProperty("service-discovery.eureka.clientDataAccept");
        this.shouldUnregisterOnShutdown = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.shouldUnregisterOnShutdown", "true"));
        this.shouldEnforceRegistrationAtInit = Boolean.parseBoolean(environment.getProperty("service-discovery.eureka.shouldEnforceRegistrationAtInit", "false"));
    }

    public int getRegistryFetchIntervalSeconds() {
        return registryFetchIntervalSeconds;
    }

    public int getInstanceInfoReplicationIntervalSeconds() {
        return instanceInfoReplicationIntervalSeconds;
    }

    public int getInitialInstanceInfoReplicationIntervalSeconds() {
        return initialInstanceInfoReplicationIntervalSeconds;
    }

    public int getEurekaServiceUrlPollIntervalSeconds() {
        return eurekaServiceUrlPollIntervalSeconds;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public String getProxyUserName() {
        return proxyUserName;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public boolean shouldGZipContent() {
        return gZipContent;
    }

    public int getEurekaServerReadTimeoutSeconds() {
        return eurekaServerReadTimeoutSeconds;
    }

    public int getEurekaServerConnectTimeoutSeconds() {
        return eurekaServerConnectTimeoutSeconds;
    }

    public String getBackupRegistryImpl() {
        return backupRegistryImpl;
    }

    public int getEurekaServerTotalConnections() {
        return eurekaServerTotalConnections;
    }

    public int getEurekaServerTotalConnectionsPerHost() {
        return eurekaServerTotalConnectionsPerHost;
    }

    public String getEurekaServerURLContext() {
        return eurekaServerURLContext;
    }

    public String getEurekaServerPort() {
        return eurekaServerPort;
    }

    public String getEurekaServerDNSName() {
        return eurekaServerDNSName;
    }

    public boolean shouldUseDnsForFetchingServiceUrls() {
        return useDnsForFetchingServiceUrls;
    }

    public boolean shouldRegisterWithEureka() {
        return registerWithEureka;
    }

    public boolean shouldUnregisterOnShutdown() {
        return shouldUnregisterOnShutdown;
    }

    public boolean shouldPreferSameZoneEureka() {
        return preferSameZoneEureka;
    }

    public boolean allowRedirects() {
        return allowRedirects;
    }

    public boolean shouldLogDeltaDiff() {
        return logDeltaDiff;
    }

    public boolean shouldDisableDelta() {
        return disableDelta;
    }

    public String fetchRegistryForRemoteRegions() {
        return fetchRemoteRegionsRegistry;
    }

    public String getRegion() {
        return region;
    }

    public String[] getAvailabilityZones(String region) {
        String availabilityZones = this.environment.getProperty("service-discovery.eureka."+region + ".availabilityZones");
        if (availabilityZones != null) {
            return availabilityZones.split(",");
        }
        return new String[]{};
    }

    public List<String> getEurekaServerServiceUrls(String myZone) {
        String serviceUrls = environment.getProperty("service-discovery.eureka.serviceUrl." + myZone);
        if (serviceUrls == null) {
            serviceUrls = environment.getProperty("service-discovery.eureka.serviceUrl.default");
        }
        return (List) (serviceUrls != null ? Arrays.asList(serviceUrls.split(",")) : new ArrayList());
    }

    public boolean shouldFilterOnlyUpInstances() {
        return filterOnlyUpInstances;
    }

    public int getEurekaConnectionIdleTimeoutSeconds() {
        return eurekaConnectionIdleTimeoutSeconds;
    }

    public boolean shouldFetchRegistry() {
        return fetchRegistry;
    }

    public String getRegistryRefreshSingleVipAddress() {
        return registryRefreshSingleVipAddress;
    }

    public int getHeartbeatExecutorThreadPoolSize() {
        return heartbeatExecutorThreadPoolSize;
    }

    public int getHeartbeatExecutorExponentialBackOffBound() {
        return heartbeatExecutorExponentialBackOffBound;
    }

    public int getCacheRefreshExecutorThreadPoolSize() {
        return cacheRefreshExecutorThreadPoolSize;
    }

    public int getCacheRefreshExecutorExponentialBackOffBound() {
        return cacheRefreshExecutorExponentialBackOffBound;
    }

    public String getDollarReplacement() {
        return dollarReplacement;
    }

    public String getEscapeCharReplacement() {
        return escapeCharReplacement;
    }

    public boolean shouldOnDemandUpdateStatusChange() {
        return onDemandUpdateStatusChange;
    }

    public boolean shouldEnforceRegistrationAtInit() {
        return shouldEnforceRegistrationAtInit;
    }

    public String getEncoderName() {
        return encoderName;
    }

    public String getDecoderName() {
        return decoderName;
    }

    public String getClientDataAccept() {
        return clientDataAccept == null ? EurekaAccept.full.name() : clientDataAccept;
    }

    public String getExperimental(String name) {
        return environment.getProperty("service-discovery.eureka.experimental." + name);
    }

    public EurekaTransportConfig getTransportConfig() {
        return this.transportConfig;
    }
}
