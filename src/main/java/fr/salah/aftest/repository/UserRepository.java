package fr.salah.aftest.repository;

import fr.salah.aftest.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User, String> {

}
