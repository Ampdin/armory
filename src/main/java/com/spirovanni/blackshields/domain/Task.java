package com.spirovanni.blackshields.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private String taskID;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "scale_id")
    private String scaleID;

    @Column(name = "scale_name")
    private String scaleName;

    @Column(name = "data_value", precision=10, scale=2)
    private BigDecimal dataValue;

    @Column(name = "n")
    private Long n;

    @Column(name = "standard_error", precision=10, scale=2)
    private BigDecimal standardError;

    @Column(name = "lower_cl_bound", precision=10, scale=2)
    private BigDecimal lowerClBound;

    @Column(name = "upper_cl_bound", precision=10, scale=2)
    private BigDecimal upperClBound;

    @Column(name = "recommend_suppress")
    private String recommendSuppress;

    @Column(name = "not_relevant")
    private String notRelevant;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "domain_source")
    private String domainSource;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "tasks")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Job> jobs = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskID() {
        return taskID;
    }

    public Task taskID(String taskID) {
        this.taskID = taskID;
        return this;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getScaleID() {
        return scaleID;
    }

    public Task scaleID(String scaleID) {
        this.scaleID = scaleID;
        return this;
    }

    public void setScaleID(String scaleID) {
        this.scaleID = scaleID;
    }

    public String getScaleName() {
        return scaleName;
    }

    public Task scaleName(String scaleName) {
        this.scaleName = scaleName;
        return this;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public Task dataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
        return this;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    public Long getN() {
        return n;
    }

    public Task n(Long n) {
        this.n = n;
        return this;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public BigDecimal getStandardError() {
        return standardError;
    }

    public Task standardError(BigDecimal standardError) {
        this.standardError = standardError;
        return this;
    }

    public void setStandardError(BigDecimal standardError) {
        this.standardError = standardError;
    }

    public BigDecimal getLowerClBound() {
        return lowerClBound;
    }

    public Task lowerClBound(BigDecimal lowerClBound) {
        this.lowerClBound = lowerClBound;
        return this;
    }

    public void setLowerClBound(BigDecimal lowerClBound) {
        this.lowerClBound = lowerClBound;
    }

    public BigDecimal getUpperClBound() {
        return upperClBound;
    }

    public Task upperClBound(BigDecimal upperClBound) {
        this.upperClBound = upperClBound;
        return this;
    }

    public void setUpperClBound(BigDecimal upperClBound) {
        this.upperClBound = upperClBound;
    }

    public String getRecommendSuppress() {
        return recommendSuppress;
    }

    public Task recommendSuppress(String recommendSuppress) {
        this.recommendSuppress = recommendSuppress;
        return this;
    }

    public void setRecommendSuppress(String recommendSuppress) {
        this.recommendSuppress = recommendSuppress;
    }

    public String getNotRelevant() {
        return notRelevant;
    }

    public Task notRelevant(String notRelevant) {
        this.notRelevant = notRelevant;
        return this;
    }

    public void setNotRelevant(String notRelevant) {
        this.notRelevant = notRelevant;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Task date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDomainSource() {
        return domainSource;
    }

    public Task domainSource(String domainSource) {
        this.domainSource = domainSource;
        return this;
    }

    public void setDomainSource(String domainSource) {
        this.domainSource = domainSource;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Task jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Task addJob(Job job) {
        this.jobs.add(job);
        job.getTasks().add(this);
        return this;
    }

    public Task removeJob(Job job) {
        this.jobs.remove(job);
        job.getTasks().remove(this);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
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
