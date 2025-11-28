package edu.dosw.infrastructure.adapters.in.mappers;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.command.AddContactCommand;
import edu.dosw.infrastructure.adapters.in.dtos.AddContactRequest;
import edu.dosw.infrastructure.adapters.in.dtos.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(),user.getProfilePhoto(), user.getIsActive());
    }
    public AddContactCommand toCommand(AddContactRequest request){
        return new AddContactCommand(request.getUserId(), request.getContactId());
    }
}
