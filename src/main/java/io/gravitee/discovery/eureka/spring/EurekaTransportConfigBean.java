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
import org.springframework.beans.factory.annotation.Value;

public class EurekaTransportConfigBean implements EurekaTransportConfig {

    @Value("${service-discovery.eureka.transport.sessionedClientReconnectIntervalSeconds:0}")
    private int sessionedClientReconnectIntervalSeconds;

    @Value("${service-discovery.eureka.transport.retryableClientQuarantineRefreshPercentage:0.66}")
    private double retryableClientQuarantineRefreshPercentage;

    @Value("${service-discovery.eureka.transport.applicationsResolverDataStalenessThresholdSeconds:300}")
    private int applicationsResolverDataStalenessThresholdSeconds;

    @Value("${service-discovery.eureka.transport.applicationsResolverUseIp:false}")
    private boolean applicationsResolverUseIp;

    @Value("${service-discovery.eureka.transport.asyncResolverRefreshIntervalMs:300000}")
    private int asyncResolverRefreshIntervalMs;

    @Value("${service-discovery.eureka.transport.asyncResolverWarmupTimeoutMs:5000}")
    private int asyncResolverWarmupTimeoutMs;

    @Value("${service-discovery.eureka.transport.asyncExecutorThreadPoolSize:5}")
    private int asyncExecutorThreadPoolSize;

    @Value("${service-discovery.eureka.transport.writeClusterVip:#{null}}")
    private String writeClusterVip;

    @Value("${service-discovery.eureka.transport.readClusterVip:#{null}}")
    private String readClusterVip;

    @Value("${service-discovery.eureka.transport.bootstrapResolverStrategy:#{null}}")
    private String bootstrapResolverStrategy;

    @Value("${service-discovery.eureka.transport.useBootstrapResolverForQuery:true}")
    private boolean useBootstrapResolverForQuery;

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
