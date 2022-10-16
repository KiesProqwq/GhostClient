package cn.KiesPro.utils.ratsiel.auth.model.microsoft;

import cn.KiesPro.utils.ratsiel.auth.abstracts.AuthenticationToken;

public class XboxLiveToken extends AuthenticationToken {
    protected String token;
    protected String uhs;

    public XboxLiveToken() {
    }

    public XboxLiveToken(final String token, final String uhs) {
        this.token = token;
        this.uhs = uhs;
    }

    public String getToken() {
        return this.token;
    }

    public String getUhs() {
        return this.uhs;
    }
}
