package entity;

public class Agency {
    @Override
	public String toString() {
		return "Agency [agencyID=" + agencyID + ", agencyName=" + agencyName + ", jurisdiction=" + jurisdiction
				+ ", contactInfo=" + contactInfo + ", officer=" + officer + "]";
	}

	private int agencyID;           // Primary Key
    private String agencyName;
    private String jurisdiction;
    private String contactInfo;
    private String officer; // Link to Officers within the agency

    // Default Constructor
    public Agency() {
    }

    // Parameterized Constructor
    public Agency(int agencyID, String agencyName, String jurisdiction, String contactInfo, String officer) {
        this.agencyID = agencyID;
        this.agencyName = agencyName;
        this.jurisdiction = jurisdiction;
        this.contactInfo = contactInfo;
        this.officer = officer;
    }

    // Getters and Setters

    public int getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(int agencyID) {
        this.agencyID = agencyID;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getOfficers() {
        return officer;
    }

    public void setOfficers(String officers) {
        this.officer = officers;
    }
    

}