package app.helipay.me.service;

import app.helipay.me.proxy.tlg.TelegramService;
import app.helipay.me.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

//    private final MatchRepository matchRepository;
//    private final TelegramService telegramService;


    public void matcher(long userFrom, long userTo) {

        log.info("Matcher: {} and {}", userFrom, userTo);



    }

    // should her make a list of users to access to see
    public Long next(long userFrom) {
        return 1l;
    }
}
