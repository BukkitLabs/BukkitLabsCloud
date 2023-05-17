package net.bukkitlabs.bukkitlabscloud.util.config;

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
    public static ConfigurationProvider getProvider(@NotNull final Class<? extends ConfigurationProvider> provider) {
        return providers.getOrDefault(provider, null);
    }

    public static void registerCustomConfiguration(@NotNull final Class<? extends ConfigurationProvider> providerClass, @NotNull final ConfigurationProvider provider) {
        providers.put(providerClass, provider);
    }

    /*------------------------------------------------------------------------*/
    public abstract void save(@NotNull final Configuration config, @NotNull final File file) throws IOException;

    public abstract void save(@NotNull final Configuration config, @NotNull final Writer writer);

    @NotNull
    public abstract Configuration load(@NotNull final File file) throws IOException;

    @NotNull
    public abstract Configuration load(@NotNull final File file, @Nullable final Configuration defaults) throws IOException;

    @NotNull
    public abstract Configuration load(@NotNull final Reader reader);

    @NotNull
    public abstract Configuration load(@NotNull final Reader reader, @Nullable final Configuration defaults);

    @NotNull
    public abstract Configuration load(@NotNull final InputStream is);

    @NotNull
    public abstract Configuration load(@NotNull final InputStream is, @Nullable final Configuration defaults);

    @NotNull
    public abstract Configuration load(@NotNull final String string);

    @NotNull
    public abstract Configuration load(@NotNull final String string, @Nullable final Configuration defaults);
}
