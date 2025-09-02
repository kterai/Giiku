/**
 * クイズ回答モニタリングスクリプト
 * 指定された質問IDの回答一覧を取得してテーブルに描画します。
 *
 * 作成日: 2025-09-02
 */

document.addEventListener('DOMContentLoaded', () => {
    const monitor = document.getElementById('quiz-answer-monitor');
    if (!monitor) {
        return;
    }
    const questionId = monitor.dataset.questionId;
    if (!questionId) {
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
});
