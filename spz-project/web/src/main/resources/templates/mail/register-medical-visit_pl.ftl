<#-- @ftlvariable name="medicalVisit" type="pl.edu.wat.spz.domain.entity.MedicalVisit" -->
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .p-td {
            padding-right: 20px;
        }

        .v-table td {
            padding-top: 1px;
            padding-bottom: 1px;
        }
    </style>
</head>
<body>
<#assign patient=medicalVisit.patient>
<#assign doctor=medicalVisit.doctorSpecialization.doctor>
<#assign specializationName=medicalVisit.doctorSpecialization.specialization.name>
<#assign date=medicalVisit.doctorTimetable.date>
<p>
    Witaj <strong>${patient.firstName + ' ' + patient.lastName}</strong>
</p>
<p>
    Poniżej znajdują się szczegółowe dane Twojej wizyty lekarskiej:
</p>
<table class="v-table">
    <tbody>
    <tr>
        <td class="p-td"><strong>Doktor:</strong></td>
        <td>${doctor.title + ' ' + doctor.firstName + ' ' + doctor.lastName}</td>
    </tr>
    <tr>
        <td class="p-td"><strong>Specjalizacja:</strong></td>
        <td>${specializationName}</td>
    </tr>
    <tr>
        <td class="p-td"><strong>Data:</strong></td>
        <td>${date?string["dd.MM.yyyy"] + ' o ' + date?string["HH:mm"]}</td>
    </tr>
    </tbody>
</table>
</body>
</html>
