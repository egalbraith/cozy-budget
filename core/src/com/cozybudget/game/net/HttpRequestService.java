package com.cozybudget.game.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.JsonReader;

public class HttpRequestService {
    private HttpRequestBuilder requestBuilder;

    public HttpRequestService() {
        requestBuilder = new HttpRequestBuilder();
    }

    public void getLinkToken(Administration admin) {
        String jsonBody = "{\n" +
                "  \"client_id\": \"{client id here}\",\n" +
                "  \"secret\": \"{secret here}\",\n" +
                "  \"products\": [\"transactions\"],\n" +
                "  \"client_name\": \"CozyBudget\",\n" +
                "  \"country_codes\": [\"US\"]," +
                "  \"language\": \"en\"," +
                "   \"user\": {" +
                "       \"client_user_id\": \"custom_cozy\"" +
                "   }" +
                "}";

        Net.HttpRequest httpRequest = requestBuilder.newRequest()
            .method(Net.HttpMethods.POST)
            .url("https://sandbox.plaid.com/link/token/create")
            .content(jsonBody)
            .build();
        httpRequest.setHeader("Content-Type", "application/json");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String jsonResult = httpResponse.getResultAsString();
                if (jsonResult != null) {
                    admin.getLinkTokens().add(new JsonReader().parse(jsonResult).getString("link_token"));
                }
            }

            @Override
            public void failed(Throwable t) {}

            @Override
            public void cancelled() {}
        });
    }

    public void getPublicToken(Administration admin) {
        String jsonBody = "";

        Net.HttpRequest httpRequest = requestBuilder.newRequest()
            .method(Net.HttpMethods.POST)
            .url("")
            .content(jsonBody)
            .build();
        httpRequest.setHeader("Content-Type", "application/json");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String jsonResult = httpResponse.getResultAsString();
                if (jsonResult != null) {
                    admin.getAccessTokens().add(new JsonReader().parse(jsonResult).getString("public_token"));
                }
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void getAccessToken(Administration admin) {
        String jsonBody = "{\n" +
                "  \"client_id\": \"{client id}\",\n" +
                "  \"secret\": \"{secret here}\",\n" +
                "   \"public_token\": \"" + admin.getPublicToken() + "\"\n" +
                "}";
        Net.HttpRequest httpRequest = requestBuilder.newRequest()
            .method(Net.HttpMethods.POST)
            .url("https://sandbox.plaid.com/item/public_token/exchange")
            .content(jsonBody)
            .build();
        httpRequest.setHeader("Content-Type", "application/json");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String jsonResult = httpResponse.getResultAsString();
                if (jsonResult != null) {
                    admin.getAccessTokens().add(new JsonReader().parse(jsonResult).getString("access_token"));
                }
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

}
