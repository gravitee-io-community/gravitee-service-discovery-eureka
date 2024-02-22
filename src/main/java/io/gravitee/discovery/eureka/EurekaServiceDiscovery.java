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
package io.gravitee.discovery.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.CacheRefreshedEvent;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaEvent;
import com.netflix.discovery.EurekaEventListener;
import io.gravitee.discovery.api.event.Event;
import io.gravitee.discovery.api.event.Handler;
import io.gravitee.discovery.api.service.AbstractServiceDiscovery;
import io.gravitee.discovery.eureka.configuration.EurekaServiceDiscoveryConfiguration;
import io.gravitee.discovery.eureka.service.EurekaService;
import io.gravitee.discovery.eureka.service.EurekaServiceResolver;
import io.gravitee.discovery.eureka.spring.EurekaClientConfigBean;
import io.gravitee.discovery.eureka.spring.EurekaTransportConfigBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

public class EurekaServiceDiscovery extends AbstractServiceDiscovery<EurekaService> implements InitializingBean {

  private static volatile DiscoveryClient client;

  private EurekaEventListener listener;
  private EurekaServiceResolver resolver;
  private EurekaServiceDiscoveryConfiguration configuration;

  @Autowired
  private ConfigurableEnvironment environment;

  public EurekaServiceDiscovery(final EurekaServiceDiscoveryConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public void listen(final Handler<Event> handler) {
    refresh(handler);
    client.registerEventListener(listener = new EurekaEventListener() {
      @Override
      public void onEvent(final EurekaEvent event) {
        if (event instanceof CacheRefreshedEvent) {
          refresh(handler);
        }
      }
    });
  }

  private void refresh(final Handler<Event> handler) {
    List<EurekaService> servicesUp = resolver.getServicesUpByApplicationName(configuration.getApplication());
    servicesUp.forEach((serviceUp) -> {
      EurekaService oldService = getService(serviceUp::equals);
      if (oldService == null) {
        handler.handle(registerEndpoint(serviceUp));
      } else if (!serviceUp.isTargetEquals(oldService)) {
          handler.handle(unregisterEndpoint(oldService));
          handler.handle(registerEndpoint(serviceUp));
      }
    });
    List<EurekaService> servicesDown = getServices((s) -> !servicesUp.contains(s));
    servicesDown.forEach((serviceDown) -> {
      handler.handle(unregisterEndpoint(serviceDown));
    });
  }

  @Override
  public void stop() throws Exception {
    client.unregisterEventListener(listener);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (client == null) {
      synchronized (this) {
        if (client == null) {
          MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
          InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
          client = new DiscoveryClient(new ApplicationInfoManager(instanceConfig, instanceInfo), new EurekaClientConfigBean(environment, new EurekaTransportConfigBean(environment)));
        }
      }
    }
    resolver = new EurekaServiceResolver(client);
  }
}
