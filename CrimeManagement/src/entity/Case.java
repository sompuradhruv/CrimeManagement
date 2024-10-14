package entity;

import java.util.List;

public class Case {
    private int caseID;
    private String caseDescription;
    private List<Incident> incidents;

    // Constructor
    public Case() {}
    
    public Case(int caseID, String caseDescription, List<Incident> incidents) {
    	this.caseID=caseID;
    	this.caseDescription=caseDescription;
    	this.incidents=incidents;
    }

    // Getters and Setters
    public int getCaseID() {
        return caseID;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Case [caseID=").append(caseID).append(", caseDescription=").append(caseDescription)
				.append(", incidents=").append(incidents).append("]");
		return builder.toString();
	}

	public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }
}
