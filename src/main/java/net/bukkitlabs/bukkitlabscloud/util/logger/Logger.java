package net.bukkitlabs.bukkitlabscloud.util.logger;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private final String logFolder = "/logs/";
    private final String logFileExtension = ".log";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // TODO: 17.05.2023 Set dateFormat with ConfigurationLoadEvent
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss"); // TODO: 17.05.2023 Set timeFormat with ConfigurationLoadEvent
    private PrintWriter writer;

    public Logger() {
        createLogFolder();
        createLogFile();
    }

    private String getDate() {
        Date now = new Date();
        return dateFormat.format(now);
    }

    private String getTime() {
        Date now = new Date();
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
        File file = new File(this.logFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void createLogFile() {
        String logFileName = getDate() + "-" + generateLogId() + logFileExtension;
        try {
            FileWriter fileWriter = new FileWriter(logFolder + logFileName, true);
            writer = new PrintWriter(fileWriter, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        public String getPrefix() {
            return prefix;
        }

        public ConsoleColor getConsoleColor() {
            return consoleColor;
        }
    }


}
