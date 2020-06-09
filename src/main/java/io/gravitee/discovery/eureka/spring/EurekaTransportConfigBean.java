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

import com.netflix.discovery.shared.transport.EurekaTransportConfig;
import org.springframework.core.env.ConfigurableEnvironment;

public class EurekaTransportConfigBean implements EurekaTransportConfig {

    private int sessionedClientReconnectIntervalSeconds;
    private double retryableClientQuarantineRefreshPercentage;
    private int applicationsResolverDataStalenessThresholdSeconds;
    private boolean applicationsResolverUseIp;
    private int asyncResolverRefreshIntervalMs;
    private int asyncResolverWarmupTimeoutMs;
    private int asyncExecutorThreadPoolSize;
    private String writeClusterVip;
    private String readClusterVip;
    private String bootstrapResolverStrategy;
    private boolean useBootstrapResolverForQuery;

    public EurekaTransportConfigBean(ConfigurableEnvironment env) {
        this.sessionedClientReconnectIntervalSeconds = Integer.parseInt(env.getProperty("service-discovery.eureka.transport.sessionedClientReconnectIntervalSeconds", "0"));
        this.retryableClientQuarantineRefreshPercentage = Double.parseDouble(env.getProperty("service-discovery.eureka.transport.retryableClientQuarantineRefreshPercentage", "0.66"));
        this.applicationsResolverDataStalenessThresholdSeconds = Integer.parseInt(env.getProperty("service-discovery.eureka.transport.applicationsResolverDataStalenessThresholdSeconds", "300"));
        this.applicationsResolverUseIp = Boolean.parseBoolean(env.getProperty("service-discovery.eureka.transport.applicationsResolverUseIp", "false"));
        this.asyncResolverRefreshIntervalMs = Integer.parseInt(env.getProperty("service-discovery.eureka.transport.asyncResolverRefreshIntervalMs", "300000"));
        this.asyncResolverWarmupTimeoutMs = Integer.parseInt(env.getProperty("service-discovery.eureka.transport.asyncResolverWarmupTimeoutMs", "5000"));
        this.asyncExecutorThreadPoolSize = Integer.parseInt(env.getProperty("service-discovery.eureka.transport.asyncExecutorThreadPoolSize", "5"));
        this.writeClusterVip = env.getProperty("service-discovery.eureka.transport.writeClusterVip");
        this.readClusterVip = env.getProperty("service-discovery.eureka.transport.readClusterVip");
        this.bootstrapResolverStrategy = env.getProperty("service-discovery.eureka.transport.bootstrapResolverStrategy");
        this.useBootstrapResolverForQuery = Boolean.parseBoolean(env.getProperty("service-discovery.eureka.transport.useBootstrapResolverForQuery", "true"));
    }

    @Override
    public int getSessionedClientReconnectIntervalSeconds() {
        return sessionedClientReconnectIntervalSeconds;
    }

    @Override
    public double getRetryableClientQuarantineRefreshPercentage() {
        return 0.66;
    }

    @Override
    public int getApplicationsResolverDataStalenessThresholdSeconds() {
        return applicationsResolverDataStalenessThresholdSeconds;
    }

    @Override
    public boolean applicationsResolverUseIp() {
        return applicationsResolverUseIp;
    }

    @Override
    public int getAsyncResolverRefreshIntervalMs() {
        return asyncResolverRefreshIntervalMs;
    }

    @Override
    public int getAsyncResolverWarmUpTimeoutMs() {
        return asyncResolverWarmupTimeoutMs;
    }

    @Override
    public int getAsyncExecutorThreadPoolSize() {
        return asyncExecutorThreadPoolSize;
    }

    @Override
    public String getWriteClusterVip() {
        return writeClusterVip;
    }

    @Override
    public String getReadClusterVip() {
        return readClusterVip;
    }

    @Override
    public String getBootstrapResolverStrategy() {
        return bootstrapResolverStrategy;
    }

    @Override
    public boolean useBootstrapResolverForQuery() {
        return useBootstrapResolverForQuery;
    }
}
