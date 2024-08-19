package com.example.teabot.bot;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TeaBot extends TelegramLongPollingBot {
    private final Environment environment;

    public TeaBot(Environment environment) {
        super(environment.getProperty("tea.bot.token"));
        this.environment = environment;
    }

    @Override
    public void onUpdateReceived(Update update) {
        ChatMessageRenderer.handle(update, this);
    }

    @Override
    public String getBotUsername() {
        return environment.getProperty("tea.bot.name");
    }
}
