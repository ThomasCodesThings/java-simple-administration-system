package base;

public abstract class Person extends Residence{
    private String firstName;
    private String lastName;
    private String gender;
    private int age;

    public Person() {
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public Person(String firstName, String lastName, String gender, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        if(gender.equals("male") || gender.equals("female")){
            this.gender = gender;
        }
        if(age >= 15){
            this.age = age;
        }
    }

    public Person(String firstName, String lastName, String gender, int age, String townName, String PSC, String street){
        super(townName, PSC, street);
        this.firstName = firstName;
        this.lastName = lastName;
        if(gender.equals("male") || gender.equals("female")){
            this.gender = gender;
        }
        if(age >= 15){
            this.age = age;
        }
    }
}
