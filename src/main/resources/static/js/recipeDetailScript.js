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
