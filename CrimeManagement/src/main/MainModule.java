package main;

import dao.CrimeAnalysisServiceImpl;
import exception.*;
import util.DBConnectionUtil;
import entity.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//public class MainModule {
//    public static void main(String[] args) throws SQLException, IncidentNumberNotFoundException {
//        CrimeAnalysisServiceImpl service = new CrimeAnalysisServiceImpl();
//        Scanner scanner = new Scanner(System.in);
//    //     Connection connection = DBConnectionUtil.getConnection(); // Create your database connection
//      
       // Officer officer = new Officer(); // Fetch this from your database or create a new instance
       // officer.setOfficerID(1); // Set officer ID for example
        
//        Incident incident = new Incident();
//        incident.setIncidentType("Robbery");
//        incident.setIncidentDate(new java.sql.Date(System.currentTimeMillis()));
//        incident.setLatitude(40.7128);
//        incident.setLongitude(-74.0060);
//        incident.setDescription("Robbery at the bank.");
//        incident.setStatus("Open");
//        incident.setVictimID(1);
//        incident.setSuspectID(1);
//        incident.setAgencyID(2);
//        // Set other necessary fields for the incident object
//        
//        // Now create the incident and automatically generate a report
//        service.createIncident(incident, officer);
//    } 
//}
public class MainModule {

    // Mock admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";
    
