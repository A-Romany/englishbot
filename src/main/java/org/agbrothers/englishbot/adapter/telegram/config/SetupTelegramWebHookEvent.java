package org.agbrothers.englishbot.adapter.telegram.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class SetupTelegramWebHookEvent implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetupTelegramWebHookEvent.class);

    private static final String TELEGRAM_URL_TEMPLATE = "https://api.telegram.org/bot{bot_token}/setWebHook?url={bot_server_url}";

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.bot.server.url}")
    private String botServerUrl;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        RestTemplate restTemplate = new RestTemplate();

        String url = TELEGRAM_URL_TEMPLATE.replace("{bot_token}", botToken).replace("{bot_server_url}", botServerUrl);

        URI uri = URI.create(url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            throw new RuntimeException("Setting web hook failed. Check configuration.");
        }

        LOGGER.info("Web hook was set");
    }
}
