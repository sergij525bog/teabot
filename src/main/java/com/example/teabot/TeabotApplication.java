package com.example.teabot;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TeabotApplication {

    @SneakyThrows
    public static void main(String[] args) {
        var context = SpringApplication.run(TeabotApplication.class, args);
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(context.getBean("teaBot", TelegramLongPollingBot.class));
    }

}
