package org.agbrothers.englishbot.adapter.telegram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Profile("production")
@Component
public class SetupTelegramWebHookEventProd extends SetupTelegramWebHookEvent implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${telegram.bot-url}")
    private String botUrl;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        setUpWebHook(botUrl);
    }
}
