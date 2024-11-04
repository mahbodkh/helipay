package app.helipay.me.api;

public interface MatchEngineApi {
    void matcher(long userFrom, long userTo);
    void next(long userFrom);
}
