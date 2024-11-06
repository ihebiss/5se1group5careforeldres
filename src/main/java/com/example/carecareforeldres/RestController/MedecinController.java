package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.DTO.MedecinDto;
import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Specialite;
import com.example.carecareforeldres.Service.IServiceMedecin;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/medecin")
public class MedecinController {
    IServiceMedecin serviceMedecin;
    @GetMapping("/retrieve-all-medecin")

    public List<MedecinDto> getMedecins() {
        List<Medecin> medecins = serviceMedecin.retrieveAllMedecins();
        List<MedecinDto>list=medecins.stream().map(medecin ->MedecinDto.toDto(medecin)).toList();
        return list ;

    }

    @GetMapping("/retrieve-medecin/{medecin-id}")

    public MedecinDto retrieveMed(@PathVariable("medecin-id") Integer idMedecin) {
        return MedecinDto.toDto(serviceMedecin.retrieveMedecin(idMedecin));
    }
    @PostMapping("/add-medecin")

    public MedecinDto addMedecin(@RequestBody MedecinDto medecin) {
        Medecin entity=MedecinDto.toEntity(medecin);
        return MedecinDto.toDto(serviceMedecin.addMedecin(entity));


    }

    @DeleteMapping("/remove-medecin/{medecin-id}")
    public void removeMed(@PathVariable("medecin-id") Integer idMedecin) {
        serviceMedecin.removeMedecin(idMedecin);
    }
    @PutMapping("/update-med")

    public MedecinDto updateMed(@RequestBody MedecinDto medecin) {
        Medecin entity=MedecinDto.toEntity(medecin);
        return MedecinDto.toDto(serviceMedecin.updateMedecin(entity));
    }
    @GetMapping("/retrieve-medecinSpec/{medecin-specialite}")

    public MedecinDto retrieveMedBySpecialite(@PathVariable("medecin-specialite") Specialite specialite) {
        return MedecinDto.toDto(serviceMedecin.retrieveMedecinBySpecialite(specialite));
    }
    @PutMapping("/medecinss/{idmed}/{idetab}")
    @ResponseBody
    public Medecin affecterMedecinToEtab(@PathVariable("idmed") Integer idMedecin, @PathVariable("idetab")Long idEtab ) {

        return serviceMedecin.addMedecinAndAssignToEtabliss(idMedecin,idEtab);
    }
    @PutMapping("/addall-medecin/{idetab}")
    public List<Medecin>  affecterMedsToEtab( @RequestBody List<Medecin> medecins,@PathVariable("idetab") Long idEtab) {
    return serviceMedecin.ajouterMedecinToEtab(medecins,idEtab);
    }

    @GetMapping("/medecinrev/{idmed}/{startDate}/{endDate}")
    @ResponseBody
    public int getNbrRdv (@PathVariable("idmed") Integer idMedecin,
                          @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
                          @PathVariable("endDate")@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate){
        return serviceMedecin.getNbrRndvBetweenTwoDates(idMedecin,startDate,endDate);
    }

    @PutMapping("/medecins-desaf/{idmed}")
    @ResponseBody
    public Medecin desactfMedecinEtabliss(@PathVariable("idmed") Integer idMedecin ) {

        return serviceMedecin.desactfMedecinEtabliss(idMedecin);
    }
    @GetMapping("/top3Medecins/{startDate}/{endDate}")
    public List<Medecin> getTop3Medecins(@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
                                         @PathVariable("endDate")@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
        return serviceMedecin.findTop3MedecinBenevole(startDate, endDate);
    }
}

