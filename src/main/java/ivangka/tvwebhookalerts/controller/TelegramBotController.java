package ivangka.tvwebhookalerts.controller;

import ivangka.tvwebhookalerts.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Controller
public class TelegramBotController implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final Config config;

    @Autowired
    public TelegramBotController(TelegramClient telegramClient,
                                 Config config) {
        this.telegramClient = telegramClient;
        this.config = config;
    }

    @Override
    public void consume(Update update) {
        // echo for checking
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage sendMessage = defaultSendMessageObject(chatId, message_text);
            execute(sendMessage);
        }
    }

    public void sendMessage(String text) {
        execute(defaultSendMessageObject(Long.parseLong(config.getChatId()), text));
    }

    private SendMessage defaultSendMessageObject(long chatId, String text) {
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .parseMode("HTML")
                .build();
    }

    private void execute(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
