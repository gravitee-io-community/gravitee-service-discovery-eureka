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
import io.gravitee.discovery.api.event.Event;
import io.gravitee.discovery.api.event.EventType;
import io.gravitee.discovery.api.service.AbstractServiceDiscovery;
import io.gravitee.discovery.api.service.Service;
import io.gravitee.discovery.eureka.service.EurekaService;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class AbstractServiceDiscoveryTest {

  @Test
  public void shouldGetServiceWorks() {
    AbstractServiceDiscovery serviceDiscovery = mock(AbstractServiceDiscovery.class, CALLS_REAL_METHODS);
    InstanceInfo instanceInfo = mock(InstanceInfo.class);
    EurekaService service = new EurekaService(instanceInfo);
    List<Service> services = new ArrayList<>();
    services.add(service);
    ReflectionTestUtils.setField(serviceDiscovery, "services", services);

    assertThat(serviceDiscovery.getService(service::equals)).isEqualTo(service);
    assertThat(serviceDiscovery.getService(s -> false)).isNull();
  }

  @Test
  public void shouldGetServicesWorks() {
    AbstractServiceDiscovery serviceDiscovery = mock(AbstractServiceDiscovery.class, CALLS_REAL_METHODS);
    InstanceInfo instanceInfo = mock(InstanceInfo.class);
    EurekaService service = new EurekaService(instanceInfo);
    List<Service> services = new ArrayList<>();
    services.add(service);
    ReflectionTestUtils.setField(serviceDiscovery, "services", services);

    assertThat(serviceDiscovery.getServices(s -> true)).containsOnly(service);
    assertThat(serviceDiscovery.getServices(s -> false)).isEmpty();
  }

  @Test
  public void shouldFireRegisterEventAndPersistNewService() {
    AbstractServiceDiscovery serviceDiscovery = mock(AbstractServiceDiscovery.class, CALLS_REAL_METHODS);
    ReflectionTestUtils.setField(serviceDiscovery, "services", new ArrayList<>());
    InstanceInfo instanceInfo = mock(InstanceInfo.class);
    EurekaService newService = new EurekaService(instanceInfo);

    Event event = serviceDiscovery.registerEndpoint(newService);

    assertThat(event.service()).isEqualTo(newService);
    assertThat(event.type()).isEqualTo(EventType.REGISTER);
    assertThat(serviceDiscovery.getService(newService::equals)).isEqualTo(newService);
  }

  @Test
  public void shouldFireUnRegisterEventAndRemoveOldService() {
    AbstractServiceDiscovery serviceDiscovery = mock(AbstractServiceDiscovery.class, CALLS_REAL_METHODS);
    InstanceInfo instanceInfo = mock(InstanceInfo.class);
    EurekaService service = new EurekaService(instanceInfo);

    List<Service> services = new ArrayList<>();
    services.add(service);
    ReflectionTestUtils.setField(serviceDiscovery, "services", services);

    Event event = serviceDiscovery.unregisterEndpoint(service);

    assertThat(event.service()).isEqualTo(service);
    assertThat(event.type()).isEqualTo(EventType.UNREGISTER);
    assertThat(serviceDiscovery.getService(service::equals)).isNull();
  }

}