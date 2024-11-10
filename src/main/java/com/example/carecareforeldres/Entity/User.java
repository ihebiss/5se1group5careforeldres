package com.example.carecareforeldres.Entity;

import com.example.carecareforeldres.DTO.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String region;
    private Integer nbr_tentatives;
    private String adresse;
    private Date sleep_time;
    private Boolean enabled;
    private Double x ;
    private Double y ;
    private String email;
    /////////////////////////////////////
    @Enumerated(EnumType.STRING)
    private TypePatient typeePatient;
    @Enumerated(EnumType.STRING)
    private Sexe sexee;
    private Boolean archiverr;
    private Float poidd;
    private Float longueurr;
    @Column(name = "datedenais")
    private LocalDate dateeDeNaissance;
    private String nom;
    private String prenom;
    private String mail;
    private Double xxx ;
    private Double yyy ;
    private String adressee;
    /////////////////////////////////////////////////////
    private Boolean disponiblee;
    @Enumerated(EnumType.STRING)
    private Specialite specialitee;
    private String nommed;
    private String prenommed;
    private String mailmed;
    private Double xx ;
    private Double yy ;
    private String adressemed;
    ////////////////////////////////////
    private Boolean disponiblenf;
    String nominf;
    String prenominf;
    String mailif;
    //////////////////////////////////////////////////////

    private LocalDate dateAjoutee;//date l'ajout plat délai
    @Enumerated(EnumType.STRING)
    private Sexe sexeeee;
    private Float salaire;
    private Boolean disponibleeee;
    ///////////////////////////////////////////
    private Integer age;
    private String situationMedicalee;
    private String situationSocialee;
    private String besoinsSpecifiquess;
    private String localisationActuellee;
    ////////////////////////////////////////////
    private String ffirstname;
    private String llastname;
    private String emaill;
    private String ntelephone;
    ///////////////////////////////////////////////
    private Boolean ddisponible;
    private Integer user;
    private String nome;
    private String prenome;

    ////////////////////////////////////////////
    private String passwd;
    private boolean mfaEnabled;
    private String secret;
    private int nombreReclamationsImportant;
    public int getNombreReclamationsImportant() {
        return nombreReclamationsImportant;
    }

    public void setNombreReclamationsImportant(int nombreReclamationsImportant) {
        this.nombreReclamationsImportant = nombreReclamationsImportant;
    }
    public UserDto toDTO() {
        return UserDto.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .region(region)
                .nbr_tentatives(nbr_tentatives)
                .sleep_time(sleep_time)
                .enabled(enabled)
                .email(email)
                .typeePatient(typeePatient)
                .sexee(sexee)
                .archiverr(archiverr)
                .poidd(poidd)
                .longueurr(longueurr)
                .dateeDeNaissance(dateeDeNaissance)
                .disponiblee(disponiblee)
                .specialitee(specialitee)
                .nom(nom)
                .prenom(prenom)
                .dateAjoutee(dateAjoutee)
                .sexeeee(sexeeee)
                .salaire(salaire)
                .disponibleeee(disponibleeee)
                .age(age)
                .situationMedicalee(situationMedicalee)
                .situationSocialee(situationSocialee)
                .besoinsSpecifiquess(besoinsSpecifiquess)
                .localisationActuellee(localisationActuellee)
                .ffirstname(ffirstname)
                .llastname(llastname)
                .emaill(emaill)
                .build();}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> roless = new ArrayList<>();
        for (Role authority : roles) {
            if (authority != null && authority.getName() != null)
                roless.add(new SimpleGrantedAuthority(authority.getName().name()));
            else
                System.out.println("----- U have no role ----");
        }
        return roless;
    }

    @ManyToMany
    @JoinTable(
            name = "utilisateur_produit_favori",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private List<Produit> produitsFavoris;

    public List<Produit> getProduitsFavoris() {
        return produitsFavoris;
    }

    public void setProduitsFavoris(List<Produit> produitsFavoris) {
        this.produitsFavoris = produitsFavoris;
    }
    private boolean interditDeReclamer;

    public boolean isInterditDeReclamer() {
        return interditDeReclamer;
    }

    public void setInterditDeReclamer(boolean interditDeReclamer) {
        this.interditDeReclamer = interditDeReclamer;
    }
   //role
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reclamation> reclamations;
    public Set<Role> getAuthFromBase() {
        return this.roles;
    }//role

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "UserAuth", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;
    //////////////////// ahiya il mmethode//////////// ray matkhdimch
    //  public Set<Role> getRoles() {return roles != null ? roles : Collections.emptySet();}
    @ManyToMany
    private List<Evennement> evennements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Commentaire> commentaires;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Reponse> reponses;
}
