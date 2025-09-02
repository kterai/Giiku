package jp.co.apsa.giiku.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * 回答ストリームコントローラー。
 *
 * 講師向けに回答ストリームの購読エンドポイントを提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
public class AnswerStreamController {

    /**
     * クイズ回答の購読エンドポイント。
     *
     * @param quizId クイズID
     */
    @SubscribeMapping("/answers/{quizId}")
    public void subscribeQuizAnswers(@DestinationVariable Long quizId) {
        // 初期データ送信は行わず、購読のみを許可。
    }

    /**
     * 演習回答の購読エンドポイント。
     *
     * @param questionId 問題ID
     */
    @SubscribeMapping("/exercise-answers/{questionId}")
    public void subscribeExerciseAnswers(@DestinationVariable Long questionId) {
        // 初期データ送信は行わず、購読のみを許可。
    }
}
