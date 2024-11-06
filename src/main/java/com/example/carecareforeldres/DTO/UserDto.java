package com.example.carecareforeldres.DTO;

import com.example.carecareforeldres.Entity.Produit;
import com.example.carecareforeldres.Entity.Sexe;
import com.example.carecareforeldres.Entity.Specialite;
import com.example.carecareforeldres.Entity.TypePatient;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

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
    private Integer age;
    private String situationMedicalee;
    private String situationSocialee;
    /////////////////////////////////////
    private TypePatient typeePatient;
    private Sexe sexee;
    private Boolean archiverr;
    private Float poidd;
    private Float longueurr;
    private LocalDate dateeDeNaissance;
    private String nomm;
    private String prenomm;
    private String mailm;
    private Double xxx ;
    private Double yyy ;
    private String adressee;
    /////////////////////////////////////////////////////
    private Boolean disponiblee;
    private Specialite specialitee;
    private String nommed;
    private String prenommed;
    private String mailmed;
    private Double xx ;
    private Double yy ;
    private String adressemed;
    private String besoinsSpecifiquess;
    private String localisationActuellee;
    private String ffirstname;
    private String llastname;
    private String emaill;
    ////////////////////////////////////
    private Boolean disponiblenf;
    String nominf;
    String prenominf;
    String mailif;
    private int nombreReclamationsImportant;
    private List<Produit> produitsFavoris;
    private boolean interditDeReclamer;
    //////////////////////////////////////////////////////
    private String nom;
    private String prenom;
    private LocalDate dateAjoutee;//date l'ajout plat délai
    private Sexe sexeeee;
    private Float salaire;
    private Boolean disponibleeee;
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleDTO {
        private String name;
    }
    public void setNombreReclamationsImportant(int nombreReclamationsImportant) {
        this.nombreReclamationsImportant = nombreReclamationsImportant;
    }
    public int getNombreReclamationsImportant() {
        return nombreReclamationsImportant;
    }


}
