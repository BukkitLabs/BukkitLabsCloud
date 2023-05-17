package net.bukkitlabs.bukkitlabscloud.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultConfigurationTest {

    @Test
    void testDefaultValues() {
        final Configuration defaultConfig = new Configuration();
        defaultConfig.set("setting", 10);
        defaultConfig.set("nested.setting", 11);
        defaultConfig.set("double.nested.setting", 12);

        final Configuration actualConfig = new Configuration(defaultConfig);

        assertEquals(10, actualConfig.getInt("setting"));
        assertEquals(11, actualConfig.getInt("nested.setting"));
        assertEquals(12, actualConfig.getInt("double.nested.setting"));
    }
}
