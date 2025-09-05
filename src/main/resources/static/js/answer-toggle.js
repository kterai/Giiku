export function showAnswer(answerId) {
    const answerElement = document.getElementById(answerId);
    if (!answerElement) {
        return;
    }

    const isShown = answerElement.classList.toggle('show');
    const button = document.querySelector(`[data-target-id="${answerId}"]`);
    if (button) {
        const icon = button.querySelector('i');
        const label = button.querySelector('.answer-toggle-label');
        if (icon && label) {
            if (isShown) {
                icon.className = 'fas fa-eye-slash';
                label.textContent = '回答を非表示';
            } else {
                icon.className = 'fas fa-eye';
                label.textContent = '回答を表示';
            }
        }
    }
}

export function toggleAnswer(answerId) {
    const answerElement = document.getElementById(answerId);
    if (!answerElement) {
        return;
    }

    const isShown = answerElement.classList.toggle('show');
    const button = document.querySelector(`[data-target-id="${answerId}"]`);
    if (button) {
        const icon = button.querySelector('i');
        const label = button.querySelector('.answer-toggle-label');
        if (icon && label) {
            if (isShown) {
                icon.className = 'fas fa-eye-slash';
                label.textContent = '回答例を非表示';
            } else {
                icon.className = 'fas fa-eye';
                label.textContent = '回答例を表示';
            }
        }
    }
}
