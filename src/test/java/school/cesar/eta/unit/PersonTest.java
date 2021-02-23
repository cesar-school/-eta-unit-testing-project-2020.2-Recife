package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    @InjectMocks
    Person person;

    @Spy
    List<Person> family = new ArrayList<Person>();

    class SpyPerson extends Person {
        private List<Person> family = new ArrayList<Person>();

        @Override
        public LocalDate getNow() {
            return LocalDate.parse("2018-11-01");
        }

        @Override
        public void addToFamily(Person person) {
            this.family.add(person);
        }
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        person.setName("Jon");
        person.setLastName("Snow");

        String expected = "Jon Snow";
        String actual = person.getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");

        String expected = "Jon";
        String actual = person.getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person.setLastName("Snow");

        String expected = "Snow";
        String actual = person.getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            person.getName();
        });
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        SpyPerson person = new SpyPerson();
        LocalDate dt = LocalDate.parse("2018-12-02");

        person.setBirthday(dt);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        SpyPerson person = new SpyPerson();
        LocalDate dt = LocalDate.parse("2018-11-02");

        person.setBirthday(dt);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        SpyPerson person = new SpyPerson();
        LocalDate dt = LocalDate.parse("2018-11-01");

        person.setBirthday(dt);
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        Person relative = new Person();
        person.addToFamily(relative);

        verify(family, times(1)).add(relative);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        Person relative = new Person();
        relative.addToFamily(person);

        verify(family, times(1)).add(relative);
    }

    @Test
    public void isFamily_relativePerson_true() {
        Person relative = new Person();
        relative.setName("Jon");

        when(family.contains(relative)).thenReturn(true);
        Assertions.assertTrue(person.isFamily(relative));
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        Person nonRelative = new Person();

        nonRelative.setName("Joffrey");

        boolean actual = person.isFamily(nonRelative);
        assertFalse(actual);
    }
}
