package io.cassiopee.textbook.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class JwtAuthenticationResponse {
    private String token;
    private String accessToken;
    private String tokenType = "Bearer";
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date expiryToken;

    public JwtAuthenticationResponse(String token, String accessToken, Date expiryToken) {
        super();
        this.token = token;
        this.accessToken = accessToken;
        this.expiryToken = expiryToken;
    }
    public JwtAuthenticationResponse(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryToken() {
        return expiryToken;
    }

    public void setExpiryToken(Date expiryToken) {
        this.expiryToken = expiryToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
