package net.bukkitlabs.bukkitlabscloud.console;

import net.bukkitlabs.bukkitlabscloud.packets.ConfigurationLoadEvent;
import net.bukkitlabs.bukkitlabscloud.util.event.PacketCatch;
import net.bukkitlabs.bukkitlabscloud.console.utils.ConsoleColor;
import net.bukkitlabs.bukkitlabscloud.util.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: 17.05.23 Implement that older logs get comprimized (lastest.log)
public class Logger implements Listener {

    private final String logFolder = "logs";
    private final String logFileExtension = ".log";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private PrintWriter writer;

    public Logger() {
        createLogFolder();
        createLogFile();
    }

    @PacketCatch
    public void onConfigurationLoad(final ConfigurationLoadEvent packet) {
        try {
            timeFormat = new SimpleDateFormat(packet.getConfigHandler().getGeneralConfiguration().getString("logger.timeFormat"));
            dateFormat = new SimpleDateFormat(packet.getConfigHandler().getGeneralConfiguration().getString("logger.dateFormat"));
        } catch (IllegalArgumentException | NullPointerException exception) {
            log(Logger.Level.ERROR, "Invalid time/date format in configuration" + exception);
        }
    }

    @NotNull
    private String getDate() {
        final Date now = new Date();
        return dateFormat.format(now);
    }

    @NotNull
    private String getTime() {
        final Date now = new Date();
        return timeFormat.format(now);
    }

    private void writeLog(@NotNull final String logMessage) {
        writer.println(logMessage);
    }

    private void printLog(@NotNull final String consoleMessage) {
        System.out.println(consoleMessage);
    }

    public void close() {
        writer.close();
    }

    private void createLogFolder() {
        final File file = Paths.get(this.logFolder).toFile();
        if (!file.exists()) file.mkdirs();
    }

    private void createLogFile() {
        final String logFileName = getDate() + "-" + generateLogId() + logFileExtension;
        final File file = Paths.get(this.logFolder, logFileName).toFile();
        try {
            file.createNewFile();
            final FileWriter fileWriter = new FileWriter(file, true);
            writer = new PrintWriter(fileWriter, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private String generateLogId() {
        return Long.toString(System.nanoTime());
    }

    public void log(@NotNull final Level level, @NotNull final String message) {
        printLog(generateConsoleMessage(level, message));
        writeLog(generateLogMessage(level, message));
    }

    public void log(@NotNull final Level level, @NotNull final String message, @NotNull final Exception exception) {
        printLog(generateConsoleMessage(level, message, exception));
        writeLog(generateLogMessage(level, message, exception));
    }

    @NotNull
    private String generateConsoleMessage(@NotNull final Level level, @NotNull final String message) {
        final StringBuilder stringBuilder = new StringBuilder()
                .append(level.getConsoleColor().getColorCode())
                .append("[")
                .append(getTime())
                .append("]")
                .append(" ")
                .append(level.getPrefix())
                .append(" ")
                .append(message)
                .append(ConsoleColor.RESET.getColorCode());

        return stringBuilder.toString();
    }

    @NotNull
    private String generateConsoleMessage(@NotNull final Level level, @NotNull final String message, @NotNull final Exception exception) {
        final StringBuilder stringBuilder = new StringBuilder()
                .append(level.getConsoleColor().getColorCode())
                .append("[")
                .append(getTime())
                .append("]")
                .append(" ")
                .append(level.getPrefix())
                .append(" ")
                .append(message)
                .append(System.lineSeparator())
                .append(exception.toString())
                .append(System.lineSeparator());
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString()).append(System.lineSeparator());
        }
        stringBuilder.append(ConsoleColor.RESET.getColorCode());
        return stringBuilder.toString();
    }

    @NotNull
    private String generateLogMessage(@NotNull final Level level, @NotNull final String message) {
        final StringBuilder stringBuilder = new StringBuilder()
                .append("[")
                .append(getTime())
                .append("]")
                .append(" ")
                .append(level.getPrefix())
                .append(" ")
                .append(message);

        return stringBuilder.toString();
    }

    @NotNull
    private String generateLogMessage(@NotNull final Level level, @NotNull final String message, @NotNull final Exception exception) {
        final StringBuilder stringBuilder = new StringBuilder()
                .append("[")
                .append(getTime())
                .append("]")
                .append(" ")
                .append(level.getPrefix())
                .append(" ")
                .append(message)
                .append(System.lineSeparator())
                .append(exception.toString())
                .append(System.lineSeparator());
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString()).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public enum Level {
        INFO("[INFO]", ConsoleColor.WHITE),
        FINE("[FINE]", ConsoleColor.GREEN),
        ERROR("[ERROR]", ConsoleColor.RED),
        WARN("[WARN]", ConsoleColor.YELLOW);

        private final String prefix;
        private final ConsoleColor consoleColor;

        Level(@NotNull final String prefix, @NotNull final ConsoleColor consoleColor) {
            this.prefix = prefix;
            this.consoleColor = consoleColor;
        }

        @NotNull
        public String getPrefix() {
            return prefix;
        }

        @NotNull
        public ConsoleColor getConsoleColor() {
            return consoleColor;
        }
    }
}
