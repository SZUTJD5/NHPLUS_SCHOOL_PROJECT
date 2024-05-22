package de.hitec.nhplus.datastorage;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory() {
    }

    /**
     * Gibt eine Instanz der DaoFactory-Klasse zurück.
     * Falls noch keine Instanz vorhanden ist, wird eine neue erstellt.
     * Nutzt das Singleton-Muster, um sicherzustellen, dass nur eine Instanz zurzeit existiert.
     *
     * @return die Singleton-Instanz der DaoFactory.
     */
    public static DaoFactory getDaoFactory() {
        if (DaoFactory.instance == null) {
            DaoFactory.instance = new DaoFactory();
        }
        return DaoFactory.instance;
    }

    /**
     * Gibt eine Instanz der TreatmentDao-Klasse zurück.
     * Diese wird für den Zugriff auf die Behandlungsdatenbank genutzt.
     * Verwendet die ConnectionBuilder.getConnection()-Methode, um sich mit der Datenbank zu verbinden.
     *
     * @return eine Instanz der TreatmentDao-Klasse.
     */
    public TreatmentDao createTreatmentDao() {
        return new TreatmentDao(ConnectionBuilder.getConnection());
    }

    /**
     * Erzeugt und gibt eine Instanz der PatientDao-Klasse zurück.
     * Diese wird für den Zugriff auf die Patientendatenbank verwendet.
     * Verwendet die ConnectionBuilder.getConnection()-Methode, um sich mit der Datenbank zu verbinden.
     *
     * @return eine Instanz der PatientDao-Klasse.
     */
    public PatientDao createPatientDAO() { // Methode und Beschreibung angepasst, damit die Namensgebung einheitlich ist
        return new PatientDao(ConnectionBuilder.getConnection());
    }

    /**
     * Erzeugt und gibt eine Instanz der CaregiverDao-Klasse zurück.
     * Diese wird für den Zugriff auf die Pflegerdatenbank verwendet.
     * Verwendet die ConnectionBuilder.getConnection()-Methode, um sich mit der Datenbank zu verbinden.
     *
     * @return eine Instanz der CaregiverDao-Klasse.
     */
    public CaregiverDao createCaregiverDAO() { // Methode und Beschreibung angepasst, damit die Namensgebung einheitlich ist
        return new CaregiverDao(ConnectionBuilder.getConnection());
    }
}