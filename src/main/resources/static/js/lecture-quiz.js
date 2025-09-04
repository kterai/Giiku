import { getCsrfToken } from './csrf.js';
import { refreshQuizAnswerMonitor } from './quiz-answer-monitor.js';

async function submitQuizAnswer(quizId, questionId) {
    const studentInput = document.getElementById('studentId');
    const studentId = studentInput ? studentInput.value : null;

    const selectedOptions = document.querySelectorAll(`input[name="quiz-${questionId}"]:checked`);
    const resultEl = document.getElementById(`quiz-result-${questionId}`);

    if (!studentId) {
        if (resultEl) {
            resultEl.textContent = '受講者IDを取得できませんでした。';
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add('text-danger');
        }
        return;
    }

    if (!selectedOptions.length) {
        if (resultEl) {
            resultEl.textContent = '回答を選択してください。';
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add('text-danger');
        }
        return;
    }

    // 選択肢を配列化し、複数選択（チェックボックス）でも回答を送信可能にする
    const answer = Array.from(selectedOptions).map(option => option.value).join(',');

    try {
        const csrfToken = getCsrfToken();
        const headers = { 'Content-Type': 'application/json' };
        if (csrfToken) {
            headers['X-CSRF-TOKEN'] = csrfToken;
        }
        const response = await fetch(`/api/quizzes/questions/${questionId}/answer`, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify({ quizId, studentId, answer })
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const result = await response.json();
        if (resultEl) {
            resultEl.textContent = result.correct ? `正解！ ${result.explanation ?? ''}` : `不正解。${result.explanation ?? ''}`;
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add(result.correct ? 'text-success' : 'text-danger');
        }
        refreshQuizAnswerMonitor(questionId);
    } catch (error) {
        if (resultEl) {
            resultEl.textContent = '送信に失敗しました';
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add('text-danger');
        }
        console.error('Failed to submit answer', error);
    }
}

document.querySelectorAll('.quiz-submit-btn').forEach(button => {
    button.addEventListener('click', () => {
        const quizId = button.dataset.quizId;
        const questionId = button.dataset.questionId;
        submitQuizAnswer(quizId, questionId);
    });
});

document.querySelectorAll('.show-answer-btn').forEach(button => {
    button.addEventListener('click', () => {
        const targetId = button.dataset.targetId;
        if (typeof window.showAnswer === 'function') {
            window.showAnswer(targetId);
        }
    });
});

document.querySelectorAll('.toggle-answer-btn').forEach(button => {
    button.addEventListener('click', () => {
        const targetId = button.dataset.targetId;
        if (typeof window.toggleAnswer === 'function') {
            window.toggleAnswer(targetId);
        }
    });
});
