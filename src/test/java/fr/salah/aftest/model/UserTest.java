package fr.salah.aftest.model;

import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.time.LocalDate;

public class UserTest {

    @Test
    public void gettersAndSettersCorrectness() {
        BeanTester tester = new BeanTester();
        tester.getFactoryCollection().addFactory(LocalDate.class, LocalDate::now);
        tester.testBean(User.class);
    }
}
