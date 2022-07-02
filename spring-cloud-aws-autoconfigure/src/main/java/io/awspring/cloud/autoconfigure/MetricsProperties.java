package io.awspring.cloud.autoconfigure;

import org.springframework.lang.Nullable;

public class MetricsProperties {
	@Nullable
	Boolean enabled;
	@Nullable
	String namespace;
	@Nullable
	Long uploadFrequencyInSeconds;

	@Nullable
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(@Nullable Boolean enabled) {
		this.enabled = enabled;
	}

	@Nullable
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(@Nullable String namespace) {
		this.namespace = namespace;
	}

	@Nullable
	public Long getUploadFrequencyInSeconds() {
		return uploadFrequencyInSeconds;
	}

	public void setUploadFrequencyInSeconds(@Nullable Long uploadFrequencyInSeconds) {
		this.uploadFrequencyInSeconds = uploadFrequencyInSeconds;
	}
}
