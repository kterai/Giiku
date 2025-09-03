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
        .then(response => {
            if (!response.ok) {
                throw new Error('HTTP error');
            }
            return response.json();
        })
        .then(data => {
            const tbody = monitor.querySelector('tbody');
            if (!tbody) {
                return;
            }
            tbody.innerHTML = '';
            data.forEach(row => {
                const tr = document.createElement('tr');

                const studentTd = document.createElement('td');
                studentTd.textContent = row.studentName;
                tr.appendChild(studentTd);

                const answerTd = document.createElement('td');
                answerTd.textContent = row.answerText ?? '';
                tr.appendChild(answerTd);

                const correctTd = document.createElement('td');
                correctTd.textContent = row.correct ? '○' : '×';
                tr.appendChild(correctTd);

                tbody.appendChild(tr);
            });
        })
        .catch(err => {
            console.error('回答取得エラー', err);
            const tbody = monitor.querySelector('tbody');
            if (tbody) {
                tbody.innerHTML = '<tr><td colspan="3">取得に失敗しました</td></tr>';
            }
        });
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
