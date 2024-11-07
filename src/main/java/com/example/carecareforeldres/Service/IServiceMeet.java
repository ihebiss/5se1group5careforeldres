package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Meeting;

import java.util.Map;


public interface IServiceMeet {

    Meeting addMeeting(Meeting meeting, Integer organisateurId);

    Meeting updateMeetingWithPatient(Long meetingId, Integer patientId);


    Map<String, Double> calculateParticipationRate();

    Map<String, Map<String, Double>> calculateParticipationRateByMonth();
}
