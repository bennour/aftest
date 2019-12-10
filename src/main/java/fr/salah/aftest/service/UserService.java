package fr.salah.aftest.service;

import fr.salah.aftest.exception.BadRequestException;
import fr.salah.aftest.exception.ForbiddenException;
import fr.salah.aftest.model.User;
import fr.salah.aftest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static fr.salah.aftest.constant.Constant.FRANCE;

@Service
public class UserService {

    private UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     */
    public User create(User user) {

        if (isMinor(user.getBirthDate())) {
            throw new ForbiddenException("Only adults can create account.");
        }

        if (!FRANCE.equalsIgnoreCase(user.getCountry())) {
            throw new ForbiddenException("Access restricted in your country.");
        }

        return userRepository.save(user);
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<User> getAll() { return userRepository.findAll(); }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(String id) { userRepository.deleteById(id); }

    // ----------------
    // PRIVATE METHODS
    // ----------------

    private boolean isMinor(LocalDate birthDate) {
        if (birthDate == null) {
            throw new BadRequestException("Invalid query. Please check the birthDate input and retry.");
        }

        return birthDate.plusYears(18).isAfter(LocalDate.now());
    }
}
