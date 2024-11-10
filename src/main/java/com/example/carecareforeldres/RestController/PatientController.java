package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.DTO.PatientDto;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Service.IServicePatient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    IServicePatient servicePatient;
    @GetMapping("/retrieve-all-patient")

    public List<PatientDto> getPatients() {
        List<Patient> patients = servicePatient.retrieveAllPatients();
        List<PatientDto>list=patients.stream().map(patient ->PatientDto.toDto(patient)).toList();
        return list ;
//déjà consomée
    }

    @GetMapping("/retrieve-patient/{patient-id}")

    public PatientDto retrievePatient(@PathVariable("patient-id") Integer idpatient) {
        return PatientDto.toDto(servicePatient.retrievePatient(idpatient));
    }
    @PostMapping("/add-patient")

    public PatientDto addPatient(@RequestBody PatientDto patient) {
        Patient entity=PatientDto.toEntity(patient);
        return PatientDto.toDto(servicePatient.addPatient(entity));

    }

    @DeleteMapping("/remove-patient/{patient-id}")
    public void removePatient(@PathVariable("patient-id") Integer idpatient) {
        servicePatient.removePatient(idpatient);
    }
    @PutMapping("/update-patient")

    public PatientDto updatePatient(@RequestBody PatientDto patient) {
        Patient entity=PatientDto.toEntity(patient);
        return PatientDto.toDto(servicePatient.updatePatient(entity));
        //déjà consomée

    }

    @PutMapping("/update-patient-Etab")
    public Patient updatePatient(@RequestBody Patient patient) {
        return servicePatient.miseAjourPatientEtablisement_Genric(patient);
        //déjà consomée

    }
    @PutMapping("/patientAmb/{idpatient}/{idAmb}")
    @ResponseBody
    public Patient AssignPatientToAmbulance(@PathVariable("idpatient")Integer idpatient,@PathVariable("idAmb")Long idAmb ) {
    return servicePatient.AssignPatientToAmbulance(idpatient,idAmb) ;
    }//déjà consomée


    @PutMapping("/patient-aff/{idpatient}/{idEtab}/{idMed}/{idInfermier}")
    @ResponseBody
    public Patient UnsPatientFromAmbulanceAndAffectToEtab(@PathVariable("idpatient")Integer idpatient,@PathVariable("idEtab")Long idEtab,
                                            @PathVariable("idMed")Integer idMed,@PathVariable("idInfermier")Integer idInfermier ) {
        return servicePatient.UnassignPatientFromAmbulanceAndAffectToEtab(idpatient,idEtab,idMed,idInfermier) ;
    }//déjà consomée



    @PutMapping("/desaffect-Morgue/{idpatient}")
    @ResponseBody
    public Patient UnassignPatientFromMorgue(@PathVariable("idpatient")Integer idpatient ) {
        return servicePatient.UnassignPatientFromMorgue(idpatient);
    //déjà consomée

}
    @PutMapping("/patientt/{idpatient}/{idInf}")
    @ResponseBody
    public Patient AssignInfermierToPatient(@PathVariable("idpatient")Integer idpatient,@PathVariable("idInf")Integer idInfermier ) {
        return servicePatient.AssignInfermierTopPatient(idpatient,idInfermier);
    }
    @GetMapping("/retrieveAmb-idAmbulance/{idpa}")
    public Long retrieveidAmbulance(@PathVariable("idpa") Integer idpatient) throws IOException {
        return servicePatient.retrieveidAmbulance(idpatient);
//déjà consomée
    }
        @GetMapping("/retrieveAllPatientAmb")
    public List<Patient> retrievePatientinAmbulance() {
        return servicePatient.GetPatientsALlInAmbulance();
    }//déjà consomée

    @GetMapping("/retrieve-Pat-death")
    public List<Patient> GetPatientsDeath() {
        return servicePatient.GetPatientsDeath();
//déjà consomée

    }
    @GetMapping("/retrievedateLast/{idpatient}")
    public LocalDateTime findRdvByPatient(@PathVariable("idpatient")Integer idpatient) {
        return servicePatient.DateDEDrnierRdv(idpatient);
}
    @GetMapping("/retrieveAllinEtab/{idEtab}")
    public Map<String, Float> retrievePatientinEtab(@PathVariable("idEtab")Long idEtab) {
        return servicePatient.FindPatirntInEtab(idEtab);
    }//déjà consomée
}