    private static final User[] users = {
            new User("user1", "password123"),
            new User("user2", "password456"),
            new User("user3", "password789")
        };
       
    
    // Method to authenticate admin login
    private static boolean authenticateAdmin(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        // Check credentials
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Invalid username or password. Try again.");
            return false;
        }
    }
    
    private static boolean authenticateUser(Scanner scanner) {
        System.out.print("Enter user username: ");
        String username = scanner.nextLine();
        System.out.print("Enter user password: ");
        String password = scanner.nextLine();

        // Check credentials against the array of users
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful.");
                return true;
            }
        }

        System.out.println("Invalid username or password. Try again.");
        return false;
    }

    public static void main(String[] args) throws IncidentNumberNotFoundException {
        CrimeAnalysisServiceImpl service = new CrimeAnalysisServiceImpl();
        Scanner scanner = new Scanner(System.in);
        
        
        System.out.println("Select Login: 1. Admin 2. User");
        int abc=scanner.nextInt();
        boolean authenticated = false;
        boolean authenticatedu = false;
        if(abc==1)
        {
        	// Admin login loop
            
            while (!authenticated) {
                System.out.println("Admin Login:");
                authenticated = authenticateAdmin(scanner); // Check admin credentials
            }
        }
        else
        {
        	
            while (!authenticatedu) {
                System.out.println("User Login:");
                authenticatedu = authenticateUser(scanner); // Check user credentials
            }
        }
        
        
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1: Create a record");
            System.out.println("2: Update a record");
            System.out.println("3: Search a record by ID");
            System.out.println("4: Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Create a record
                    System.out.println("Which table do you want to create a record in? (1: Incident, 2: Victim, 3: Suspect, 4: Agency, 5: Officer, 6: Evidence,7:Case)");
                    int createTableChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (createTableChoice) {
                        case 1: // Create Incident
                            Incident newIncident = new Incident();
                            // Get necessary fields from user for Incident
                           
                            System.out.print("Enter Incident Type: ");
                            newIncident.setIncidentType(scanner.nextLine());
                            System.out.print("Enter Incident Date (YYYY-MM-DD): ");
                            newIncident.setIncidentDate(java.sql.Date.valueOf(scanner.nextLine()));
                            System.out.print("Enter Latitude: ");
                            newIncident.setLatitude(scanner.nextDouble());
                            System.out.print("Enter Longitude: ");
                            newIncident.setLongitude(scanner.nextDouble());
                            scanner.nextLine(); // Consume newline
                            System.out.print("Enter Description: ");
                            newIncident.setDescription(scanner.nextLine());
                            System.out.print("Enter Status: ");
                            newIncident.setStatus(scanner.nextLine());
                            System.out.print("Enter Victim ID: ");
                            newIncident.setVictimID(scanner.nextInt());
                            System.out.print("Enter Suspect ID: ");
                            newIncident.setSuspectID(scanner.nextInt());
                            System.out.print("Enter Agency ID: ");
                            newIncident.setAgencyID(scanner.nextInt());
                            System.out.print("Enter Officer ID: ");
                            int officerId = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            Officer officer = new Officer();
                            officer.setOfficerID(officerId);

						service.createIncident(newIncident, officer);
						System.out.println("Incident created successfully!");
                            break;
                            
                           

                        // Implement similar creation for other tables as needed
                        case 2: // Create Victim
                            Victim newVictim = new Victim();
                          
                            System.out.print("Enter First Name: ");
                            newVictim.setFirstName(scanner.nextLine());
                            System.out.print("Enter Last Name: ");
                            newVictim.setLastName(scanner.nextLine());
                            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                            newVictim.setDateOfBirth(scanner.nextLine());
                            System.out.print("Enter Gender: ");
                            newVictim.setGender(scanner.nextLine());
                            System.out.print("Enter Contact Information: ");
                            newVictim.setContactInfo(scanner.nextLine());

                            if (service.createVictim(newVictim)) {
                                System.out.println("Victim created successfully.");
                            } else {
                                System.out.println("Failed to create Victim.");
                            }
                            break;

                        case 3: // Create Suspect
                            Suspect newSuspect = new Suspect();
                            
                            System.out.print("Enter First Name: ");
                            newSuspect.setFirstName(scanner.nextLine());
                            System.out.print("Enter Last Name: ");
                            newSuspect.setLastName(scanner.nextLine());
                            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                            newSuspect.setDateOfBirth(scanner.nextLine());
                            System.out.print("Enter Gender: ");
                            newSuspect.setGender(scanner.nextLine());
                            System.out.print("Enter Contact Information: ");
                            newSuspect.setContactInfo(scanner.nextLine());

                            if (service.createSuspect(newSuspect)) {
                                System.out.println("Suspect created successfully.");
                            } else {
                                System.out.println("Failed to create Suspect.");
                            }
                            break;

                        case 4: // Create Agency
                            Agency newAgency = new Agency();
                           
                            System.out.print("Enter Agency Name: ");
                            newAgency.setAgencyName(scanner.nextLine());
                            System.out.print("Enter Jurisdiction: ");
                            newAgency.setJurisdiction(scanner.nextLine());
                            System.out.print("Enter Contact Information: ");
                            newAgency.setContactInfo(scanner.nextLine());

                            if (service.createAgency(newAgency)) {
                                System.out.println("Agency created successfully.");
                            } else {
                                System.out.println("Failed to create Agency.");
                            }
                            break;

                        case 5: // Create Officer
                            Officer newOfficer = new Officer();
                            
                            System.out.print("Enter First Name: ");
                            newOfficer.setFirstName(scanner.nextLine());
                            System.out.print("Enter Last Name: ");
                            newOfficer.setLastName(scanner.nextLine());
                            System.out.print("Enter Badge Number: ");
                            newOfficer.setBadgeNumber(scanner.nextLine());
                            System.out.print("Enter Rank: ");
                            newOfficer.setRank(scanner.nextLine());
                            System.out.print("Enter Contact Information: ");
                            newOfficer.setContactInfo(scanner.nextLine());
                            System.out.print("Enter Agency ID: ");
                            newOfficer.setAgencyID(scanner.nextInt());

                            if (service.createOfficer(newOfficer)) {
                                System.out.println("Officer created successfully.");
                            } else {
                                System.out.println("Failed to create Officer.");
                            }
                            break;

                        case 6: // Create Evidence
                            Evidence newEvidence = new Evidence();
                            
                            System.out.print("Enter Description: ");
                            newEvidence.setDescription(scanner.nextLine());
                            System.out.print("Enter Location Found: ");
                            newEvidence.setLocationFound(scanner.nextLine());
                            System.out.print("Enter Incident ID: ");
                            newEvidence.setIncidentID(scanner.nextInt());

                            if (service.createEvidence(newEvidence)) {
                                System.out.println("Evidence created successfully.");
                            } else {
                                System.out.println("Failed to create Evidence.");
                            }
                            break;
                        case 7: // Insert a new case
                        	System.out.println("Enter Case Description: ");
                        	String caseDescription = scanner.nextLine(); // Read the case description from the user

                        	// Get the number of incidents to add to the case
                        	System.out.println("How many incidents do you want to add to the case?");
                        	int numIncidents = scanner.nextInt();
                        	scanner.nextLine(); // Consume the newline

                        	List<Incident> incidents = new ArrayList<>();
                        	for (int i = 1; i <= numIncidents; i++) {
                        	    System.out.println("Enter Incident ID for Incident " + i + ": ");
                        	    int incidentID = scanner.nextInt();
                        	    scanner.nextLine(); // Consume the newline
                        	    Incident incident = new Incident();
                        	    incident.setIncidentID(incidentID); // Set the incident ID
                        	    incidents.add(incident); // Add the incident to the list
                        	}

                        	// Create the case using the description and incidents
                        	Case newCase = new Case();
                        	newCase.setCaseDescription(caseDescription); // Ensure the case description is not null
                        	newCase.setIncidents(incidents);

                        	// Call the service to create the case in the database
                        	if (service.createCase(newCase.getCaseDescription(), newCase.getIncidents())) {
                        	    System.out.println("Case created successfully.");
                        	} else {
                        	    System.out.println("Failed to create Case.");
                        	}


                        default:
                            System.out.println("Invalid table choice!");
                    }
                    break;

                case 2: // Update a record
                    System.out.println("Which table do you want to update a record in? (1: Incident, 2: Victim, 3: Suspect, 4: Agency, 5: Officer, 6: Evidence,7: Case)");
                    int updateTableChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (updateTableChoice) {
                        case 1: // Update Incident Status
                            System.out.print("Enter Incident ID: ");
                            int incidentID = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            System.out.print("Enter new Status: ");
                            String newStatus = scanner.nextLine();
                            if (service.updateIncidentStatus(newStatus, incidentID)) {
                                System.out.println("Incident status updated successfully.");
                            } else {
                                System.out.println("Failed to update Incident status.");
                            }
                            break;

                        case 2: // Update Victim
                            System.out.print("Enter Victim ID: ");
                            int victimID = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            Victim victimToUpdate = new Victim();
                            victimToUpdate.setVictimID(victimID);
                            System.out.print("Enter new First Name: ");
                            victimToUpdate.setFirstName(scanner.nextLine());
                            System.out.print("Enter new Last Name: ");
                            victimToUpdate.setLastName(scanner.nextLine());
                            System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
                            victimToUpdate.setDateOfBirth(scanner.nextLine());
                            System.out.print("Enter new Gender: ");
                            victimToUpdate.setGender(scanner.nextLine());
                            System.out.print("Enter new Contact Information: ");
                            victimToUpdate.setContactInfo(scanner.nextLine());

                            if (service.updateVictim(victimToUpdate)) {
                                System.out.println("Victim updated successfully.");
                            } else {
                                System.out.println("Failed to update Victim.");
                            }
                            break;

                        case 3: // Update Suspect
                            System.out.print("Enter Suspect ID: ");
                            int suspectID = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            Suspect suspectToUpdate = new Suspect();
                            suspectToUpdate.setSuspectID(suspectID);
                            System.out.print("Enter new First Name: ");
                            suspectToUpdate.setFirstName(scanner.nextLine());
                            System.out.print("Enter new Last Name: ");
                            suspectToUpdate.setLastName(scanner.nextLine());
                            System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
                            suspectToUpdate.setDateOfBirth(scanner.nextLine());
                            System.out.print("Enter new Gender: ");
                            suspectToUpdate.setGender(scanner.nextLine());
                            System.out.print("Enter new Contact Information: ");
                            suspectToUpdate.setContactInfo(scanner.nextLine());

                            if (service.updateSuspect(suspectToUpdate)) {
                                System.out.println("Suspect updated successfully.");
                            } else {
                                System.out.println("Failed to update Suspect.");
                            }
                            break;

                        case 4: // Update Agency
                            System.out.print("Enter Agency ID: ");
                            int agencyID = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            Agency agencyToUpdate = new Agency();
                            agencyToUpdate.setAgencyID(agencyID);
                            System.out.print("Enter new Agency Name: ");
                            agencyToUpdate.setAgencyName(scanner.nextLine());
                            System.out.print("Enter new Jurisdiction: ");
                            agencyToUpdate.setJurisdiction(scanner.nextLine());
                            System.out.print("Enter new Contact Information: ");
                            agencyToUpdate.setContactInfo(scanner.nextLine());

                            if (service.updateAgency(agencyToUpdate)) {
                                System.out.println("Agency updated successfully.");
                            } else {
                                System.out.println("Failed to update Agency.");
                            }
                            break;

                        case 5: // Update Officer
                            System.out.print("Enter Officer ID: ");
                            int officerID = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            Officer officerToUpdate = new Officer();
                            officerToUpdate.setOfficerID(officerID);
                            System.out.print("Enter new First Name: ");
                            officerToUpdate.setFirstName(scanner.nextLine());
                            System.out.print("Enter new Last Name: ");
                            officerToUpdate.setLastName(scanner.nextLine());
                            System.out.print("Enter new Badge Number: ");
                            officerToUpdate.setBadgeNumber(scanner.nextLine());
                            System.out.print("Enter new Rank: ");
                            officerToUpdate.setRank(scanner.nextLine());
                            System.out.print("Enter new Agency ID: ");
                            // Convert the string input to an int
                            int AgencyID = scanner.nextInt();
                            officerToUpdate.setAgencyID(AgencyID); // Now this will accept an int

                            scanner.nextLine();
                            
                            System.out.print("Enter new Contact Information: ");
                            officerToUpdate.setContactInfo(scanner.nextLine());

                            if (service.updateOfficer(officerToUpdate)) {
                                System.out.println("Officer updated successfully.");
                            } else {
                                System.out.println("Failed to update Officer.");
                            }
                            break;

                        case 6: // Update Evidence
                            System.out.print("Enter Evidence ID: ");
                            int evidenceID = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            Evidence evidenceToUpdate = new Evidence();
                            evidenceToUpdate.setEvidenceID(evidenceID);
                            System.out.print("Enter new Description: ");
                            evidenceToUpdate.setDescription(scanner.nextLine());
                            System.out.print("Enter new Location Found: ");
                            evidenceToUpdate.setLocationFound(scanner.nextLine());
                            System.out.print("Enter new Incident ID: ");
                            evidenceToUpdate.setIncidentID(scanner.nextInt());

                            if (service.updateEvidence(evidenceToUpdate)) {
                                System.out.println("Evidence updated successfully.");
                            } else {
                                System.out.println("Failed to update Evidence.");
                            }
                            break;
                        case 7: // Update Case and its Incidents
                            System.out.print("Enter Case ID: ");
                            int caseID = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            
                            // Fetch the case details from the user
                            System.out.print("Enter new Case Description: ");
                            String newDescription = scanner.nextLine();

                            // Update case description
                            Case caseToUpdate = new Case();
                            caseToUpdate.setCaseID(caseID);
                            caseToUpdate.setCaseDescription(newDescription);

                            // Update the case details
                            if (service.updateCaseDetails(caseToUpdate)) {
                                System.out.println("Case updated successfully.");
                            } else {
                                System.out.println("Failed to update Case.");
                            }

                            // Update associated incidents
                            System.out.println("Do you want to update the associated incidents? (y/n)");
                            String updateIncidentsChoice = scanner.nextLine();

                            if (updateIncidentsChoice.equalsIgnoreCase("y")) {
                                System.out.print("How many incidents do you want to associate with this case? ");
                                int numIncidents = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                List<Incident> updatedIncidents = new ArrayList<>();
                                for (int i = 0; i < numIncidents; i++) {
                                    System.out.print("Enter Incident ID: ");
                                    int incidentID1 = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline
                                    
                                    // Create Incident object for association
                                    Incident incident = service.getIncidentByID(incidentID1);
                                    updatedIncidents.add(incident);
                                }

                                // Link the incidents to the case
                                if (service.updateCaseIncidents(caseID, updatedIncidents)) {
                                    System.out.println("Case and incidents updated successfully.");
                                } else {
                                    System.out.println("Failed to update Case incidents.");
                                }
                            }
                            break;

                        default:
                            System.out.println("Invalid table choice!");
                    }
                    break;

                case 3: // Search a record by ID
                    System.out.println("Which table do you want to search? (1: Incident, 2: Victim, 3: Suspect, 4: Agency, 5: Officer, 6: Evidence, 7:Case)");
                    int searchTableChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter ID: ");
                    int searchID = scanner.nextInt();

                    try {
                        switch (searchTableChoice) {
                            case 1: // Search Incident
                                Incident incident = service.getIncidentByID(searchID);
                                System.out.println(incident);
                                break;
                            case 2: // Search Victim
                                Victim victim = service.getVictimByID(searchID);
                                System.out.println(victim);
                                break;
                            case 3: // Search Suspect
                                Suspect suspect = service.getSuspectByID(searchID);
                                System.out.println(suspect);
                                break;
                            case 4: // Search Agency
                                Agency agency = service.getAgencyByID(searchID);
                                System.out.println(agency);
                                break;
                            case 5: // Search Officer
                                Officer officer = service.getOfficerByID(searchID);
                                System.out.println(officer);
                                break;
                            case 6: // Search Evidence
                                Evidence evidence = service.getEvidenceByID(searchID);
                                System.out.println(evidence);
                                break;
                            case 7: // Search Case
                                Case caseDetails = service.getCaseByID(searchID);
                                System.out.println(caseDetails);

                                // Display associated incidents
                                if (caseDetails.getIncidents() != null && !caseDetails.getIncidents().isEmpty()) {
                                    System.out.println("Associated Incidents:");
                                    for (Incident incident1 : caseDetails.getIncidents()) {
                                        System.out.println("Incident ID: " + incident1.getIncidentID());
                                    }
                                } else {
                                    System.out.println("No associated incidents found.");
                                }
                                break;
                            
                            default:
                                System.out.println("Invalid table choice!");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4: // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

//        while (true) {
//            System.out.println("Choose an action:");
//            System.out.println("1: Create a record");
//            System.out.println("2: Update a record");
//            System.out.println("3: Search a record by ID");
//            System.out.println("4: Exit");
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume newline
//
//            switch (choice) {
//                case 1: // Create a record
//                    System.out.println("Which table do you want to create a record in? (1: Incident, 2: Victim, 3: Suspect, 4: Agency, 5: Officer, 6: Evidence)");
//                    int createTableChoice = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline
//
//                    switch (createTableChoice) {
//                        case 1: // Create Incident
//                            Incident newIncident = new Incident();
//                            // Get necessary fields from user for Incident
//                           
//                            System.out.print("Enter Incident Type: ");
//                            newIncident.setIncidentType(scanner.nextLine());
//                            System.out.print("Enter Incident Date (YYYY-MM-DD): ");
//                            newIncident.setIncidentDate(java.sql.Date.valueOf(scanner.nextLine()));
//                            System.out.print("Enter Latitude: ");
//                            newIncident.setLatitude(scanner.nextDouble());
//                            System.out.print("Enter Longitude: ");
//                            newIncident.setLongitude(scanner.nextDouble());
//                            scanner.nextLine(); // Consume newline
//                            System.out.print("Enter Description: ");
//                            newIncident.setDescription(scanner.nextLine());
//                            System.out.print("Enter Status: ");
//                            newIncident.setStatus(scanner.nextLine());
//                            System.out.print("Enter Victim ID: ");
//                            newIncident.setVictimID(scanner.nextInt());
//                            System.out.print("Enter Suspect ID: ");
//                            newIncident.setSuspectID(scanner.nextInt());
//                            System.out.print("Enter Agency ID: ");
//                            newIncident.setAgencyID(scanner.nextInt());
//                            
//                            if (service.createIncident(newIncident)) {
//                                System.out.println("Incident created successfully.");
//                            } else {
//                                System.out.println("Failed to create Incident.");
//                            }
//                            break;
//
//                        // Implement similar creation for other tables as needed
//                        case 2: // Create Victim
//                            Victim newVictim = new Victim();
//                          
//                            System.out.print("Enter First Name: ");
//                            newVictim.setFirstName(scanner.nextLine());
//                            System.out.print("Enter Last Name: ");
//                            newVictim.setLastName(scanner.nextLine());
//                            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
//                            newVictim.setDateOfBirth(scanner.nextLine());
//                            System.out.print("Enter Gender: ");
//                            newVictim.setGender(scanner.nextLine());
//                            System.out.print("Enter Contact Information: ");
//                            newVictim.setContactInfo(scanner.nextLine());
//
//                            if (service.createVictim(newVictim)) {
//                                System.out.println("Victim created successfully.");
//                            } else {
//                                System.out.println("Failed to create Victim.");
//                            }
//                            break;
//
//                        case 3: // Create Suspect
//                            Suspect newSuspect = new Suspect();
//                            
//                            System.out.print("Enter First Name: ");
//                            newSuspect.setFirstName(scanner.nextLine());
//                            System.out.print("Enter Last Name: ");
//                            newSuspect.setLastName(scanner.nextLine());
//                            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
//                            newSuspect.setDateOfBirth(scanner.nextLine());
//                            System.out.print("Enter Gender: ");
//                            newSuspect.setGender(scanner.nextLine());
//                            System.out.print("Enter Contact Information: ");
//                            newSuspect.setContactInfo(scanner.nextLine());
//
//                            if (service.createSuspect(newSuspect)) {
//                                System.out.println("Suspect created successfully.");
//                            } else {
//                                System.out.println("Failed to create Suspect.");
//                            }
//                            break;
//
//                        case 4: // Create Agency
//                            Agency newAgency = new Agency();
//                           
//                            System.out.print("Enter Agency Name: ");
//                            newAgency.setAgencyName(scanner.nextLine());
//                            System.out.print("Enter Jurisdiction: ");
//                            newAgency.setJurisdiction(scanner.nextLine());
//                            System.out.print("Enter Contact Information: ");
//                            newAgency.setContactInfo(scanner.nextLine());
//
//                            if (service.createAgency(newAgency)) {
//                                System.out.println("Agency created successfully.");
//                            } else {
//                                System.out.println("Failed to create Agency.");
//                            }
//                            break;
//
//                        case 5: // Create Officer
//                            Officer newOfficer = new Officer();
//                            
//                            System.out.print("Enter First Name: ");
//                            newOfficer.setFirstName(scanner.nextLine());
//                            System.out.print("Enter Last Name: ");
//                            newOfficer.setLastName(scanner.nextLine());
//                            System.out.print("Enter Badge Number: ");
//                            newOfficer.setBadgeNumber(scanner.nextLine());
//                            System.out.print("Enter Rank: ");
//                            newOfficer.setRank(scanner.nextLine());
//                            System.out.print("Enter Contact Information: ");
//                            newOfficer.setContactInfo(scanner.nextLine());
//                            System.out.print("Enter Agency ID: ");
//                            newOfficer.setAgencyID(scanner.nextInt());
//
//                            if (service.createOfficer(newOfficer)) {
//                                System.out.println("Officer created successfully.");
//                            } else {
//                                System.out.println("Failed to create Officer.");
//                            }
//                            break;
//
//                        case 6: // Create Evidence
//                            Evidence newEvidence = new Evidence();
//                            
//                            System.out.print("Enter Description: ");
//                            newEvidence.setDescription(scanner.nextLine());
//                            System.out.print("Enter Location Found: ");
//                            newEvidence.setLocationFound(scanner.nextLine());
//                            System.out.print("Enter Incident ID: ");
//                            newEvidence.setIncidentID(scanner.nextInt());
//
//                            if (service.createEvidence(newEvidence)) {
//                                System.out.println("Evidence created successfully.");
//                            } else {
//                                System.out.println("Failed to create Evidence.");
//                            }
//                            break;
//
//                        default:
//                            System.out.println("Invalid table choice!");
//                    }
//                    break;
//
//                case 2: // Update a record
//                    System.out.println("Which table do you want to update a record in? (1: Incident, 2: Victim, 3: Suspect, 4: Agency, 5: Officer, 6: Evidence)");
//                    int updateTableChoice = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline
//
//                    switch (updateTableChoice) {
//                        case 1: // Update Incident Status
//                            System.out.print("Enter Incident ID: ");
//                            int incidentID = scanner.nextInt();
//                            scanner.nextLine(); // Consume newline
//                            System.out.print("Enter new Status: ");
//                            String newStatus = scanner.nextLine();
//                            if (service.updateIncidentStatus(newStatus, incidentID)) {
//                                System.out.println("Incident status updated successfully.");
//                            } else {
//                                System.out.println("Failed to update Incident status.");
//                            }
//                            break;
//
//                        case 2: // Update Victim
//                            System.out.print("Enter Victim ID: ");
//                            int victimID = scanner.nextInt();
//                            scanner.nextLine(); // Consume newline
//                            Victim victimToUpdate = new Victim();
//                            victimToUpdate.setVictimID(victimID);
//                            System.out.print("Enter new First Name: ");
//                            victimToUpdate.setFirstName(scanner.nextLine());
//                            System.out.print("Enter new Last Name: ");
//                            victimToUpdate.setLastName(scanner.nextLine());
//                            System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
//                            victimToUpdate.setDateOfBirth(scanner.nextLine());
//                            System.out.print("Enter new Gender: ");
//                            victimToUpdate.setGender(scanner.nextLine());
//                            System.out.print("Enter new Contact Information: ");
//                            victimToUpdate.setContactInfo(scanner.nextLine());
//
//                            if (service.updateVictim(victimToUpdate)) {
//                                System.out.println("Victim updated successfully.");
//                            } else {
//                                System.out.println("Failed to update Victim.");
//                            }
//                            break;
//
//                        case 3: // Update Suspect
//                            System.out.print("Enter Suspect ID: ");
//                            int suspectID = scanner.nextInt();
//                            scanner.nextLine(); // Consume newline
//                            Suspect suspectToUpdate = new Suspect();
//                            suspectToUpdate.setSuspectID(suspectID);
//                            System.out.print("Enter new First Name: ");
//                            suspectToUpdate.setFirstName(scanner.nextLine());
//                            System.out.print("Enter new Last Name: ");
//                            suspectToUpdate.setLastName(scanner.nextLine());
//                            System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
//                            suspectToUpdate.setDateOfBirth(scanner.nextLine());
//                            System.out.print("Enter new Gender: ");
//                            suspectToUpdate.setGender(scanner.nextLine());
//                            System.out.print("Enter new Contact Information: ");
//                            suspectToUpdate.setContactInfo(scanner.nextLine());
//
//                            if (service.updateSuspect(suspectToUpdate)) {
//                                System.out.println("Suspect updated successfully.");
//                            } else {
//                                System.out.println("Failed to update Suspect.");
//                            }
//                            break;
//
//                        case 4: // Update Agency
//                            System.out.print("Enter Agency ID: ");
//                            int agencyID = scanner.nextInt();
//                            scanner.nextLine(); // Consume newline
//                            Agency agencyToUpdate = new Agency();
//                            agencyToUpdate.setAgencyID(agencyID);
//                            System.out.print("Enter new Agency Name: ");
//                            agencyToUpdate.setAgencyName(scanner.nextLine());
//                            System.out.print("Enter new Jurisdiction: ");
//                            agencyToUpdate.setJurisdiction(scanner.nextLine());
//                            System.out.print("Enter new Contact Information: ");
//                            agencyToUpdate.setContactInfo(scanner.nextLine());
//
//                            if (service.updateAgency(agencyToUpdate)) {
//                                System.out.println("Agency updated successfully.");
//                            } else {
//                                System.out.println("Failed to update Agency.");
//                            }
//                            break;
//
//                        case 5: // Update Officer
//                            System.out.print("Enter Officer ID: ");
//                            int officerID = scanner.nextInt();
//                            scanner.nextLine(); // Consume newline
//                            Officer officerToUpdate = new Officer();
//                            officerToUpdate.setOfficerID(officerID);
//                            System.out.print("Enter new First Name: ");
//                            officerToUpdate.setFirstName(scanner.nextLine());
//                            System.out.print("Enter new Last Name: ");
//                            officerToUpdate.setLastName(scanner.nextLine());
//                            System.out.print("Enter new Badge Number: ");
//                            officerToUpdate.setBadgeNumber(scanner.nextLine());
//                            System.out.print("Enter new Rank: ");
//                            officerToUpdate.setRank(scanner.nextLine());
//                            System.out.print("Enter new Agency ID: ");
//                            // Convert the string input to an int
//                            int AgencyID = scanner.nextInt();
//                            officerToUpdate.setAgencyID(AgencyID); // Now this will accept an int
//
//                            scanner.nextLine();
//                            
//                            System.out.print("Enter new Contact Information: ");
//                            officerToUpdate.setContactInfo(scanner.nextLine());
//
//                            if (service.updateOfficer(officerToUpdate)) {
//                                System.out.println("Officer updated successfully.");
//                            } else {
//                                System.out.println("Failed to update Officer.");
//                            }
//                            break;
//
//                        case 6: // Update Evidence
//                            System.out.print("Enter Evidence ID: ");
//                            int evidenceID = scanner.nextInt();
//                            scanner.nextLine(); // Consume newline
//                            Evidence evidenceToUpdate = new Evidence();
//                            evidenceToUpdate.setEvidenceID(evidenceID);
//                            System.out.print("Enter new Description: ");
//                            evidenceToUpdate.setDescription(scanner.nextLine());
//                            System.out.print("Enter new Location Found: ");
//                            evidenceToUpdate.setLocationFound(scanner.nextLine());
//                            System.out.print("Enter new Incident ID: ");
//                            evidenceToUpdate.setIncidentID(scanner.nextInt());
//
//                            if (service.updateEvidence(evidenceToUpdate)) {
//                                System.out.println("Evidence updated successfully.");
//                            } else {
//                                System.out.println("Failed to update Evidence.");
//                            }
//                            break;
//
//                        default:
//                            System.out.println("Invalid table choice!");
//                    }
//                    break;
//
//                case 3: // Search a record by ID
//                    System.out.println("Which table do you want to search? (1: Incident, 2: Victim, 3: Suspect, 4: Agency, 5: Officer, 6: Evidence)");
//                    int searchTableChoice = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline
//
//                    System.out.print("Enter ID: ");
//                    int searchID = scanner.nextInt();
//
//                    try {
//                        switch (searchTableChoice) {
//                            case 1: // Search Incident
//                                Incident incident = service.getIncidentByID(searchID);
//                                System.out.println(incident);
//                                break;
//                            case 2: // Search Victim
//                                Victim victim = service.getVictimByID(searchID);
//                                System.out.println(victim);
//                                break;
//                            case 3: // Search Suspect
//                                Suspect suspect = service.getSuspectByID(searchID);
//                                System.out.println(suspect);
//                                break;
//                            case 4: // Search Agency
//                                Agency agency = service.getAgencyByID(searchID);
//                                System.out.println(agency);
//                                break;
//                            case 5: // Search Officer
//                                Officer officer = service.getOfficerByID(searchID);
//                                System.out.println(officer);
//                                break;
//                            case 6: // Search Evidence
//                                Evidence evidence = service.getEvidenceByID(searchID);
//                                System.out.println(evidence);
//                                break;
//                            default:
//                                System.out.println("Invalid table choice!");
//                        }
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
//                    break;
//
//                case 4: // Exit
//                    System.out.println("Exiting...");
//                    scanner.close();
//                    return;
//
//                default:
//                    System.out.println("Invalid choice! Please try again.");
//            }
//        }
//    }
//}
