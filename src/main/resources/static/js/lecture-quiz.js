async function submitQuizAnswer(questionId) {
    const studentInput = document.getElementById('studentId');
    const studentId = studentInput ? studentInput.value : null;

    const selectedOptions = document.querySelectorAll(`input[name="quiz-${questionId}"]:checked`);
    const resultEl = document.getElementById(`quiz-result-${questionId}`);

    if (!selectedOptions.length) {
        if (resultEl) {
            resultEl.textContent = '回答を選択してください。';
            resultEl.className = 'text-danger';
        }
        return;
    }

    const answer = Array.from(selectedOptions).map(opt => opt.value).join(',');

    try {
        const response = await fetch(`/api/quizzes/questions/${questionId}/answer`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ studentId: studentId, answer: answer })
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const result = await response.json();
        if (resultEl) {
            resultEl.textContent = result.correct ? `正解！ ${result.explanation ?? ''}` : `不正解。${result.explanation ?? ''}`;
            resultEl.className = result.correct ? 'text-success' : 'text-danger';
        }
    } catch (error) {
        console.error('Failed to submit answer', error);
    }
}
