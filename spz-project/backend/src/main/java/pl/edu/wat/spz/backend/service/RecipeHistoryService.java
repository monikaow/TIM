package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import pl.edu.wat.spz.backend.dto.RecipeHistoryDto;
import pl.edu.wat.spz.backend.events.RegisterRecipeEvent;
import pl.edu.wat.spz.backend.repository.RecipeHistoryRepository;
import pl.edu.wat.spz.domain.entity.MedicalVisit;
import pl.edu.wat.spz.domain.entity.RecipeHistory;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class RecipeHistoryService {

    private RecipeHistoryRepository repository;

    private MedicalVisitService medicalVisitService;

    private ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    public RecipeHistoryService(RecipeHistoryRepository repository, MedicalVisitService medicalVisitService, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.medicalVisitService = medicalVisitService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Page<RecipeHistory> findAllByPatientId(Long patientId, Pageable pageable) {
        return repository.findAllByPatientId(patientId, pageable);
    }

    public RecipeHistory registerRecipeHistory(RecipeHistoryDto recipeHistoryDto) {

        RecipeHistory recipeHistory = new RecipeHistory();
        Optional<MedicalVisit> medicalVisit = medicalVisitService.findById(recipeHistoryDto.getMedicalVisitId());

        if (medicalVisit.isPresent()) {
            recipeHistory.setMedicalVisit(medicalVisit.get());
            recipeHistory.setMedicineName(recipeHistoryDto.getMedicineName());
            recipeHistory.setComment(recipeHistoryDto.getComment());
            recipeHistory.setReceiptDate(recipeHistoryDto.getReceiptDate());

            recipeHistory = repository.save(recipeHistory);

            applicationEventPublisher.publishEvent(new RegisterRecipeEvent(this, recipeHistory));
        }

        return recipeHistory;
    }
}
