package ivangka.tvwebhookalerts.runner;

import ivangka.tvwebhookalerts.config.Config;
import ivangka.tvwebhookalerts.controller.TelegramBotController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotRunner implements CommandLineRunner {

    private final TelegramBotsLongPollingApplication botsApplication;
    private final TelegramBotController telegramBotController;
    private final Config config;

    @Autowired
    public TelegramBotRunner(TelegramBotsLongPollingApplication botsApplication,
                             TelegramBotController telegramBotController,
                             Config config) {
        this.botsApplication = botsApplication;
        this.telegramBotController = telegramBotController;
        this.config = config;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            botsApplication.registerBot(config.getBotToken(), telegramBotController);
            Thread.currentThread().join();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}