package cn.KiesPro.utils.ratsiel.auth.abstracts;

public abstract class TextureVariable {
    private String id;
    private String state;
    private String url;
    private String alias;

    public TextureVariable() {
    }

    public TextureVariable(final String id, final String state, final String url, final String alias) {
        this.id = id;
        this.state = state;
        this.url = url;
        this.alias = alias;
    }

    public String getId() {
        return this.id;
    }

    public String getState() {
        return this.state;
    }

    public String getUrl() {
        return this.url;
    }

    public String getAlias() {
        return this.alias;
    }

    @Override
    public String toString() {
        return "TextureVariable{id='" + this.id + '\'' + ", state='" + this.state + '\'' + ", url='" + this.url + '\'' + ", alias='" + this.alias + '\'' + '}';
    }
}
