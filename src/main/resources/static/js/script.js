function submitForm(element) {
    element.querySelector('form').submit();
}

function updateColumns() {
    var altstuCheckbox = document.getElementById('altstuCheckbox');
    var iprsmartCheckbox = document.getElementById('iprsmartCheckbox');

    var altstuColumn = document.querySelector('.altstu-column');
    var iprsmartColumn = document.querySelector('.iprsmart-column');

    if (altstuCheckbox.checked && iprsmartCheckbox.checked) {
        altstuColumn.style.display = 'block';
        iprsmartColumn.style.display = 'block';
        altstuColumn.classList.remove('text-center');
        iprsmartColumn.classList.remove('text-center');
    } else if (altstuCheckbox.checked) {
        altstuColumn.style.display = 'block';
        iprsmartColumn.style.display = 'none';
        altstuColumn.classList.add('text-center');
    } else if (iprsmartCheckbox.checked) {
        altstuColumn.style.display = 'none';
        iprsmartColumn.style.display = 'block';
        iprsmartColumn.classList.add('text-center');
    } else {
        altstuColumn.style.display = 'none';
        iprsmartColumn.style.display = 'none';
    }
}

document.getElementById('altstuCheckbox').addEventListener('change', updateColumns);
document.getElementById('iprsmartCheckbox').addEventListener('change', updateColumns);

updateColumns();