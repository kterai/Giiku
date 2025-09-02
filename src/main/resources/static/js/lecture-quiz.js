async function submitQuizAnswer(quizId, questionId, answer) {
    const studentInput = document.getElementById('studentId');
    const studentId = studentInput ? studentInput.value : null;
    try {
        const response = await fetch(`/api/quizzes/questions/${questionId}/answer`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ quizId: quizId, studentId: studentId, answer: answer })
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const result = await response.json();
        const resultEl = document.getElementById(`quiz-result-${questionId}`);
        if (resultEl) {
            resultEl.textContent = result.correct ? `正解！ ${result.explanation ?? ''}` : `不正解。${result.explanation ?? ''}`;
            resultEl.className = result.correct ? 'text-success' : 'text-danger';
        }
    } catch (error) {
        console.error('Failed to submit answer', error);
    }
}
