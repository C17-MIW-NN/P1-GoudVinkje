$(document).ready(function () {
    let numberOfSteps = $("#steps-section .step-row").length;
    let numberOfIngredients = $("#ingredients-section .ingredient-row").length;

    $("#add-step").click(function () {
        const newStep =
        `<div class="step-row">
            <table>
            <tbody>
            <tr>
                <td>
                    <input type="hidden" name="steps[${numberOfSteps}].stepId" value=""/>
                </td>
                <td>
                    <input type="number" name="steps[${numberOfSteps}].sequenceNr" value="${numberOfSteps + 1}"/>
                </td>
                <td>
                    <input type="text" name="steps[${numberOfSteps}].instruction" placeholder="Bijv. Snipper de ui"/>
                </td>
                <td>
                    <button type="button" class="remove-step">Verwijder stap</button>
                </td>
            </tr>
            </tbody>
            </table>
        </div>`;
        $("#steps-section").append(newStep);
        numberOfSteps++;
    });

    $("#add-ingredient").click(function () {

        const newIngredient =
            `<div class="ingredient-row">
            <table>
                <tr>
                    <td>
                        <input type="hidden"
                               name="recipeHasIngredients[${numberOfIngredients}].ingredient">
                    </td>
                    <td>
                        <input type="hidden"
                               name="recipeHasIngredients[${numberOfIngredients}].recipeHasIngredientID">
                    </td>
                    <td>
                        <input type="hidden"
                               name="recipeHasIngredients[${numberOfIngredients}].recipe">
                    </td>
                    <td>
                        <input type="text"
                               name="recipeHasIngredients[${numberOfIngredients}].quantity">
                    </td>
                    <td>
                        <input type="text"
                               name="recipeHasIngredients[${numberOfIngredients}].ingredient.quantityUnit">
                    </td>
                    <td>
                        <input type="text"
                               name="recipeHasIngredients[${numberOfIngredients}].ingredient.description">
                    </td>
                    <td>
                        <button type="button" class="remove-ingredient">Verwijder ingrediënt</button>
                    </td>
                </tr>
            </table>
            </div>`
        $("#ingredients-section").append(newIngredient);
        numberOfIngredients ++;
    });

    $("#steps-section").on("click",".remove-step", function (){
        $(this).closest(".step-row").remove();

        numberOfSteps = $("#steps-section .step-row").length;

        $("#steps-section .step-row").each(function (index) {
            $(this)
                .find("input")
                .each(function () {
                    const name = $(this).attr("name");
                    if (name) {
                        $(this).attr("name", name.replace(/\d+/, index));
                    }
                });
            $(this).find("input[name*='sequenceNr']").val(index + 1);
        });
    });

    $("#ingredients-section").on("click",".remove-ingredient", function (){

        $(this).closest(".ingredient-row").remove();

        numberOfIngredients = $("#ingredients-section .ingredient-row").length;

        $("#ingredients-section .ingredient-row").each(function (index) {
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
});