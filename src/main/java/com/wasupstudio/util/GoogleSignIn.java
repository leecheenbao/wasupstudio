package com.wasupstudio.util;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleSignIn {

    private static final String APPLICATION_NAME = "Your Application Name";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CLIENT_SECRET_FILE = "/client_secret.json";
    private static final String REDIRECT_URI = "http://localhost:8080/wasupstudio/api/member/oauth2callback";

    public static HttpTransport httpTransport;

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAuthorizationUrl() throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, getClientSecrets(), Collections.singleton("email"))
                .setAccessType("offline")
                .build();
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl()
                .setRedirectUri(REDIRECT_URI);
        return url.build();
    }

    public static Credential exchangeAuthorizationCode(String code) throws IOException {

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, getClientSecrets(), Collections.singleton("email"))
                .setAccessType("offline")
                .build();
        return flow.loadCredential("");
    }

    public static Userinfo getUserInfo(Credential credential) throws IOException {
        Oauth2 oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        return oauth2.userinfo().get().execute();
    }

    private static GoogleClientSecrets getClientSecrets() throws IOException {
        InputStream inputStream = GoogleSignIn.class.getResourceAsStream(CLIENT_SECRET_FILE);
        return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));
    }

    public static void main(String[] args) throws IOException {
        GoogleClientSecrets googleClientSecrets = getClientSecrets();
        String authUrl = GoogleSignIn.getAuthorizationUrl();
        System.out.println("Please go to this URL to authorize the application: ");
        System.out.println(authUrl);

        System.out.print("Enter the authorization code: ");
        String code = System.console().readLine().trim();

        Credential credential = GoogleSignIn.exchangeAuthorizationCode(code);

        Userinfo userInfo = GoogleSignIn.getUserInfo(credential);
        System.out.println("Email: " + userInfo.getEmail());

        // Use the user's email to create or authenticate your user
    }
}
