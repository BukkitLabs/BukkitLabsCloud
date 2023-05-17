package net.bukkitlabs.bukkitlabscloud.util.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlConfiguration extends ConfigurationProvider {

    @NotNull
    private final ThreadLocal<Yaml> yaml = ThreadLocal.withInitial(() -> {
        final Representer representer = new Representer() {{
            representers.put(Configuration.class, data -> represent(((Configuration) data).self));
        }};

        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        return new Yaml(new Constructor(), representer, options);
    });

    @Override
    public void save(@NotNull final Configuration config, final @NotNull File file) throws IOException {
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            save(config, writer);
        }
    }

    @Override
    public void save(@NotNull final Configuration config, @NotNull final Writer writer) {
        yaml.get().dump(config.self, writer);
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
        Map<String, Object> map = yaml.get().loadAs(reader, LinkedHashMap.class);
        if (map == null) map = new LinkedHashMap<>();
        return new Configuration(map, defaults);
    }

    @Override
    @NotNull
    public Configuration load(@NotNull final InputStream is) {
        return load(is, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    @NotNull
    public Configuration load(@NotNull final InputStream is, @Nullable final Configuration defaults) {
        Map<String, Object> map = yaml.get().loadAs(is, LinkedHashMap.class);
        if (map == null) map = new LinkedHashMap<>();
        return new Configuration(map, defaults);
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
        Map<String, Object> map = yaml.get().loadAs(string, LinkedHashMap.class);
        if (map == null) map = new LinkedHashMap<>();
        return new Configuration(map, defaults);
    }
}
