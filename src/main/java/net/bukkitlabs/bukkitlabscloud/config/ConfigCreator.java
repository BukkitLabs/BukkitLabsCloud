package net.bukkitlabs.bukkitlabscloud.config;

import com.google.common.io.ByteStreams;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigCreator {

    private final Path dataFolder;

    public ConfigCreator(@NotNull Path dataFolder) {
        this.dataFolder = dataFolder;
    }

    @NotNull
    public File copyDefaultFile(@NotNull Path fromPath) throws IOException {
        return copyDefaultFile(fromPath, fromPath);
    }

    @NotNull
    public File copyDefaultFile(@NotNull Path fromPath, @NotNull Path toPath) throws IOException {
        final File file = createFile(toPath);

        if (file.length() <= 2) {
            try (final InputStream in = this.getClass().getClassLoader().getResourceAsStream(fromPath.toString());
                 final FileOutputStream out = new FileOutputStream(file)) {
                if (in != null) ByteStreams.copy(in, out);
            }
        }

        return file;
    }

    @NotNull
    public File createFile(@NotNull Path filePath) throws IOException {
        final Path configFile = dataFolder.resolve(filePath);

        Files.createDirectories(configFile.getParent());

        if (!configFile.toFile().exists()) Files.createFile(configFile);

        return configFile.toFile();
    }

}

