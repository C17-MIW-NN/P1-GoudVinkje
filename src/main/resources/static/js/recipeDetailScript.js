$(document).ready(function () {

    const noSleep = new NoSleep();

    noSleep.enable();
});

document.querySelectorAll('.cross-checkbox').forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        const id = this.dataset.id;
        const type = this.dataset.type;
        const textElement = document.getElementById(`${type}${id}`);

        if (this.checked) {
            textElement.classList.add('checked')
        } else {
            textElement.classList.remove('checked');
        }
    });
});