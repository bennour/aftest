package fr.salah.aftest.mapper;

import fr.salah.aftest.model.User;
import fr.salah.aftest.model.UserForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * As user user.
     *
     * @param userForm the user form
     * @return the user
     */
    @Mapping(target = "username", source="username", defaultExpression = "java( userForm.getFirstName() + userForm.getLastName() )")
    User asUser(UserForm userForm);
}
