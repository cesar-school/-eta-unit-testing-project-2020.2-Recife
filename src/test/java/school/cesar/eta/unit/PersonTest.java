package school.cesar.eta.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class PersonTest extends Person {

    @Mock
    Person mockPerson1, mockPerson2;

    @Spy
    Person spyPerson;

    class StubPersonNewMember extends Person {

        List<Person> family = new ArrayList<Person>();

        @Override
        public void addToFamily(Person person) {
            family.add(person);
        }

    }

    class StubPerson2FamilyUpdated extends Person {

        List<Person> family = new ArrayList<Person>();

        @Override
        public void addToFamily(Person person) {
            //person.family.add(this);
            //System.out.println("personAddedAlsoHasItsFamilyUpdated");
            //person.family.add(this);
        }
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {

        spyPerson.setName("Jon");
        spyPerson.setLastName("Snow");
        String resultName = spyPerson.getName();
        verify(spyPerson, times(1)).getName();
        Assertions.assertEquals("Jon Snow", resultName);
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {

        spyPerson.setName("Jon");
        spyPerson.setLastName(null);
        String resultName = spyPerson.getName();
        verify(spyPerson, times(1)).getName();
        Assertions.assertEquals("Jon", resultName);
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {

        spyPerson.setName(null);
        spyPerson.setLastName("Snow");
        String resultName = spyPerson.getName();
        verify(spyPerson, times(1)).getName();
        Assertions.assertEquals("Snow", resultName);
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {

        spyPerson.setName(null);
        spyPerson.setLastName(null);
        Assertions.assertThrows(RuntimeException.class, () -> spyPerson.getName());
        verify(spyPerson, times(1)).getName();
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {

        //Setar a data de aniversário com um mês e um dia a menos da data de hoje.
        spyPerson.setBirthday(spyPerson.getNow().minusMonths(1).minusDays(1));
        boolean resultBirhtday = spyPerson.isBirthdayToday();
        Assertions.assertFalse(resultBirhtday);
        verify(spyPerson, times(1)).isBirthdayToday();
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {

        //Setar a data de aniversário com um dia a menos da data de hoje.
        spyPerson.setBirthday(spyPerson.getNow().minusDays(1));
        boolean resultBirhtday = spyPerson.isBirthdayToday();
        Assertions.assertFalse(resultBirhtday);
        verify(spyPerson, times(1)).isBirthdayToday();
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {

        //Setar a data de aniversário igual a data de hoje.
        spyPerson.setBirthday(spyPerson.getNow());
        boolean resultBirhtday = spyPerson.isBirthdayToday();
        Assertions.assertTrue(resultBirhtday);
        verify(spyPerson, times(1)).isBirthdayToday();
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {

//        expectativa
//        doNothing().when(mockPerson1).addToFamily(any(Person.class));
//        // criar p1
//        mockPerson1.setName(anyString());
//        // criar p2
//        mockPerson2.setName(anyString());
//        //p1.addToFamily(p2);
//        mockPerson1.addToFamily(mockPerson2);
//        //verificar que o método foi executado 1 vez
//        verify(mockPerson1, times(1)).addToFamily(mockPerson2);

        //@Override
        StubPersonNewMember sPerson1 = new StubPersonNewMember();
        StubPersonNewMember sPerson2 = new StubPersonNewMember();

        sPerson1.setName("P1 sPerson");
        sPerson2.setName("P2 sPerson");

        //Invocando o método addToFamily isolado @Override
        sPerson1.addToFamily(sPerson2);

        String newMember = sPerson1.family.get(0).getName();

        Assertions.assertAll(
                "Várias Assertivas",
                () -> Assertions.assertEquals("P2 sPerson", newMember),
                () -> Assertions.assertNotNull(sPerson1.family)
        );
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {

//        //expectativa
//        doNothing().when(mockPerson1).addToFamily(any(Person.class));
//
//        // criar p1
//        mockPerson1.setName(anyString());
//
//        // criar p2
//        mockPerson2.setName(anyString());
//
//        //p1.addToFamily(p2);
//        mockPerson1.addToFamily(mockPerson2);
//
//        //verificar que p1 esta no array de p2
//
//        //verificar que o método foi executado 1 vez
//        verify(mockPerson1, times(1)).addToFamily(mockPerson2);
//
//        @Override
//        StubPerson sPerson1 = new StubPerson();
//        StubPerson sPerson2 = new StubPerson();
//        sPerson1.setName("P1 sPerson");
//        sPerson2.setName("P2 sPerson");
//        sPerson1.addToFamily(sPerson2);
//        int i = sPerson1.family.size();
//        Assertions.assertEquals(1, i);
//        System.out.println("");
    }

    @Test
    public void isFamily_nonRelativePerson_false() {

        //Retornar false quando o método isFamily é invocado
        when(mockPerson1.isFamily(mockPerson2)).thenReturn(false);

        // mocar alguma string em P1
        mockPerson1.setName(anyString());

        // mocar alguma string em p2
        mockPerson2.setName(anyString());

        //receber o retorno do método isFamily igual a false;
        boolean isFamilyFalse = mockPerson1.isFamily(mockPerson2);

        //Faz uma assertiva verificando que a váriável é false
        Assertions.assertFalse(isFamilyFalse);

        //verifica que o método addToFamily nunca foi invocado
        verify(mockPerson1, times(0)).addToFamily(any());

        //verifica que o método isFamily foi invocado 1 vez
        verify(mockPerson1, times(1)).isFamily(any());
    }

    @Test
    public void isFamily_relativePerson_true() {

        //Retornar true quando o método isFamily é invocado
        when(mockPerson1.isFamily(mockPerson2)).thenReturn(true);

        // mocar alguma string em P1
        mockPerson1.setName(anyString());

        // mocar alguma string em p2
        mockPerson2.setName(anyString());

        //invocar o método addToFamily para adicionar p2 na familia de p1
        mockPerson1.addToFamily(mockPerson2);

        //receber o retorno do método isFamily igual a true;
        boolean isFamilyTrue = mockPerson1.isFamily(mockPerson2);

        //Faz uma assertiva verificando que a váriável é true
        Assertions.assertTrue(isFamilyTrue);

        //verifica que o método addToFamily foi invocado 1 vez
        verify(mockPerson1, times(1)).addToFamily(any());

        //verifica que o método isFamily foi invocado 1 vez
        verify(mockPerson1, times(1)).isFamily(any());
    }
}
