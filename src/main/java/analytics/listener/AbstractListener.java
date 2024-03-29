package analytics.listener;

import analytics.exception.MessageReadException;
import analytics.mapper.AnalyticsEventMapper;
import analytics.model.AnalyticsEvent;
import analytics.repository.AnalyticsEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractListener <T> implements MessageListener {
    private final ObjectMapper objectMapper;
    protected final AnalyticsEventMapper analyticsEventMapper;
    private final AnalyticsEventRepository repository;

    protected T readValue(byte[] json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Failed to read message", e);
            throw new MessageReadException(e);
        }
    }

    @Transactional
    protected void save(AnalyticsEvent event) {
        repository.save(event);
        log.info("Saved new analytics event: {}", event);
    }
}