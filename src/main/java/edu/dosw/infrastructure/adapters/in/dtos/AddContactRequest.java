package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.Data;

@Data
public class AddContactRequest {
    private String userId;
    private String contactId;
}