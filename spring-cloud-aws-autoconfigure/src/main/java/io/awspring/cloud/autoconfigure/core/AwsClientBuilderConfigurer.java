/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.awspring.cloud.autoconfigure.core;

import io.awspring.cloud.autoconfigure.AwsClientProperties;
import io.awspring.cloud.core.SpringCloudClientConfiguration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;

import java.util.Optional;

/**
 * Provides a convenience method to apply common configuration to any {@link AwsClientBuilder}.
 *
 * @author Maciej Walkowiak
 * @since 3.0
 */
public class AwsClientBuilderConfigurer {
	private final AwsCredentialsProvider credentialsProvider;
	private final AwsRegionProvider regionProvider;
	private final AwsProperties awsProperties;
	private final ClientOverrideConfiguration.Builder clientOverrideConfigurationBuilder;

	AwsClientBuilderConfigurer(AwsCredentialsProvider credentialsProvider, AwsRegionProvider regionProvider,
			AwsProperties awsProperties) {
		this.credentialsProvider = credentialsProvider;
		this.regionProvider = regionProvider;
		this.awsProperties = awsProperties;
		this.clientOverrideConfigurationBuilder = new SpringCloudClientConfiguration()
				.clientOverrideConfigurationBuilder();
	}

	public AwsClientBuilder<?, ?> configure(AwsClientBuilder<?, ?> builder, AwsClientProperties clientProperties,
			Optional<MetricPublisher> metricPublisher) {
		metricPublisher.ifPresent(clientOverrideConfigurationBuilder::addMetricPublisher);
		builder.credentialsProvider(credentialsProvider).region(resolveRegion(clientProperties))
				.overrideConfiguration(clientOverrideConfigurationBuilder.build());
		Optional.ofNullable(awsProperties.getEndpoint()).ifPresent(builder::endpointOverride);
		Optional.ofNullable(clientProperties.getEndpoint()).ifPresent(builder::endpointOverride);
		return builder;
	}

	public Region resolveRegion(AwsClientProperties clientProperties) {
		return StringUtils.hasLength(clientProperties.getRegion()) ? Region.of(clientProperties.getRegion())
				: regionProvider.getRegion();
	}
}
