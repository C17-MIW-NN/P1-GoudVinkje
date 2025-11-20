package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Image;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Annelies Hofman
 * handle all requests regadring images
 */

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeRepository recipeRepository;

    public ImageController(ImageService imageService, RecipeRepository recipeRepository) {
        this.imageService = imageService;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/image/{imageName}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        Image image = imageService.getImage(imageName);

        if (image.getData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().contentType(image.getContentType()).body(image.getData());
    }

    @GetMapping("/recept/afbeelding/{recipeID}")
    public String showUploadImageForm(@PathVariable("recipeID") Long recipeId, Model datamodel) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isPresent()) {
            return showRecipeImageForm(datamodel, optionalRecipe.get());
        }
        return "redirect:/recept/overzicht";
    }

    private String showRecipeImageForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);

        return "uploadImage";
    }


    @PostMapping("/afbeeldingopslaan")
    public String addImage(@ModelAttribute("formRecipe") Recipe recipe,
                           BindingResult result,
                           @RequestParam MultipartFile recipeImage) {
        try {
            imageService.saveImage(recipeImage);
            recipe.setImageURL("/image/" + recipeImage.getOriginalFilename());
        } catch (IOException imageError) {
            result.rejectValue("authorImage", "imageNotSaved", "Image not saved");
        }

        if (result.hasErrors()) {
            return "redirect:/";
        }

        recipeRepository.save(recipe);
        return "redirect:/recept/" + recipe.getRecipeID();
    }

}
