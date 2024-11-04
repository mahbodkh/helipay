package app.helipay.ce.conversation;

import app.helipay.me.api.MatchEngineApi;

public interface MatchEngineProxy extends MatchEngineApi {
    @Override
    void matcher(long userFrom, long userTo);

    @Override
    void next(long userFrom);
}
