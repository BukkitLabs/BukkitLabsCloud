package net.bukkitlabs.bukkitlabscloud.util.logger;

import net.bukkitlabs.bukkitlabscloud.events.ConfigurationLoadEvent;
import net.bukkitlabs.bukkitlabscloud.util.config.JsonConfiguration;
import net.bukkitlabs.bukkitlabscloud.util.event.Callable;
import net.bukkitlabs.bukkitlabscloud.util.event.Listener;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

public class Logger implements Listener{

    private final String logFolder="/logs/";
    private final String logFileExtension=".log";
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat=new SimpleDateFormat("HH-mm-ss");
    private PrintWriter writer;

    public Logger(){
        createLogFolder();
        createLogFile();
    }

    @Callable
    public void onConfigurationLoad(final ConfigurationLoadEvent event){
        log(Level.INFO, "Loading time/date format from configuration.");
        try{
            timeFormat=new SimpleDateFormat(event.getConfigHandler().getGeneralConfiguration().getString("logger.timeFormat"));
            dateFormat=new SimpleDateFormat(event.getConfigHandler().getGeneralConfiguration().getString("logger.dateFormat"));
        }catch(IllegalArgumentException|NullPointerException exception){
            log(Logger.Level.ERROR,"Invalid time/date format in configuration"+exception);
        }
    }

    private String getDate(){
        Date now=new Date();
        return dateFormat.format(now);
    }

    private String getTime(){
        Date now=new Date();
        return timeFormat.format(now);
    }

    private void writeLog(@NotNull final String logMessage){
        writer.println(logMessage);
    }

    private void printLog(@NotNull final String consoleMessage){
        System.out.println(consoleMessage);
    }

    public void close(){
        writer.close();
    }

    private void createLogFolder(){
        File file=new File(this.logFolder);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    private void createLogFile(){
        final String logFileName=getDate()+"-"+generateLogId()+logFileExtension;
        final File file=new File(logFolder+logFileName);
        try{
            file.createNewFile();
            FileWriter fileWriter=new FileWriter(logFolder+logFileName,true);
            writer=new PrintWriter(fileWriter,true);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String generateLogId(){
        return Long.toString(System.nanoTime());
    }

    public void log(@NotNull final Level level,@NotNull final String message){
        printLog(generateConsoleMessage(level,message));
        writeLog(generateLogMessage(level,message));
    }

    public void log(@NotNull final Level level,@NotNull final String message,@NotNull final Exception exception){
        printLog(generateConsoleMessage(level,message,exception));
        writeLog(generateLogMessage(level,message,exception));
    }

    private String generateConsoleMessage(@NotNull final Level level,@NotNull final String message){
        final StringBuilder stringBuilder=new StringBuilder()
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

    private String generateConsoleMessage(@NotNull final Level level,@NotNull final String message,@NotNull final Exception exception){
        final StringBuilder stringBuilder=new StringBuilder()
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
        for(StackTraceElement stackTraceElement: exception.getStackTrace()){
            stringBuilder.append(stackTraceElement.toString()).append(System.lineSeparator());
        }
        stringBuilder.append(ConsoleColor.RESET.getColorCode());
        return stringBuilder.toString();
    }

    private String generateLogMessage(@NotNull final Level level,@NotNull final String message){
        final StringBuilder stringBuilder=new StringBuilder()
                .append("[")
                .append(getTime())
                .append("]")
                .append(" ")
                .append(level.getPrefix())
                .append(" ")
                .append(message);

        return stringBuilder.toString();
    }

    private String generateLogMessage(@NotNull final Level level,@NotNull final String message,@NotNull final Exception exception){
        final StringBuilder stringBuilder=new StringBuilder()
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
        for(StackTraceElement stackTraceElement: exception.getStackTrace()){
            stringBuilder.append(stackTraceElement.toString()).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public enum Level{
        INFO("[INFO]",ConsoleColor.WHITE),
        FINE("[FINE]",ConsoleColor.GREEN),
        ERROR("[ERROR]",ConsoleColor.RED),
        WARN("[WARN]",ConsoleColor.YELLOW);

        private final String prefix;
        private final ConsoleColor consoleColor;

        Level(@NotNull final String prefix,@NotNull final ConsoleColor consoleColor){
            this.prefix=prefix;
            this.consoleColor=consoleColor;
        }

        public String getPrefix(){
            return prefix;
        }

        public ConsoleColor getConsoleColor(){
            return consoleColor;
        }
    }


}
