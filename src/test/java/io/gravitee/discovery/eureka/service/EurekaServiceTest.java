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
import org.junit.Test;
import org.mockito.Mockito;

import static com.netflix.appinfo.InstanceInfo.InstanceStatus.UNKNOWN;
import static com.netflix.appinfo.InstanceInfo.InstanceStatus.UP;
import static com.netflix.appinfo.InstanceInfo.PortType.SECURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EurekaServiceTest {

  @Test
  public void shouldConstructorRejectNullInstanceInfo() {
    assertThatThrownBy(() -> new EurekaService(null)).isInstanceOf(IllegalArgumentException.class).hasMessage("Service instance required");
  }

  @Test
  public void shouldBeUpIfInstanceInfoStatusIsUp() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getStatus()).thenReturn(UP);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.isUp()).isTrue();
  }

  @Test
  public void shouldNotBeUpIfInstanceInfoStatusIsNotUp() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getStatus()).thenReturn(UNKNOWN);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.isUp()).isFalse();
  }

  @Test
  public void shouldBeSecureIfInstanceInfoPortIsSecure() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.isPortEnabled(SECURE)).thenReturn(true);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.isSecure()).isTrue();
    verify(instanceInfo).isPortEnabled(SECURE);
  }

  @Test
  public void shouldNotBeSecureIfInstanceInfoPortIsNotSecure() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.isPortEnabled(SECURE)).thenReturn(false);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.isSecure()).isFalse();
    verify(instanceInfo).isPortEnabled(SECURE);
  }

  @Test
  public void shouldTargetBeDifferentWhenHostIsDifferent() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getHostName()).thenReturn("host1");
    EurekaService eurekaService = new EurekaService(instanceInfo);

    InstanceInfo instanceInfo2 = Mockito.mock(InstanceInfo.class);
    when(instanceInfo2.getHostName()).thenReturn("host2");
    EurekaService eurekaService2 = new EurekaService(instanceInfo2);
    assertThat(eurekaService.isTargetEquals(eurekaService2)).isFalse();
  }

  @Test
  public void shouldTargetBeDifferentWhenPortIsDifferent() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getHostName()).thenReturn("host1");
    when(instanceInfo.getPort()).thenReturn(80);
    EurekaService eurekaService = new EurekaService(instanceInfo);

    InstanceInfo instanceInfo2 = Mockito.mock(InstanceInfo.class);
    when(instanceInfo2.getHostName()).thenReturn("host1");
    when(instanceInfo2.getPort()).thenReturn(8080);
    EurekaService eurekaService2 = new EurekaService(instanceInfo2);
    assertThat(eurekaService.isTargetEquals(eurekaService2)).isFalse();
  }

  @Test
  public void shouldTargetBeDifferentWhenPortAndHostIsDifferent() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getHostName()).thenReturn("host1");
    when(instanceInfo.getPort()).thenReturn(80);
    EurekaService eurekaService = new EurekaService(instanceInfo);

    InstanceInfo instanceInfo2 = Mockito.mock(InstanceInfo.class);
    when(instanceInfo2.getHostName()).thenReturn("host2");
    when(instanceInfo2.getPort()).thenReturn(8080);
    EurekaService eurekaService2 = new EurekaService(instanceInfo2);
    assertThat(eurekaService.isTargetEquals(eurekaService2)).isFalse();
  }

  @Test
  public void shouldTargetTheSameWhenPortAndHostAreIdenticals() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getHostName()).thenReturn("host1");
    when(instanceInfo.getPort()).thenReturn(80);
    EurekaService eurekaService = new EurekaService(instanceInfo);

    InstanceInfo instanceInfo2 = Mockito.mock(InstanceInfo.class);
    when(instanceInfo2.getHostName()).thenReturn("host1");
    when(instanceInfo2.getPort()).thenReturn(80);
    EurekaService eurekaService2 = new EurekaService(instanceInfo2);
    assertThat(eurekaService.isTargetEquals(eurekaService2)).isTrue();
  }

  @Test
  public void shouldTargetDifferentForNullInstance() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getHostName()).thenReturn("host1");
    when(instanceInfo.getPort()).thenReturn(80);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.isTargetEquals(null)).isFalse();
  }

  @Test
  public void shouldTargetEqualsForSameObject() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getHostName()).thenReturn("host1");
    when(instanceInfo.getPort()).thenReturn(80);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.isTargetEquals(eurekaService)).isTrue();
  }

  @Test
  public void shouldGetIdReturnInstanceInfoId() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getId()).thenReturn("1");
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.id()).isEqualTo("1");
  }

  @Test
  public void shouldGetHostReturnInstanceInfoHost() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getHostName()).thenReturn("host");
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.host()).isEqualTo("host");
  }

  @Test
  public void shouldPortReturnSecurePortIfSecure() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.isPortEnabled(SECURE)).thenReturn(true);
    when(instanceInfo.getSecurePort()).thenReturn(443);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.port()).isEqualTo(443);
  }

  @Test
  public void shouldPortReturnBasicPortIfNotSecure() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.isPortEnabled(SECURE)).thenReturn(false);
    when(instanceInfo.getPort()).thenReturn(8080);
    EurekaService eurekaService = new EurekaService(instanceInfo);
    assertThat(eurekaService.port()).isEqualTo(8080);
  }

  @Test
  public void shouldBeEqualsIfSameId() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getId()).thenReturn("1");
    EurekaService eurekaService = new EurekaService(instanceInfo);

    InstanceInfo instanceInfo2 = Mockito.mock(InstanceInfo.class);
    when(instanceInfo2.getId()).thenReturn("1");
    EurekaService eurekaService2 = new EurekaService(instanceInfo2);
    assertThat(eurekaService.equals(eurekaService2)).isTrue();
  }

  @Test
  public void shouldNotBeEqualsIfNotSameId() {
    InstanceInfo instanceInfo = Mockito.mock(InstanceInfo.class);
    when(instanceInfo.getId()).thenReturn("1");
    EurekaService eurekaService = new EurekaService(instanceInfo);

    InstanceInfo instanceInfo2 = Mockito.mock(InstanceInfo.class);
    when(instanceInfo2.getId()).thenReturn("2");
    EurekaService eurekaService2 = new EurekaService(instanceInfo2);
    assertThat(eurekaService.equals(eurekaService2)).isFalse();
  }

}