package com.ftn.sbnz.service;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.ObjectDataCompiler;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Servis koji ucitava Drools Rule Template (.drt) fajlove i primenjuje
 * podatke na sablone da bi generisao DRL pravila.
 *
 * Svaki sablon definise strukturu pravila sa parametrima (@{param}).
 * ObjectDataCompiler zamenjuje parametre konkretnim vrednostima iz tabele
 * podataka i generise gotov DRL string koji se moze ucitati u KieSession.
 */
@Service
public class TemplateService {

    /**
     * Sablon 1: Provera isteka dokumenata
     * Generiše pravila koja proveravaju da li je vozacka dozvola istekla
     * ili je blizu isteka, sa razlicitim nivoima ozbiljnosti.
     */
    public String generisiPravilaIstekaDozvole() {
        List<Map<String, Object>> podaci = List.of(
            row("Vozacka dozvola istekla",
                "expiryDate != null, expiryDate.isBefore(java.time.LocalDate.now())",
                "ISTEKLA_VOZACKA_SABLON", "Visok",
                "Vozacka dozvola je istekla", 2, "ISKLJUCENJE_IZ_SAOBRACAJA"),
            row("Vozacka dozvola istice za 30 dana",
                "expiryDate != null, expiryDate.isBefore(java.time.LocalDate.now().plusDays(30)), expiryDate.isAfter(java.time.LocalDate.now())",
                "DOZVOLA_ISTICE_USKORO", "Nizak",
                "Vozacka dozvola istice za manje od 30 dana", 1, "UPOZORENJE")
        );
        return kompajlirajSablon("/com/ftn/sbnz/kjar/rules/templates/template-istek-dokumenta.drt", podaci);
    }

    /**
     * Sablon 2: Pravila za vozace pocetnike
     * Nulta tolerancija na alkohol — svaki detektovani alkohol je prekrsaj.
     * Sablon primenjuje parametre na template-pocetnik-vozac.drt.
     */
    public String generisiPravilaPocetnikaVozaca() {
        List<Map<String, Object>> podaci = List.of(
            rowKategorija(0.0,
                "POCETNIK_ALKOHOL_SABLON_01", "Visok",
                "Vozac pocetnik ima alkohol u krvi - nulta tolerancija (do 0.5 promila)", 3, "ZADRZAVANJE_VOZACA"),
            rowKategorija(0.5,
                "POCETNIK_ALKOHOL_SABLON_02", "Kritican",
                "Vozac pocetnik sa znacajnim alkoholom u krvi (iznad 0.5 promila)", 4, "ISKLJUCENJE_IZ_SAOBRACAJA")
        );
        return kompajlirajSablon("/com/ftn/sbnz/kjar/rules/templates/template-pocetnik-vozac.drt", podaci);
    }

    /**
     * Sablon 3: Pravila za profesionalne vozace
     * Nulta tolerancija na alkohol zbog kategorizacije vozacke dozvole (C, D, E).
     * Sablon primenjuje parametre na template-profesionalni-vozac.drt.
     */
    public String generisiPravilaProfesionalnihVozaca() {
        List<Map<String, Object>> podaci = List.of(
            rowKategorija(0.0,
                "PROFESIONALNI_ALKOHOL_SABLON_01", "Visok",
                "Profesionalni vozac ima alkohol u krvi - nulta tolerancija", 3, "ISKLJUCENJE_IZ_SAOBRACAJA"),
            rowKategorija(0.5,
                "PROFESIONALNI_ALKOHOL_SABLON_02", "Kritican",
                "Profesionalni vozac sa znacajnim alkoholom (iznad 0.5 promila) - teza mera", 4, "ZADRZAVANJE_VOZACA")
        );
        return kompajlirajSablon("/com/ftn/sbnz/kjar/rules/templates/template-profesionalni-vozac.drt", podaci);
    }

