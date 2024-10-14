package entity;
public class CaseIncident {
    private int caseID;
    private int incidentID;

    // Constructor
    public CaseIncident(int caseID, int incidentID) {
        this.caseID = caseID;
        this.incidentID = incidentID;
    }

    // Getters and Setters
    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public int getIncidentID() {
        return incidentID;
    }

    public void setIncidentID(int incidentID) {
        this.incidentID = incidentID;
    }

    @Override
    public String toString() {
        return "CaseIncident{" +
                "caseID=" + caseID +
                ", incidentID=" + incidentID +
                '}';
    }
}
