package edu.dosw.infrastructure.persistence.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Date;


@Document(collection = "conversationmessages")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConversationMessageDocument{

    @Id
    private String id;

    @Field("conversation_id")
    private String conversationId;

    @Field("send_date")
    private Instant sendDate;

    @Field("author_id")
    private String authorId;

    @Field("text")
    private String text;

    @Field("is_read")
    private Boolean isRead;

}