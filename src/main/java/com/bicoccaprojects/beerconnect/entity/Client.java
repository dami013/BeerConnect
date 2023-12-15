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
    private Long id_client;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name_client;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;

    @Column(name = "date_birth", nullable = false, columnDefinition = "INTEGER")
    private Integer date_birth;

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
        this.name_client = name;
        this.email = email;
        this.date_birth = date_birth;
        this.address = address;
        this.preferences = preferences;
    }

    public Client() {

    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id) {
        this.id_client = id;
    }

    public String getName_client() {
        return name_client;
    }

    public void setName_client(String name) {
        this.name_client = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(Integer date_birth) {
        this.date_birth = date_birth;
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
                "id=" + id_client +
                ", name='" + name_client + '\'' +
                ", email='" + email + '\'' +
                ", date_birth=" + date_birth +
                ", address='" + address + '\'' +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}
