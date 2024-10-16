package assignment2;
import java.util.ArrayList;
import java.util.List;

public class PersonDirectory {
    private List<Person> personList;

    public PersonDirectory() {
        personList = new ArrayList<>();
    }

    public void addPerson(Person person) {
        personList.add(person);
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public Person searchPerson(String query) {
        for (Person person : personList) {
            String fullName = (person.getFirstName() + " " + person.getLastName()).trim();
            if (person.getFirstName().equalsIgnoreCase(query) ||
                    person.getLastName().equalsIgnoreCase(query) ||
                    person.getHomeAddress().getStreetAddress().equalsIgnoreCase(query) ||
                    fullName.equalsIgnoreCase(query)) {
                return person;
            }
        }
        return null;
    }

    public void removePerson(String firstName, String lastName) {
        personList.removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                person.getLastName().equalsIgnoreCase(lastName));
    }
}