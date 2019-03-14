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

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.CacheRefreshedEvent;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaEvent;
import com.netflix.discovery.EurekaEventListener;
import io.gravitee.discovery.api.event.Event;
import io.gravitee.discovery.api.event.EventType;
import io.gravitee.discovery.eureka.configuration.EurekaServiceDiscoveryConfiguration;
import io.gravitee.discovery.eureka.service.EurekaService;
import io.gravitee.discovery.eureka.service.EurekaServiceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EurekaServiceDiscoveryTest {

  @Mock
  private DiscoveryClient discoveryClient;

  @Mock
  private EurekaServiceResolver eurekaServiceResolver;

  @Captor
  ArgumentCaptor<EurekaEventListener> argCaptor;

  @InjectMocks
  private EurekaServiceDiscovery eurekaServiceDiscovery;

  private EurekaServiceDiscoveryConfiguration configuration;

  @Before
  public void setUp() {
    configuration = new EurekaServiceDiscoveryConfiguration();
    configuration.setApplication("APP");
    eurekaServiceDiscovery = new EurekaServiceDiscovery(configuration);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldFireEventOnlyWhenCacheRefreshEventIsFired() {
    eurekaServiceDiscovery.listen(event -> fail("no Event must be Fired"));
    verify(discoveryClient).registerEventListener(argCaptor.capture());
    argCaptor.getValue().onEvent(new EurekaEvent() {});
  }

  @Test
  public void shouldFireRegisterEventType() {
    List<EurekaService> services = new ArrayList<>();
    EurekaService serviceMock1 = mock(EurekaService.class);
    services.add(serviceMock1);

    when(eurekaServiceResolver.getServicesUpByApplicationName(configuration.getApplication())).thenReturn(Collections.emptyList(), services);
    eurekaServiceDiscovery.listen(event -> {
      assertThat(event.type()).isEqualTo(EventType.REGISTER);
      assertThat(event.service()).isEqualTo(serviceMock1);
    });

    verify(discoveryClient).registerEventListener(argCaptor.capture());
    argCaptor.getValue().onEvent(new CacheRefreshedEvent());
  }

  @Test
  public void shouldFireUnRegisterEventType() {
    List<EurekaService> services = new ArrayList<>();
    EurekaService serviceMock1 = mock(EurekaService.class);
    services.add(serviceMock1);

    List<Event> events = new ArrayList<>();
    when(eurekaServiceResolver.getServicesUpByApplicationName(configuration.getApplication())).thenReturn(services);
    eurekaServiceDiscovery.listen(e -> events.add(e));
    verify(discoveryClient).registerEventListener(argCaptor.capture());

    when(eurekaServiceResolver.getServicesUpByApplicationName(configuration.getApplication())).thenReturn(Collections.emptyList());
    argCaptor.getValue().onEvent(new CacheRefreshedEvent());

    assertThat(events.get(1).type()).isEqualTo(EventType.UNREGISTER);
    assertThat(events.get(1).service()).isEqualTo(serviceMock1);
  }

  @Test
  public void shouldFireUnRegisterAndRegisterForTargetHostUpdate() {
    List<EurekaService> services = new ArrayList<>();
    InstanceInfo instanceInfo = mock(InstanceInfo.class);
    EurekaService eurekaService1 = new EurekaService(instanceInfo);
    services.add(eurekaService1);
    when(instanceInfo.getId()).thenReturn("1");
    when(instanceInfo.getHostName()).thenReturn("host1");

    when(eurekaServiceResolver.getServicesUpByApplicationName(configuration.getApplication())).thenReturn(services);
    List<Event> events = new ArrayList<>();
    eurekaServiceDiscovery.listen(event -> events.add(event));
    verify(discoveryClient).registerEventListener(argCaptor.capture());

    services = new ArrayList<>();
    InstanceInfo instanceInfo2 = mock(InstanceInfo.class);
    EurekaService eurekaService2 = new EurekaService(instanceInfo2);
    services.add(eurekaService2);
    when(instanceInfo2.getId()).thenReturn("1");
    when(instanceInfo2.getHostName()).thenReturn("host2");

    when(eurekaServiceResolver.getServicesUpByApplicationName(configuration.getApplication())).thenReturn(services);
    argCaptor.getValue().onEvent(new CacheRefreshedEvent());

    assertThat(events.get(1).type()).isEqualTo(EventType.UNREGISTER);
    assertThat(events.get(1).service().host()).isEqualTo("host1");
    assertThat(events.get(2).type()).isEqualTo(EventType.REGISTER);
    assertThat(events.get(2).service().host()).isEqualTo("host2");
  }
}