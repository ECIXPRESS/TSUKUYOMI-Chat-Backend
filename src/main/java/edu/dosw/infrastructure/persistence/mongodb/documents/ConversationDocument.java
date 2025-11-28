package edu.dosw.infrastructure.persistence.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Document(collection = "conversations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConversationDocument{

    @Id
    private String id;

    @Field("creation_date")
    private Instant creationDate;

    @Field("users")
    private List<String> users;

    @Field("messages")
    private List<String> messages;

    @Field("last_updated")
    private Date lastUpdated;
}