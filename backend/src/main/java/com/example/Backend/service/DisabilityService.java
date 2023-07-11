package com.example.Backend.service;

import com.example.Backend.Repository.DisabilityRepository;
import com.example.Backend.model.Disability;
import com.example.Backend.schema.DisabilityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisabilityService {
    @Autowired
    private DisabilityRepository disabilityRepository;

    public List<DisabilityInfo> getAllDisabilities(){
        List<DisabilityInfo> disabilityInfoList = new ArrayList<>();
        List<Disability> disabilities = disabilityRepository.findAll();
        for(Disability disability : disabilities){
            disabilityInfoList.add(
                    new DisabilityInfo(
                            disability.getDisabilityId(),
                            disability.getDisabilityName())
            );
        }
        return disabilityInfoList;
    }
}
