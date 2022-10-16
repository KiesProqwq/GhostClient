package cn.KiesPro.utils.ratsiel.auth.abstracts;

import cn.KiesPro.utils.ratsiel.json.Json;

public abstract class Authenticator<T> {
    protected final Json json;

    public Authenticator() {
        this.json = new Json();
    }

    public abstract T login(final String p0, final String p1);
}
