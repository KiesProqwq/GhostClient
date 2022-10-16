package cn.KiesPro.utils.ratsiel.auth.model.microsoft;

import cn.KiesPro.utils.ratsiel.auth.abstracts.AuthenticationToken;

public class MicrosoftToken extends AuthenticationToken {
    protected String token;
    protected String refreshToken;

    public MicrosoftToken() {
    }

    public MicrosoftToken(final String token, final String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return this.token;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }
}
