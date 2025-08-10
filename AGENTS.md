# Repository Guidelines

- Keep `README.md` up to date with the progress described in `作業進捗と計画.html`.
- When adding a new day page, copy `pages/day/day_template.html` and rename it to `dayX.html` (X is the day number).
- This project has no automated tests. After editing HTML or CSS, manually open `index.html` in a browser to verify that navigation and layout work correctly.
- Document major updates or milestones in `作業進捗と計画.html` as well.
- Keep the monthly plan in this README consistent with any curriculum updates.
- CSS や JS ライブラリは WebJar を利用し、Thymeleaf の `th:` 属性で読み込む。

- すべての Web ページはスマートフォンでも閲覧しやすいレスポンシブデザインとする。

- Codex は質問への回答を原則日本語で行う。
- Java 実行環境は Amazon Corretto を利用する。
- 推奨 IDE は Pleiades (Eclipse) とする。

- すべてのJavaクラスには以下のJavadocタグを含めること:
  - @author 株式会社アプサ
  - @version 1.0
  - @since 2025
