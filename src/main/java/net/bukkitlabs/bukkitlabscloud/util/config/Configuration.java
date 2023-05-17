package net.bukkitlabs.bukkitlabscloud.util.config;

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

    public Configuration(@Nullable final Configuration defaults) {
        this(new LinkedHashMap<>(), defaults);
    }

    Configuration(@NotNull final Map<?, ?> map, @Nullable final Configuration defaults) {
        this.self = new LinkedHashMap<>();
        this.defaults = defaults;

        map.forEach((key, value) -> {
            final String actualKey = (key == null) ? "null" : key.toString();

            if (!(value instanceof Map valueMap)) {
                this.self.put(actualKey, value);
                return;
            }
            this.self.put(actualKey, new Configuration(valueMap, (defaults == null) ? null : defaults.getSection(actualKey)));
        });
    }

    @NotNull
    private Configuration getSectionFor(@NotNull final String path) {
        int index = path.indexOf(SEPARATOR);
        if (index == -1) return this;

        final String root = path.substring(0, index);
        Object section = self.get(root);
        if (section == null) {
            section = new Configuration((defaults == null) ? null : defaults.getSection(root));
            self.put(root, section);
        }

        return (Configuration) section;
    }

    @NotNull
    private String getChild(@NotNull final String path) {
        int index = path.indexOf(SEPARATOR);
        return (index == -1) ? path : path.substring(index + 1);
    }

    /*------------------------------------------------------------------------*/
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T get(@NotNull final String path, @Nullable final T def) {
        final Configuration section = getSectionFor(path);
        Object val;
        if (section == this) val = self.get(path);
        else val = section.get(getChild(path), def);

        if (val == null && def instanceof Configuration) self.put(path, def);

        return (val != null) ? (T) val : def;
    }

    public boolean contains(@NotNull final String path) {
        return get(path, null) != null;
    }

    @Nullable
    public Object get(@NotNull final String path) {
        return get(path, getDefault(path));
    }

    @Nullable
    public Object getDefault(@NotNull final String path) {
        return (defaults == null) ? null : defaults.get(path);
    }

    public void set(@NotNull final String path, @Nullable Object value) {
        if (value instanceof Map map)
            value = new Configuration(map, (defaults == null) ? null : defaults.getSection(path));

        final Configuration section = getSectionFor(path);
        if (section == this) {
            if (value == null) self.remove(path);
            else self.put(path, value);
        } else section.set(getChild(path), value);
    }

    /*------------------------------------------------------------------------*/
    @Nullable
    public Configuration getSection(@NotNull final String path) {
        final Object def = getDefault(path);
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
    public byte getByte(@NotNull final String path) {
        final Object def = getDefault(path);
        return getByte(path, (def instanceof final Number number) ? number.byteValue() : (byte) 0);
    }

    public byte getByte(@NotNull final String path, byte def) {
        final Object val = get(path, def);
        return (val instanceof final Number number) ? number.byteValue() : def;
    }

    @NotNull
    public List<Byte> getByteList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Byte> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Number number)
                result.add(number.byteValue());

        return result;
    }

    public short getShort(@NotNull final String path) {
        final Object def = getDefault(path);
        return getShort(path, (def instanceof final Number number) ? number.shortValue() : (short) 0);
    }

    public short getShort(@NotNull final String path, short def) {
        final Object val = get(path, def);
        return (val instanceof final Number number) ? number.shortValue() : def;
    }

    @NotNull
    public List<Short> getShortList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Short> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Number number)
                result.add(number.shortValue());

        return result;
    }

    public int getInt(@NotNull final String path) {
        final Object def = getDefault(path);
        return getInt(path, (def instanceof final Number number) ? number.intValue() : 0);
    }

    public int getInt(@NotNull final String path, int def) {
        final Object val = get(path, def);
        return (val instanceof final Number number) ? number.intValue() : def;
    }

    @NotNull
    public List<Integer> getIntList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Integer> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Number number)
                result.add(number.intValue());

        return result;
    }

    public long getLong(@NotNull final String path) {
        final Object def = getDefault(path);
        return getLong(path, (def instanceof final Number number) ? number.longValue() : 0);
    }

    public long getLong(@NotNull final String path, long def) {
        final Object val = get(path, def);
        return (val instanceof final Number number) ? number.longValue() : def;
    }

    @NotNull
    public List<Long> getLongList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Long> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Number number)
                result.add(number.longValue());

        return result;
    }

    public float getFloat(@NotNull final String path) {
        final Object def = getDefault(path);
        return getFloat(path, (def instanceof final Number number) ? number.floatValue() : 0);
    }

    public float getFloat(@NotNull final String path, final float def) {
        final Object val = get(path, def);
        return (val instanceof final Number number) ? number.floatValue() : def;
    }

    @NotNull
    public List<Float> getFloatList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Float> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Number number)
                result.add(number.floatValue());

        return result;
    }

    public double getDouble(@NotNull final String path) {
        final Object def = getDefault(path);
        return getDouble(path, (def instanceof final Number number) ? number.doubleValue() : 0);
    }

    public double getDouble(@NotNull final String path, final double def) {
        final Object val = get(path, def);
        return (val instanceof final Number number) ? number.doubleValue() : def;
    }

    @NotNull
    public List<Double> getDoubleList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Double> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Number number)
                result.add(number.doubleValue());

        return result;
    }

    public boolean getBoolean(@NotNull final String path) {
        final Object def = getDefault(path);
        return getBoolean(path, (def instanceof final Boolean bool) && bool);
    }

    public boolean getBoolean(@NotNull final String path, final boolean def) {
        final Object val = get(path, def);
        return (val instanceof final Boolean bool) ? bool : def;
    }

    @NotNull
    public List<Boolean> getBooleanList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Boolean> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Boolean bool)
                result.add(bool);

        return result;
    }

    public char getChar(@NotNull final String path) {
        final Object def = getDefault(path);
        return getChar(path, (def instanceof final Character character) ? character : '\u0000');
    }

    public char getChar(@NotNull final String path, final char def) {
        final Object val = get(path, def);
        return (val instanceof final Character character) ? character : def;
    }

    @NotNull
    public List<Character> getCharList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<Character> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final Character character)
                result.add(character);

        return result;
    }

    @NotNull
    public String getString(@NotNull final String path) {
        final Object def = getDefault(path);
        return Objects.requireNonNull(getString(path, (def instanceof final String string) ? string : ""));
    }

    @Nullable
    public String getString(@NotNull final String path, @Nullable final String def) {
        final Object val = get(path, def);
        return (val instanceof final String string) ? string : def;
    }

    @NotNull
    public List<String> getStringList(@NotNull final String path) {
        final List<?> list = getList(path);
        final List<String> result = new ArrayList<>();

        for (final Object object : list)
            if (object instanceof final String string)
                result.add(string);

        return result;
    }

    /*------------------------------------------------------------------------*/
    public List<?> getList(@NotNull final String path) {
        final Object def = getDefault(path);
        return getList(path, (def instanceof List<?>) ? (List<?>) def : Collections.emptyList());
    }

    public List<?> getList(@NotNull final String path, @NotNull final List<?> def) {
        final List<?> val = get(path, def);
        return (val != null) ? val : def;
    }
}
