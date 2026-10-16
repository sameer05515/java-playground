package com.p.chat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "snapshot")
public class SnapshotProperties {

    private Path configPath;
    private Path outputRoot;
    private String createdBy = "Prem";
    private boolean runOnStartup = true;
    private boolean continueOnError = true;

    public Path getConfigPath() {
        return configPath;
    }

    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
    }

    public Path getOutputRoot() {
        return outputRoot;
    }

    public void setOutputRoot(Path outputRoot) {
        this.outputRoot = outputRoot;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isRunOnStartup() {
        return runOnStartup;
    }

    public void setRunOnStartup(boolean runOnStartup) {
        this.runOnStartup = runOnStartup;
    }

    public boolean isContinueOnError() {
        return continueOnError;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public Path getBaseJsonPath() {
        if (outputRoot == null) {
            throw new IllegalStateException("snapshot.output-root is not configured");
        }
        return outputRoot.resolve("base.json");
    }

    public Path getSnapshotOutputRoot() {
        if (outputRoot == null) {
            throw new IllegalStateException("snapshot.output-root is not configured");
        }
        return outputRoot.resolve("itr2");
    }
}
