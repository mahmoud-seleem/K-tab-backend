package com.example.Backend.schema;

import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component
public class ContributionInfo {

    private String ownerId;
    private String bookId;
    private String contributorId;
    private List<String> chaptersIds;

    public ContributionInfo() {
    }

    public ContributionInfo(String ownerId, String bookId, String contributorId, List<String> chaptersIds) {
        this.ownerId = ownerId;
        this.bookId = bookId;
        this.contributorId = contributorId;
        this.chaptersIds = chaptersIds;
    }

    public UUID getOwnerId() {
        return convertStringToUUID(ownerId);
    }

    public void setOwnerId(String ownerId) throws InputNotLogicallyValidException {
        validateUUIDString("ownerId",ownerId);
        this.ownerId = ownerId;
    }
    public void setOwnerId(UUID ownerId){
        this.ownerId = ownerId.toString();
    }

    public UUID getBookId() {
        return convertStringToUUID(bookId);
    }

    public void setBookId(String bookId) throws InputNotLogicallyValidException {
        validateUUIDString("bookId",bookId);
        this.bookId = bookId;
    }
    public void setBookId(UUID bookId){
        this.bookId = bookId.toString();
    }

    public UUID getContributorId() {
        return convertStringToUUID(contributorId);
    }

    public void setContributorId(String contributorId) throws InputNotLogicallyValidException {
        validateUUIDString("contributorId",contributorId);
        this.contributorId = contributorId;
    }

    public void setContributorId(UUID contributorId){
        this.contributorId = contributorId.toString();
    }
    public List<String> getChaptersIds() {
        return chaptersIds;
    }

    public void setChaptersIds(List<String> chaptersIds) throws InputNotLogicallyValidException {
        if(chaptersIds != null){
            for(int i = 0 ; i<chaptersIds.size();i++){
                validateUUIDString("chaptersIds["+i+"]" ,chaptersIds.get(i));
            }
        }
        ValidationUtils validationUtils = new ValidationUtils();
        validationUtils.checkForEmptyList("chaptersIds",chaptersIds);
        this.chaptersIds = chaptersIds;
    }
    private UUID convertStringToUUID(String s){
        if (bookId == null){
            return null;
        }
        return UUID.fromString(bookId);
    }
    private void validateUUIDString(String name,String value) throws InputNotLogicallyValidException {
        if(ownerId != null){
            ValidationUtils validationUtils = new ValidationUtils();
            validationUtils.checkForValidUUIDString(name,value);
        }
    }
}
