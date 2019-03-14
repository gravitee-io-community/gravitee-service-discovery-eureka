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

import io.gravitee.discovery.api.ServiceDiscovery;
import io.gravitee.discovery.api.event.Event;
import io.gravitee.discovery.api.event.EventType;
import io.gravitee.discovery.api.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractServiceDiscovery<T extends Service> implements ServiceDiscovery {

  private List<T> services = new ArrayList<>();

  protected Event registerEndpoint(T service) {
    services.add(service);
    return new Event() {
      @Override
      public EventType type() {
        return EventType.REGISTER;
      }

      @Override
      public Service service() {
        return service;
      }
    };
  }

  protected Event unregisterEndpoint(T service) {
    services.remove(service);
    return new Event() {
      @Override
      public EventType type() {
        return EventType.UNREGISTER;
      }

      @Override
      public Service service() {
        return service;
      }
    };
  }

  protected T getService(Predicate<T> predicate) {
    return services.stream().filter(predicate).findAny().orElse(null);
  }

  protected List<T> getServices(Predicate<T> predicate) {
    return services.stream().filter(predicate).collect(Collectors.toList());
  }

}
