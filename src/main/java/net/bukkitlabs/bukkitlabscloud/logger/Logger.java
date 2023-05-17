package net.bukkitlabs.bukkitlabscloud.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger{

    private final String logFolder="/logs/";
    private final String logFileExtension=".log";
    private final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat=new SimpleDateFormat("HH-mm-ss");
    private PrintWriter writer;

    public Logger(){
        createLogFolder();
        createLogFile();
    }

    private String getDate(){
        Date now=new Date();
        return dateFormat.format(now);
    }

    private String getTime(){
        Date now=new Date();
        return timeFormat.format(now);
    }

    private void writeLog(final String logMessage){
        writer.println(logMessage);
        System.out.println(logMessage);
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
        String logFileName=getDate()+"-"+generateLogId()+logFileExtension;
        try{
            FileWriter fileWriter=new FileWriter(logFolder+logFileName,true);
            writer=new PrintWriter(fileWriter,true);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String generateLogId(){
        return Long.toString(System.nanoTime());
    }

    public void log(final Level level,final String message){
        writeLog(generateLogMessage(level,message));
    }

    public void log(final Level level,final String message,final Exception exception){
        writeLog(generateLogMessage(level,message,exception));
    }

    private String generateLogMessage(final Level level,final String message){
        final StringBuilder stringBuilder=new StringBuilder()
                .append(level.getConsoleColor().getColorCode())
                .append("["+getTime()+"]")
                .append(" ")
                .append(level.getPrefix())
                .append(" ")
                .append(message)
                .append(ConsoleColor.RESET.getColorCode());

        return stringBuilder.toString();
    }

    private String generateLogMessage(final Level level,final String message,final Exception exception){
        final StringBuilder stringBuilder=new StringBuilder()
                .append(level.getConsoleColor().getColorCode())
                .append("["+getTime()+"]")
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


    public enum Level{
        INFO("[INFO]",ConsoleColor.WHITE),
        FINE("[FINE]",ConsoleColor.GREEN),
        ERROR("[ERROR]",ConsoleColor.RED),
        WARN("[WARN]",ConsoleColor.YELLOW);

        Level(final String prefix,final ConsoleColor consoleColor){
            this.prefix=prefix;
            this.consoleColor=consoleColor;
        }

        private final String prefix;
        private final ConsoleColor consoleColor;

        public String getPrefix(){
            return prefix;
        }

        public ConsoleColor getConsoleColor(){
            return consoleColor;
        }
    }


}
