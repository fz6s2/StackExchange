package ru.inp.stackexchange.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stack Exchange API question.owner class.
 * Can be parsed from JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SEQuestionOwner {
   
    @JsonProperty("reputation")
    private int reputation;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("user_type")
    private String userType;
    @JsonProperty("profile_image")
    private String profileImage;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("link")
    private String link;
    
    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    @Override
    public int hashCode() {
        return Long.valueOf(userId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SEQuestionOwner other = (SEQuestionOwner) obj;
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }
}
