$(document).ready(function () {
    let numberOfSteps = $("#steps-section .step-row").length;
    let numberOfIngredients = $("#ingredients-section .ingredients-row").length;

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
        $("#test").text("nieuwe tekst");
        const newIngredient =
            `<div class="ingredient-row" th:each="ingredient, order : *{recipeHasIngredients}">
            <table>
                <tr>
                    <td>
                        <input type="hidden"
                               th:field="*{recipeHasIngredients[${numberOfIngredients}].ingredient}">
                    </td>
                    <td>
                        <input type="hidden"
                               th:field="*{recipeHasIngredients[${numberOfIngredients}].recipeHasIngredientID}">
                    </td>
                    <td>
                        <input type="hidden"
                               th:field="*{recipeHasIngredients[${numberOfIngredients}].recipe}">
                    </td>
                    <td>
                        <input type="text"
                               th:field="*{recipeHasIngredients[${numberOfIngredients}].quantity}">
                    </td>
                    <td>
                        <input type="text"
                               th:field="*{recipeHasIngredients[${numberOfIngredients}].ingredient.quantityUnit}">
                    </td>
                    <td>
                        <input type="text"
                               th:field="*{recipeHasIngredients[${numberOfIngredients}].ingredient.description}">
                    </td>
                    <td>
                        <button type="button" class="remove-ingredient">Verwijder ingrediënt</button>
                    </td>
                </tr>
            </table>
            </div>`
        $("#ingredients-section").append(newIngredient);
    });

    $("#steps-section").on("click",".remove-step", function (){
        $(this).closest(".step-row").remove();

        numberOfSteps = $("#steps-section .step-row").length;

        $("#steps-section .step-row").each(function (index) {
            $(this)
                .find("input, button")
                .each(function () {
                    const name = $(this).attr("name");
                    if (name) {
                        $(this).attr("name", name.replace(/(steps\[)\d+(\])/, `$1${index}$2`));
                    }
                });
            $(this).find("input[name*='sequenceNr']").val(index + 1);
        });
    });
});