package fr.salah.aftest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.salah.aftest.constant.Constant;
import fr.salah.aftest.model.User;
import fr.salah.aftest.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final static String USER_URI = "/user";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    // ------------------------------
    // Tests cases : GET /user/{id}
    // ------------------------------

    @Test
    public void shouldGetUser() throws Exception {

        User user = createAndSaveUser();

        String uri = USER_URI + "/" + user.getId();

        mockMvc.perform(get(uri))
                /*.andDo(print())*/
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("salah"))
                .andExpect(jsonPath("$.lastName").value("bennour"))
                .andExpect(jsonPath("$.username").value("salahBennour06"))
                .andExpect(jsonPath("$.birthDate").value("1991-01-26"))
                .andExpect(jsonPath("$.email").value("bennour.salah.26@gmail.com"))
                .andExpect(jsonPath("$.country").value("FRANCE"));
    }

    @Test
    public void shouldNotFoundUser() throws Exception {
        String id = "12345678910";
        String uri = USER_URI + "/" + id;

        mockMvc.perform(get(uri))
                /*.andDo(print())*/
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException().getMessage().equals("User id "+id+" not found");
    }

    // ------------------------------
    // Tests cases : POST /user
    // ------------------------------

    @Test
    public void shouldCreateUser() throws Exception {

        Map<String, String> form = createUserForm();


        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("salah"))
                .andExpect(jsonPath("$.lastName").value("bennour"))
                .andExpect(jsonPath("$.username").value("salahBennour06"))
                .andExpect(jsonPath("$.birthDate").value(form.get("birthDate")))
                .andExpect(jsonPath("$.email").value("bennour.salah.26@gmail.com"))
                .andExpect(jsonPath("$.country").value("FRANCE"));
    }

    @Test
    public void shouldNotCreateUserInNotAllowedCountry() throws Exception {

        Map<String, String> form = createUserForm();
        form.put("country", "ITALIE");

        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isForbidden())
                .andReturn().getResolvedException().getMessage().equals("Access restricted in your country.");
    }

    @Test
    public void shouldNotCreateMinorUser() throws Exception {

        Map<String, String> form = createUserForm();
        LocalDate birthDate = LocalDate.now().minusYears(18).plusDays(1);

        form.put("birthDate", birthDate.format(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT)));

        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isForbidden())
                .andReturn().getResolvedException().getMessage().equals("Only adults can create account.");
    }

    @Test
    public void shouldNotCreateUserWhenInvalidFirstName() throws Exception {

        Map<String, String> form = createUserForm();
        form.put("firstName", "a");

        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage().contains("FirstName must be between 2 and 30 characters");

        form.put("firstName", "thisIsAVeryVeryVeryLongFirstName");
        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage().contains("FirstName must be between 2 and 30 characters");
    }

    @Test
    public void shouldNotCreateUserWhenInvalidLastName() throws Exception {

        Map<String, String> form = createUserForm();
        form.put("lastName", "a");

        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage().contains("LastName must be between 2 and 30 characters");

        form.put("lastName", "thisIsAVeryVeryVeryLongLastName");
        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage().contains("LastName must be between 2 and 30 characters");
    }

    @Test
    public void shouldNotCreateUserWhenInvalidEmail() throws Exception {

        Map<String, String> form = createUserForm();
        form.put("email", "bennour.salah.26@.com");

        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage().contains("Email should be valid");
    }


    @Test
    public void shouldNotCreateUserWhenInvalidBirthDate() throws Exception {

        Map<String, String> form = createUserForm();
        form.put("birthDate", "26-01-1991");

        mockMvc.perform(post(USER_URI)
                .content(objectMapper.writeValueAsString(form))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                /*.andDo(print())*/
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage().contains("Cannot deserialize");
    }

    // ----------------
    // PRIVATE METHODS
    // ----------------

    private User createAndSaveUser() {
        User user = new User();
        user.setFirstName("salah");
        user.setLastName("bennour");
        user.setUsername("salahBennour06");
        user.setBirthDate(LocalDate.of(1991, 1, 26));
        user.setCountry("FRANCE");
        user.setEmail("bennour.salah.26@gmail.com");

        return userRepository.save(user);
    }

    private Map<String, String> createUserForm() {
        Map<String, String> form = new HashMap<>();
        form.put("firstName", "salah");
        form.put("lastName", "bennour");
        form.put("username", "salahBennour06");
        LocalDate birthDate = LocalDate.now().minusYears(18);
        form.put("birthDate", birthDate.format(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT)));
        form.put("country", "FRANCE");
        form.put("email", "bennour.salah.26@gmail.com");

        return form;
    }
}
