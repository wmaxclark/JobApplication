package clark.jobapplication;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author William Clark
 */
public class Application implements Comparable {

    private int id;
    private int jobid;
    private Instant dateTimeSubmitted;
    private boolean active;
    private String firstName;
    private String firstNameError;
    private String lastName;
    private String lastNameError;
    private String email;
    private String emailError;
    private int phoneNumber;
    private String phoneNumberError;
    private Attachment attachment;
    private String attachmentError;
    private double salary;
    private String salaryError;
    private LocalDate startDate;
    private String startDateError;

    public Application(int id, int jobid, Instant dateTimeSubmitted, boolean active, String firstName, String firstNameError, String lastName, String lastNameError, String email, String emailError, int phoneNumber, String phoneNumberError, Attachment attachment, String attachmentError, double salary, String salaryError, LocalDate startDate, String startDateError) {
        this.id = id;
        this.jobid = jobid;
        this.dateTimeSubmitted = dateTimeSubmitted;
        this.active = active;
        this.firstName = firstName;
        this.firstNameError = firstNameError;
        this.lastName = lastName;
        this.lastNameError = lastNameError;
        this.email = email;
        this.emailError = emailError;
        this.phoneNumber = phoneNumber;
        this.phoneNumberError = phoneNumberError;
        this.attachment = attachment;
        this.attachmentError = attachmentError;
        this.salary = salary;
        this.salaryError = salaryError;
        this.startDate = startDate;
        this.startDateError = startDateError;
    }

    public Application() {
        this.id = 0;
        this.jobid = 0;
        this.dateTimeSubmitted = Instant.now();
        this.active = true;
        this.firstName = "";
        this.firstNameError = "";
        this.lastName = "";
        this.lastNameError = "";
        this.email = "";
        this.emailError = "";
        this.phoneNumber = 0;
        this.phoneNumberError = "";
        this.attachment = null;
        this.attachmentError = "";
        this.salary = 0.0;
        this.salaryError = "";
        this.startDate = LocalDate.now();
        this.startDateError = "";
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the jobid
     */
    public int getJobid() {
        return jobid;
    }

    /**
     * @param jobid the jobid to set
     */
    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    /**
     * @return the dateTimeSubmitted
     */
    public Instant getDateTimeSubmitted() {
        return dateTimeSubmitted;
    }
    
    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateTimeSubmitted(String dateTimeSubmitted) {
        try {
            this.dateTimeSubmitted = Instant.parse(dateTimeSubmitted);
        } catch (DateTimeParseException | NullPointerException e) {
            this.dateTimeSubmitted = null;
        }
    }

    /**
     * @param dateTimeSubmitted the dateTimeSubmitted to set
     */
    public void setDateTimeSubmitted(Instant dateTimeSubmitted) {
        this.dateTimeSubmitted = dateTimeSubmitted;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the firstNameError
     */
    public String getFirstNameError() {
        return firstNameError;
    }

    /**
     * @param firstNameError the firstNameError to set
     */
    public void setFirstNameError(String firstNameError) {
        this.firstNameError = firstNameError;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the lastNameError
     */
    public String getLastNameError() {
        return lastNameError;
    }

    /**
     * @param lastNameError the lastNameError to set
     */
    public void setLastNameError(String lastNameError) {
        this.lastNameError = lastNameError;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the emailError
     */
    public String getEmailError() {
        return emailError;
    }

    /**
     * @param emailError the emailError to set
     */
    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    /**
     * @return the phoneNumber
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the phoneNumberError
     */
    public String getPhoneNumberError() {
        return phoneNumberError;
    }

    /**
     * @param phoneNumberError the phoneNumberError to set
     */
    public void setPhoneNumberError(String phoneNumberError) {
        this.phoneNumberError = phoneNumberError;
    }

    /**
     * @return the attachment
     */
    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * @param attachment the attachment to set
     */
    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    /**
     * @return the attachmentError
     */
    public String getAttachmentError() {
        return attachmentError;
    }

    /**
     * @param attachmentError the attachmentError to set
     */
    public void setAttachmentError(String attachmentError) {
        this.attachmentError = attachmentError;
    }

    /**
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * @return the salaryError
     */
    public String getSalaryError() {
        return salaryError;
    }

    /**
     * @param salaryError the salaryError to set
     */
    public void setSalaryError(String salaryError) {
        this.salaryError = salaryError;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the startDateError
     */
    public String getStartDateError() {
        return startDateError;
    }

    /**
     * @param startDateError the startDateError to set
     */
    public void setStartDateError(String startDateError) {
        this.startDateError = startDateError;
    }

    @Override
    public int compareTo(Object otherObject) {
        Application otherApplication = (Application)otherObject;
        return this.dateTimeSubmitted.compareTo(otherApplication.dateTimeSubmitted);
    }
    
    @Override
    public String toString() {
        return "Application{firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + '}';
    }
    
}
