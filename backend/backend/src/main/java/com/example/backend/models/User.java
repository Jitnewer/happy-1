package com.example.backend.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "mail")})
public class User {
    public static final String ADMIN = "ADMIN";
    public static final String ENTREPRENEUR = "ENTREPRENEUR";
    public static final String PARTNER = "PARTNER";
    public static final String SUPERUSER = "SUPERUSER";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String BANNED = "BANNED";
    public static final String UNBANNED = "UNBANNED";
    @Id
    @GeneratedValue
    private long id;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserEvent> userEvents = new HashSet<>();
    public enum UserType {
        ADMIN,
        ENTREPRENEUR,
        PARTNER,
        SUPERUSER
    }
    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        BANNED,
        UNBANNED
    }
    private int id;
    private static int idCounter = 3001;
    private String profilePic;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String mail;
    private String gender;
    private int age;
    @Column(nullable = false)
    private String companyType;
    @Column(nullable = false)
    private String tag;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String userType;
    @Column(nullable = false)
    private UserStatus status;
    private UserType userType;
    private String postalCode;
    @Column(nullable = false)
    private String password;

    public User() {

    }

    public long getId() {
        return id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getMail() {
        return mail;
    }
    public Set<UserEvent> getUserEvents() {
        return userEvents;
    }
    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getCompanyType() {
        return companyType;
    }

    public String getTag() {
        return tag;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public User(String profilePic, String firstname, String lastname, String mail, String gender, int age, String companyType, String tag, String status, String userType, String postalCode, String password) {
        this.profilePic = profilePic;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.gender = gender;
        this.age = age;
        this.companyType = companyType;
        this.tag = tag;
        this.status = status;
        this.userType = userType;
        this.postalCode = postalCode;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public static User copyConstructor(User user) {
        if (user == null) return null;

        return new User(
                user.profilePic,
                user.firstname,
                user.lastname,
                user.mail,
                user.gender,
                user.age,
                user.companyType,
                user.tag,
                user.status,
                user.userType,
                user.postalCode,
                user.password
        );
    }

    public String getPassword() {
        return password;
    }

    public static User createSampleUser() {
        Random random = new Random();
        String image = "profilepic.png";

        String[] firstnames = {"Tim", "Mylo", "Pim", "Eva"};
        String[] lastnames = {"Groot", "van Dijk", "de Jong", "de Graaf"};
        int randomIndex = random.nextInt(firstnames.length);
        String randomFirstname = firstnames[randomIndex];
        String randomLastname = lastnames[randomIndex];

        randomIndex = random.nextInt(firstnames.length);
        UserType[] userTypeValues = {UserType.ADMIN, UserType.ENTREPRENEUR, UserType.PARTNER, UserType.SUPERUSER};
        UserType randomUserType = userTypeValues[randomIndex];

        String mail = randomFirstname.toLowerCase() + randomLastname.replaceAll("\\s", "").toLowerCase() + "@gmail.com";
        String gender = "male";
        String tag = (randomUserType.equals(UserType.PARTNER)) ? "Chain-partner" : "N/A";

        double randomStatusNum = random.nextDouble();
        UserStatus status = (randomStatusNum < 0.5) ? UserStatus.ACTIVE : UserStatus.INACTIVE;
        int age = random.nextInt(50) + 50;
        String companyType = "Catering";
        String postalCode = "1242 DA";

        return new User(image, randomFirstname, randomLastname, mail, gender, age, companyType, tag, status, randomUserType, postalCode, "test");
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", profilePic='" + profilePic + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", companyType='" + companyType + '\'' +
                ", tag='" + tag + '\'' +
                ", status='" + status + '\'' +
                ", userType='" + userType + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