    /**
     * Sablon 3: Kontekstualna procena nivoa rizika
     * Generiše pravila koja povecavaju nivo rizika na osnovu konteksta kontrole
     * (nocna kontrola, zona skole, vikend, zona visokog rizika).
     */
    public String generisiPravilaKontekstRizika() {
        try (
            InputStream templateIs = getClass().getResourceAsStream(
                "/com/ftn/sbnz/kjar/rules/templates/template-procena-rizika.drt");
            InputStream csvIs = getClass().getResourceAsStream("/procena-rizika-podaci.csv")
        ) {
            if (templateIs == null || csvIs == null) {
                return "// GRESKA: fajl nije pronadjen";
            }
            String csvSadrzaj = new String(csvIs.readAllBytes(), StandardCharsets.UTF_8);
            String[] linije = csvSadrzaj.trim().split("\n");
            String[][] podaci = new String[linije.length][];
            for (int i = 0; i < linije.length; i++) {
                podaci[i] = linije[i].trim().split(",");
            }
            final int[] idx = {0};
            DataProvider dataProvider = new DataProvider() {
                public boolean hasNext() { return idx[0] < podaci.length; }
                public String[] next() { return podaci[idx[0]++]; }
            };
            DataProviderCompiler compiler = new DataProviderCompiler();
            return compiler.compile(dataProvider, templateIs);
        } catch (Exception e) {
            return "// GRESKA pri kompajliranju sablona: " + e.getMessage();
        }
    }

    /**
     * Sablon 4: Pravila za CEP alarme
     * Generiše CEP pravila sa razlicitim pragovima za detekciju agresivne voznje.
     * Isti sablon se moze primeniti za razlicite kombinacije dogadjaja i vremenskih prozora.
     */
    public String generisiPravilaCepAlarmi() {
        List<Map<String, Object>> podaci = List.of(
            rowCep("AGRESIVNA_VOZNJA_STANDARDNA", 3, 2, 15,
                "Standardni obrazac agresivne voznje: 3+ prekoracenja + 2+ kocenja u 15 min"),
            rowCep("AGRESIVNA_VOZNJA_STROGA", 2, 1, 10,
                "Strogi obrazac agresivne voznje: 2+ prekoracenja + 1 kocenje u 10 min")
        );
        return kompajlirajSablon("/com/ftn/sbnz/kjar/rules/templates/template-cep-alarmi.drt", podaci);
    }

    /**
     * Vraca generisana DRL pravila za svih 5 sablona — koristi se za prikaz na odbrani.
     */
    public Map<String, String> sviGenerisaniSabloni() {
        Map<String, String> rezultati = new LinkedHashMap<>();
        rezultati.put("1_istek_dokumenta", generisiPravilaIstekaDozvole());
        rezultati.put("2_vozaci_pocetnici", generisiPravilaPocetnikaVozaca());
        rezultati.put("3_profesionalni_vozaci", generisiPravilaProfesionalnihVozaca());
        rezultati.put("4_kontekstualni_rizik", generisiPravilaKontekstRizika());
        rezultati.put("5_cep_alarmi", generisiPravilaCepAlarmi());
        return rezultati;
    }

    public List<Map<String, Object>> pregledSablona() {
        List<Map<String, Object>> rezultat = new ArrayList<>();

        rezultat.add(sablon("istek-dokumenta", "Provera isteka dokumenata",
            "Proverava da li je vozacka dozvola istekla ili istice za manje od 30 dana.",
            "ObjectDataCompiler",
            new String[]{"Dokument", "Kod prekrsaja", "Ozbiljnost", "Nivo rizika", "Preporuka"},
            new String[][]{
                {"Vozacka dozvola istekla", "ISTEKLA_VOZACKA_SABLON", "Visok", "2", "ISKLJUCENJE_IZ_SAOBRACAJA"},
                {"Vozacka dozvola istice za 30 dana", "DOZVOLA_ISTICE_USKORO", "Nizak", "1", "UPOZORENJE"}
            }));

        rezultat.add(sablon("pocetnici", "Pravila za vozace pocetnike",
            "Nulta tolerancija na alkohol — svaki detektovani alkohol je prekrsaj.",
            "ObjectDataCompiler",
            new String[]{"Prag alkohola (‰)", "Kod prekrsaja", "Ozbiljnost", "Nivo rizika", "Preporuka"},
            new String[][]{
                {"> 0.0", "POCETNIK_ALKOHOL_SABLON_01", "Visok", "3", "ZADRZAVANJE_VOZACA"},
                {"> 0.5", "POCETNIK_ALKOHOL_SABLON_02", "Kritican", "4", "ISKLJUCENJE_IZ_SAOBRACAJA"}
            }));

        rezultat.add(sablon("profesionalni", "Pravila za profesionalne vozace",
            "Vozaci kategorije C, D, E — nulta tolerancija na alkohol.",
            "ObjectDataCompiler",
            new String[]{"Prag alkohola (‰)", "Kod prekrsaja", "Ozbiljnost", "Nivo rizika", "Preporuka"},
            new String[][]{
                {"> 0.0", "PROFESIONALNI_ALKOHOL_SABLON_01", "Visok", "3", "ISKLJUCENJE_IZ_SAOBRACAJA"},
                {"> 0.5", "PROFESIONALNI_ALKOHOL_SABLON_02", "Kritican", "4", "ZADRZAVANJE_VOZACA"}
            }));

        try (InputStream csvIs = getClass().getResourceAsStream("/procena-rizika-podaci.csv")) {
            String csv = new String(csvIs.readAllBytes(), StandardCharsets.UTF_8);
            String[] linije = csv.trim().split("\n");
            String[][] csvRedovi = new String[linije.length][];
            for (int i = 0; i < linije.length; i++) {
                String[] cols = linije[i].trim().split(",");
                csvRedovi[i] = new String[]{cols[0], "+" + cols[1], cols[2], cols.length > 3 ? cols[3] : ""};
            }
            rezultat.add(sablon("kontekstualni-rizik", "Kontekstualna procena nivoa rizika",
                "Uvecava nivo rizika na osnovu konteksta kontrole. Podaci se ucitavaju iz CSV fajla.",
                "DataProvider + DataProviderCompiler (CSV fajl)",
                new String[]{"Kontekst", "Uvecanje rizika", "Preporuka", "Opis"},
                csvRedovi));
        } catch (Exception e) {
            rezultat.add(sablon("kontekstualni-rizik", "Kontekstualna procena nivoa rizika",
                "Greska pri ucitavanju CSV fajla.", "CSV", new String[]{}, new String[][]{}));
        }

        rezultat.add(sablon("cep-alarmi", "Pravila za CEP alarme",
            "Detektuje obrasce agresivne voznje u kliznom vremenskom prozoru.",
            "ObjectDataCompiler",
            new String[]{"Tip alarma", "Min. prekoračenja", "Min. kočenja", "Vremenski prozor"},
            new String[][]{
                {"AGRESIVNA_VOZNJA_STANDARDNA", "3", "2", "15 min"},
                {"AGRESIVNA_VOZNJA_STROGA", "2", "1", "10 min"}
            }));

        return rezultat;
    }

