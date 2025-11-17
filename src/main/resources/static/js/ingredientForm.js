$(document).ready(function() {
    let quantityUnit = $("#quantityUnit")
    quantityUnit.on("input", function() {
        let value = $(this).val();
        $("#unitDisplay").text(value);
    });

    if (quantityUnit.val() == "") {
        $("#unitDisplay").text("...");
    } else {
        $("#unitDisplay").text(quantityUnit.val());
    }
});