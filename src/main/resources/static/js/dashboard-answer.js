/**
 * ダッシュボード回答送信用スクリプト
 * fetchで回答APIに送信し、WebSocketでリアルタイム更新を受信します。
 * 作成日: 2025-09-03
 */

import { getCsrfToken } from './csrf.js';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('answer-form');
    const quizIdInput = document.getElementById('quiz-id');
    const questionIdInput = document.getElementById('question-id');
    const answerInput = document.getElementById('answer-text');
    const statusEl = document.getElementById('answer-status');
    const feedEl = document.getElementById('answer-feed');
    let stompClient = null;

    function connect(quizId) {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, () => {
            stompClient.subscribe(`/topic/answers/${quizId}`, (message) => {
                const payload = JSON.parse(message.body);
                renderFeed(payload);
            });
        });
    }

    function renderFeed(answers) {
        feedEl.innerHTML = '';
        Object.keys(answers).forEach(questionId => {
            const li = document.createElement('li');
            li.classList.add('list-group-item');
            li.textContent = `Q${questionId}: ${answers[questionId]}`;
            feedEl.appendChild(li);
        });
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const quizId = quizIdInput.value.trim();
        const questionId = questionIdInput.value.trim();
        const answerText = answerInput.value.trim();

        if (!quizId || !questionId) {
            statusEl.textContent = 'クイズIDと問題IDを入力してください。';
            statusEl.classList.remove('text-success', 'text-danger');
            statusEl.classList.add('text-danger');
            return;
        }

        try {
            const csrfToken = getCsrfToken();
            const headers = { 'Content-Type': 'application/json' };
            if (csrfToken) {
                headers['X-CSRF-TOKEN'] = csrfToken;
            }
            const response = await fetch(`/api/quizzes/${quizId}/answer`, {
                method: 'POST',
                headers: headers,
                body: JSON.stringify({ [questionId]: answerText })
            });

            if (response.ok) {
                statusEl.textContent = '送信が完了しました。';
                statusEl.classList.remove('text-success', 'text-danger');
                statusEl.classList.add('text-success');
                if (!stompClient) {
                    connect(quizId);
                }
            } else {
                statusEl.textContent = '送信に失敗しました。';
                statusEl.classList.remove('text-success', 'text-danger');
                statusEl.classList.add('text-danger');
            }
        } catch (error) {
            statusEl.textContent = '送信中にエラーが発生しました。';
            statusEl.classList.remove('text-success', 'text-danger');
            statusEl.classList.add('text-danger');
        }
    });
});
