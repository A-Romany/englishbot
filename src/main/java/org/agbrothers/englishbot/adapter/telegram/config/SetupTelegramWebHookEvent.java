package org.agbrothers.englishbot.adapter.telegram.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


public abstract class SetupTelegramWebHookEvent {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SetupTelegramWebHookEvent.class);

    protected static final String TELEGRAM_URL_TEMPLATE = "https://api.telegram.org/bot{bot_token}/setWebHook?url={bot_server_url}";

    @Value("${telegram.token}")
    protected String botToken;

    protected RestTemplate restTemplate;

    protected void setUpWebHook(String botUrl){
        String url = TELEGRAM_URL_TEMPLATE.replace("{bot_token}", botToken).replace("{bot_server_url}", botUrl);

        URI uri = URI.create(url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            LOGGER.error("Setting web hook failed. Check configuration.");
            LOGGER.error("Response from telegram API: " + responseEntity.getBody());
        } else {
            LOGGER.info("Web hook was set to {}", botUrl);
        }
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
