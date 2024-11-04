package app.helipay.me.proxy.tlg;

import app.helipay.me.domain.MatchEntity;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramService {
    private Map<Long, MatchEntity> registrationCache = new HashMap<>();

    public void start() {
        final String token = "8018593919:AAFzD5zkV8XoiFrEBawsw2CF8wwwOCvkDRw";
        TelegramBot bot = new TelegramBot(token);

        // Register for updates
        bot.setUpdatesListener(updates -> {
            // return id of last processed update or confirm them all
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
            // Create Exception Handler
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } else {
                // probably network error
                e.printStackTrace();
            }
        });

        // Send messages
//        long chatId = update.message().chat().id();
        SendResponse response = bot.execute(new SendMessage(1, "Hello!"));
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if (messageText.equals("/start")) {
                registration(chatId);
            } else if (registrationCache.containsKey(chatId)) {
                continueRegistration(chatId, messageText);
            }
        }
    }

    private void registration(Long chatId) {
        sendMessage(chatId, "Welcome! Let's get you registered. What language do you prefer?");
        registrationCache.put(chatId, new MatchEntity());
    }

    private void continueRegistration(Long chatId, String messageText) {
        UserEntity user = registrationCache.get(chatId);

        if (user.getLanguage() == null) {
            user.setLanguage(messageText);
            sendMessage(chatId, "Great! Now, please enter your age:");
        } else if (user.getAge() == 0) {
            try {
                user.setAge(Integer.parseInt(messageText));
                sendMessage(chatId, "Perfect! Please describe yourself:");
            } catch (NumberFormatException e) {
                sendMessage(chatId, "Invalid input for age. Please enter a number:");
            }
        } else if (user.getDescription() == null) {
            user.setDescription(messageText);
            sendMessage(chatId, "Nice! Now, enter your city:");
        } else if (user.getLocation() == null) {
            user.setLocation(messageText);
            userRepository.save(user);
            sendMessage(chatId, "Registration complete! You can now start finding matches.");
            registrationCache.remove(chatId);
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
