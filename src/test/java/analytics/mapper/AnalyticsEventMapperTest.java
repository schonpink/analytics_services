package analytics.mapper;

import analytics.dto.CommentEventDto;
import analytics.model.AnalyticsEvent;
import analytics.model.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class AnalyticsEventMapperTest {
    @Spy
    private AnalyticsEventMapperImpl mapper;
    private AnalyticsEvent analyticsEventExpected;
    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        dateTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        analyticsEventExpected = AnalyticsEvent.builder()
                .receiverId(2L)
                .actorId(1L)
                .eventType(EventType.RECOMMENDATION_RECEIVED)
                .receivedAt(dateTime)
                .build();
    }

    @Test
    void toCommentEntity_shouldMatchAllFields() {
        CommentEventDto commentEventDto = new CommentEventDto();

        commentEventDto.setAuthorId(1L);
        commentEventDto.setCreatedAt(LocalDateTime.of(2023, 8, 24, 12, 34, 56));

        AnalyticsEvent actual = mapper.toCommentEntity(commentEventDto);

        AnalyticsEvent expected = new AnalyticsEvent();
        expected.setActorId(1L);
        expected.setReceivedAt(commentEventDto.getCreatedAt());
        expected.setEventType(mapper.getEventType());

        assertEquals(expected, actual);
    }

    @Test
    void toCommentDto_shouldMatchAllFields() {
        AnalyticsEvent analyticsEvent = new AnalyticsEvent();

        analyticsEvent.setActorId(1L);
        analyticsEvent.setReceivedAt(LocalDateTime.of(2023, 8, 24, 12, 34, 56));

        CommentEventDto actual = mapper.toCommentDto(analyticsEvent);

        CommentEventDto expected = new CommentEventDto();
        expected.setAuthorId(1L);
        expected.setCreatedAt(analyticsEvent.getReceivedAt());

        assertEquals(expected, actual);
    }

    @Test
    void getCommentEventType_shouldReturnCommentEventType() {
        assertEquals(EventType.POST_COMMENT, mapper.getEventType());
    }
}