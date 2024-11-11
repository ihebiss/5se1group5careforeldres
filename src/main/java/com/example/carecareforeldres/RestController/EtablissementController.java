package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.DTO.EtablissementDto;
import com.example.carecareforeldres.DTO.InfermierDto;
import com.example.carecareforeldres.DTO.MedecinDto;
import com.example.carecareforeldres.DTO.PatientDto;
import com.example.carecareforeldres.Entity.Etablissement;
import com.example.carecareforeldres.Entity.Infermier;
import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Service.IServiceEtablissement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/etab")
@CrossOrigin("*")

public class EtablissementController {
    IServiceEtablissement serviceEtablissement;
    @GetMapping("/retrieve-all-etab")
    public List<EtablissementDto> getEtablissements() {
        List<Etablissement> etablissements = serviceEtablissement.retrieveAllEtablissements();
        List<EtablissementDto> list = etablissements.stream().map(etablissement -> EtablissementDto.toDto(etablissement)).toList();
        return list;
    }

    @GetMapping("/retrieve-etab/{etab-id}")
    public EtablissementDto retrieveEtablissement(@PathVariable("etab-id") Long idEtab) {
        return   EtablissementDto.toDto(serviceEtablissement.retrieveEtablissement(idEtab)) ;


    }

    @PostMapping("/add-etab")
    //public Etablissement addEtablissement(@RequestBody Etablissement etablissement) {
    //  return serviceEtablissement.addEtablissement(etablissement);
    public EtablissementDto addEtablissement(@RequestBody EtablissementDto etablissement) throws IOException {
        Etablissement entity = EtablissementDto.toEntity(etablissement);
        return EtablissementDto.toDto(serviceEtablissement.addEtablissement(entity));
    }

    @DeleteMapping("/remove-etab/{etab-id}")
    public void removeEtablissement(@PathVariable("etab-id") Long idEtab) {
        serviceEtablissement.removeEtablissement(idEtab);
    }
    @PutMapping("/update-etab")
    /*public Etablissement updateEtablissement(@RequestBody Etablissement etablissement) {
        return serviceEtablissement.updateEtablissement(etablissement);*/
    public EtablissementDto updateEtablissement(@RequestBody EtablissementDto etablissement)throws IOException {
        Etablissement entity=EtablissementDto.toEntity(etablissement);
        return EtablissementDto.toDto(serviceEtablissement.updateEtablissement(entity));
    }
    @GetMapping("/etabMed/{idEtab}")
    public List<MedecinDto> getMedecinsByEtab(@PathVariable("idEtab") Long idEtab) {

        List<Medecin> medecins=serviceEtablissement.getMedecinsByEtab(idEtab);;
        List<MedecinDto> list = medecins.stream().map(medecin -> MedecinDto.toDto(medecin)).toList();
        return list;
    }

    @GetMapping("/etabinf/{idEtab}")
    public List<InfermierDto> getInfirmiersByEtab(@PathVariable("idEtab") Long idEtab) {

        List<Infermier> infermiers=serviceEtablissement.getInfirmiersByEtab(idEtab);
        List<InfermierDto> list = infermiers.stream().map(infermier -> InfermierDto.toDto(infermier)).toList();
        return list;
    }

    @GetMapping("/etabpat/{idMed}")
    public List<PatientDto> getpatientsByEtab(@PathVariable("idMed") Integer idMed) {
        List<Patient> patients=serviceEtablissement.getPatientsByEtabMedecin(idMed);
        List<PatientDto> list = patients.stream().map(patient -> PatientDto.toDto(patient)).toList();
        return list;
    }
}
