package app.helipay.ce.service;

import app.helipay.ce.conversation.MatchEngineProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchEngineService {
    private final MatchEngineProxy matchEngineProxy;
    private final UserManagerService userManagerService;

    public void matcher(long userFrom, long userTo) {
        matchEngineProxy.matcher(userFrom, userTo);

    }
}
