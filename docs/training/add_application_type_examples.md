# 申請種別追加トレーニング

このドキュメントでは、[docs/add_application_type.md](../add_application_type.md) を題材にした研修用演習を紹介します。既存の `TravelRequest` を参考に、新しい申請種別を実装する練習課題を行いましょう。

## 本書の目的

実際の開発手順を演習として体験し、申請種別追加の流れを理解することを目的としています。各演習の後には必ずテストを実行し、処理が正しく動作するか確認してください。

## 演習1: "OvertimeRequest" を追加する

1. 申請種別マスタへ `OvertimeRequest` を登録し、コードに `OT` を割り当てます。
2. 承認ルートを 2 ステップで設定し、最初に所属長、次に総務部が承認する流れとします。
3. `jp.co.apsa.unryu.domain.entity` 配下に `OvertimeRequestDetails` を作成し、残業理由や時間を保持できるようにします。
4. `OvertimeRequestService` を実装し、申請作成から承認までを扱えるようにします。
5. `ApplicationNumber` の `isValidTypeCode` と `getTypeName` に `OT` を追加します。

### 期待される結果

- 新しい申請画面から残業申請が行える。
- 承認ルートに沿って承認処理が進む。
- 申請番号に `OT` が組み込まれ、一覧画面に表示される。

## 演習2: "PurchaseRequest" を追加する

1. `PR` というコードで申請種別を登録します。
2. 承認ルートは 3 ステップとし、所属長 → 部長 → 経理部の順で設定します。
3. `PurchaseRequestDetails` エンティティを作成し、購入品目、金額、購入理由を保持します。
4. `PurchaseRequestService` を実装し、画面から登録・更新できるようにします。
5. `ApplicationNumber` に `PR` を追記します。

### 期待される結果

- 購入申請が登録でき、3 ステップの承認フローを通過する。
- 金額や購入理由が一覧画面に正しく表示される。

## テスト作成のポイント

演習を終えたら `src/test/java` 配下へ以下のテストを追加します。

- `ApplicationNumber` のバリデーションメソッドが新しいコードを受け付けることを確認するテスト
- 各サービス (`OvertimeRequestService`、`PurchaseRequestService`) の `create` メソッドが正常に動作することを検証する単体テスト
- コントローラーレベルで新規申請が登録できることを確認する統合テスト

テストクラス名やメソッド名は既存のテストを参考に `should...` 形式で記述してください。テスト追加後は `./gradlew test` を実行し、すべて成功することを確認しましょう。
