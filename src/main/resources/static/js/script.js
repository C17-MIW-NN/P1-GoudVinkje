$(document).ready(function () {
    let numberOfSteps = $("#steps-section .step-row").length;

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

    })
});