package org.agbrothers.englishbot.adapter.telegram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;


@Profile("development")
@Component
public class NgrokTunnelStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(NgrokTunnelStarter.class);

    @Value("${server.port}")
    private String port;

    private Process process;

    private SimpleAsyncTaskExecutor taskExecutor;

    private RestTemplate restTemplate;

    private static final String NGROK_LOCAL_URL = "http://localhost:4040/api/tunnels";

    @PostConstruct
    private void postConstruct(){
        LOGGER.info("Starting ngrok tunneling");
        String osName = System.getProperty("os.name");
        String command = osName.toLowerCase().startsWith("windows")
                ? "cmd /c ngrok.exe http " + port
                : "sh -c ngrok http " + port;

        taskExecutor.submit(() -> {
            try {
                process = Runtime.getRuntime()
                        .exec(command, null, new File("./ngrok"));
            } catch (IOException e) {
                LOGGER.warn("Starting ngrok tunneling failed");
                e.printStackTrace();
            }
        });
    }

    /**
     * This method is intended to shutdown the ngrok process. In fact, the process is not always shutting down, especially in debug mode.
     * For this case you should kill the process manually
     */
    @PreDestroy
    private void preDestroy(){
        try {
            if(process.isAlive()){
                LOGGER.info("Shutdown ngrok tunnel process");
                process.destroy();
            }
        }
        catch (Exception e) {
            LOGGER.error("Attempt to shutdown ngrok tunnel process is failed");
        }
        finally {
            if(process.isAlive()){
                LOGGER.info("Shutdown ngrok tunnel process forcibly");
                process.destroyForcibly();
            }
            LOGGER.info("Shutdown of ngrok tunnel executor is finished");
        }
    }

    public String getNgrokBotServerUrl() {
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

    @Autowired
    public void setTaskExecutor(SimpleAsyncTaskExecutor simpleAsyncTaskExecutor) {
        this.taskExecutor = simpleAsyncTaskExecutor;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
