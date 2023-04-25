package com.team5.quickcashteam5;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Job implements Parcelable {
    private String title;
    private String Location;
    private int Duration;
    private double PaymentAmount;
    private long JobCreated;
    private long Deadline;
    private String Description;
    private ArrayList<String> Equipment;
    private ArrayList<String> Pictures;
    private String author;
    private String employeeID;
    private String jobID;
    private int state;
    private Boolean EmployeeSigned = false;
    private Boolean EmployerSigned = false;

    /**
     * JOB STATUSES FOR JOB STATE
     */
    public static final int JobHiring = 0;
    public static final int JobActive = 1;
    public static final int JobEmployeeSigned = 2;
    public static final int JobEmployerSigned = 3;
    public static final int JobBothSigned = 4;
    public static final int JobSupportIntervention = 5;
    public static final int JobComplete = 6;

    // For use with Firebase loading.
    public Job() {
        JobCreated = new Date().getTime();
        Equipment = new ArrayList<>();
        Pictures = new ArrayList<>();
        state = JobHiring;
    }

    public Job(String author) {
        this.author = author;
        JobCreated = new Date().getTime();
        Equipment = new ArrayList<>();
        Pictures = new ArrayList<>();
        state = JobHiring;
    }

    protected Job(Parcel in) {
        title = in.readString();
        Location = in.readString();
        Duration = in.readInt();
        PaymentAmount = in.readDouble();
        JobCreated = in.readLong();
        Deadline = in.readLong();
        Description = in.readString();
        Equipment = in.createStringArrayList();
        Pictures = in.createStringArrayList();
        author = in.readString();
        employeeID = in.readString();
        jobID = in.readString();
        state = in.readInt();
        byte tmpEmployeeSigned = in.readByte();
        EmployeeSigned = tmpEmployeeSigned == 0 ? null : tmpEmployeeSigned == 1;
        byte tmpEmployerSigned = in.readByte();
        EmployerSigned = tmpEmployerSigned == 0 ? null : tmpEmployerSigned == 1;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() { return state; }

    public void SupportRequested() {
        state = JobSupportIntervention;
    }

    public void MarkCompleted() {
        state = JobComplete;
    }

    public void EmployeeSigned() {
        EmployeeSigned = true;
        if (EmployerSigned) { state = JobBothSigned; }
        else { state = JobEmployeeSigned; }
    }

    public Boolean getEmployeeSigned() { return EmployeeSigned; }

    public void EmployerSigned() {
        EmployerSigned = true;
        if (EmployeeSigned) { state = JobBothSigned; }
        else { state = JobEmployerSigned; }
    }

    public Boolean getEmployerSigned() { return EmployerSigned; }

    public Boolean partyApproved() { return (EmployeeSigned == true) && (EmployerSigned == true); }

    public String getAuthor() {
        return author;
    }
    public String getWorker() { return employeeID; }

    public void setWorker(String employeeID) {
        this.employeeID = employeeID;
        state = JobActive;
    }

    /**
     * Returns the creation date of the job. Will deserialize and return a date
     * object.
     */
    public Date getCreateDate() {
        return new Date(JobCreated);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        this.Duration = duration;
    }

    public double getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(double payment) {
        this.PaymentAmount = payment;
    }

    /**
     * Deserializes and returns a date object
     * of the deadline for the job.
     */
    public Date getDeadline() {
        return new Date(Deadline);
    }

    /**
     * Set the deadline for the job. Will serialize the date object
     * into a better format for Firebase.
     */
    public void setDeadline(Date deadline) {
        this.Deadline = deadline.getTime();
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public ArrayList<String> getEquipment() {
        return Equipment;
    }

    public void setEquipment(ArrayList<String> equipment) {
        this.Equipment = equipment;
    }

    public void addEquipment(String item) {
        this.Equipment.add(item);
    }

    public ArrayList<String> getPictures() {
        return Pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.Pictures = pictures;
    }

    public void addPicture(String image) {
        this.Pictures.add(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(Location);
        dest.writeInt(Duration);
        dest.writeDouble(PaymentAmount);
        dest.writeLong(JobCreated);
        dest.writeLong(Deadline);
        dest.writeString(Description);
        dest.writeStringList(Equipment);
        dest.writeStringList(Pictures);
        dest.writeString(author);
        dest.writeString(employeeID);
        dest.writeString(jobID);
        dest.writeInt(state);
        dest.writeByte((byte) (EmployeeSigned == null ? 0 : EmployeeSigned ? 1 : 2));
        dest.writeByte((byte) (EmployerSigned == null ? 0 : EmployerSigned ? 1 : 2));
    }
}
