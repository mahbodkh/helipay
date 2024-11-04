package app.helipay.me.api;

import app.helipay.me.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MatchEngineController implements MatchEngineApi {
    private final MatchService matchService;

    @Override
    public void matcher(long userFrom, long userTo) {
        matchService.matcher(userFrom, userTo);
    }

    @Override
    public void next(long userFrom) {
        matchService.next(userFrom);
    }
}
