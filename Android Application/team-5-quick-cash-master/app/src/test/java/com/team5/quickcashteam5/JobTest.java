package com.team5.quickcashteam5;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class JobTest {
    /**
     * COMMON VARIABLES FOR TESTING
     */
    private String Title = "Title Text";
    private String Location = "Halifax, NS";
    private int Duration = 60;
    private double PaymentAmount = 50.75;
    private Date Deadline = new Date();
    private String Description = "This is a description";
    private ArrayList<String> Equipment = new ArrayList<String>();
    private ArrayList<String> Pictures = new ArrayList<String>();
    private String newEquipment = "Camera";
    private String newPicture = "Earth.jpg";
    private String userID = "FHoekstra";
    private String workerID = "HKagura";
    private Job job;

    @Before
    public void InitializeCommon() {
        job = new Job(userID);

        Equipment.add("Shovel");
        Equipment.add("Gloves");
        Equipment.add("Bottled Water");

        Pictures.add("House1.png");
        Pictures.add("House2.png");
        Pictures.add("MyCat.jpeg");
    }

    @Test
    public void testDefaultConstructor() {
        job = new Job();

        assertTrue(job.getState() == Job.JobHiring);
    }

    @Test
    public void getState() {
        job.setState(0);
        assertTrue(job.getState() == 0);
    }

    @Test
    public void setState(){
        job.setState(3);
        assertTrue(job.getState() == 3);
    }

    @Test
    public void getWorker() {
        job.setWorker(workerID);
        assertTrue(job.getWorker().equals(workerID));
    }

    @Test
    public void setWorker() {
        job.setWorker(workerID);
        assertTrue(job.getWorker().equals(workerID));
    }

    @Test
    public void EmployeeSignedOff() {
        job.setWorker(workerID);
        job.EmployeeSigned();

        assertTrue(job.getEmployeeSigned());
    }

    @Test
    public void EmployerSignedOff() {
        job.EmployerSigned();

        assertTrue(job.getEmployerSigned());
    }

    @Test
    public void BothPartySignedOff() {
        job.setWorker(workerID);
        job.EmployeeSigned();
        job.EmployerSigned();

        assertTrue(job.partyApproved());
    }

    @Test
    public void getTitle() {
        job.setTitle(Title);
        assertTrue(Title.equals(job.getTitle()));
    }

    @Test
    public void setTitle() {
        job.setTitle(Title);
        assertTrue(Title.equals(job.getTitle()));
    }

    @Test
    public void getLocation() {
        job.setLocation(Location);
        assertTrue(Location.equals(job.getLocation()));
    }

    @Test
    public void setLocation() {
        job.setLocation(Location);
        assertTrue(Location.equals(job.getLocation()));
    }

    @Test
    public void getDuration() {
        job.setDuration(Duration);
        assertTrue(Duration == job.getDuration());
    }

    @Test
    public void setDuration() {
        job.setDuration(Duration);
        assertTrue(Duration == job.getDuration());
    }

    @Test
    public void getPayment() {
        job.setPaymentAmount(PaymentAmount);
        assertTrue(PaymentAmount == job.getPaymentAmount());
    }

    @Test
    public void setPayment() {
        job.setPaymentAmount(PaymentAmount);
        assertTrue(PaymentAmount == job.getPaymentAmount());
    }

    @Test
    public void getDeadline() {
        job.setDeadline(Deadline);
        assertTrue(Deadline.toString().equals(job.getDeadline().toString()));
    }

    @Test
    public void setDeadline() {
        job.setDeadline(Deadline);
        assertTrue(Deadline.toString().equals(job.getDeadline().toString()));
    }

    @Test
    public void getDescription() {
        job.setDescription(Description);
        assertTrue(Description.equals(job.getDescription()));
    }

    @Test
    public void setDescription() {
        job.setDescription(Description);
        assertTrue(Description.equals(job.getDescription()));
    }

    @Test
    public void getEquipmentList() {
        job.setEquipment(Equipment);
        assertTrue(Equipment.equals(job.getEquipment()));
    }

    @Test
    public void setEquipmentList() {
        job.setEquipment(Equipment);
        assertTrue(Equipment.equals(job.getEquipment()));
    }

    @Test
    public void addEquipment() {
        job.setEquipment(Equipment);

        job.addEquipment(newEquipment);
        assertTrue(job.getEquipment().size() == 4);
    }

    @Test
    public void getPictures() {
        job.setPictures(Pictures);
        assertTrue(Pictures.equals(job.getPictures()));
    }

    @Test
    public void setPictures() {
        job.setPictures(Pictures);
        assertTrue(Pictures.equals(job.getPictures()));
    }

    @Test
    public void addPicture() {
        job.setPictures(Pictures);

        job.addPicture(newPicture);
        assertTrue(job.getPictures().size() == 4);
    }

    @Test
    public void getCreateDate() {
        assertTrue(job.getCreateDate() instanceof Date);
    }

    @Test
    public void getAuthor() {
        assertTrue(userID.equals(job.getAuthor()));
    }

    @Test
    public void getInitialJobState() {
        assertTrue(0 == job.getState());
    }

    @Test
    public void getHiredJobState() {
        job.setWorker(workerID);
        assertTrue(job.getState() == Job.JobActive);
    }

    @Test
    public void getWorkerSignedJobState() {
        job.setWorker(workerID);
        job.EmployeeSigned();

        assertTrue(job.getState() == Job.JobEmployeeSigned);
    }

    @Test
    public void getEmployerSignedJobState() {
        job.EmployerSigned();

        assertTrue(job.getState() == Job.JobEmployerSigned);
    }

    @Test
    public void getBothSignedJobState() {
        job.setWorker(workerID);
        job.EmployeeSigned();
        job.EmployerSigned();

        assertTrue(job.getState() == Job.JobBothSigned);
    }

    @Test
    public void SupportInterventionJobState() {
        job.SupportRequested();

        assertTrue(job.getState() == Job.JobSupportIntervention);
    }

    @Test
    public void CompleteJobState() {
        job.EmployeeSigned();
        job.EmployerSigned();

        job.MarkCompleted();

        assertTrue(job.getState() == Job.JobComplete);
    }
}


