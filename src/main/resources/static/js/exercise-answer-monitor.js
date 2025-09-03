/**
 * 演習回答モニタリングスクリプト
 * 指定された質問IDの回答一覧を取得し、受講者をクリックすると内容を表示します。
 *
 * 作成日: 2025-09-02
 */

document.addEventListener('DOMContentLoaded', () => {
    const monitors = document.querySelectorAll('.exercise-answer-monitor');
    monitors.forEach(monitor => {
        const questionId = monitor.dataset.questionId;
        if (!questionId) {
            return;
        }

        fetch(`/api/question-banks/${questionId}/answers`)
            .then(response => response.json())
            .then(data => {
                const list = monitor.querySelector('.exercise-student-list');
                const display = monitor.querySelector('.exercise-answer-display');
                if (!list || !display) {
                    return;
                }
                list.innerHTML = '';
                data.forEach(row => {
                    const li = document.createElement('li');
                    li.className = 'list-group-item list-group-item-action';
                    li.textContent = row.studentName;
                    li.addEventListener('click', () => {
                        display.textContent = row.answerText ?? '';
                    });
                    list.appendChild(li);
                });
            })
            .catch(err => console.error('回答取得エラー', err));
    });
});
