package analytics.mapper;

import analytics.dto.CommentEventDto;
import analytics.model.AnalyticsEvent;
import analytics.model.EventType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalyticsEventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actorId", source = "authorId")
    @Mapping(target = "receivedAt", source = "date")
    @Mapping(target = "eventType", expression = "java(getEventType())")
    AnalyticsEvent toCommentEntity(CommentEventDto commentEventDto);


    @Mapping(target = "authorId", source = "actorId")
    @Mapping(target = "date", source = "receivedAt")
    CommentEventDto toCommentDto(AnalyticsEvent entity);

    default EventType getEventType() {
        return EventType.POST_COMMENT;
    }
}