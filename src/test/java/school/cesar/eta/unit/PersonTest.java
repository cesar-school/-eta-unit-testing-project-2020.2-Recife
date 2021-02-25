package school.cesar.eta.unit;

import net.bytebuddy.dynamic.scaffold.TypeInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {
    class PersonSpy extends Person {
        int counter = 0;

        @Override
        public void addToFamily(Person person) {
            super.addToFamily(person);
            counter++;
        }

        @Override
        public LocalDate getNow() {
            return LocalDate.parse("2021-02-24");
        }
    }

    Person person;

    @BeforeEach
    public void setup() {
        person = new Person();
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        fail();
    }

    @Test //DAYSE
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");
        Assertions.assertEquals("Jon", person.getName());
        Assertions.assertNull(person.getLastName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        fail();
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        fail();
    }

    @Test //DAYSE
    public void isBirthdayToday_differentMonthAndDay_false() {
        PersonSpy person = new PersonSpy();
        LocalDate birthday = LocalDate.parse("1992-07-01");
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        fail();
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        fail();
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        PersonSpy somePerson = new PersonSpy();
        somePerson.addToFamily(this.person);
        Assertions.assertEquals(1, somePerson.counter);
        Assertions.assertTrue(somePerson.isFamily(this.person));
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        PersonSpy somePerson = new PersonSpy();
        somePerson.addToFamily(this.person);
        Assertions.assertTrue(somePerson.isFamily(this.person));
        Assertions.assertTrue(this.person.isFamily(somePerson));
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        fail();
    }

    @Test //DAYSE
    public void isFamily_relativePerson_true() {
        Person anotherPerson = new Person();
        anotherPerson.addToFamily(person);
        Assertions.assertTrue(anotherPerson.isFamily(person));
        Assertions.assertTrue(person.isFamily(anotherPerson));
    }
}
