package edu.dosw.application;

import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.AddContactUseCase;
import edu.dosw.domain.ports.inbound.command.AddContactCommand;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddContactUseCaseImpl implements AddContactUseCase {
    private final UserRepository userRepository;

    public AddContactUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(AddContactCommand command) {
        User user = userRepository.findUserById(command.getUserId());
        User contact = userRepository.findUserById(command.getContactId());

        if (user == null || contact == null) {
            throw ApplicationLayerExceptions.cannotAddContact();
        }

        if (!user.getContacts().contains(contact.getId())) {
            user.getContacts().add(contact.getId());
            userRepository.updateUser(user);
        }

        if (!contact.getContacts().contains(user.getId())) {
            contact.getContacts().add(user.getId());
            userRepository.updateUser(contact);
        }
    }

}
