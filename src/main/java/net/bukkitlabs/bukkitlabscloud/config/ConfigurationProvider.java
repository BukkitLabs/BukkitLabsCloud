package net.bukkitlabs.bukkitlabscloud.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ConfigurationProvider {

    private static final Map<Class<? extends ConfigurationProvider>, ConfigurationProvider> providers = new HashMap<>();

    static {
        try {
            providers.put(YamlConfiguration.class, new YamlConfiguration());
        } catch (NoClassDefFoundError ex) {
            // Ignore, no SnakeYAML
        }

        try {
            providers.put(JsonConfiguration.class, new JsonConfiguration());
        } catch (NoClassDefFoundError ex) {
            // Ignore, no Gson
        }
    }

    @Nullable
    public static ConfigurationProvider getProvider(Class<? extends ConfigurationProvider> provider) {
        return providers.getOrDefault(provider, null);
    }

    public static void registerCustomConfiguration(@NotNull Class<? extends ConfigurationProvider> providerClass, @NotNull ConfigurationProvider provider) {
        providers.put(providerClass, provider);
    }

    /*------------------------------------------------------------------------*/
    public abstract void save(@NotNull Configuration config, @NotNull File file) throws IOException;

    public abstract void save(@NotNull Configuration config, @NotNull Writer writer);

    @NotNull
    public abstract Configuration load(@NotNull File file) throws IOException;

    @NotNull
    public abstract Configuration load(@NotNull File file, @Nullable Configuration defaults) throws IOException;

    @NotNull
    public abstract Configuration load(@NotNull Reader reader);

    @NotNull
    public abstract Configuration load(@NotNull Reader reader, @Nullable Configuration defaults);

    @NotNull
    public abstract Configuration load(@NotNull InputStream is);

    @NotNull
    public abstract Configuration load(@NotNull InputStream is, @Nullable Configuration defaults);

    @NotNull
    public abstract Configuration load(@NotNull String string);

    @NotNull
    public abstract Configuration load(@NotNull String string, @Nullable Configuration defaults);
}
