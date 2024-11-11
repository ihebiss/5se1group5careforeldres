package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.Entity.Meeting;
import com.example.carecareforeldres.Service.IServiceMeet;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/meet")
public class MeetingController {
  //  private MeetInterface meetInterface;
   private final IServiceMeet serviceMeet;



    @PostMapping("/addMeet/{idOrganisateur}")

        public ResponseEntity<Meeting> addMeeting(@RequestBody Meeting meeting,@PathVariable("idOrganisateur") Integer idOrganisateur) {
        Meeting addedMeeting = serviceMeet.addMeeting(meeting,idOrganisateur);
        return new ResponseEntity<>(addedMeeting, HttpStatus.CREATED);
    }

    @PutMapping("/{meetId}/{patientId}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable Long meetId, @PathVariable("patientId") Integer patientId) {
        Meeting updatedMeeting = serviceMeet.updateMeetingWithPatient(meetId,patientId);
        return new ResponseEntity<>(updatedMeeting, HttpStatus.OK);
    }
    @GetMapping("/participationRate")
    public ResponseEntity<Map<String, Double>> getParticipationRate() {
        Map<String, Double> participationRate = serviceMeet.calculateParticipationRate();
        return new ResponseEntity<>(participationRate, HttpStatus.OK);
    }
    @GetMapping("/participation-rate-by-month")
    public ResponseEntity<Map<String, Map<String, Double>>> getParticipationRateByMonth() {
        Map<String, Map<String, Double>> participationRatesByMonth = serviceMeet.calculateParticipationRateByMonth();
        return new ResponseEntity<>(participationRatesByMonth, HttpStatus.OK);
    }
}