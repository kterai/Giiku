package jp.co.apsa.giiku.infrastructure.notification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * SlackNotificationService のテストクラス。
 *
 * <p>RestTemplate をモック化し、通知送信の成否やメッセージ生成処理を検証する。</p>
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class SlackNotificationServiceTest {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private SlackNotificationService service;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        objectMapper = new ObjectMapper();
        service = new SlackNotificationService(restTemplate, objectMapper);

        ReflectionTestUtils.setField(service, "webhookUrl", "https://hooks.slack.test");
        ReflectionTestUtils.setField(service, "defaultChannel", "#general");
        ReflectionTestUtils.setField(service, "urgentChannel", "#urgent");
        ReflectionTestUtils.setField(service, "botUsername", "Giiku");
        ReflectionTestUtils.setField(service, "botIcon", ":robot:");
        ReflectionTestUtils.setField(service, "notificationEnabled", true);
    }

    @Test
    void sendNotification_success() throws Exception {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("ok", HttpStatus.OK));

        boolean result = service.sendNotification("U123", "テストメッセージ");

        assertTrue(result);
        ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(eq("https://hooks.slack.test"), captor.capture(), eq(String.class));

        String body = (String) captor.getValue().getBody();
        SlackNotificationService.SlackMessage message =
                objectMapper.readValue(body, SlackNotificationService.SlackMessage.class);
        assertEquals("#general", message.getChannel());
        assertEquals("Giiku", message.getUsername());
        assertTrue(message.getText().contains("<@U123>"));
        assertTrue(message.getText().contains("テストメッセージ"));
        assertFalse(message.getText().contains("緊急通知"));
    }

    @Test
    void sendNotification_urgentMessage() throws Exception {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("ok", HttpStatus.OK));

        boolean result = service.sendNotification("U999", "至急対応してください", true);

        assertTrue(result);
        ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(eq("https://hooks.slack.test"), captor.capture(), eq(String.class));

        String body = (String) captor.getValue().getBody();
        SlackNotificationService.SlackMessage message =
                objectMapper.readValue(body, SlackNotificationService.SlackMessage.class);
        assertEquals("#urgent", message.getChannel());
        assertTrue(message.getText().startsWith("🚨 **緊急通知**"));
    }

    @Test
    void sendNotification_failure_noRetry() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenThrow(new RestClientException("error"));

        boolean result = service.sendNotification("U123", "失敗テスト");

        assertFalse(result);
        verify(restTemplate).postForEntity(eq("https://hooks.slack.test"), any(), eq(String.class));
    }
}
