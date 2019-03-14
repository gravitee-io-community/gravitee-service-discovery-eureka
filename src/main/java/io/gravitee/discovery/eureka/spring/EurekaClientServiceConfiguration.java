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

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DiscoveryClient;
import io.gravitee.discovery.eureka.service.EurekaServiceResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class EurekaClientServiceConfiguration {

  @Bean
  public EurekaClientConfigBean eurekaClientConfig(ConfigurableEnvironment env, EurekaTransportConfigBean eurekaTransportConfig) {
    return new EurekaClientConfigBean(env, eurekaTransportConfig);
  }

  @Bean
  public EurekaTransportConfigBean eurekaClientTransportConfig() {
    return new EurekaTransportConfigBean();
  }

  @Bean
  public DiscoveryClient eurekaClient(EurekaClientConfigBean cfg) {
    MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
    InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
    return new DiscoveryClient(new ApplicationInfoManager(instanceConfig, instanceInfo), cfg);
  }

  @Bean
  public EurekaServiceResolver eurekaServiceResolver(DiscoveryClient discoveryClient) {
    return new EurekaServiceResolver(discoveryClient);
  }
}
