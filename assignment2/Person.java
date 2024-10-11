package assignment2;

public class Person {
    private String firstName;
    private String lastName;
    private Address homeAddress;
    private Address workAddress;
    public Person(String firstName, String lastName, String homeAddress, String workAddress){
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
    }
}
