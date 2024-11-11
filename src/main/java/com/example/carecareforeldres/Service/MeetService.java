package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Meeting;
import com.example.carecareforeldres.Entity.Organisateur;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Repository.MeetRepository;
import com.example.carecareforeldres.Repository.OrganisateurRepository;
import com.example.carecareforeldres.Repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class MeetService implements IServiceMeet {

    MeetRepository meetRepository;
    OrganisateurRepository organisateurRepository;
    PatientRepository userRepository ;

    @Override

    public Meeting addMeeting(Meeting meeting, Integer organisateurId) {
        Organisateur organisateur = organisateurRepository.findById(organisateurId).orElse(null);
        if (organisateur == null) {
            // Gérer l'erreur si l'organisateur n'est pas trouvé
            return null;
        }

        meeting.setOrganisateur(organisateur);
        // Laissez l'ID du patient null par défaut
        meeting.setPatient(null);
        return meetRepository.save(meeting);

    }
    @Override

    public Meeting updateMeetingWithPatient(Long meetingId, Integer patientId) {
        Patient patient = userRepository.findById(patientId).get();
        Meeting meeting = meetRepository.findById(meetingId).orElse(null);
        if (meeting == null) {
            // Gérer l'erreur si la réunion n'est pas trouvée
            return null;
        }

        // Mettre à jour l'ID du patient dans la réunion
        meeting.setPatient(patient);

        return meetRepository.save(meeting);
    }
    @Override
    public Map<String, Double> calculateParticipationRate() {
        // Récupérer toutes les réunions
        List<Meeting> meetings = meetRepository.findAll();
        Map<String, Integer> participationCount = new HashMap<>();
        Map<String, Double> participationRate = new HashMap<>();

        // Compter le nombre de réunions auxquelles chaque patient a participé
        for (Meeting meeting : meetings) {
            if (meeting.getPatient() != null) {
                String patientName = meeting.getPatient().getNom(); // Supposons que getNom() récupère le nom du patient
                participationCount.put(patientName, participationCount.getOrDefault(patientName, 0) + 1);
            }
        }

        // Calculer le pourcentage de participation pour chaque patient
        int totalMeetings = meetings.size();
        for (Map.Entry<String, Integer> entry : participationCount.entrySet()) {
            String patientName = entry.getKey();
            int meetingsAttended = entry.getValue();
            double participationPercentage = (double) meetingsAttended / totalMeetings * 100;
            participationRate.put(patientName, participationPercentage);
        }

        return participationRate;
    }
    @Override
    public Map<String, Map<String, Double>> calculateParticipationRateByMonth() {
        // Récupérer toutes les réunions
        List<Meeting> meetings = meetRepository.findAll();
        Map<String, Map<String, Integer>> participationCountsByMonth = new HashMap<>();
        Map<String, Map<String, Double>> participationRatesByMonth = new HashMap<>();

        // Initialiser les données pour chaque mois
        for (int month = 1; month <= 12; month++) {
            String monthKey = String.format("%02d", month); // Formatage du mois pour obtenir des nombres à deux chiffres (01, 02, ..., 12)
            participationCountsByMonth.put(monthKey, new HashMap<>());
            participationRatesByMonth.put(monthKey, new HashMap<>());
        }

        // Compter le nombre de réunions auxquelles chaque patient a participé pour chaque mois
        for (Meeting meeting : meetings) {
            if (meeting.getPatient() != null && meeting.getDateMeet() != null) {
                String monthKey = new SimpleDateFormat("MM").format(meeting.getDateMeet());
                String patientName = meeting.getPatient().getNom();

                Map<String, Integer> participationCountForMonth = participationCountsByMonth.get(monthKey);
                participationCountForMonth.put(patientName, participationCountForMonth.getOrDefault(patientName, 0) + 1);
            }
        }

        // Calculer le pourcentage de participation pour chaque patient pour chaque mois
        for (Map.Entry<String, Map<String, Integer>> monthEntry : participationCountsByMonth.entrySet()) {
            String monthKey = monthEntry.getKey();
            Map<String, Integer> participationCountForMonth = monthEntry.getValue();
            int totalMeetingsForMonth = participationCountForMonth.values().stream().mapToInt(Integer::intValue).sum();

            for (Map.Entry<String, Integer> entry : participationCountForMonth.entrySet()) {
                String patientName = entry.getKey();
                int meetingsAttended = entry.getValue();
                double participationPercentage = (double) meetingsAttended / totalMeetingsForMonth * 100;

                Map<String, Double> participationRatesForMonth = participationRatesByMonth.get(monthKey);
                participationRatesForMonth.put(patientName, participationPercentage);
            }
        }

        return participationRatesByMonth;
    }
}

















