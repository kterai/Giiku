/**
 * 演習回答モニタリングスクリプト
 * 指定された質問IDの回答一覧を取得し、受講者をクリックすると内容を表示します。
 *
 * 作成日: 2025-09-02
 */

export function refreshExerciseAnswerMonitor(questionId) {
    const monitor = document.querySelector(`.exercise-answer-monitor[data-question-id="${questionId}"]`);
    if (!monitor) {
        return;
    }

    fetch(`/api/question-banks/${questionId}/answers`)
        .then(response => {
            if (!response.ok) {
                throw new Error('HTTP error');
            }
            return response.json();
        })
        .then(data => {
            const list = monitor.querySelector('.exercise-student-list');
            const display = monitor.querySelector('.exercise-answer-display');
            if (!list || !display) {
                return;
            }
            list.innerHTML = '';
            display.textContent = '受講者を選択してください';
            data.forEach(row => {
                const li = document.createElement('li');
                li.classList.add('list-group-item', 'list-group-item-action');
                li.textContent = row.studentName;
                li.addEventListener('click', () => {
                    display.textContent = row.answerText && row.answerText.trim() !== ''
                        ? row.answerText
                        : '回答なし';
                });
                list.appendChild(li);
            });
        })
        .catch(err => {
            console.error('回答取得エラー', err);
            const display = monitor.querySelector('.exercise-answer-display');
            if (display) {
                display.textContent = '取得に失敗しました';
            }
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const monitors = document.querySelectorAll('.exercise-answer-monitor');
    monitors.forEach(monitor => {
        const questionId = monitor.dataset.questionId;
        if (questionId) {
            refreshExerciseAnswerMonitor(questionId);
        }
    });
});
