package com.bicoccaprojects.beerconnect.entity;

import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * The Client class is a JPA entity mapped to the "client" table in the database, managing attributes such as
 * name, email, date of birth, address, and preferences. It includes uniqueness constraints for email
 * and a validation rule requiring the client to be of legal age.
 * The class handles relationships between clients, implementing a self-loop relationship to track other
 * clients. Additionally, it provides methods to retrieve and set the clients being followed and those following the current client.
 */

@Entity(name = "Client")
@Table(name = "client", uniqueConstraints = {
                @UniqueConstraint(name = "client_email_unique", columnNames = "email")}) // email must be unique for each Client
public class Client {
    @Id
    @SequenceGenerator(name = "client_sequence", sequenceName = "client_sequence", allocationSize = 1,initialValue = 14)
    @GeneratedValue(strategy = SEQUENCE, generator = "client_sequence")
    @Column(name = "id_client", updatable = false)
    private Long idClient;

    @Column(name = "name_client", nullable = false)
    private String nameClient;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_birth", nullable = false)
    private LocalDate dateBirth;


    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "preferences", nullable = false)
    private String preferences;

    // Method that validates the date of birth by checking whether the client is > 18 years old
    @PrePersist
    @PreUpdate
    private void validateBirthDate() {
        if (dateBirth != null) {
            LocalDate now = LocalDate.now();
            LocalDate eighteenYearsAgo = now.minusYears(18);

            if (dateBirth.isAfter(eighteenYearsAgo)) {
                throw new IllegalStateException("You must be of legal age.");
            }
        }
    }

    // foreign key for client_review table
    @OneToMany(mappedBy = "idClient", cascade = CascadeType.REMOVE)
    private List<ClientReview> clientReviews;

    // self-loop relationship between clients
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "client_to_client",
            joinColumns = @JoinColumn(name = "id_client_followed"),
            inverseJoinColumns = @JoinColumn(name = "client_id"),
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"client_id", "id_client_followed"})
            })
    private Set<Client> clientFollowers = new HashSet<Client>(); // altri utenti che seguono il Client soggetto

    @ManyToMany(mappedBy = "clientFollowers", fetch = FetchType.LAZY)
    private Set<Client> followedByClient = new HashSet<Client>(); // utenti seguiti dal client soggetto

    public Client(String name, String email, LocalDate date_birth, String address, String preferences) {
        this.nameClient = name;
        this.email = email;
        this.dateBirth = date_birth;
        this.address = address;
        this.preferences = preferences;
    }
    public Client(Long idClient, String name, String email, LocalDate date_birth, String address, String preferences) {
        this.idClient = idClient;
        this.nameClient = name;
        this.email = email;
        this.dateBirth = date_birth;
        this.address = address;
        this.preferences = preferences;
    }

    // constructors
    public Client(Long idClient) {
        this.idClient = idClient;
    }

    public Client() {

    }

    // setter and getter

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long id) {
        this.idClient = id;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String name) {
        this.nameClient = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate date_birth) {
        this.dateBirth = date_birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Set<Client> getClientFollowers(){
        return clientFollowers;
    }

    public Set<Client> getFollowedByClient(){
        return followedByClient;
    }

    public void setClientFollowers(Set<Client> followers) {
        this.clientFollowers = followers;
    }

    public void setFollowedByClient(Set<Client> following) {
        this.followedByClient = following;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + idClient +
                ", name='" + nameClient + '\'' +
                ", email='" + email + '\'' +
                ", date_birth=" + dateBirth +
                ", address='" + address + '\'' +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}
