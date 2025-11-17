document.querySelectorAll('.step-checkbox').forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        const stepId = this.dataset.stepId;
        const textElement = document.getElementById('instruction-' + stepId);

        if (this.checked) {
            textElement.classList.add('checked')
        } else {
            textElement.classList.remove('checked');
        }
    });
});

document.querySelectorAll('.ingredient-checkbox').forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        const ingredientId = this.dataset.ingredientId;
        const textElement = document.getElementById('description-' + ingredientId);

        if (this.checked) {
            textElement.classList.add('checked')
        } else {
            textElement.classList.remove('checked');
        }
    });
});
