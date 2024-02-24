package com.cozybudget.game.net;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Administration {

    private HttpRequestService httpRequestService = new HttpRequestService();
    private Set<String> linkTokens = new HashSet<>();
    private Set<String> publicTokens = new HashSet<>();
    private Set<String> accessTokens = new HashSet<>();
    String linkToken;
    String publicToken;
    String accessToken;

    public Set<String> getLinkTokens() {
        return linkTokens;
    }

    public Set<String> getPublicTokens() {
        return publicTokens;
    }

    public Set<String> getAccessTokens() {
        return accessTokens;
    }

    public String getLinkToken() {
        if (linkToken != null) {
            return linkToken;
        }

        httpRequestService.getLinkToken(this);
        linkToken = linkTokens.stream()
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
        return linkToken;
    }

    public String getPublicToken() {
        if (publicToken != null) {
            return publicToken;
        }

        publicToken = publicTokens.stream()
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
        return publicToken;
    }

    public String getAccessToken() {
        if (accessToken != null) {
            return accessToken;
        }

        httpRequestService.getAccessToken(this);
        accessToken = accessTokens.stream()
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
        return accessToken;
    }
}
