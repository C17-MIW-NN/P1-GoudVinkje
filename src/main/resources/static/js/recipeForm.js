$(document).ready(function () {
    let stepsSectionRows = $("#steps-section .step-row")
    let numberOfSteps = stepsSectionRows.length;
    let ingredientSectionRows = $("#ingredients-section .ingredient-row")
    let numberOfIngredients = ingredientSectionRows.length;

    $('#steps-section').sortable({
        items: '.step-row',
        onEnd: function () {
            renumberStepRows();
        }
    });

    $("#add-step").click(function () {
        const newStep =
        `<div class="step-row">
   
            <table>
            <tbody>
            <tr>
                <td><i class="bi-grip-vertical"></i>
                    <input type="hidden" name="steps[${numberOfSteps}].stepId" value=""/>
                </td>
                <td>
                    <input type="number" class="form-control"
                           name="steps[${numberOfSteps}].sequenceNr" value="${numberOfSteps + 1}" readonly/>
                </td>
                <td class="instruction-line">
                    <input type="text" class="form-control" 
                    name="steps[${numberOfSteps}].instruction"/>
                </td>
                <td>
                    <i class="ms-2 bi bi-trash-fill remove-step"></i></button>
                </td>
            </tr>
            </tbody>
            </table>
        </div>`;
        $("#steps-section").append(newStep);
        numberOfSteps++;
    });

    $("#add-ingredient").click(function () {

        //clone quantityUnitDropdown from html
        let quantityUnitSelect = $("#quantityUnitDropdown").clone();
        quantityUnitSelect.removeAttr("id");
        quantityUnitSelect.show();
        quantityUnitSelect.attr("name",
            `recipeHasIngredients[${numberOfIngredients}].ingredient.quantityUnit`)

        const newIngredient =
            `<div class="ingredient-row">
            <input type="hidden" name="recipeHasIngredients[${numberOfIngredients}].ingredient">
            <input type="hidden" name="recipeHasIngredients[${numberOfIngredients}].recipeHasIngredientID">
            <input type="hidden" name="recipeHasIngredients[${numberOfIngredients}].recipe">
            <table>
                <tr>
                    <td>
                        <input type="text" class="form-control"
                               name="recipeHasIngredients[${numberOfIngredients}].quantity">
                    </td>
                    <td>
                        <div class="quantityUnitCell"></div>
                    </td>
                    <td class="description-line">
                        <input type="text" class="form-control"
                               name="recipeHasIngredients[${numberOfIngredients}].ingredient.description">
                    </td>
                    <td>
                        <td><i class="ms-2 bi bi-trash-fill remove-ingredient"></i></td>
                    </td>
                </tr>
            </table>
            </div>`;

        $row = $(newIngredient);
        $row.find(".quantityUnitCell").append(quantityUnitSelect);
        $("#ingredients-section").append($row);

        numberOfIngredients ++;
    });

    $("#steps-section").on("click",".remove-step", function (){
        $(this).closest(".step-row").remove();

        renumberStepRows();

        numberOfSteps = stepsSectionRows.length;
    });

    $("#ingredients-section").on("click",".remove-ingredient", function (){

        $(this).closest(".ingredient-row").remove();

        numberOfIngredients = ingredientSectionRows.length;

        ingredientSectionRows.each(function (index) {
            $(this)
                .find("input")
                .each(function () {
                    const name = $(this).attr("name");
                    if (name) {
                        $(this).attr("name", name.replace(/\d+/, index));
                    }
                });
        });
    });

    function renumberStepRows() {

        stepsSectionRows.each(function (index) {

            $(this).find("input[name*='sequenceNr']").val(index + 1);

            $(this)
                .find("input, textarea, select")
                .each(function () {
                    const name = $(this).attr("name");
                    if (name) {
                        $(this).attr("name", name.replace(/\d+/, index));
                    }
                    const id = $(this).attr("id");
                    if (id) {
                        $(this).attr("id", id.replace(/\d+/, index));
                    }

                });

            $(this).find("input[name*='sequenceNr']").val(index + 1);
        });

    }
});