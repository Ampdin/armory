package com.spirovanni.blackshields.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Task entity.
 */
public class TaskDTO implements Serializable {

    private Long id;

    private String taskID;

    private String taskName;

    private String scaleID;

    private String scaleName;

    private BigDecimal dataValue;

    private Long n;

    private BigDecimal standardError;

    private BigDecimal lowerClBound;

    private BigDecimal upperClBound;

    private String recommendSuppress;

    private String notRelevant;

    private ZonedDateTime date;

    private String domainSource;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getScaleID() {
        return scaleID;
    }

    public void setScaleID(String scaleID) {
        this.scaleID = scaleID;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public BigDecimal getStandardError() {
        return standardError;
    }

    public void setStandardError(BigDecimal standardError) {
        this.standardError = standardError;
    }

    public BigDecimal getLowerClBound() {
        return lowerClBound;
    }

    public void setLowerClBound(BigDecimal lowerClBound) {
        this.lowerClBound = lowerClBound;
    }

    public BigDecimal getUpperClBound() {
        return upperClBound;
    }

    public void setUpperClBound(BigDecimal upperClBound) {
        this.upperClBound = upperClBound;
    }

    public String getRecommendSuppress() {
        return recommendSuppress;
    }

    public void setRecommendSuppress(String recommendSuppress) {
        this.recommendSuppress = recommendSuppress;
    }

    public String getNotRelevant() {
        return notRelevant;
    }

    public void setNotRelevant(String notRelevant) {
        this.notRelevant = notRelevant;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDomainSource() {
        return domainSource;
    }

    public void setDomainSource(String domainSource) {
        this.domainSource = domainSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskDTO taskDTO = (TaskDTO) o;
        if(taskDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", taskID='" + getTaskID() + "'" +
            ", taskName='" + getTaskName() + "'" +
            ", scaleID='" + getScaleID() + "'" +
            ", scaleName='" + getScaleName() + "'" +
            ", dataValue='" + getDataValue() + "'" +
            ", n='" + getN() + "'" +
            ", standardError='" + getStandardError() + "'" +
            ", lowerClBound='" + getLowerClBound() + "'" +
            ", upperClBound='" + getUpperClBound() + "'" +
            ", recommendSuppress='" + getRecommendSuppress() + "'" +
            ", notRelevant='" + getNotRelevant() + "'" +
            ", date='" + getDate() + "'" +
            ", domainSource='" + getDomainSource() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
