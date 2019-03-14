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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EurekaClientConfigBean implements EurekaClientConfig {

    private final EurekaTransportConfig transportConfig;

    private final ConfigurableEnvironment environment;

    @Value("${service-discovery.eureka.client.refresh.interval:30}")
    private int registryFetchIntervalSeconds;

    @Value("${service-discovery.eureka.appinfo.replicate.interval:30}")
    private int instanceInfoReplicationIntervalSeconds;

    @Value("${service-discovery.eureka.appinfo.initial.replicate.time:40}")
    private int initialInstanceInfoReplicationIntervalSeconds = 40;

    @Value("${service-discovery.eureka.serviceUrlPollIntervalMs:300}")
    private int eurekaServiceUrlPollIntervalSeconds;

    @Value("${service-discovery.eureka.eurekaServer.proxyPort:#{null}}")
    private String proxyPort;

    @Value("${service-discovery.eureka.eurekaServer.proxyHost:#{null}}")
    private String proxyHost;

    @Value("${service-discovery.eureka.eurekaServer.proxyUserName:#{null}}")
    private String proxyUserName;

    @Value("${service-discovery.eureka.eurekaServer.proxyPassword:#{null}}")
    private String proxyPassword;

    @Value("${service-discovery.eureka.eurekaServer.readTimeout:8}")
    private int eurekaServerReadTimeoutSeconds;

    @Value("${service-discovery.eureka.eurekaServer.connectTimeout:5}")
    private int eurekaServerConnectTimeoutSeconds;

    @Value("${service-discovery.eureka.eurekaServer.backupregistry:#{null}}")
    private String backupRegistryImpl;

    @Value("${service-discovery.eureka.eurekaServer.maxTotalConnections:200}")
    private int eurekaServerTotalConnections;

    @Value("${service-discovery.eureka.eurekaServer.maxConnectionsPerHost:50}")
    private int eurekaServerTotalConnectionsPerHost;

    @Value("${service-discovery.eureka.eurekaServer.context:#{null}}")
    private String eurekaServerURLContext;

    @Value("${service-discovery.eureka.eurekaServer.port:#{null}}")
    private String eurekaServerPort;

    @Value("${service-discovery.eureka.eurekaServer.domainName:#{null}}")
    private String eurekaServerDNSName;

    @Value("${service-discovery.eureka.region:us-east-1}")
    private String region;

    @Value("${service-discovery.eureka.eurekaserver.connectionIdleTimeoutInSeconds:30}")
    private int eurekaConnectionIdleTimeoutSeconds;

    @Value("${service-discovery.eureka.registryRefreshSingleVipAddress:#{null}}")
    private String registryRefreshSingleVipAddress;

    @Value("${service-discovery.eureka.client.heartbeat.threadPoolSize:5}")
    private int heartbeatExecutorThreadPoolSize;

    @Value("${service-discovery.eureka.client.heartbeat.exponentialBackOffBound:10}")
    private int heartbeatExecutorExponentialBackOffBound;

    @Value("${service-discovery.eureka.client.cacheRefresh.threadPoolSize:5}")
    private int cacheRefreshExecutorThreadPoolSize;

    @Value("${service-discovery.eureka.client.cacheRefresh.exponentialBackOffBound:10}")
    private int cacheRefreshExecutorExponentialBackOffBound;

    @Value("${service-discovery.eureka.eurekaServer.gzipContent:true}")
    private boolean gZipContent;

    @Value("${service-discovery.eureka.shouldUseDns:false}")
    private boolean useDnsForFetchingServiceUrls;

    private boolean registerWithEureka = false;

    @Value("${service-discovery.eureka.preferSameZone:true}")
    private boolean preferSameZoneEureka;

    @Value("${service-discovery.eureka.printDeltaFullDiff:false}")
    private boolean logDeltaDiff;

    @Value("${service-discovery.eureka.disableDelta:false}")
    private boolean disableDelta;

    @Value("${service-discovery.eureka.fetchRemoteRegionsRegistry:#{null}}")
    private String fetchRemoteRegionsRegistry;

    @Value("${service-discovery.eureka.shouldFilterOnlyUpInstances:true}")
    private boolean filterOnlyUpInstances;

    @Value("${service-discovery.eureka.shouldFetchRegistry:true}")
    private boolean fetchRegistry;

    @Value("${service-discovery.eureka.dollarReplacement:_-}")
    private String dollarReplacement;

    @Value("${service-discovery.eureka.escapeCharReplacement:__}")
    private String escapeCharReplacement;

    @Value("${service-discovery.eureka.allowRedirects:false}")
    private boolean allowRedirects;

    @Value("${service-discovery.eureka.shouldOnDemandUpdateStatusChange:true}")
    private boolean onDemandUpdateStatusChange;

    @Value("${service-discovery.eureka.encoderName:#{null}}")
    private String encoderName;

    @Value("${service-discovery.eureka.decoderName:#{null}}")
    private String decoderName;

    @Value("${service-discovery.eureka.clientDataAccept:#{null}}")
    private String clientDataAccept;

    @Value("${service-discovery.eureka.shouldUnregisterOnShutdown:true}")
    private boolean shouldUnregisterOnShutdown;

    @Value("${service-discovery.eureka.shouldEnforceRegistrationAtInit:false}")
    private boolean shouldEnforceRegistrationAtInit;


    public EurekaClientConfigBean(ConfigurableEnvironment environment, EurekaTransportConfig eurekaTransportConfigBean) {
        this.environment = environment;
        this.transportConfig = eurekaTransportConfigBean;
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
