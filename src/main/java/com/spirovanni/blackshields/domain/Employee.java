package com.spirovanni.blackshields.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "player_type")
    private String playerType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "badge_number")
    private String badgeNumber;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "previous_salary")
    private Long previousSalary;

    @Column(name = "current_salary")
    private Long currentSalary;

    @Column(name = "goal_salary")
    private Long goalSalary;

    @Column(name = "path_goal")
    private String pathGoal;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private Long zip;

    @Column(name = "state")
    private String state;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Job> jobs = new HashSet<>();

    @ManyToOne
    private Employee manager;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Employee employeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getPlayerType() {
        return playerType;
    }

    public Employee playerType(String playerType) {
        this.playerType = playerType;
        return this;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Employee phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public Employee badgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
        return this;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Employee startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public Long getPreviousSalary() {
        return previousSalary;
    }

    public Employee previousSalary(Long previousSalary) {
        this.previousSalary = previousSalary;
        return this;
    }

    public void setPreviousSalary(Long previousSalary) {
        this.previousSalary = previousSalary;
    }

    public Long getCurrentSalary() {
        return currentSalary;
    }

    public Employee currentSalary(Long currentSalary) {
        this.currentSalary = currentSalary;
        return this;
    }

    public void setCurrentSalary(Long currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Long getGoalSalary() {
        return goalSalary;
    }

    public Employee goalSalary(Long goalSalary) {
        this.goalSalary = goalSalary;
        return this;
    }

    public void setGoalSalary(Long goalSalary) {
        this.goalSalary = goalSalary;
    }

    public String getPathGoal() {
        return pathGoal;
    }

    public Employee pathGoal(String pathGoal) {
        this.pathGoal = pathGoal;
        return this;
    }

    public void setPathGoal(String pathGoal) {
        this.pathGoal = pathGoal;
    }

    public String getAddress() {
        return address;
    }

    public Employee address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Employee city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getZip() {
        return zip;
    }

    public Employee zip(Long zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public Employee state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Employee jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Employee addJob(Job job) {
        this.jobs.add(job);
        job.setEmployee(this);
        return this;
    }

    public Employee removeJob(Job job) {
        this.jobs.remove(job);
        job.setEmployee(null);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Employee getManager() {
        return manager;
    }

    public Employee manager(Employee employee) {
        this.manager = employee;
        return this;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeId='" + getEmployeeId() + "'" +
            ", playerType='" + getPlayerType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", badgeNumber='" + getBadgeNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", previousSalary='" + getPreviousSalary() + "'" +
            ", currentSalary='" + getCurrentSalary() + "'" +
            ", goalSalary='" + getGoalSalary() + "'" +
            ", pathGoal='" + getPathGoal() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", zip='" + getZip() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
