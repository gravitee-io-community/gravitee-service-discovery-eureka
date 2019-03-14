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
import io.gravitee.discovery.api.service.Service;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.netflix.appinfo.InstanceInfo.PortType.SECURE;

public class EurekaService implements Service {

  private InstanceInfo instance;

  public EurekaService(InstanceInfo instance) {
    Assert.notNull(instance, "Service instance required");
    this.instance = instance;
  }

  public boolean isUp() {
    return instance.getStatus().equals(InstanceInfo.InstanceStatus.UP);
  }

  public boolean isSecure() {
    return this.instance.isPortEnabled(SECURE);
  }

  public boolean isTargetEquals(EurekaService o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    return o.port() == this.port() && o.host().equals(this.host());
  }

  @Override
  public String id() {
    return instance.getId();
  }

  @Override
  public String host() {
    return this.instance.getHostName();
  }

  @Override
  public int port() {
    if (isSecure()) {
      return this.instance.getSecurePort();
    }
    return this.instance.getPort();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    EurekaService that = (EurekaService) o;
    return id().equals(that.id());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id());
  }
}
