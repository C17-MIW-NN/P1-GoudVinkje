$(document).ready(function() {
    let quantityUnit = $("#quantityUnit")
    quantityUnit.on("input", function() {
        let value = $(this).val();
        $("#unitDisplay").text(value);
    });

    $("#unitDisplay").text(quantityUnit.val());
});