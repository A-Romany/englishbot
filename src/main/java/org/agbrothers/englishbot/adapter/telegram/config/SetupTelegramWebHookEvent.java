package org.agbrothers.englishbot.adapter.telegram.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Profile("development")
@Component
public class SetupTelegramWebHookEvent implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetupTelegramWebHookEvent.class);

    private static final String TELEGRAM_URL_TEMPLATE = "https://api.telegram.org/bot{bot_token}/setWebHook?url={bot_server_url}";

    @Value("${telegram.token}")
    private String botToken;

    private RestTemplate restTemplate;

    private NgrokTunnelStarter ngrokTunnelStarter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextStartedEvent) {
        String serverUrl = ngrokTunnelStarter.getNgrokBotServerUrl();

        String url = TELEGRAM_URL_TEMPLATE.replace("{bot_token}", botToken).replace("{bot_server_url}", serverUrl);

        URI uri = URI.create(url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            throw new RuntimeException("Setting web hook failed. Check configuration.");
        }

        LOGGER.info("Web hook was set to " + serverUrl);
    }

    @Autowired
    public void setNgrokTunnelStarter(NgrokTunnelStarter ngrokTunnelStarter) {
        this.ngrokTunnelStarter = ngrokTunnelStarter;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
