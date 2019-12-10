package fr.salah.aftest.mapper;

import fr.salah.aftest.model.User;
import fr.salah.aftest.model.UserForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    private UserForm userForm;

    @Before
    public void setUp() {
        userForm = createUserForm();
    }

    @Test
    public void shouldMapUserFormToUser() {
        User user = userMapper.asUser(userForm);

        assertThat(user.getFirstName()).isEqualTo(userForm.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userForm.getLastName());
        assertThat(user.getUsername()).isEqualTo(userForm.getUsername());
        assertThat(user.getBirthDate()).isEqualTo(userForm.getBirthDate());
        assertThat(user.getCountry()).isEqualTo(userForm.getCountry());
        assertThat(user.getEmail()).isEqualTo(userForm.getEmail());
    }

    @Test
    public void shouldAddDefaultUserNameWhenNotCompleted() {
        userForm.setUsername(null);
        User user = userMapper.asUser(userForm);

        assertThat(user.getUsername()).isEqualTo("salahbennour");
    }

    // ----------------
    // PRIVATE METHODS
    // ----------------

    private UserForm createUserForm() {
        UserForm userForm = new UserForm();
        userForm.setFirstName("salah");
        userForm.setLastName("bennour");
        userForm.setUsername("salahBennour06");
        userForm.setBirthDate(LocalDate.of(1991, 1, 26));
        userForm.setCountry("FRANCE");
        userForm.setEmail("bennour.salah.26@gmail.com");

        return userForm;
    }
}
