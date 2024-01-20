package PageObject.TokenModel;

public class TokenModel {
    String AccessToken;
    int ExpiresIn;

    public TokenModel(String accessToken, int expiresIn) {
        this.AccessToken = accessToken;
        this.ExpiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.AccessToken = accessToken;
    }

    public int getExpiresIn() {
        return ExpiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.ExpiresIn = expiresIn;
    }
}