    private Map<String, Object> sablon(String id, String naziv, String opis, String izvorPodataka,
                                        String[] kolone, String[][] redovi) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("naziv", naziv);
        m.put("opis", opis);
        m.put("izvorPodataka", izvorPodataka);
        m.put("kolone", Arrays.asList(kolone));
        m.put("redovi", Arrays.stream(redovi).map(Arrays::asList).toList());
        return m;
    }

    // -----------------------------------------------------------------------
    // Pomocne metode za kreiranje redova podataka
    // -----------------------------------------------------------------------

    private Map<String, Object> row(String documentField, String expiryCondition,
                                    String offenseCode, String offenseSeverity,
                                    String offenseDescription, int riskLevel, String recommendation) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("documentField", documentField);
        m.put("expiryCondition", expiryCondition);
        m.put("offenseCode", offenseCode);
        m.put("offenseSeverity", offenseSeverity);
        m.put("offenseDescription", offenseDescription);
        m.put("riskLevel", riskLevel);
        m.put("recommendation", recommendation);
        return m;
    }


    private Map<String, Object> rowKategorija(double alcoholThreshold,
                                               String offenseCode, String offenseSeverity,
                                               String offenseDescription, int riskLevel, String recommendation) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("alcoholThreshold", alcoholThreshold);
        m.put("offenseCode", offenseCode);
        m.put("offenseSeverity", offenseSeverity);
        m.put("offenseDescription", offenseDescription);
        m.put("riskLevel", riskLevel);
        m.put("recommendation", recommendation);
        return m;
    }


    private Map<String, Object> rowCep(String alarmType, int minSpeedingCount,
                                        int minBrakingCount, int timeWindowMinutes,
                                        String alarmDescription) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("alarmType", alarmType);
        m.put("minSpeedingCount", minSpeedingCount);
        m.put("minBrakingCount", minBrakingCount);
        m.put("timeWindowMinutes", timeWindowMinutes);
        m.put("alarmDescription", alarmDescription);
        return m;
    }

    private String kompajlirajSablon(String putanja, List<Map<String, Object>> podaci) {
        try (InputStream is = getClass().getResourceAsStream(putanja)) {
            if (is == null) {
                return "// GRESKA: sablon nije pronadjen na putanji: " + putanja;
            }
            ObjectDataCompiler compiler = new ObjectDataCompiler();
            return compiler.compile(podaci, is);
        } catch (Exception e) {
            return "// GRESKA pri kompajliranju sablona: " + e.getMessage();
        }
    }
}
