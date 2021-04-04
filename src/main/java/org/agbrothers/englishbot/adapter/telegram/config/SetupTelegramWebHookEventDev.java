package org.agbrothers.englishbot.adapter.telegram.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Profile("development")
@Component
public class SetupTelegramWebHookEventDev extends SetupTelegramWebHookEvent implements ApplicationListener<ContextRefreshedEvent> {

    private NgrokTunnelStarter ngrokTunnelStarter;

    @Autowired
    public void setNgrokTunnelStarter(NgrokTunnelStarter ngrokTunnelStarter) {
        this.ngrokTunnelStarter = ngrokTunnelStarter;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String serverUrl = ngrokTunnelStarter.getNgrokBotServerUrl();
        setUpWebHook(serverUrl);
    }
}
