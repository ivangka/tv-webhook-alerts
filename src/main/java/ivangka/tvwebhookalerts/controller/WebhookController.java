package ivangka.tvwebhookalerts.controller;

import ivangka.tvwebhookalerts.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebhookController {

    private final TelegramBotController telegramBotController;
    private final Config config;

    @Autowired
    public WebhookController(TelegramBotController telegramBotController,
                             Config config) {
        this.telegramBotController = telegramBotController;
        this.config = config;
    }

    // endpoint: /tradingview/alert?token=<URL_TOKEN>
    @PostMapping("/tradingview/alert")
    public void receiveAlert(@RequestParam String token,
                             @RequestBody String payload) {
        if (token.equals(config.getUrlToken())) {
            telegramBotController.sendMessage(payload);
        }
    }

}
