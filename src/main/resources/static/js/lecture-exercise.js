import { getCsrfToken } from './csrf.js';

async function submitExerciseAnswer(questionId, lectureId, answerText) {
    const studentInput = document.getElementById('studentId');
    const studentId = studentInput ? studentInput.value : null;
    const resultEl = document.getElementById(`exercise-result-${questionId}`);
    if (!studentId) {
        if (resultEl) {
            resultEl.textContent = '受講者IDを取得できませんでした。';
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add('text-danger');
        }
        return;
    }
    if (!answerText || answerText.trim() === '') {
        if (resultEl) {
            resultEl.textContent = '回答を入力してください';
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add('text-danger');
        }
        return;
    }
    try {
        const csrfToken = getCsrfToken();
        const headers = { 'Content-Type': 'application/json' };
        if (csrfToken) {
            headers['X-CSRF-TOKEN'] = csrfToken;
        }
        const response = await fetch(`/api/question-banks/${questionId}/answer`, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify({ lectureId: lectureId, studentId: studentId, answerText: answerText })
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        if (resultEl) {
            resultEl.textContent = '回答を送信しました';
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add('text-success');
        }
        refreshExerciseAnswerMonitor(questionId);
    } catch (error) {
        console.error('Failed to submit exercise answer', error);
        if (resultEl) {
            resultEl.textContent = '送信に失敗しました';
            resultEl.classList.remove('text-success', 'text-danger');
            resultEl.classList.add('text-danger');
        }
    }
}
