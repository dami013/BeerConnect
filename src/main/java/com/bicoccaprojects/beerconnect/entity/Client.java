package com.bicoccaprojects.beerconnect.entity;

import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Client")
@Table(name = "client", uniqueConstraints = {
                @UniqueConstraint(name = "client_email_unique", columnNames = "email")})
public class Client {
    @Id
    @SequenceGenerator(name="client_sequence", sequenceName = "client_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "client_sequence") // per avere ID che parte da 1 e incrementa di 1 per ogni entità nella tabella
    @Column(name = "id_client", updatable = false)
    private Long idClient;

    @Column(name = "name_client", nullable = false, columnDefinition = "TEXT")
    private String nameClient;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;

    @Column(name = "date_birth", nullable = false, columnDefinition = "INTEGER")
    private Integer dateBirth;

    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "preferences", nullable = false, columnDefinition = "TEXT")
    private String preferences;

    @ManyToMany
    @JoinTable(
            name = "client review",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_beer")
    )
    private List<Beer> beers;

    public Client(String name, String email, Integer date_birth, String address, String preferences) {
        this.nameClient = name;
        this.email = email;
        this.dateBirth = date_birth;
        this.address = address;
        this.preferences = preferences;
    }

    public Client() {

    }

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

    public Integer getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Integer date_birth) {
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
