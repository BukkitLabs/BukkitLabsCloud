package net.bukkitlabs.bukkitlabscloud.http;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.EventListener;

public class JavaScriptHandler{
    private final ScriptEngine engine;
    private final Invocable invocable;

    public JavaScriptHandler() {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("js");
        invocable = (Invocable) engine;
    }

    public void loadScript(String script) throws ScriptException {
        engine.eval(script);
    }

    public void callFunction(String functionName, Object... args) throws ScriptException, NoSuchMethodException {
        invocable.invokeFunction(functionName, args);
    }

    public void registerEventListener(EventListener listener) {
        try{
            invocable.invokeFunction("listener", listener);
        }catch(ScriptException e){
            throw new RuntimeException(e);
        }catch(NoSuchMethodException e){
            throw new RuntimeException(e);
        }
        engine.put("listener", listener);
    }
}

