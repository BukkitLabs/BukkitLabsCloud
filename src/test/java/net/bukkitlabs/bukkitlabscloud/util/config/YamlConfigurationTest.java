package net.bukkitlabs.bukkitlabscloud.util.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class YamlConfigurationTest {

    @TempDir
    public Path temporaryFolder;
    private ConfigurationProvider configurationProvider;

    @BeforeEach
    void start() {
        this.configurationProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    }

    @Test
    void configurationProviderShouldExists() {
        assertNotNull(this.configurationProvider);
    }

    @Test
    void loadYamlConfigurationValues() throws IOException {
        final ConfigCreator creator = new ConfigCreator(temporaryFolder);
        final File file = creator.copyDefaultFile(Paths.get("config.yml"), Files.createTempFile("temp", "suff").toAbsolutePath());
        final Configuration configuration = this.configurationProvider.load(file);
        assertEquals((byte) 2, configuration.getByte("byte"));
        assertEquals((short) 46, configuration.getShort("short"));
        assertEquals(13243463, configuration.getInt("integer"));
        assertFalse(configuration.getBoolean("boolean"));
        assertEquals(123.4643F, configuration.getFloat("float"));
        assertEquals(123434.466544356465432344D, configuration.getDouble("double"));
        assertEquals("Hello World", configuration.getString("string"));
        assertFalse(configuration.getIntList("lists.ints").isEmpty());
        assertFalse(configuration.getFloatList("lists.floats").isEmpty());
    }
}
