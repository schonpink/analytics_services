package analytics.listener;

import analytics.dto.CommentEventDto;
import analytics.mapper.AnalyticsEventMapper;
import analytics.repository.AnalyticsEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommentEventListener extends AbstractListener<CommentEventDto> {
    public CommentEventListener(ObjectMapper objectMapper,
                                AnalyticsEventMapper analyticsEventMapper,
                                AnalyticsEventRepository repository) {
        super(objectMapper, analyticsEventMapper, repository);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEventDto commentEventDto = readValue(message.getBody(), CommentEventDto.class);
        log.info("Received new comment on post: {}", commentEventDto);
        save(analyticsEventMapper.toCommentEntity(commentEventDto));
    }
}