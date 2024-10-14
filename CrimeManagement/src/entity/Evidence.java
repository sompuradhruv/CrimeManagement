package entity;

public class Evidence {
    @Override
	public String toString() {
		return "Evidence [evidenceID=" + evidenceID + ", description=" + description + ", locationFound="
				+ locationFound + ", incidentID=" + incidentID + "]";
	}

	private int evidenceID;      // Primary Key
    private String description;
    private String locationFound;
    private int incidentID;       // Foreign Key, linking to Incidents

    // Default Constructor
    public Evidence() {
    }

    // Parameterized Constructor
    public Evidence(int evidenceID, String description, String locationFound, int incidentID) {
        this.evidenceID = evidenceID;
        this.description = description;
        this.locationFound = locationFound;
        this.incidentID = incidentID;
    }

    // Getters and Setters

    public int getEvidenceID() {
        return evidenceID;
    }

    public void setEvidenceID(int evidenceID) {
        this.evidenceID = evidenceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationFound() {
        return locationFound;
    }

    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

    public int getIncidentID() {
        return incidentID;
    }

    public void setIncidentID(int incidentID) {
        this.incidentID = incidentID;
    }

}
