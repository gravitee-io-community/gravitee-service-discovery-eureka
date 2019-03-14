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
package io.gravitee.discovery.eureka.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EurekaServiceResolverTest {

  @Test
  public void shouldReturnEmptyListIfUnknownApplication() {
    DiscoveryClient discoveryClient = mock(DiscoveryClient.class);
    EurekaServiceResolver resolver = new EurekaServiceResolver(discoveryClient);

    when(discoveryClient.getApplication("APP")).thenReturn(null);

    List<EurekaService> eurekaServices = resolver.getServicesUpByApplicationName("APP");
    assertThat(eurekaServices).isEmpty();
  }

  @Test
  public void shouldReturnEmptyListIfNoInstance() {
    DiscoveryClient discoveryClient = mock(DiscoveryClient.class);
    EurekaServiceResolver resolver = new EurekaServiceResolver(discoveryClient);
    Application application = mock(Application.class);

    when(discoveryClient.getApplication("APP")).thenReturn(application);

    List<EurekaService> eurekaServices = resolver.getServicesUpByApplicationName("APP");
    assertThat(eurekaServices).isEmpty();
  }

  @Test
  public void shouldGetOnlyUpInstance() {
    DiscoveryClient discoveryClient = mock(DiscoveryClient.class);
    EurekaServiceResolver resolver = new EurekaServiceResolver(discoveryClient);
    Application application = mock(Application.class);
    List<InstanceInfo> instanceInfos = new ArrayList<>();
    InstanceInfo instanceInfo1 = mock(InstanceInfo.class);
    InstanceInfo instanceInfo2 = mock(InstanceInfo.class);
    instanceInfos.add(instanceInfo1);
    instanceInfos.add(instanceInfo2);

    when(instanceInfo1.getStatus()).thenReturn(InstanceInfo.InstanceStatus.UP);
    when(instanceInfo1.getId()).thenReturn("1");
    when(instanceInfo2.getStatus()).thenReturn(InstanceInfo.InstanceStatus.DOWN);
    when(application.getInstances()).thenReturn(instanceInfos);
    when(discoveryClient.getApplication("APP")).thenReturn(application);

    List<EurekaService> eurekaServices = resolver.getServicesUpByApplicationName("APP");
    assertThat(eurekaServices).containsOnly(new EurekaService(instanceInfo1));
  }

}