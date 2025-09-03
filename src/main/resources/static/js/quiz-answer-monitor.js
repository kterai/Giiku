/**
 * クイズ回答モニタリングスクリプト
 * 指定された質問IDの回答一覧を取得してテーブルに描画します。
 *
 * 作成日: 2025-09-02
 */

function refreshQuizAnswerMonitor(questionId) {
    const monitor = document.querySelector(`.quiz-answer-monitor[data-question-id="${questionId}"]`);
    if (!monitor) {
        return;
    }

    fetch(`/api/quizzes/questions/${questionId}/answers`)
        .then(response => response.json())
        .then(data => {
            const tbody = monitor.querySelector('tbody');
            if (!tbody) {
                return;
            }
            tbody.innerHTML = '';
            data.forEach(row => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${row.studentName}</td>
                    <td>${row.answerText ?? ''}</td>
                    <td>${row.correct ? '○' : '×'}</td>`;
                tbody.appendChild(tr);
            });
        })
        .catch(err => console.error('回答取得エラー', err));
}

document.addEventListener('DOMContentLoaded', () => {
    const monitors = document.querySelectorAll('.quiz-answer-monitor');
    monitors.forEach(monitor => {
        const questionId = monitor.dataset.questionId;
        if (questionId) {
            refreshQuizAnswerMonitor(questionId);
        }
    });
});
