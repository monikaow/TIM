package pl.edu.wat.spz.backend.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.spz.domain.entity.RecipeHistory;

import javax.servlet.http.HttpServletRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterRecipeEvent extends BaseEmailApplicationEvent<RecipeHistory> {

    private RecipeHistory recipeHistory;

    public RegisterRecipeEvent(Object source, RecipeHistory recipeHistory) {
        super(source);
        this.recipeHistory = recipeHistory;
    }
}