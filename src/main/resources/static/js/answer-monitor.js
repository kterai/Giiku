/**
 * 回答モニタ用スクリプト
 * WebSocket を利用して /topic/answers/* を購読
 * 作成日: 2025-09-02
 */

document.addEventListener('DOMContentLoaded', () => {
    const connectBtn = document.getElementById('connect-btn');
    const quizIdInput = document.getElementById('quiz-id');
    const answersList = document.getElementById('answers');
    let stompClient = null;

    connectBtn.addEventListener('click', () => {
        const quizId = quizIdInput.value.trim();
        if (!quizId) {
            alert('クイズIDを入力してください');
            return;
        }

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, () => {
            stompClient.subscribe(`/topic/answers/${quizId}`, (message) => {
                const payload = JSON.parse(message.body);
                renderAnswers(payload);
            });
        });
    });

    function renderAnswers(answers) {
        answersList.innerHTML = '';
        Object.keys(answers).forEach(questionId => {
            const li = document.createElement('li');
            li.classList.add('list-group-item');
            li.textContent = `Q${questionId}: ${answers[questionId]}`;
            answersList.appendChild(li);
        });
    }
});
