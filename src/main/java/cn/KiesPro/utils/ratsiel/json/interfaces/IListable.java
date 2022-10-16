package cn.KiesPro.utils.ratsiel.json.interfaces;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import cn.KiesPro.utils.ratsiel.json.abstracts.JsonValue;

public interface IListable<T> {
    int size();

    void add(final T p0);

    void add(final int p0, final T p1);

    T get(final int p0, final Class<T> p1);

    T get(final int p0);

    void loop(final Consumer<JsonValue> p0);

    void loop(final BiConsumer<Integer, JsonValue> p0);
}
