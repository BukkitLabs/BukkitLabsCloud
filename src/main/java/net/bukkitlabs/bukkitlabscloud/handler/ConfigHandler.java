package net.bukkitlabs.bukkitlabscloud.handler;

import net.bukkitlabs.bukkitlabscloudapi.internal.config.ConfigCreator;
import net.bukkitlabs.bukkitlabscloudapi.internal.config.Configuration;
import net.bukkitlabs.bukkitlabscloudapi.internal.config.ConfigurationProvider;
import net.bukkitlabs.bukkitlabscloudapi.internal.config.JsonConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
public class ConfigHandler {

    private final ConfigurationProvider provider = Objects.requireNonNull(ConfigurationProvider.getProvider(JsonConfiguration.class));
    private File generalConfigurationFile;
    private Configuration generalConfiguration;

    public ConfigHandler() throws IOException {
        this.generalConfigurationFile = Paths.get(".", "config.json").toFile();
        if (!generalConfigurationFile.exists()) {
            final ConfigCreator creator = new ConfigCreator(Paths.get("."));
            this.generalConfigurationFile = creator.copyDefaultFile(Paths.get("config.json"));
        }
        this.reloadConfigurations();
    }

    public void reloadConfigurations() throws IOException {
        this.generalConfiguration = this.provider.load(this.generalConfigurationFile);
    }

    public void saveConfigurations() throws IOException {
        this.provider.save(this.generalConfiguration, this.generalConfigurationFile);
    }

    public Configuration getGeneralConfiguration() {
        return generalConfiguration;
    }
}
