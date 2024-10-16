package assignment2;
import java.util.ArrayList;
import java.util.List;

public class PersonDirectory {
    private List<Person> personList;

    public PersonDirectory() {
        personList = new ArrayList<>();
        personList.add(new Person("John", "Doe", "123456789", "john.doe@example.com", 30, 175.5, 70.0, 'M', new Address("123 Main St", "A1", "City1", "State1", "10001", 1234567890L), new Address("456 Oak St", "B1", "City2", "State2", "20002", 1234567891L)));
        personList.add(new Person("Jane", "Smith", "987654321", "jane.smith@example.com", 28, 165.0, 60.0, 'F', new Address("789 Pine St", "C1", "City3", "State3", "30003", 9876543210L), new Address("321 Elm St", "D1", "City4", "State4", "40004", 9876543211L)));
        personList.add(new Person("Alice", "Brown", "111223333", "alice.brown@example.com", 35, 168.0, 65.0, 'F', new Address("654 Maple St", "E1", "City5", "State5", "50005", 1112233334L), new Address("987 Cedar St", "F1", "City6", "State6", "60006", 1112233335L)));
        personList.add(new Person("Bob", "White", "444556666", "bob.white@example.com", 40, 180.0, 80.0, 'M', new Address("159 Birch St", "G1", "City7", "State7", "70007", 4445566667L), new Address("753 Aspen St", "H1", "City8", "State8", "80008", 4445566668L)));
        personList.add(new Person("Charlie", "Green", "777889999", "charlie.green@example.com", 32, 172.0, 75.0, 'M', new Address("951 Fir St", "I1", "City9", "State9", "90009", 7778899990L), new Address("159 Spruce St", "J1", "City10", "State10", "100010", 7778899991L)));
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