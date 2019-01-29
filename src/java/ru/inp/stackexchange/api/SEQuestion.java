package ru.inp.stackexchange.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import ru.inp.stackexchange.format.JSONUnixTimeDeserializer;

/**
 * Stack Exchange API question class.
 * Can be parsed from JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SEQuestion {

    public SEQuestionOwner getOwner() {
        return owner;
    }

    public void setOwner(SEQuestionOwner owner) {
        this.owner = owner;
    }

    @JsonProperty("title")
    private String title;
    @JsonProperty("link")
    private String link;
    @JsonProperty("question_id")
    private long questionId;
    @JsonProperty("score")
    private int score;
    @JsonProperty("view_count")
    private int viewCount;
    @JsonProperty("is_answered")
    private boolean isAnswered;
    @JsonProperty("answer_count")
    private int answerCount;
    @JsonProperty("creation_date")
    @JsonDeserialize(using = JSONUnixTimeDeserializer.class) 
    private LocalDateTime creationDate;
    @JsonProperty("last_activity_date")
    @JsonDeserialize(using = JSONUnixTimeDeserializer.class)
    private LocalDateTime lastActivityDate;
    @JsonProperty("owner")
    SEQuestionOwner owner;
    
    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int aAnswerCount) {
        answerCount = aAnswerCount;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String aTitle) {
        title = aTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String aLink) {
        link = aLink;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long aQuestionId) {
        questionId = aQuestionId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int aScore) {
        score = aScore;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int aViewCount) {
        viewCount = aViewCount;
    }

    public boolean isIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(boolean aIsAnswered) {
        isAnswered = aIsAnswered;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime aCreationDate) {
        creationDate = aCreationDate;
    }

    public LocalDateTime getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDateTime aLastActivityDate) {
        lastActivityDate = aLastActivityDate;
    }
 
    @Override
    public int hashCode() {
        return Long.valueOf(questionId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SEQuestion other = (SEQuestion) obj;
        if (this.questionId != other.questionId) {
            return false;
        }
        return true;
    }        
}
