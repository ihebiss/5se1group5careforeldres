package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Attendence;

import java.util.List;

public interface IAttendenceService {

    public List<Attendence> retrieveAllAttendence();
    public Attendence retrieveAttendence(Long attendenceId);
//    public Attendence addAttendence(Attendence a);
    //update add with user id
public Attendence addAttendence(Integer userId,Attendence a);

    Long startAttendance(Integer userId);

    public void removeAttendence(Long attendenceId);
    public Attendence modifyAttendence(Attendence attendence);

    void endAttendance(Long attendenceId);
    List<Attendence> retrievePresence(Integer cuisinierId);
}
