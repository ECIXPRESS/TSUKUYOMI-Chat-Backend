package edu.dosw.infrastructure.persistence.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "chat-users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDocument {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("profile_photo")
    private String profilePhoto;

    @Field("is_active")
    private boolean isActive;

    @Field("conversations")
    private List<String> conversations;

    @Field("contacts")
    private List<String> contacts;

}