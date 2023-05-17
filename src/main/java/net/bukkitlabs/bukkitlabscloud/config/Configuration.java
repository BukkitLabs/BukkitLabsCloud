package net.bukkitlabs.bukkitlabscloud.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class Configuration {

    private static final char SEPARATOR = '.';
    final Map<String, Object> self;
    private final Configuration defaults;

    public Configuration() {
        this(null);
    }

    public Configuration(@Nullable Configuration defaults) {
        this(new LinkedHashMap<>(), defaults);
    }

    Configuration(@NotNull Map<?, ?> map, @Nullable Configuration defaults) {
        this.self = new LinkedHashMap<>();
        this.defaults = defaults;

        map.forEach((key, value) -> {
            String actualKey = (key == null) ? "null" : key.toString();

            if (!(value instanceof Map valueMap)) {
                this.self.put(actualKey, value);
                return;
            }
            this.self.put(actualKey, new Configuration(valueMap, (defaults == null) ? null : defaults.getSection(actualKey)));
        });
    }

    @NotNull
    private Configuration getSectionFor(@NotNull String path) {
        int index = path.indexOf(SEPARATOR);
        if (index == -1) return this;

        String root = path.substring(0, index);
        Object section = self.get(root);
        if (section == null) {
            section = new Configuration((defaults == null) ? null : defaults.getSection(root));
            self.put(root, section);
        }

        return (Configuration) section;
    }

    @NotNull
    private String getChild(@NotNull String path) {
        int index = path.indexOf(SEPARATOR);
        return (index == -1) ? path : path.substring(index + 1);
    }

    /*------------------------------------------------------------------------*/
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T get(@NotNull String path, @Nullable T def) {
        Configuration section = getSectionFor(path);
        Object val;
        if (section == this) val = self.get(path);
        else val = section.get(getChild(path), def);

        if (val == null && def instanceof Configuration) self.put(path, def);

        return (val != null) ? (T) val : def;
    }

    public boolean contains(@NotNull String path) {
        return get(path, null) != null;
    }

    @Nullable
    public Object get(@NotNull String path) {
        return get(path, getDefault(path));
    }

    @Nullable
    public Object getDefault(@NotNull String path) {
        return (defaults == null) ? null : defaults.get(path);
    }

    public void set(@NotNull String path, @Nullable Object value) {
        if (value instanceof Map map)
            value = new Configuration(map, (defaults == null) ? null : defaults.getSection(path));

        Configuration section = getSectionFor(path);
        if (section == this) {
            if (value == null) self.remove(path);
            else self.put(path, value);
        } else section.set(getChild(path), value);
    }

    /*------------------------------------------------------------------------*/
    @Nullable
    public Configuration getSection(@NotNull String path) {
        Object def = getDefault(path);
        return (Configuration) get(path, (def instanceof Configuration) ? def : new Configuration((defaults == null) ? null : defaults.getSection(path)));
    }

    /**
     * Gets keys, not deep by default.
     *
     * @return top level keys for this section
     */
    @NotNull
    public Collection<String> getKeys() {
        return new LinkedHashSet<>(self.keySet());
    }

    /*------------------------------------------------------------------------*/
    public byte getByte(@NotNull String path) {
        Object def = getDefault(path);
        return getByte(path, (def instanceof Number number) ? number.byteValue() : (byte) 0);
    }

    public byte getByte(@NotNull String path, byte def) {
        Object val = get(path, def);
        return (val instanceof Number number) ? number.byteValue() : def;
    }

    @NotNull
    public List<Byte> getByteList(@NotNull String path) {
        List<?> list = getList(path);
        List<Byte> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Number number)
                result.add(number.byteValue());

        return result;
    }

    public short getShort(@NotNull String path) {
        Object def = getDefault(path);
        return getShort(path, (def instanceof Number number) ? number.shortValue() : (short) 0);
    }

    public short getShort(@NotNull String path, short def) {
        Object val = get(path, def);
        return (val instanceof Number number) ? number.shortValue() : def;
    }

    @NotNull
    public List<Short> getShortList(@NotNull String path) {
        List<?> list = getList(path);
        List<Short> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Number number)
                result.add(number.shortValue());

        return result;
    }

    public int getInt(@NotNull String path) {
        Object def = getDefault(path);
        return getInt(path, (def instanceof Number number) ? number.intValue() : 0);
    }

    public int getInt(@NotNull String path, int def) {
        Object val = get(path, def);
        return (val instanceof Number number) ? number.intValue() : def;
    }

    @NotNull
    public List<Integer> getIntList(@NotNull String path) {
        List<?> list = getList(path);
        List<Integer> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Number number)
                result.add(number.intValue());

        return result;
    }

    public long getLong(@NotNull String path) {
        Object def = getDefault(path);
        return getLong(path, (def instanceof Number number) ? number.longValue() : 0);
    }

    public long getLong(@NotNull String path, long def) {
        Object val = get(path, def);
        return (val instanceof Number number) ? number.longValue() : def;
    }

    @NotNull
    public List<Long> getLongList(@NotNull String path) {
        List<?> list = getList(path);
        List<Long> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Number number)
                result.add(number.longValue());

        return result;
    }

    public float getFloat(@NotNull String path) {
        Object def = getDefault(path);
        return getFloat(path, (def instanceof Number number) ? number.floatValue() : 0);
    }

    public float getFloat(@NotNull String path, float def) {
        Object val = get(path, def);
        return (val instanceof Number number) ? number.floatValue() : def;
    }

    @NotNull
    public List<Float> getFloatList(@NotNull String path) {
        List<?> list = getList(path);
        List<Float> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Number number)
                result.add(number.floatValue());

        return result;
    }

    public double getDouble(@NotNull String path) {
        Object def = getDefault(path);
        return getDouble(path, (def instanceof Number number) ? number.doubleValue() : 0);
    }

    public double getDouble(@NotNull String path, double def) {
        Object val = get(path, def);
        return (val instanceof Number number) ? number.doubleValue() : def;
    }

    @NotNull
    public List<Double> getDoubleList(@NotNull String path) {
        List<?> list = getList(path);
        List<Double> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Number number)
                result.add(number.doubleValue());

        return result;
    }

    public boolean getBoolean(@NotNull String path) {
        Object def = getDefault(path);
        return getBoolean(path, (def instanceof Boolean bool) && bool);
    }

    public boolean getBoolean(@NotNull String path, boolean def) {
        Object val = get(path, def);
        return (val instanceof Boolean bool) ? bool : def;
    }

    @NotNull
    public List<Boolean> getBooleanList(@NotNull String path) {
        List<?> list = getList(path);
        List<Boolean> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Boolean bool)
                result.add(bool);

        return result;
    }

    public char getChar(@NotNull String path) {
        Object def = getDefault(path);
        return getChar(path, (def instanceof Character character) ? character : '\u0000');
    }

    public char getChar(@NotNull String path, char def) {
        Object val = get(path, def);
        return (val instanceof Character character) ? character : def;
    }

    @NotNull
    public List<Character> getCharList(@NotNull String path) {
        List<?> list = getList(path);
        List<Character> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof Character character)
                result.add(character);

        return result;
    }

    @NotNull
    public String getString(@NotNull String path) {
        Object def = getDefault(path);
        return Objects.requireNonNull(getString(path, (def instanceof String string) ? string : ""));
    }

    @Nullable
    public String getString(@NotNull String path, @Nullable String def) {
        Object val = get(path, def);
        return (val instanceof String string) ? string : def;
    }

    @NotNull
    public List<String> getStringList(@NotNull String path) {
        List<?> list = getList(path);
        List<String> result = new ArrayList<>();

        for (Object object : list)
            if (object instanceof String string)
                result.add(string);

        return result;
    }

    /*------------------------------------------------------------------------*/
    public List<?> getList(@NotNull String path) {
        Object def = getDefault(path);
        return getList(path, (def instanceof List<?>) ? (List<?>) def : Collections.emptyList());
    }

    public List<?> getList(@NotNull String path, @NotNull List<?> def) {
        List<?> val = get(path, def);
        return (val != null) ? val : def;
    }
}
