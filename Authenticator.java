package com.sitereader;

import java.net.URI;
import java.util.Scanner;

import com.neovisionaries.i18n.CountryCode;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

public class Authenticator 
{
    private static final String CLIENT_ID = "";
    private static final String SECRET_CLIENT_ID = "";
    private static final URI REDIRECT_URI = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");

    private static String CODE = "";

    private static final SpotifyApi SPOTIFY_API = SpotifyApi.builder()
        .setClientId(CLIENT_ID)
        .setClientSecret(SECRET_CLIENT_ID)
        .setRedirectUri(REDIRECT_URI)
        .build();

        private static final AuthorizationCodeUriRequest URI_REQUEST = SPOTIFY_API.authorizationCodeUri().show_dialog(true).build();
        private static  AuthorizationCodeRequest AUTH_REQUEST = SPOTIFY_API.authorizationCode(CODE).build();

        private static SearchAlbumsRequest searchAlbum;


    public void getAuthCode()
    {
        URI uri = URI_REQUEST.execute();
        System.out.println("URI: " + uri.toString());
        AUTH_REQUEST = SPOTIFY_API.authorizationCode(uri.getAuthority()).build();
        //getAlbum();
        System.out.println(uri.getRawAuthority());
    }
    
    public void getAlbum()
    {
        //searchAlbum = SPOTIFY_API.searchAlbums("Arcade Fire").market(CountryCode.CA).build();
        
        try 
        {
            AuthorizationCodeCredentials authCode = AUTH_REQUEST.execute();
            
            SPOTIFY_API.setAccessToken(authCode.getAccessToken());
            SPOTIFY_API.setRefreshToken(authCode.getRefreshToken());
            
            //Paging<AlbumSimplified> album = searchAlbum.execute();

            System.out.println("Expires in: " + authCode.getExpiresIn());
        } 
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
