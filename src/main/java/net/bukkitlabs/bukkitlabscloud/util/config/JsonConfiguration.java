package net.bukkitlabs.bukkitlabscloud.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonConfiguration extends ConfigurationProvider {

    private final Gson json = new GsonBuilder().serializeNulls().setPrettyPrinting().registerTypeAdapter(Configuration.class,
            (JsonSerializer<Configuration>) (src, typeOfSrc, context) -> context.serialize(src.self)).create();

    @Override
    public void save(@NotNull final Configuration config, @NotNull final File file) throws IOException {
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            save(config, writer);
        }
    }

    @Override
    public void save(@NotNull final Configuration config, @NotNull final Writer writer) {
        json.toJson(config.self, writer);
    }

    @Override
    @NotNull
    public Configuration load(@NotNull final File file) throws IOException {
        return load(file, null);
    }

    @Override
    @NotNull
    public Configuration load(@NotNull final File file, @Nullable final Configuration defaults) throws IOException {
        try (final FileInputStream is = new FileInputStream(file)) {
            return load(is, defaults);
        }
    }

    @Override
    @NotNull
    public Configuration load(@NotNull final Reader reader) {
        return load(reader, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    @NotNull
    public Configuration load(@NotNull final Reader reader, @Nullable final Configuration defaults) {
        Map<String, Object> map = json.fromJson(reader, LinkedHashMap.class);
        if (map == null) map = new LinkedHashMap<>();
        return new Configuration(map, defaults);
    }

    @Override
    @NotNull
    public Configuration load(@NotNull final InputStream is) {
        return load(is, null);
    }

    @Override
    @NotNull
    public Configuration load(@NotNull final InputStream is, @Nullable final Configuration defaults) {
        return load(new InputStreamReader(is, StandardCharsets.UTF_8), defaults);
    }

    @Override
    @NotNull
    public Configuration load(@NotNull final String string) {
        return load(string, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    @NotNull
    public Configuration load(@NotNull final String string, @Nullable final Configuration defaults) {
        Map<String, Object> map = json.fromJson(string, LinkedHashMap.class);
        if (map == null) map = new LinkedHashMap<>();
        return new Configuration(map, defaults);
    }
}
