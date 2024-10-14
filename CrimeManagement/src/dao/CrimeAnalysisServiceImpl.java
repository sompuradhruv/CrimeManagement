package dao;

import entity.*;
import exception.*;
import util.DBConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar; // For handling dates
 
public class CrimeAnalysisServiceImpl implements ICrimeAnalysisService {
    private static Connection connection;

    // Constructor to initialize the connection
    public CrimeAnalysisServiceImpl() {
        try {
            // Get the connection using DBConnUtil class
            connection = DBConnectionUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    // Method to create an incident and automatically generate a report
    public void createIncident(Incident incident, Officer officer) {
//        // Insert the incident into the database first
//        insertIncidentIntoDatabase(incident);
//
//        // Now generate a report for the newly created incident
//        Report report = generateIncidentReport(incident, officer, 0);
//        
//        // Insert the generated report into the database
//        insertReportIntoDatabase(report);
//    }
    	  int generatedIncidentID = insertIncidentIntoDatabase(incident);

          // Debugging: Print the generated incidentID
          System.out.println("Generated Incident ID: " + generatedIncidentID);

          // Ensure generatedIncidentID is valid before proceeding
          if (generatedIncidentID > 0) {
              // Now generate a report for the newly created incident using the generated ID
              Report report = generateIncidentReport(incident, officer, generatedIncidentID);
              
              // Insert the generated report into the database
              insertReportIntoDatabase(report);
          } else {
              System.out.println("Failed to insert incident. No report will be created.");
          }
      }


    // Method to insert the incident into the database
    public int insertIncidentIntoDatabase(Incident incident) {
        String query = "INSERT INTO Incident (incidentType, incidentDate, latitude, longitude, description, status, victimID, suspectID, agencyID) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, incident.getIncidentType());
            statement.setDate(2, new java.sql.Date(incident.getIncidentDate().getTime())); // Convert to java.sql.Date
            statement.setDouble(3, incident.getLatitude());
            statement.setDouble(4, incident.getLongitude());
            statement.setString(5, incident.getDescription());
            statement.setString(6, incident.getStatus());
            statement.setInt(7, incident.getVictimID());
            statement.setInt(8, incident.getSuspectID());
            statement.setInt(9, incident.getAgencyID());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new incident was inserted successfully!");

                // Retrieve the generated incidentID
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated incidentID
                    } else {
                        throw new SQLException("Creating incident failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return an invalid ID if insertion fails
    }

    @Override
    public boolean updateIncidentStatus(String status, int incidentID) {
        String query = "UPDATE Incident SET Status = ? WHERE IncidentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, incidentID);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Incident> getIncidentsInDateRange(String startDate, String endDate) {
        List<Incident> incidents = new ArrayList<>();
        String query = "SELECT * FROM Incident WHERE IncidentDate BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Incident incident = new Incident();
                incident.setIncidentID(rs.getInt("IncidentID"));
                incident.setIncidentType(rs.getString("IncidentType"));
                incident.setIncidentDate(rs.getDate("IncidentDate"));
                incident.setLatitude(rs.getDouble("Latitude"));
                incident.setLongitude(rs.getDouble("Longitude"));
                incident.setDescription(rs.getString("Description"));
                incident.setStatus(rs.getString("Status"));
                incident.setVictimID(rs.getInt("VictimID"));
                incident.setSuspectID(rs.getInt("SuspectID"));
                incident.setAgencyID(rs.getInt("AgencyID")); // Set AgencyID
                incidents.add(incident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incidents;
    }


    
//    public List<Incident> searchIncidents(String incidentType) {
//        List<Incident> incidents = new ArrayList<>();
//        String query = "SELECT * FROM Incident WHERE IncidentType = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, incidentType);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Incident incident = new Incident();
//                incident.setIncidentID(rs.getInt("IncidentID"));
//                incident.setIncidentType(rs.getString("IncidentType"));
//                incident.setIncidentDate(rs.getDate("IncidentDate"));
//                incident.setLatitude(rs.getDouble("Latitude"));
//                incident.setLongitude(rs.getDouble("Longitude"));
//                incident.setDescription(rs.getString("Description"));
//                incident.setStatus(rs.getString("Status"));
//                incident.setVictimID(rs.getInt("VictimID"));
//                incident.setSuspectID(rs.getInt("SuspectID"));
//                incident.setAgencyID(rs.getInt("AgencyID"));
//                incidents.add(incident);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return incidents;
//    }
//
//    //issue: to generate reports we need reportsid and reportingoffiicer where do i get them?? 
//    //other fields i am getting from incident object, these 2 i am not
//    @Override
//    public Report generateIncidentReport(Incident incident) {
//        Report report = new Report();
//        
//        // Set the incidentID from the Incident object
//        report.setIncidentID(incident.getIncidentID());
//        
//        // Assuming reportingOfficer, status, and reportDetails are set elsewhere
//        report.setReportingOfficer(1234);  // Example officer ID, adjust as needed
//        report.setStatus("Pending Investigation");
//        
//        // Set current date as report date
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String currentDate = sdf.format(new Date());
//        report.setReportDate(currentDate);
//        
//        // Generate report details from the Incident description
//        String reportDetails = "Incident Type: " + incident.getIncidentType() +
//                               "\nDate: " + incident.getIncidentDate() +
//                               "\nLocation: (" + incident.getLatitude() + ", " + incident.getLongitude() + ")" +
//                               "\nDescription: " + incident.getDescription();
//        report.setReportDetails(reportDetails);
//        
//        return report; // Return the constructed report object
//    }
//    public void insertReportIntoDatabase(Report report) {
//        String query = "INSERT INTO Reports (incidentID, reportingOfficer, reportDate, reportDetails, status) " +
//                     "VALUES (?, ?, ?, ?, ?)";
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//
//            // Set the values for the PreparedStatement
//            statement.setInt(1, report.getIncidentID());
//            statement.setInt(2, report.getReportingOfficer());
//            statement.setString(3, report.getReportDate());
//            statement.setString(4, report.getReportDetails());
//            statement.setString(5, report.getStatus());
//            
//            // Execute the insert statement
//            int rowsInserted = statement.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("A new report was inserted successfully!");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    } private void fetchAllReports() {
//        String query = "SELECT * FROM Report";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet rs = statement.executeQuery()) {
//            while (rs.next()) {
//                System.out.println("Report ID: " + rs.getInt("reportID") + ", " +
//                                   "Incident ID: " + rs.getInt("incidentID") + ", " +
//                                   "Officer ID: " + rs.getInt("reportingOfficer") + ", " +
//                                   "Report Date: " + rs.getDate("reportDate") + ", " +
//                                   "Details: " + rs.getString("reportDetails") + ", " +
//                                   "Status: " + rs.getString("status"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void generateReportFromIncident(int incidentID) {
        Incident incident = getIncidentById(incidentID);
        Officer officer = getAvailableOfficer();
        
        if (incident != null && officer != null) {
            Report report = generateIncidentReport(incident, officer, incidentID);
            insertReportIntoDatabase(report);
        } else {
            System.out.println("Incident or officer not found.");
        }
    }

    // Method to retrieve an incident by ID
    public Incident getIncidentById(int incidentID) {
        String query = "SELECT * FROM Incident WHERE incidentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, incidentID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Incident(
                    resultSet.getInt("incidentID"),
                    resultSet.getString("incidentType"),
                    resultSet.getDate("incidentDate"),
                    resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getInt("victimID"),
                    resultSet.getInt("suspectID"),
                    resultSet.getInt("agencyID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve an available officer
    public Officer getAvailableOfficer() {
        String query = "SELECT * FROM Officer LIMIT 1"; // Assuming we take the first available officer
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Officer(
                    resultSet.getInt("officerID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("badgeNumber"),
                    resultSet.getString("rank_"),
                    resultSet.getString("contactInfo"),
                    resultSet.getInt("agencyID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Incident fetchLatestIncident(Connection connection) {
        String query = "SELECT * FROM Incident ORDER BY incidentID DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                // Create and return an Incident object from the result
                return new Incident(
                    resultSet.getInt("incidentID"),
                    resultSet.getString("incidentType"),
                    resultSet.getDate("incidentDate"),
                    resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getInt("victimID"),
                    resultSet.getInt("suspectID"),
                    resultSet.getInt("agencyID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No incident found
    }

    public Officer fetchAnyOfficer(Connection connection) {
        String query = "SELECT * FROM Officer LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                // Create and return an Officer object from the result
                return new Officer(
                    resultSet.getInt("officerID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("badgeNumber"),
                    resultSet.getString("rank_"),
                    resultSet.getString("contactInfo"),
                    resultSet.getInt("agencyID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No officer found
    }
    // Method to generate a report based on the incident and officer
    public   Report generateIncidentReport(Incident incident, Officer officer, int generatedIncidentID) {
        Report report = new Report();
        report.setIncidentID(generatedIncidentID); // Use the generated incidentID
        report.setReportingOfficer(officer.getOfficerID());
        report.setReportDate(new java.sql.Date(System.currentTimeMillis())); // Set the report date as current date
        report.setReportDetails(incident.getDescription());
        report.setStatus(incident.getStatus());
        return report;
    }



    // Method to insert the report into the database
    public void insertReportIntoDatabase(Report report) {
        String query = "INSERT INTO Report (incidentID, reportingOfficer, reportDate, reportDetails, status) " +
                       "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, report.getIncidentID());
            statement.setInt(2, report.getReportingOfficer());
            statement.setDate(3, report.getReportDate());
            statement.setString(4, report.getReportDetails());
            statement.setString(5, report.getStatus());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new report was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Optional method to fetch all reports (for verification/debugging)
    public void fetchAllReports() {
        String query = "SELECT * FROM Report";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Report ID: " + resultSet.getInt("reportID"));
                System.out.println("Incident ID: " + resultSet.getInt("incidentID"));
                System.out.println("Reporting Officer: " + resultSet.getInt("reportingOfficer"));
                System.out.println("Report Date: " + resultSet.getDate("reportDate"));
                System.out.println("Report Details: " + resultSet.getString("reportDetails"));
                System.out.println("Status: " + resultSet.getString("status"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   
    @Override
    public boolean createCase(String caseDescription, List<Incident> incidents) {
        Case newCase = new Case();
        newCase.setCaseDescription(caseDescription);
        newCase.setIncidents(incidents);

        // Insert the case into the database using the INSERT statement
        String insertCaseQuery = "INSERT INTO Case_ (caseDescription) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertCaseQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, caseDescription);
            stmt.executeUpdate();

            // Retrieve the generated case ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                newCase.setCaseID(generatedKeys.getInt(1)); // Set the generated Case ID
                
                // If there are incidents, link each incident to the case
                if (incidents != null && !incidents.isEmpty()) {
                    for (Incident incident : incidents) {
                        linkIncidentToCase(newCase.getCaseID(), incident.getIncidentID());
                    }
                }
            } else {
                System.out.println("Failed to retrieve the generated case ID.");
                return false; // Case insertion failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error occurred while creating the case.");
            return false;
        }

        return true; // Successfully created case
    }

    public void linkIncidentToCase(int caseID, int incidentID) {
        String linkQuery = "INSERT INTO Case_Incident (caseID, incidentID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(linkQuery)) {
            stmt.setInt(1, caseID);
            stmt.setInt(2, incidentID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to link Incident ID: " + incidentID + " to Case ID: " + caseID);
        }
    }


    
    


    @Override
    public Case getCaseDetails(int caseID) {
        Case caseDetails = null;

        try {
            // Query to get case details
            String caseQuery = "SELECT * FROM Case_ WHERE caseID = ?";
            PreparedStatement caseStmt = connection.prepareStatement(caseQuery);
            caseStmt.setInt(1, caseID);
            ResultSet caseResultSet = caseStmt.executeQuery();

            // Check if the case exists
            if (caseResultSet.next()) {
                caseDetails = new Case();
                caseDetails.setCaseID(caseResultSet.getInt("caseID"));
                caseDetails.setCaseDescription(caseResultSet.getString("caseDescription"));

                // Now retrieve the associated incidents
                List<Incident> incidentList = new ArrayList<>();
                String incidentQuery = "SELECT i.* FROM Incidents i "
                                      + "JOIN IncidentCases ic ON i.incidentID = ic.incidentID "
                                      + "WHERE ic.caseID = ?";
                PreparedStatement incidentStmt = connection.prepareStatement(incidentQuery);
                incidentStmt.setInt(1, caseID);
                ResultSet incidentResultSet = incidentStmt.executeQuery();

                while (incidentResultSet.next()) {
                    Incident incident = new Incident();
                    incident.setIncidentID(incidentResultSet.getInt("incidentID"));
                    incident.setIncidentType(incidentResultSet.getString("incidentType"));
                    incident.setIncidentDate(incidentResultSet.getDate("incidentDate"));
                    
                    incident.setLatitude(incidentResultSet.getDouble("latitude")); 
                    incident.setLongitude(incidentResultSet.getDouble("longitude")); 
                    
                    incident.setDescription(incidentResultSet.getString("description"));
                    incident.setStatus(incidentResultSet.getString("status"));
                    incident.setVictimID(incidentResultSet.getInt("victimID"));
                    incident.setSuspectID(incidentResultSet.getInt("suspectID"));
                    incident.setAgencyID(incidentResultSet.getInt("AgencyID"));

                    incidentList.add(incident);
                }

                caseDetails.setIncidents(incidentList); // Associate incidents with the case
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return caseDetails; // Return the populated case object or null if not found
    }



    @Override
    public boolean updateCaseDetails(Case caseDetails) {
        String updateQuery = "UPDATE Case_ SET caseDescription = ? WHERE caseID = ?";
        
        // Using try-with-resources to automatically close the PreparedStatement
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, caseDetails.getCaseDescription()); // Set new case description
            stmt.setInt(2, caseDetails.getCaseID()); // Set case ID for which the description needs to be updated

            // Execute update and check if any rows were updated
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated, false otherwise
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception stack trace for debugging
            return false; // Return false if any exception occurs
        }
    }
    public boolean updateCaseIncidents(int caseID, List<Incident> updatedIncidents) {
        try {
            // First, delete old associations for this case
            String deleteQuery = "DELETE FROM Case_Incident WHERE caseID = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, caseID);
            deleteStmt.executeUpdate();
            
            // Now, add the updated incidents
            String insertQuery = "INSERT INTO Case_Incident (caseID, incidentID) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            
            for (Incident incident : updatedIncidents) {
                insertStmt.setInt(1, caseID);
                insertStmt.setInt(2, incident.getIncidentID());
                insertStmt.addBatch(); // Batch the insert statements
            }
            
            // Execute the batch insert
            insertStmt.executeBatch();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public List<Case> getAllCases() {
        List<Case> caseList = new ArrayList<>();

        try {
            // Query to get all cases
            String query = "SELECT * FROM Case_";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Case caseDetails = new Case();
                caseDetails.setCaseID(resultSet.getInt("caseID"));
                caseDetails.setCaseDescription(resultSet.getString("caseDescription"));

                caseList.add(caseDetails); // Add the case to the list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return caseList; // Return the list of cases
    }
    
    @Override
    public Incident getIncidentByID(int incidentID) throws IncidentNumberNotFoundException {
        String query = "SELECT * FROM Incident WHERE IncidentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, incidentID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Incident incident = new Incident();
                incident.setIncidentID(rs.getInt("IncidentID"));
                incident.setIncidentType(rs.getString("IncidentType"));
                incident.setIncidentDate(rs.getDate("IncidentDate"));
                incident.setLatitude(rs.getDouble("Latitude"));
                incident.setLongitude(rs.getDouble("Longitude"));
                incident.setDescription(rs.getString("Description"));
                incident.setStatus(rs.getString("Status"));
                incident.setVictimID(rs.getInt("VictimID"));
                incident.setSuspectID(rs.getInt("SuspectID"));
                return incident;
            } else {
                throw new IncidentNumberNotFoundException("Incident number " + incidentID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Victim getVictimByID(int victimID) throws VictimNumberNotFoundException {
        String query = "SELECT * FROM Victim WHERE VictimID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, victimID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Victim victim = new Victim();
                victim.setVictimID(rs.getInt("VictimID"));
                victim.setFirstName(rs.getString("FirstName"));
                victim.setLastName(rs.getString("LastName"));
                victim.setDateOfBirth(rs.getString("DateOfBirth"));
                victim.setGender(rs.getString("Gender"));
                victim.setContactInfo(rs.getString("contactInfo"));
                return victim;
            } else {
                throw new VictimNumberNotFoundException("Victim ID " + victimID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Suspect getSuspectByID(int suspectID) throws SuspectNumberNotFoundException {
        String query = "SELECT * FROM Suspect WHERE SuspectID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, suspectID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Suspect suspect = new Suspect();
                suspect.setSuspectID(rs.getInt("SuspectID"));
                suspect.setFirstName(rs.getString("FirstName"));
                suspect.setLastName(rs.getString("LastName"));
                suspect.setDateOfBirth(rs.getString("DateOfBirth"));
                suspect.setGender(rs.getString("Gender"));
                suspect.setContactInfo(rs.getString("contactInfo"));
                return suspect;
            } else {
                throw new SuspectNumberNotFoundException("Suspect ID " + suspectID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Agency getAgencyByID(int agencyID) throws AgencyNumberNotFoundException {
        String query = "SELECT * FROM LawEnforcementAgencies WHERE AgencyID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, agencyID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Agency agency = new Agency();
                agency.setAgencyID(rs.getInt("AgencyID"));
                agency.setAgencyName(rs.getString("AgencyName"));
                agency.setJurisdiction(rs.getString("Jurisdiction"));
                agency.setContactInfo(rs.getString("contactInfo"));
                return agency;
            } else {
                throw new AgencyNumberNotFoundException("Agency ID " + agencyID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Officer getOfficerByID(int officerID) throws OfficerNumberNotFoundException {
        String query = "SELECT * FROM Officer WHERE OfficerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, officerID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Officer officer = new Officer();
                officer.setOfficerID(rs.getInt("OfficerID"));
                officer.setFirstName(rs.getString("FirstName"));
                officer.setLastName(rs.getString("LastName"));
                officer.setBadgeNumber(rs.getString("BadgeNumber"));
                officer.setRank(rs.getString("Rank_"));
                officer.setContactInfo(rs.getString("contactInfo"));
                officer.setAgencyID(rs.getInt("AgencyID"));
                return officer;
            } else {
                throw new OfficerNumberNotFoundException("Officer ID " + officerID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Case getCaseByID(int caseID) throws CaseNumberNotFoundException {
        String query = "SELECT * FROM Case_ WHERE caseID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, caseID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Case caseDetails = new Case();
                caseDetails.setCaseID(rs.getInt("caseID"));
                caseDetails.setCaseDescription(rs.getString("caseDescription"));

                // Now retrieve the associated incidents through Case_Incident
                List<Incident> incidentList = new ArrayList<>();
                String incidentQuery = "SELECT i.* FROM Incident i " +
                                        "JOIN Case_Incident ci ON i.incidentID = ci.incidentID " +
                                        "WHERE ci.caseID = ?";
                try (PreparedStatement incidentStmt = connection.prepareStatement(incidentQuery)) {
                    incidentStmt.setInt(1, caseID);
                    ResultSet incidentResultSet = incidentStmt.executeQuery();

                    while (incidentResultSet.next()) {
                        Incident incident = new Incident();
                        incident.setIncidentID(incidentResultSet.getInt("incidentID"));
                        incident.setIncidentType(incidentResultSet.getString("incidentType"));
                        incident.setIncidentDate(incidentResultSet.getDate("incidentDate"));
                        // Set other incident attributes if necessary
                        incidentList.add(incident);
                    }
                }

                caseDetails.setIncidents(incidentList); // Associate incidents with the case
                return caseDetails;
            } else {
                throw new CaseNumberNotFoundException("Case ID " + caseID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    @Override
    public Report getReportByID(int reportID) throws ReportNumberNotFoundException {
        String query = "SELECT * FROM Reports WHERE ReportID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reportID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Report reportDetails = new Report();
                reportDetails.setReportID(rs.getInt("ReportID"));
                reportDetails.setIncidentID(rs.getInt("IncidentID"));
                reportDetails.setReportingOfficer(rs.getInt("ReportingOfficer"));
                reportDetails.setReportDate((java.sql.Date) new Date(System.currentTimeMillis()));;
                reportDetails.setReportDetails(rs.getString("ReportDetails"));
                reportDetails.setStatus(rs.getString("Status"));

                return reportDetails;
            } else {
                throw new ReportNumberNotFoundException("Report ID " + reportID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Evidence getEvidenceByID(int evidenceID) throws EvidenceNumberNotFoundException {
        String query = "SELECT * FROM Evidence WHERE EvidenceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, evidenceID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Evidence evidenceDetails = new Evidence();
                evidenceDetails.setEvidenceID(rs.getInt("EvidenceID"));
                evidenceDetails.setDescription(rs.getString("Description"));
                evidenceDetails.setLocationFound(rs.getString("LocationFound"));
                evidenceDetails.setIncidentID(rs.getInt("IncidentID"));

                return evidenceDetails;
            } else {
                throw new EvidenceNumberNotFoundException("Evidence ID " + evidenceID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
//--------------------------------------------------------------------------------------------------------
    @Override
    public boolean createVictim(Victim victim) {
        String query = "INSERT INTO Victim (VictimID, FirstName, LastName, DateOfBirth, Gender, ContactInfo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, victim.getVictimID());
            stmt.setString(2, victim.getFirstName());
            stmt.setString(3, victim.getLastName());
            stmt.setString(4, victim.getDateOfBirth());
            stmt.setString(5, victim.getGender());
            stmt.setString(6, victim.getContactInfo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateVictim(Victim victim) {
        String query = "UPDATE Victim SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, ContactInfo = ? WHERE VictimID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, victim.getFirstName());
            stmt.setString(2, victim.getLastName());
            stmt.setString(3, victim.getDateOfBirth());
            stmt.setString(4, victim.getGender());
            stmt.setString(5, victim.getContactInfo());
            stmt.setInt(6, victim.getVictimID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Victim> getAllVictims() {
        List<Victim> victims = new ArrayList<>();
        String query = "SELECT * FROM Victim";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Victim victim = new Victim();
                victim.setVictimID(rs.getInt("VictimID"));
                victim.setFirstName(rs.getString("FirstName"));
                victim.setLastName(rs.getString("LastName"));
                victim.setDateOfBirth(rs.getString("DateOfBirth"));
                victim.setGender(rs.getString("Gender"));
                victim.setContactInfo(rs.getString("ContactInfo"));
                victims.add(victim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return victims;
    }

    
//------------------------------------------------------------------------------------------------

    @Override
    public boolean createSuspect(Suspect suspect) {
        String query = "INSERT INTO Suspect ( FirstName, LastName, DateOfBirth, Gender, ContactInfo) VALUES ( ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, suspect.getFirstName());
            stmt.setString(2, suspect.getLastName());
            stmt.setString(3, suspect.getDateOfBirth());
            stmt.setString(4, suspect.getGender());
            stmt.setString(5, suspect.getContactInfo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSuspect(Suspect suspect) {
        String query = "UPDATE Suspect SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, ContactInfo = ? WHERE SuspectID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, suspect.getFirstName());
            stmt.setString(2, suspect.getLastName());
            stmt.setString(3, suspect.getDateOfBirth());
            stmt.setString(4, suspect.getGender());
            stmt.setString(5, suspect.getContactInfo());
            stmt.setInt(6, suspect.getSuspectID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Suspect> getAllSuspects() {
        List<Suspect> suspects = new ArrayList<>();
        String query = "SELECT * FROM Suspect";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Suspect suspect = new Suspect();
                suspect.setSuspectID(rs.getInt("SuspectID"));
                suspect.setFirstName(rs.getString("FirstName"));
                suspect.setLastName(rs.getString("LastName"));
                suspect.setDateOfBirth(rs.getString("DateOfBirth"));
                suspect.setGender(rs.getString("Gender"));
                suspect.setContactInfo(rs.getString("ContactInfo"));
                suspects.add(suspect);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suspects;
    }

//---------------------------------------------------------------------------------------------------------------------
    public boolean createAgency(Agency agency) {
        // Check if connection is established
        if (connection == null) {
            System.err.println("Database connection is not established.");
            return false;
        }

        String query = "INSERT INTO Agency (agencyName, jurisdiction, contactInfo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, agency.getAgencyName());
            stmt.setString(2, agency.getJurisdiction());
            stmt.setString(3, agency.getContactInfo());

            // Debug output before execution
            System.out.println("Inserting Agency: " + agency.getAgencyName() + ", " + agency.getJurisdiction() + ", " + agency.getContactInfo());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for debugging
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            return false;         // Return false if the insert failed
        }
    }



    @Override
    public boolean updateAgency(Agency agency) {
    	String query = "UPDATE Agency SET agencyName = ?, jurisdiction = ?, contactInfo = ? WHERE agencyID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, agency.getAgencyName());
            stmt.setString(2, agency.getJurisdiction());
            stmt.setString(3, agency.getContactInfo());
            stmt.setInt(4, agency.getAgencyID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Agency> getAllAgencies() {
        List<Agency> agencies = new ArrayList<>();
        String query = "SELECT * FROM LawEnforcementAgencies";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Agency agency = new Agency();
                agency.setAgencyID(rs.getInt("AgencyID"));
                agency.setAgencyName(rs.getString("AgencyName"));
                agency.setJurisdiction(rs.getString("Jurisdiction"));
                agency.setContactInfo(rs.getString("ContactInfo"));
                agencies.add(agency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agencies;
    }

//-----------------------------------------------------------------------------------------------------------------
    
    @Override
    public boolean createOfficer(Officer officer) {
        String query = "INSERT INTO Officer (FirstName, LastName, BadgeNumber, Rank_ , contactInfo, AgencyID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, officer.getFirstName());
            stmt.setString(2, officer.getLastName());
            stmt.setString(3, officer.getBadgeNumber());
            stmt.setString(4, officer.getRank());
            stmt.setString(5, officer.getContactInfo());
            stmt.setInt(6, officer.getAgencyID()); // Ensure this value is correct
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
   public boolean updateOfficer(Officer officer) {
        String query = "UPDATE Officer SET FirstName = ?, LastName = ?, BadgeNumber = ?, Rank_ = ?, AgencyID = ?, ContactInfo = ? WHERE OfficerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, officer.getFirstName());
            stmt.setString(2, officer.getLastName());
            stmt.setString(3, officer.getBadgeNumber());
            stmt.setString(4, officer.getRank());  // Ensure the correct column name in the database
            stmt.setInt(5, officer.getAgencyID());
            stmt.setString(6, officer.getContactInfo());  // ContactInfo is now correctly placed as the 6th parameter
            stmt.setInt(7, officer.getOfficerID());  // OfficerID should be the last parameter in WHERE clause

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        
    @Override
    public List<Officer> getAllOfficers() {
        List<Officer> officers = new ArrayList<>();
        String query = "SELECT * FROM Officer";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Officer officer = new Officer();
                officer.setOfficerID(rs.getInt("OfficerID"));
                officer.setFirstName(rs.getString("FirstName"));
                officer.setLastName(rs.getString("LastName"));
                officer.setBadgeNumber(rs.getString("BadgeNumber"));
                officer.setRank(rs.getString("Rank"));
                officer.setContactInfo(rs.getString("ContactInfo"));
                officer.setAgencyID(rs.getInt("AgencyID"));
                officers.add(officer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return officers;
    }

 //-------------------------------------------------------------------------------------------------
    
    @Override
    public boolean createEvidence(Evidence evidence) {
        String query = "INSERT INTO Evidence ( Description, LocationFound, IncidentID) VALUES ( ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
          
            stmt.setString(1, evidence.getDescription());
            stmt.setString(2, evidence.getLocationFound());
            stmt.setInt(3, evidence.getIncidentID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEvidence(Evidence evidence) {
        String query = "UPDATE Evidence SET Description = ?, LocationFound = ?, IncidentID = ? WHERE EvidenceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, evidence.getDescription());
            stmt.setString(2, evidence.getLocationFound());
            stmt.setInt(3, evidence.getIncidentID());
            stmt.setInt(4, evidence.getEvidenceID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Evidence> getAllEvidence() {
        List<Evidence> evidenceList = new ArrayList<>();
        String query = "SELECT * FROM Evidence";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Evidence evidence = new Evidence();
                evidence.setEvidenceID(rs.getInt("EvidenceID"));
                evidence.setDescription(rs.getString("Description"));
                evidence.setLocationFound(rs.getString("LocationFound"));
                evidence.setIncidentID(rs.getInt("IncidentID"));
                evidenceList.add(evidence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evidenceList;
    }

	@Override
	public List<Incident> searchIncidents(String incidentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Report generateIncidentReport(Incident incident) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean createIncident(Incident incident) {
		// TODO Auto-generated method stub
		return false;
	}


 //---------------------------------------------------------------------------------------------------
    
    
    

}
