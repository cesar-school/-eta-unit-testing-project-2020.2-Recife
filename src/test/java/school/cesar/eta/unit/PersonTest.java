package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonTest {

    Person person;

    class StubPerson extends Person {

        List<Person> family = new ArrayList<Person>();
        @Override
        public void addToFamily(Person person) {
            family.add(person);
            super.addToFamily(person);
        }

    }

    @BeforeEach
    public void setup() {
        this.person = new Person();
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        this.person.setName("jon");
        this.person.setLastName("snow");
        Assertions.assertEquals("jon snow", this.person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        this.person.setName("jon");
        Assertions.assertEquals("jon", this.person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        this.person.setLastName("snow");
        Assertions.assertEquals("snow", this.person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            this.person.getName();
        });
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        LocalDate birthday = LocalDate.of(2021, 04, 02);
        this.person.setBirthday(LocalDate.now());
        boolean birthdayValidate = birthday.equals(this.person.getBirthday());
        Assertions.assertNotEquals(birthdayValidate, this.person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        LocalDate birthday = LocalDate.of(2021, 02, 02);
        this.person.setBirthday(LocalDate.now());
        boolean birthdayValidate = birthday.equals(this.person.getBirthday());
        Assertions.assertNotEquals(birthdayValidate, this.person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        LocalDate birthday = LocalDate.now();
        this.person.setBirthday(LocalDate.now());
        boolean birthdayValidate = birthday.equals(this.person.getBirthday());
        Assertions.assertEquals(birthdayValidate, this.person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        this.person.setName("Fulano");
        StubPerson somePerson = new StubPerson();
        somePerson.addToFamily(this.person);

        String newMember = somePerson.family.get(0).getName();
        Assertions.assertEquals("Fulano",newMember);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        StubPerson somePerson = new StubPerson();
        somePerson.addToFamily(this.person);
        Assertions.assertTrue(somePerson.isFamily(this.person));
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        Assertions.assertFalse(this.person.isFamily(null));
    }

    @Test
    public void isFamily_relativePerson_true() {
        Person anotherPerson = new Person();
        anotherPerson.addToFamily(this.person);
        Assertions.assertTrue(anotherPerson.isFamily(this.person));
        Assertions.assertTrue(this.person.isFamily(anotherPerson));
    }
}
