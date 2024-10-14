package dao;

import entity.*;

import exception.*;

import java.util.List;

public interface ICrimeAnalysisService{
    boolean createIncident(Incident incident);
    boolean updateIncidentStatus(String status, int incidentID);
    List<Incident> getIncidentsInDateRange(String startDate, String endDate);
    List<Incident> searchIncidents(String incidentType);
    Report generateIncidentReport(Incident incident);
    boolean createCase(String caseDescription, List<Incident> incidents);
    Case getCaseDetails(int caseID);
    boolean updateCaseDetails(Case caseDetails);
    List<Case> getAllCases();
	Incident getIncidentByID(int incidentID) throws IncidentNumberNotFoundException;
	Victim getVictimByID(int victimID) throws VictimNumberNotFoundException;
	Suspect getSuspectByID(int suspectID) throws SuspectNumberNotFoundException;
	Agency getAgencyByID(int agencyID) throws AgencyNumberNotFoundException;
	Officer getOfficerByID(int officerID) throws OfficerNumberNotFoundException;
	Case getCaseByID(int caseID) throws CaseNumberNotFoundException;
	Report getReportByID(int reportID) throws ReportNumberNotFoundException;
	Evidence getEvidenceByID(int evidenceID) throws EvidenceNumberNotFoundException;
	boolean createVictim(Victim victim);
	boolean updateVictim(Victim victim);
	List<Victim> getAllVictims();
	boolean createSuspect(Suspect suspect);
	boolean updateSuspect(Suspect suspect);
	List<Suspect> getAllSuspects();
	boolean createAgency(Agency agency);
	boolean updateAgency(Agency agency);
	List<Agency> getAllAgencies();
	List<Officer> getAllOfficers();
	boolean updateOfficer(Officer officer);
	boolean createOfficer(Officer officer);
	List<Evidence> getAllEvidence();
	boolean updateEvidence(Evidence evidence);
	boolean createEvidence(Evidence evidence);
}
