package entity;

public class Officer {
    @Override
	public String toString() {
		return "Officer [officerID=" + officerID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", badgeNumber=" + badgeNumber + ", rank=" + rank + ", contactInfo=" + contactInfo + ", agencyID="
				+ agencyID + "]";
	}

	private int officerID;
    private String firstName;
    private String lastName;
    private String badgeNumber;
    private String rank;
    private String contactInfo;
    private int agencyID;

    // Default constructor
    public Officer() {
    }

    // Parameterized constructor
    public Officer(int officerID, String firstName, String lastName, String badgeNumber, 
                   String rank, String contactInfo, int agencyID) {
        this.officerID = officerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.badgeNumber = badgeNumber;
        this.rank = rank;
        this.contactInfo = contactInfo;
        this.agencyID = agencyID;
    }

    // Getters
    public int getOfficerID() {
        return officerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public String getRank() {
        return rank;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public int getAgencyID() {
        return agencyID;
    }

    // Setters
    public void setOfficerID(int officerID) {
        this.officerID = officerID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

	
    public void setAgencyID(int incidentID) {
        this.agencyID = incidentID;
    }

	

	

   
}
