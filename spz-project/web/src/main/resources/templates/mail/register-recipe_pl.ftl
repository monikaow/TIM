<#-- @ftlvariable name="recipeHistory" type="pl.edu.wat.spz.domain.entity.RecipeHistory" -->
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

        .pt-td {
            padding-top: 15px;
        }
    </style>
</head>
<body>
<#assign patient=recipeHistory.medicalVisit.patient>
<#assign doctor=recipeHistory.medicalVisit.doctorSpecialization.doctor>
<#assign specializationName=recipeHistory.medicalVisit.doctorSpecialization.specialization.name>
<#assign date=recipeHistory.medicalVisit.doctorTimetable.date>
<p>
    Witaj <strong>${patient.firstName + ' ' + patient.lastName}</strong>
</p>
<p>
    Poniżej znajdują się informacje dotyczące Twojej recepty:
</p>
<table class="v-table">
    <tbody>
    <tr>
        <td class="p-td" colspan="2"><strong>RECEPTA</strong></td>
    </tr>
    <tr>
        <td class="p-td"><strong>Lek:</strong></td>
        <td>${recipeHistory.medicineName}</td>
    </tr>
    <tr>
        <td class="p-td"><strong>Komentarz:</strong></td>
        <td>${recipeHistory.comment}</td>
    </tr>
    <tr>
        <td class="p-td"><strong>Data odbioru:</strong></td>
        <td>${recipeHistory.receiptDate?string["dd.MM.yyyy"] + ' o ' + recipeHistory.receiptDate?string["HH:mm"]}</td>
    </tr>

    <tr>
        <td class="p-td pt-td" colspan="2"><strong>WIZYTA</strong></td>
    </tr>
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
