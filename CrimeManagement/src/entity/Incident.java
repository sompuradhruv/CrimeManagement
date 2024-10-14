package entity;

import java.sql.Date;

public class Incident {
    private int incidentID;
    private String incidentType;
    private Date incidentDate;
    private double latitude;
    private double longitude;
    private String description;
    private String status;
    private int victimID;
    private int suspectID;
    private int agencyID; // Foreign Key to Law Enforcement Agency

    // Constructor
    public Incident() {}

    public Incident(int incidentID, String incidentType, Date incidentDate, double latitude, double longitude, String description, String status, int victimID, int suspectID, int agencyID) {
        this.incidentID = incidentID;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.status = status;
        this.victimID = victimID;
        this.suspectID = suspectID;
        this.agencyID = agencyID;
    }

    @Override
	public String toString() {
		return "Incident [incidentID=" + incidentID + ", incidentType=" + incidentType + ", incidentDate="
				+ incidentDate + ", latitude=" + latitude + ", longitude=" + longitude + ", description=" + description
				+ ", status=" + status + ", victimID=" + victimID + ", suspectID=" + suspectID + ", agencyID="
				+ agencyID + "]";
	}

	// Getters and Setters
    public int getIncidentID() {
        return incidentID;
    }

    public void setIncidentID(int incidentID) {
        this.incidentID = incidentID;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVictimID() {
        return victimID;
    }

    public void setVictimID(int victimID) {
        this.victimID = victimID;
    }

    public int getSuspectID() {
        return suspectID;
    }

    public void setSuspectID(int suspectID) {
        this.suspectID = suspectID;
    }
    
    public int getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(int lawEnforcementAgencyID) {
        this.agencyID = lawEnforcementAgencyID;
    }
}
