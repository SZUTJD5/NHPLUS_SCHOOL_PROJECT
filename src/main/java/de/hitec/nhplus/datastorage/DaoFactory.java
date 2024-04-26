package de.hitec.nhplus.datastorage;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory() {
    }

    /**
        Diese Methode gibt eine Instanz der DaoFactory-Klasse aus.
        Falls noch keine Instanz vorhanden ist wird eine neue erstellt.
        Nutzt Singletons um sicher zu stellen das nur eine Instanz zur Zeit existiert.
     */
    public static DaoFactory getDaoFactory() {
        if (DaoFactory.instance == null) {
            DaoFactory.instance = new DaoFactory();
        }
        return DaoFactory.instance;
    }

    /**
     *  Diese Methode gibt eine Instanz der TreatmentDao-Klasse aus.
     *  Diese wird für den Zugriff auf die Behandlungsdatenbank genutzt.
     *  Die Methode verwendet die "ConnectionBuilder.getConnection()-Methode" um sich mit der Datenbank zu verbinden.
     */
    public TreatmentDao createTreatmentDao() {
        return new TreatmentDao(ConnectionBuilder.getConnection());
    }

    /**
     *  Diese Methode erzeugt und gibt eine Instanz der PatientDao-Klasse zurück.
     *  Diese wird für den Zugriff auf die patient-Datenbank verwendet.
     *  Hier wird die ConnectionBuilder.getConnection()-Methode verwendet, um sich mit der Datenbank zu verbinden.
     */
    public PatientDao createPatientDAO() {
        return new PatientDao(ConnectionBuilder.getConnection());
    }
}
