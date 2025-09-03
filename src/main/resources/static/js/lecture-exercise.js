async function submitExerciseAnswer(questionId, lectureId, answerText) {
    const studentInput = document.getElementById('studentId');
    const studentId = studentInput ? studentInput.value : null;
    const resultEl = document.getElementById(`exercise-result-${questionId}`);
    if (!answerText || answerText.trim() === '') {
        if (resultEl) {
            resultEl.textContent = '回答を入力してください';
            resultEl.className = 'mt-2 text-danger';
        }
        return;
    }
    try {
        const response = await fetch(`/api/question-banks/${questionId}/answer`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ lectureId: lectureId, studentId: studentId, answerText: answerText })
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        if (resultEl) {
            resultEl.textContent = '回答を送信しました';
            resultEl.className = 'mt-2 text-success';
        }
        refreshExerciseAnswerMonitor(questionId);
    } catch (error) {
        console.error('Failed to submit exercise answer', error);
        if (resultEl) {
            resultEl.textContent = '送信に失敗しました';
            resultEl.className = 'mt-2 text-danger';
        }
    }
}
