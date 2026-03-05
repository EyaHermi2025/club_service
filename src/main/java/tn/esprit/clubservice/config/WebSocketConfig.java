package tn.esprit.clubservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import tn.esprit.clubservice.service.PopularityWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final PopularityWebSocketHandler popularityWebSocketHandler;

    public WebSocketConfig(PopularityWebSocketHandler popularityWebSocketHandler) {
        this.popularityWebSocketHandler = popularityWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(popularityWebSocketHandler, "/ws-popularity")
                .setAllowedOrigins("*");
    }
}
