package clark.jobapplication;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author William Clark
 */
public class Job implements Comparable{

    private int id;
    private boolean active;
    private LocalDate dateCreated;
    private String title;
    private String city;
    private String state;
    private boolean fullTime;
    private String department;
    private String experience;
    private String wageCategory;
    private double salary;
    private String description;

    public Job(int id, boolean active, String dateCreated, String title, String city, String state, boolean fullTime, String department, String experience, String wageCategory, double salary, String description) {
        this.id = id;
        this.active = active;
        this.setDateCreated(dateCreated);
        this.title = title;
        this.city = city;
        this.state = state;
        this.fullTime = fullTime;
        this.department = department;
        this.experience = experience;
        this.wageCategory = wageCategory;
        this.salary = salary;
        this.description = description;
    }

    public Job() {
        id = 0;
        active = true;
        dateCreated = LocalDate.now();
        title = "";
        city = "";
        state = "";
        fullTime = true;
        department = "";
        experience = "";
        wageCategory = "";
        salary = 0.0;
        description = "";
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getNewDateCreated() {
        return java.sql.Date.valueOf(dateCreated);
    }

    /**
     * @return the dateCreated
     */
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(String dateCreated) {
        DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            this.dateCreated = LocalDate.parse(dateCreated, formatterInput);
        } catch (DateTimeParseException | NullPointerException e) {
            this.dateCreated = null;
        }
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWageCategory() {
        return wageCategory;
    }

    public void setWageCategory(String wageCategory) {
        this.wageCategory = wageCategory;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Job{title=" + title + ", city=" + city + ", state=" + state + ", department=" + department + ", active=" + active + '}';
    }

    @Override
    public int compareTo(Object otherObject) {
        Job otherJob = (Job)otherObject;
        if (this.dateCreated.equals(otherJob.dateCreated)) {
            return this.title.compareTo(otherJob.title);
        }
        else{
            return this.dateCreated.compareTo(otherJob.dateCreated);
        }
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
