package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Image;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.ImageService;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.RecipeService;
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
 * handle all requests regarding images
 */

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
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
        Optional<Recipe> optionalRecipe = recipeService.getOptionalRecipe(recipeId);

        if (optionalRecipe.isPresent()) {
            return showRecipeImageForm(datamodel, optionalRecipe.get());
        }
        return "redirect:/recept/overzicht";
    }

    private String showRecipeImageForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);

        return "uploadImage";
    }

    @PostMapping("/afbeelding/opslaan")
    public String addImage(@ModelAttribute("formRecipe") Recipe recipe,
                           BindingResult result, @RequestParam MultipartFile recipeImage) {
        try {
            imageService.saveImage(recipeImage);
            recipe.setImageURL("/image/" + recipeImage.getOriginalFilename());
        } catch (IOException imageError) {
            result.rejectValue("authorImage", "imageNotSaved", "Image not saved");
        }

        if (result.hasErrors()) {
            return "redirect:/";
        }

        return recipeService.saveRecipe(recipe);
    }

}
