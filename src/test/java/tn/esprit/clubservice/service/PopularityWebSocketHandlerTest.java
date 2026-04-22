package tn.esprit.clubservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class PopularityWebSocketHandlerTest {

    private PopularityWebSocketHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PopularityWebSocketHandler();
    }

    @Test
    void testBroadcast() throws Exception {
        WebSocketSession session1 = mock(WebSocketSession.class);
        WebSocketSession session2 = mock(WebSocketSession.class);
        
        when(session1.isOpen()).thenReturn(true);
        when(session2.isOpen()).thenReturn(true);
        
        handler.afterConnectionEstablished(session1);
        handler.afterConnectionEstablished(session2);
        
        handler.broadcast("Message");
        
        verify(session1, times(1)).sendMessage(any(TextMessage.class));
        verify(session2, times(1)).sendMessage(any(TextMessage.class));
    }

    @Test
    void testCleanupOnBroadcastFailure() throws Exception {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.isOpen()).thenReturn(true);
        doThrow(new IOException()).when(session).sendMessage(any(TextMessage.class));
        
        handler.afterConnectionEstablished(session);
        handler.broadcast("Message");
        
        // Internal sessions should be cleaned up or at least handled
    }
}
