package net.bukkitlabs.bukkitlabscloud.config;

import com.google.common.io.ByteStreams;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigCreator {

    private static final Logger log = Logger.getLogger("ConfigCreator");

    private final Path dataFolder;

    /**
     * Creates a new ConfigCreator with internat config directory on archive's root
     *
     * @param dataFolder your plugin's dataFolder
     */
    public ConfigCreator(@NotNull Path dataFolder) {
        this.dataFolder = dataFolder;
    }

    /**
     * Copies a file and its content from the Java archive to the specified toPath location in the plugin's folder.
     * If the specified file cannot be found, a FileNotFoundException is thrown.
     *
     * @param fromPath the subPath of the config you want to load from
     * @return the file with the content inside the dataFolder
     * @throws IOException              if your disk is not writable or any path could not be found
     * @throws IllegalArgumentException if any argument is null
     */
    @NotNull
    public File copyDefaultFile(@NotNull Path fromPath) throws IOException {
        return copyDefaultFile(fromPath, fromPath);
    }

    /**
     * Copies a file and its content from the Java archive to the specified toPath location in the plugin's folder.
     * If the specified file cannot be found, a FileNotFoundException is thrown.
     *
     * @param fromPath the subPath of the config you want to load from
     * @param toPath   the subPath where the file should be copied
     * @return the file with the content inside the dataFolder
     * @throws IOException              if your disk is not writable or any path could not be found
     * @throws IllegalArgumentException if any argument is null
     */
    @NotNull
    public File copyDefaultFile(@NotNull Path fromPath, @NotNull Path toPath) throws IOException {
        // create new file if not exists
        File file = createFile(toPath);

        // try to get FileStream (Linux creates empty files with 2 bytes whitespace, so do override content if file is "empty")
        if (file.length() <= 2) {
            try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(fromPath.toString());
                 FileOutputStream out = new FileOutputStream(file)) {
                // check if file inside jar was found
                if (in != null) {
                    ByteStreams.copy(in, out);
                } else
                    log.log(Level.WARNING, String.format("Unable to find %s inside of Plugin-Archive", fromPath.getFileName()));
            }
        }

        return file;
    }

    /**
     * Creates a new file inside the plugin's dataFolder.
     *
     * @param filePath the subPath of the new file. Starting from the plugin's dataFolder. Cannot be null
     * @return the created file
     * @throws IOException              if the disk is not writable
     * @throws IllegalArgumentException if the parameter is null
     */
    @NotNull
    public File createFile(@NotNull Path filePath) throws IOException {
        Path configFile = dataFolder.resolve(filePath);

        // Create dataFolder
        Files.createDirectories(configFile.getParent());

        if (!configFile.toFile().exists()) {
            Files.createFile(configFile);

            log.log(Level.FINE, "Created new file {0} in datafolder", configFile.getFileName());
        }

        return configFile.toFile();
    }

}

