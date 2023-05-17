package net.bukkitlabs.bukkitlabscloud.util.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigCreatorTest {

    @TempDir
    public Path temporaryFolder;
    private ConfigCreator creator;

    @BeforeEach
    void start() {
        this.creator = new ConfigCreator(temporaryFolder);
    }

    @Test
    void loadEmptyYamlConfig() throws IOException {
        final File file = this.creator.createFile(Paths.get("config.yml"));
        assertTrue(file.exists());
        assertEquals(0L, file.length());
    }

    @Test
    void shouldLoadYamlConfiguration() throws IOException {
        final File file = this.creator.copyDefaultFile(Paths.get("config.yml"), Files.createTempFile("temp", "suff").toAbsolutePath());
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

}
