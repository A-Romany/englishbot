package org.agbrothers.englishbot.adapter.telegram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile("development")
@Component
public class SetupTelegramWebHookEvent implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetupTelegramWebHookEvent.class);

    private static final String TELEGRAM_URL_TEMPLATE = "https://api.telegram.org/bot{bot_token}/setWebHook?url={bot_server_url}";

    private static final String NGROK_LOCAL_URL = "http://localhost:4040/api/tunnels";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telegram.token}")
    private String botToken;

    @Value("${server.port}")
    private String port;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String serverUrl = getNgrokBotServerUrl();

        String url = TELEGRAM_URL_TEMPLATE.replace("{bot_token}", botToken).replace("{bot_server_url}", serverUrl);

        URI uri = URI.create(url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            throw new RuntimeException("Setting web hook failed. Check configuration.");
        }

        LOGGER.info("Web hook was set");
    }

    private String getNgrokBotServerUrl() {

        String osName = System.getProperty("os.name");
        String command = osName.toLowerCase().startsWith("windows")
                ? "cmd /c ngrok.exe http " + port
                : "sh -c ngrok http " + port;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                LOGGER.info("Starting ngrok tunneling");
                Runtime.getRuntime()
                        .exec(command, null, new File("./ngrok"));
            } catch (IOException e) {
                LOGGER.warn("Starting ngrok tunneling failed");
                e.printStackTrace();
            }
        });

        ResponseEntity<String> responseEntity = restTemplate.exchange(NGROK_LOCAL_URL, HttpMethod.GET, null, String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            throw new RuntimeException(String.format("Failed to obtain ngrok server url. Reason: %s", responseEntity.getBody()));
        }

        String ngrokUrl;
        try {
            ngrokUrl = new ObjectMapper().readTree(responseEntity.getBody()).get("tunnels").get(0).get("public_url").asText();
        } catch (IOException e) {
            throw new RuntimeException("Failed to obtain ngrok server url.", e);
        }
        return ngrokUrl;
    }
}
