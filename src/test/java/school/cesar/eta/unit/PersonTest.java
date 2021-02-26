package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {
    class PersonSpy extends Person{
        int counter = 0;

        @Override
        public void addToFamily(Person person) {
            super.addToFamily(person);
            counter ++;
        }
    }

    Person person;

    @BeforeEach
    public void setup(){
        person = new Person();
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {

        this.person.setName("Jon");
        this.person.setLastName("Snow");

        assertEquals("Jon Snow", this.person.getName());

    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        fail();
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        fail();
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        fail();
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        fail();
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        fail();
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {

        LocalDate today = LocalDate.now();

        this.person.setBirthday(today);
        Assertions.assertTrue(this.person.isBirthdayToday());

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

    @Test
    public void isFamily_relativePerson_true() {
        fail();
    }
}
