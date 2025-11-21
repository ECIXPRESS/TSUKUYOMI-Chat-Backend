package edu.dosw.infrastructure.adapters.in.messaging;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserEventListener {

    private final UserRepository userRepository;

    public UserEventListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @KafkaListener(topics = "user-events")
//    public void handleUserEvent(UserCreatedEvent event) {
  //      User chatUser = new User(
    //            event.getUserId(),
      //          event.getName(),
        //        event.getProfilePhoto(),
          //      true,
            //    new ArrayList<>(),
              //  new ArrayList<>()
        //);
        //userRepository.saveUser(chatUser);
    //}
}