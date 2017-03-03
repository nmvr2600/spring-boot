/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.autoconfigure;

import java.util.Map;

import org.junit.Test;

import org.springframework.boot.actuate.trace.TraceProperties;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.boot.actuate.trace.WebRequestTraceFilter;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TraceWebFilterAutoConfiguration}.
 *
 * @author Phillip Webb
 */
public class TraceWebFilterAutoConfigurationTests {

	@Test
	public void configureFilter() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				PropertyPlaceholderAutoConfiguration.class,
				TraceRepositoryAutoConfiguration.class,
				TraceWebFilterAutoConfiguration.class);
		assertThat(context.getBean(WebRequestTraceFilter.class)).isNotNull();
		context.close();
	}

	@Test
	public void overrideTraceFilter() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				CustomTraceFilterConfig.class, PropertyPlaceholderAutoConfiguration.class,
				TraceRepositoryAutoConfiguration.class,
				TraceWebFilterAutoConfiguration.class);
		WebRequestTraceFilter filter = context.getBean(WebRequestTraceFilter.class);
		assertThat(filter).isInstanceOf(TestWebRequestTraceFilter.class);
		context.close();
	}

	@Configuration
	static class CustomTraceFilterConfig {

		@Bean
		public TestWebRequestTraceFilter testWebRequestTraceFilter(
				TraceRepository repository, TraceProperties properties) {
			return new TestWebRequestTraceFilter(repository, properties);
		}

	}

	static class TestWebRequestTraceFilter extends WebRequestTraceFilter {

		TestWebRequestTraceFilter(TraceRepository repository,
				TraceProperties properties) {
			super(repository, properties);
		}

		@Override
		protected void postProcessRequestHeaders(Map<String, Object> headers) {
			headers.clear();
		}

	}

}
