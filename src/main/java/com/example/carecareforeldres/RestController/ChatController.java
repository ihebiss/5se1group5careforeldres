package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Message;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Repository.MedecinRepository;
import com.example.carecareforeldres.Repository.MessageRepository;
import com.example.carecareforeldres.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/chat")

public class ChatController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private PatientRepository patientRepository;



    @PostMapping("/medecin/{idMedecin}/patient/{idPatient}/send/{contenu}")
    public void sendMessageToPatient(@PathVariable Integer idMedecin, @PathVariable Integer idPatient, @PathVariable String contenu) {
        Medecin medecin = medecinRepository.findById(idMedecin).orElseThrow(() -> new NotFoundException("Medecin not found"));
        Patient patient = patientRepository.findById(idPatient).orElseThrow(() -> new NotFoundException("Patient not found"));

        Message message = new Message();
        message.setContenu(contenu);
        message.setMedecin(medecin);
        message.setPatient(patient);
        message.setEnvoye(false);
        message.setDateEnvoi(LocalDateTime.now());
        messageRepository.save(message);
    }

    @PostMapping("/patient/{idPatient}/medecin/{idMedecin}/send/{message}")
    public void sendMessageToMedecin(@PathVariable Integer idPatient, @PathVariable Integer idMedecin, @PathVariable String message) {
        Medecin medecin = medecinRepository.findById(idMedecin).orElseThrow(() -> new NotFoundException("Medecin not found"));
        Patient patient = patientRepository.findById(idPatient).orElseThrow(() -> new NotFoundException("Patient not found"));

        Message message1 = new Message();
        message1.setContenu(message);
        message1.setMedecin(medecin);
        message1.setPatient(patient);
        message1.setDateEnvoi(LocalDateTime.now());
        message1.setEnvoye(true);
        messageRepository.save(message1);
    }

    @GetMapping("/medecin/{idMedecin}/patient/{idPatient}/messages")
    public List<Message> getMessagesFromMedecinToPatient(@PathVariable Integer idMedecin, @PathVariable Integer idPatient) {
        Medecin medecin = medecinRepository.findById(idMedecin).orElseThrow(() -> new NotFoundException("Medecin not found"));
        Patient patient = patientRepository.findById(idPatient).orElseThrow(() -> new NotFoundException("Patient not found"));

        return messageRepository.findByMedecinAndPatient(medecin, patient);
    }

    @GetMapping("/patient/{idPatient}/medecin/{idMedecin}/messages")
    public List<Message> getMessagesFromPatientToMedecin(@PathVariable Integer idPatient, @PathVariable Integer idMedecin) {
        Medecin medecin = medecinRepository.findById(idMedecin).orElseThrow(() -> new NotFoundException("Medecin not found"));
        Patient patient = patientRepository.findById(idPatient).orElseThrow(() -> new NotFoundException("Patient not found"));

        return messageRepository.findByPatientAndMedecin(patient, medecin);
    }

    @GetMapping("/getAllPatients")
    public List<Patient> getAllPatients() {

        return patientRepository.findAll();

    }

    @GetMapping("/getAllMedecin")
    public List<Medecin> getAllCuisinier() {

        return medecinRepository.findAll();

    }




}