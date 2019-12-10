package fr.salah.aftest.model;

import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.time.LocalDate;

public class UserFormTest {

    @Test
    public void gettersAndSettersCorrectness() {
        BeanTester tester = new BeanTester();
        tester.getFactoryCollection().addFactory(LocalDate.class, LocalDate::now);
        tester.testBean(UserForm.class);
    }
}
