-- V004__Insert_Java_Lecture_Data.sql
-- JavaSilver準拠の講義1-3（Java基礎）の詳細データ投入
-- PostgreSQL17対応、主キー「id」統一、comment形式「名称（説明）」統一

-- 講義1: Java言語基礎（8チャプター）の投入
INSERT INTO lectures (id, title, description, duration_minutes, difficulty_level, is_active, created_at, updated_at) VALUES
(1, 'Java言語基礎', 'Java言語の基本概念、構文、特徴を学び、プログラミングの基礎を身につけます。コメント、命名規則、リテラル、コンパイルプロセスまで包括的に学習します。', 480, 'BEGINNER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義1のチャプター投入
INSERT INTO lecture_chapters (id, lecture_id, chapter_number, title, description, duration_minutes, sort_order, is_active, created_at, updated_at) VALUES
(1, 1, 1, 'Javaとは何か', 'Java言語の歴史、特徴、用途について学びます。プラットフォーム独立性やオブジェクト指向の概念を理解します。', 60, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 2, 'Javaの特徴と利点', 'Write Once, Run Anywhere、ガベージコレクション、豊富なライブラリなどJavaの主要な特徴を詳しく学習します。', 60, 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 3, 'プログラムの基本構造', 'Javaプログラムの基本構造、mainメソッド、クラス定義の書き方を実践的に学びます。', 60, 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 4, 'コメントの書き方', '単行コメント、複数行コメント、JavaDocコメントの書き方と活用方法を学習します。', 45, 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 5, '命名規則', 'Java言語の命名規則（クラス名、メソッド名、変数名、定数名）を詳しく学び、適切なコーディング習慣を身につけます。', 45, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 6, 'リテラル', '整数リテラル、浮動小数点リテラル、文字リテラル、文字列リテラル、論理リテラルについて学習します。', 60, 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1, 7, 'セミコロンと文の終了', 'Javaにおける文の終了規則、セミコロンの使い方、ブロック文について学習します。', 30, 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 8, 'コンパイルと実行の流れ', 'javac、java コマンドの使い方、クラスファイルの生成プロセス、実行環境について詳しく学習します。', 60, 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義1の学習目標投入
INSERT INTO lecture_goals (id, lecture_id, goal_description, sort_order, created_at, updated_at) VALUES
(1, 1, 'Java言語の歴史と特徴を理解し、他のプログラミング言語との違いを説明できる', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'プラットフォーム独立性の概念とJVMの役割を理解する', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'Javaプログラムの基本構造を理解し、簡単なHello Worldプログラムを作成できる', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, '適切な命名規則に従ってクラス、メソッド、変数の名前を付けることができる', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, '各種リテラルの書き方と使い方を理解し、実際のコードで使用できる', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 'コメントの種類と書き方を理解し、保守性の高いコードを書くことができる', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1, 'コンパイルから実行までの流れを理解し、エラーの原因を特定できる', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 講義1-1のコンテンツブロック投入
INSERT INTO lecture_content_blocks (id, chapter_id, block_type, title, content, sort_order, created_at, updated_at) VALUES
(1, 1, 'OVERVIEW', '概要', 'Java言語は1995年にSun Microsystems（現Oracle）によって開発されたオブジェクト指向プログラミング言語です。「Write Once, Run Anywhere」の理念のもと、プラットフォーム独立性を実現し、現在では企業システム開発からモバイルアプリケーション開発まで幅広く使用されています。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'DETAIL', 'Javaの歴史と発展', 'Java言語は以下のような発展を遂げてきました：

■ 1991年: Green Project開始（組み込みシステム向け言語Oak）
■ 1995年: Java 1.0リリース（Webアプレット技術で注目）
■ 1998年: Java 2（J2SE 1.2）リリース（Swing GUI、Collections Framework追加）
■ 2004年: Java 5（J2SE 1.5）リリース（ジェネリクス、アノテーション導入）
■ 2014年: Java 8リリース（ラムダ式、Stream API導入）
■ 2017年以降: 6ヶ月リリースサイクル導入（Java 9～）
■ 2021年: Java 17 LTS（Long Term Support）リリース

現在のJavaは単なるプログラミング言語ではなく、JVM（Java Virtual Machine）、JRE（Java Runtime Environment）、JDK（Java Development Kit）を含む包括的な開発・実行プラットフォームとして位置づけられています。', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'EXAMPLE', 'Javaの活用分野', '■ 企業システム開発
- 基幹業務システム
- Webアプリケーション（Spring Framework）
- マイクロサービスアーキテクチャ

■ Androidアプリケーション開発
- モバイルアプリケーション
- ゲーム開発

■ デスクトップアプリケーション
- IDEツール（Eclipse、IntelliJ IDEA）
- 業務アプリケーション

■ ビッグデータ・分析
- Apache Hadoop
- Apache Spark
- Elasticsearch

■ IoT・組み込みシステム
- 産業用制御システム
- スマートデバイス', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'CODE', 'Hello World プログラム', '最初のJavaプログラムを見てみましょう：

```java
// HelloWorld.java
public class HelloWorld {
    /**
     * プログラムのエントリーポイント
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        // コンソールにメッセージを出力
        System.out.println("Hello, World!");
        System.out.println("Javaプログラミング学習開始！");
    }
}
```

このプログラムは以下の要素で構成されています：
- public class HelloWorld: publicクラスの定義
- main メソッド: プログラムの実行開始点
- System.out.println(): コンソール出力メソッド
- コメント: プログラムの説明', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'NOTE', '重要なポイント', '■ プラットフォーム独立性
Javaプログラムは一度コンパイルすると、JVMがインストールされた任意のOS上で実行可能です。

■ オブジェクト指向
Javaは純粋なオブジェクト指向言語で、すべてのコードはクラス内に記述する必要があります。

■ 強い型付け
変数の型は明示的に宣言する必要があり、コンパイル時に型チェックが行われます。

■ 自動メモリ管理
ガベージコレクションにより、不要になったオブジェクトのメモリが自動的に解放されます。', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義1-2のコンテンツブロック投入
INSERT INTO lecture_content_blocks (id, chapter_id, block_type, title, content, sort_order, created_at, updated_at) VALUES
(6, 2, 'OVERVIEW', '概要', 'Javaの主要な特徴と利点を詳しく学習します。プラットフォーム独立性、オブジェクト指向、メモリ管理、セキュリティ、豊富なライブラリなど、Javaが企業システム開発で選ばれる理由を理解します。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 2, 'DETAIL', 'Write Once, Run Anywhere（WORA）', 'Javaの最大の特徴の一つは、プラットフォーム独立性です：

■ コンパイルプロセス
1. Javaソースコード（.java）をjavacでコンパイル
2. バイトコード（.class）が生成される
3. バイトコードはプラットフォーム独立

■ 実行プロセス
1. JVM（Java Virtual Machine）がバイトコードを読み込み
2. JVMが各OS固有の機械語に変換
3. どのOS上でも同じバイトコードで実行可能

■ メリット
- 開発コストの削減（一度の開発で複数プラットフォーム対応）
- 保守性の向上
- 移植性の確保

■ JVMの役割
- バイトコードの実行
- メモリ管理（ガベージコレクション）
- セキュリティ管理
- 最適化（JIT コンパイル）', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'DETAIL', 'オブジェクト指向プログラミング', 'Javaは純粋なオブジェクト指向言語です：

■ 基本概念
- クラス（Class）: オブジェクトの設計図
- オブジェクト（Object）: クラスから作成されたインスタンス
- メソッド（Method）: オブジェクトの振る舞い
- フィールド（Field）: オブジェクトの状態

■ 4つの原則
1. カプセル化（Encapsulation）
   - データと操作をクラス内に隠蔽
   - privateフィールド、publicメソッドの使い分け

2. 継承（Inheritance）
   - 既存クラスの機能を引き継ぐ
   - コードの再利用性向上

3. ポリモーフィズム（Polymorphism）
   - 同じインターフェースで異なる実装
   - メソッドのオーバーライド、オーバーロード

4. 抽象化（Abstraction）
   - 複雑な実装を隠し、必要な機能のみを公開
   - abstract クラス、interface の活用', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 'CODE', 'オブジェクト指向の実例', '```java
// Student クラスの定義（カプセル化の例）
public class Student {
    // private フィールド（データの隠蔽）
    private String name;
    private int age;
    private String studentId;

    // コンストラクタ
    public Student(String name, int age, String studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }

    // public メソッド（外部インターフェース）
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // バリデーション処理
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public void displayInfo() {
        System.out.println("学生名: " + name);
        System.out.println("年齢: " + age);
        System.out.println("学生ID: " + studentId);
    }
}

// 使用例
public class StudentTest {
    public static void main(String[] args) {
        Student student = new Student("田中太郎", 20, "S001");
        student.displayInfo();

        // private フィールドには直接アクセス不可
        // student.name = "変更"; // コンパイルエラー

        // public メソッドを通してアクセス
        student.setName("田中次郎");
        System.out.println("変更後: " + student.getName());
    }
}
```', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 'NOTE', 'Javaの性能特性', '■ JITコンパイル
- 実行時に頻繁に使用されるコードを機械語にコンパイル
- C/C++に匹敵する実行速度を実現

■ マルチスレッド対応
- 標準でマルチスレッドプログラミングをサポート
- synchronized キーワードによる排他制御
- java.util.concurrent パッケージによる高度な並行処理

■ スケーラビリティ
- 大規模システムでの実績
- エンタープライズ向け機能の充実
- 負荷分散、クラスタリング対応

■ 開発効率
- IDEによる強力な開発支援
- 豊富なフレームワーク（Spring、Hibernate等）
- テストフレームワーク（JUnit、Mockito等）', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義2: 開発環境構築（7チャプター）の投入
INSERT INTO lectures (id, title, description, duration_minutes, difficulty_level, is_active, created_at, updated_at) VALUES
(2, '開発環境構築', 'Java開発に必要な環境構築を実践的に学習。JDKインストール、IDE設定、プロジェクト作成、デバッグ環境の構築まで、実際の開発フローに沿って学びます。', 420, 'BEGINNER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義2のチャプター投入
INSERT INTO lecture_chapters (id, lecture_id, chapter_number, title, description, duration_minutes, sort_order, is_active, created_at, updated_at) VALUES
(9, 2, 1, 'JDKのインストール', 'Oracle JDKとOpenJDKの違い、適切なバージョンの選択、各OS（Windows、macOS、Linux）でのインストール手順を学習します。', 60, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 2, 'IDEの選択と設定', 'IntelliJ IDEA、Eclipse、Visual Studio Codeの特徴比較と、基本設定、プラグイン導入を実践的に学びます。', 75, 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2, 3, 'プロジェクトの作成', 'IDE上でのJavaプロジェクト作成、フォルダ構成の理解、ビルドパスの設定を学習します。', 60, 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 2, 4, 'コンパイル環境の確認', 'コマンドライン、IDE両方でのコンパイル手順、エラーメッセージの読み方、トラブルシューティングを学習します。', 60, 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 2, 5, 'デバッグ環境の設定', 'ブレークポイントの設定、ステップ実行、変数の監視など、効果的なデバッグ技術を習得します。', 60, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 2, 6, '外部ライブラリの管理', 'classpathの概念、JARファイルの追加、Maven/Gradleビルドツールの基本的な使い方を学習します。', 60, 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 2, 7, 'バージョン管理システムの導入', 'Git/GitHubの基本操作、IDE連携、Javaプロジェクトでのバージョン管理のベストプラクティスを学習します。', 45, 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義2の学習目標投入
INSERT INTO lecture_goals (id, lecture_id, goal_description, sort_order, created_at, updated_at) VALUES
(8, 2, 'JDKを正しくインストールし、コマンドラインからjavac、javaコマンドを実行できる', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 'IDEを使用してJavaプロジェクトを作成し、基本的な設定を行うことができる', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 'コンパイルエラーの内容を理解し、適切に修正することができる', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2, 'デバッガーを使用してプログラムの動作を追跡し、バグを特定できる', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 2, '外部ライブラリをプロジェクトに追加し、利用することができる', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 2, 'バージョン管理システムを使用してソースコードを管理できる', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義2-1のコンテンツブロック投入（JDKのインストール）
INSERT INTO lecture_content_blocks (id, chapter_id, block_type, title, content, sort_order, created_at, updated_at) VALUES
(11, 9, 'OVERVIEW', '概要', 'Java開発を始めるために最も重要なのがJDK（Java Development Kit）のインストールです。JDKにはJavaコンパイラ（javac）、Java実行環境（JRE）、開発ツール群が含まれています。Oracle JDKとOpenJDKの違いを理解し、開発目的に応じた適切な選択を行います。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 9, 'DETAIL', 'JDKの種類と選択', '■ Oracle JDK vs OpenJDK
【Oracle JDK】
- Oracle社が提供する商用JDK
- 企業向けサポートが充実
- 長期サポート（LTS）版あり
- 商用利用時はライセンス料が必要（Java 11以降）

【OpenJDK】
- オープンソースの無料JDK
- Oracle JDKとほぼ同じ機能
- 商用利用も無料
- 複数のディストリビューションあり（AdoptOpenJDK、Amazon Corretto、Azul Zulu等）

■ バージョン選択の指針
- 学習目的: OpenJDK 17 LTS（無料、最新LTS）
- 企業開発: Java 8, 11, 17 LTS（長期サポート）
- 最新機能試用: 最新バージョン（6ヶ月ごとにリリース）', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 9, 'CODE', 'Windows環境インストール確認', '```bash
# コマンドプロンプトで実行
java -version
javac -version
echo %JAVA_HOME%
echo %PATH%

# 期待される出力例
openjdk version "17.0.2" 2022-01-18
OpenJDK Runtime Environment Temurin-17.0.2+8 (build 17.0.2+8)
OpenJDK 64-Bit Server VM Temurin-17.0.2+8 (build 17.0.2+8, mixed mode, sharing)
```

■ 環境変数の手動設定（必要に応じて）
1. システムの詳細設定 → 環境変数
2. システム環境変数でJAVA_HOMEを設定
   - 変数名: JAVA_HOME
   - 変数値: C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot
3. PATHに%JAVA_HOME%\binを追加', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 9, 'CODE', 'インストール確認用サンプル', '```java
// InstallationCheck.java
public class InstallationCheck {
    public static void main(String[] args) {
        System.out.println("=== Java Installation Check ===");

        // Javaバージョン情報
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("Java Home: " + System.getProperty("java.home"));

        // OS情報
        System.out.println("OS Name: " + System.getProperty("os.name"));
        System.out.println("OS Version: " + System.getProperty("os.version"));
        System.out.println("OS Architecture: " + System.getProperty("os.arch"));

        System.out.println("=== Installation Successful! ===");
    }
}
```

コンパイルと実行：
```bash
# コンパイル
javac InstallationCheck.java

# 実行
java InstallationCheck
```', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 9, 'NOTE', 'トラブルシューティング', '■ よくある問題と対処法

【問題1】「javacが見つからない」
- 原因: JDKではなくJREがインストールされている
- 対処: JDK（開発用）を再インストール

【問題2】「JAVA_HOMEが設定されていない」
- 原因: 環境変数が正しく設定されていない
- 対処: 環境変数を手動で設定し、ターミナル/コマンドプロンプトを再起動

【問題3】「バージョンが異なる」
- 原因: 複数のJavaバージョンがインストールされている
- 対処: PATHの順序を確認、不要なバージョンをアンインストール

■ 環境確認コマンド
```bash
# インストール済みJavaの場所を確認
which java
which javac

# 環境変数の確認
echo $JAVA_HOME
echo $PATH
```', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義2-2のコンテンツブロック投入（IDEの選択と設定）
INSERT INTO lecture_content_blocks (id, chapter_id, block_type, title, content, sort_order, created_at, updated_at) VALUES
(16, 10, 'OVERVIEW', '概要', 'IDE（統合開発環境）は、Java開発の生産性を大幅に向上させるツールです。主要なJava IDE（IntelliJ IDEA、Eclipse、Visual Studio Code）の特徴を比較し、用途に応じた選択方法を学習します。その後、選択したIDEの基本設定とプラグイン導入を実践的に行います。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 10, 'DETAIL', '主要Java IDEの比較', '■ IntelliJ IDEA
【特徴】
- JetBrains製の高機能IDE
- 優れたコード補完とリファクタリング機能
- Spring、Android開発に最適
- Ultimate版（有料）とCommunity版（無料）

【メリット】
- 直感的なUI/UX
- 強力な静的解析
- Git統合が優秀
- プラグインエコシステムが充実

■ Eclipse
【特徴】
- Eclipse Foundation製のオープンソースIDE
- 企業開発で広く使用
- プラグインによる機能拡張が可能
- 完全無料

【メリット】
- 軽量で高速
- 豊富なプラグイン
- 企業での採用実績
- カスタマイズ性が高い

■ Visual Studio Code
【特徴】
- Microsoft製の軽量エディタ
- Extension Packで機能拡張
- Web開発にも対応

【メリット】
- 起動が高速
- モダンなUI
- 多言語対応
- 豊富な拡張機能', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 10, 'CODE', 'IDE設定確認用プロジェクト', '```java
// IDETestProject/src/main/java/com/example/IDETest.java
package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * IDE設定確認用のテストクラス
 * コード補完、エラー検出、リファクタリング機能をテスト
 */
public class IDETest {
    private List<String> messages;

    public IDETest() {
        this.messages = new ArrayList<>();
        initializeMessages();
    }

    /**
     * メッセージリストを初期化
     */
    private void initializeMessages() {
        messages.add("Hello, World!");
        messages.add("IDE setup successful!");
        messages.add("Ready for Java development!");
    }

    /**
     * すべてのメッセージを表示
     */
    public void displayAllMessages() {
        System.out.println("=== IDE Test Results ===");
        for (int i = 0; i < messages.size(); i++) {
            System.out.println((i + 1) + ": " + messages.get(i));
        }
    }

    public static void main(String[] args) {
        IDETest test = new IDETest();
        test.displayAllMessages();

        System.out.println("\n=== IDE Features Test ===");
        System.out.println("1. Try code completion: type 'test.' and see suggestions");
        System.out.println("2. Try refactoring: right-click on method name");
        System.out.println("3. Try debugging: set breakpoint and run in debug mode");
    }
}
```', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 10, 'NOTE', 'IDE選択の指針とTips', '■ 用途別IDE選択指針

【学習・個人開発】
- IntelliJ IDEA Community: 機能豊富、学習しやすい
- Visual Studio Code: 軽量、モダンなUI

【企業・チーム開発】
- Eclipse: 実績豊富、カスタマイズ性
- IntelliJ IDEA Ultimate: 高機能、Spring開発最適

■ 生産性向上Tips
1. キーボードショートカットの習得
   - Ctrl+Space: コード補完
   - Ctrl+Shift+F: コード整形
   - Ctrl+/: コメントアウト
   - F2: エラー箇所へジャンプ

2. テンプレート活用
   - Live Templates（IntelliJ）
   - Code Templates（Eclipse）
   - Snippets（VS Code）

■ パフォーマンス最適化
- ヒープサイズの調整（-Xmx設定）
- 不要なプラグインの無効化
- インデックス設定の最適化', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義3: パッケージとインポート（6チャプター）の投入
INSERT INTO lectures (id, title, description, duration_minutes, difficulty_level, is_active, created_at, updated_at) VALUES
(3, 'パッケージとインポート', 'Javaのパッケージシステムを理解し、名前空間の管理、クラスの整理、再利用可能なコードの作成方法を学習します。大規模開発に必要な基礎知識を身につけます。', 360, 'BEGINNER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義3のチャプター投入
INSERT INTO lecture_chapters (id, lecture_id, chapter_number, title, description, duration_minutes, sort_order, is_active, created_at, updated_at) VALUES
(16, 3, 1, 'パッケージの概念', 'パッケージとは何か、なぜ必要なのかを理解し、Java標準パッケージの構造を学習します。', 60, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 3, 2, 'パッケージの作成', 'package文の書き方、ディレクトリ構造との関係、パッケージ内でのクラス定義方法を実践的に学びます。', 60, 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 3, 3, 'importステートメント', 'import文の基本的な使い方、単一クラスのインポート、パッケージ全体のインポートを学習します。', 60, 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 3, 4, '静的インポート', 'static importの概念と使用方法、適切な使用場面と注意点を学習します。', 45, 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 3, 5, 'パッケージの階層構造', 'サブパッケージの作成、階層構造の設計方針、企業での命名規則を学習します。', 60, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 3, 6, '名前空間の管理', 'パッケージによる名前の競合回避、FQCN（完全修飾クラス名）の使用、ベストプラクティスを学習します。', 75, 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義3の学習目標投入
INSERT INTO lecture_goals (id, lecture_id, goal_description, sort_order, created_at, updated_at) VALUES
(14, 3, 'パッケージの概念と必要性を理解し、説明することができる', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 3, 'package文を使用してクラスをパッケージに所属させることができる', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 3, 'import文を適切に使用して他のパッケージのクラスを利用できる', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 3, '静的インポートを理解し、適切な場面で使用することができる', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 3, '階層的なパッケージ構造を設計し、実装することができる', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 3, '名前空間の衝突を理解し、適切に回避する方法を知っている', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義3-1のコンテンツブロック投入（パッケージの概念）
INSERT INTO lecture_content_blocks (id, chapter_id, block_type, title, content, sort_order, created_at, updated_at) VALUES
(20, 16, 'OVERVIEW', '概要', 'パッケージはJavaにおける名前空間（namespace）の仕組みです。関連するクラスやインターフェースをグループ化し、名前の衝突を避け、コードの整理と再利用を促進します。大規模なソフトウェア開発には不可欠な概念を基礎から理解します。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 16, 'DETAIL', 'パッケージの必要性', '■ 名前の衝突回避
異なる開発者や組織が同じクラス名を使用する場合の問題：

パッケージなしの問題：
```java
// A社のライブラリ
class Logger {
    public void log(String message) {
        System.out.println("A社Logger: " + message);
    }
}

// B社のライブラリ
class Logger {
    public void log(String message) {
        System.out.println("B社Logger: " + message);
    }
}
// 問題: 同じクラス名で衝突が発生
```

パッケージによる解決：
```java
// com.companyA.util.Logger
package com.companyA.util;
public class Logger {
    public void log(String message) {
        System.out.println("A社Logger: " + message);
    }
}

// com.companyB.util.Logger
package com.companyB.util;
public class Logger {
    public void log(String message) {
        System.out.println("B社Logger: " + message);
    }
}
```', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 16, 'DETAIL', 'Java標準パッケージの構造', '■ 主要な標準パッケージ

【java.lang】
- 基本的なクラス（自動インポート）
- String, Object, System, Math, Thread等
- すべてのJavaプログラムで利用可能

【java.util】
- ユーティリティクラス群
- コレクション（List, Set, Map）
- 日付・時刻（Date, Calendar）
- 乱数（Random）

【java.io】
- 入出力処理
- ファイル操作（File, FileInputStream）
- ストリーム処理（InputStream, OutputStream）

【java.net】
- ネットワーク通信
- URL, Socket, HttpURLConnection

【java.sql】
- データベース接続（JDBC）
- Connection, Statement, ResultSet

【javax.*】
- Java拡張パッケージ
- javax.swing: GUI（Swing）
- javax.xml: XML処理
- javax.servlet: Web開発

■ パッケージの命名規則
- 逆ドメイン名記法（Reverse Domain Name Notation）
- 例: com.oracle.database, org.apache.commons
- 小文字のみ使用
- 単語区切りはドット（.）', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 16, 'EXAMPLE', 'パッケージ使用例', '■ パッケージを使用した実際の例
```java
// com/school/model/Student.java
package com.school.model;

public class Student {
    private String name;
    private int age;
    private String studentId;

    public Student(String name, int age, String studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }

    public void displayInfo() {
        System.out.println("学生名: " + name);
        System.out.println("年齢: " + age);
        System.out.println("学生ID: " + studentId);
    }
}

// com/library/model/Book.java
package com.library.model;

public class Book {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public void displayInfo() {
        System.out.println("書籍名: " + title);
        System.out.println("著者: " + author);
        System.out.println("ISBN: " + isbn);
    }
}

// com/school/service/StudentService.java
package com.school.service;

import com.school.model.Student;

public class StudentService {
    public void processStudent(Student student) {
        student.displayInfo();
        // 学生データの処理
    }
}
```', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 16, 'DETAIL', 'パッケージとディレクトリ構造', '■ パッケージとフォルダの対応関係
パッケージ名とディレクトリ構造は完全に一致する必要があります：

```
プロジェクトルート/
└── src/
    └── com/
        └── school/
            ├── model/
            │   ├── Student.java    # package com.school.model;
            │   └── Teacher.java    # package com.school.model;
            ├── service/
            │   ├── StudentService.java # package com.school.service;
            │   └── TeacherService.java # package com.school.service;
            └── util/
                ├── DateUtil.java   # package com.school.util;
                └── StringUtil.java # package com.school.util;
```

■ コンパイル時のクラスパス
```bash
# ソースコードのコンパイル
javac -d classes src/com/school/model/Student.java

# 生成されるクラスファイル
classes/
└── com/
    └── school/
        └── model/
            └── Student.class

# 実行時のクラスパス指定
java -cp classes com.school.model.Student
```', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 16, 'NOTE', 'パッケージ設計のベストプラクティス', '■ 命名規則のガイドライン
1. 逆ドメイン名記法の使用
   - 正: com.example.myapp
   - 誤: myapp.example.com

2. 小文字のみ使用
   - 正: com.school.student
   - 誤: com.School.Student

3. Java予約語の回避
   - 正: com.school.models（複数形）
   - 誤: com.school.class（classは予約語）

■ パッケージ設計の原則
1. **単一責任の原則**: 1つのパッケージは1つの関心事に集中
2. **疎結合**: パッケージ間の依存関係を最小化
3. **高内聚**: 関連性の高いクラスを同一パッケージに配置
4. **階層化**: ビジネスロジック、データアクセス、UI等の分離

■ 一般的なパッケージ構成例
```
com.company.project
├── model/entity     # エンティティクラス
├── model/dto        # データ転送オブジェクト
├── service         # ビジネスロジック
├── repository      # データアクセス層
├── controller      # コントローラー層
├── util           # ユーティリティクラス
└── exception      # 例外クラス
```

■ 注意点
- パッケージ名の変更は大きな影響を与える
- 初期設計時に十分検討する
- チーム内での命名規則統一が重要
- リファクタリング時のパッケージ移動に注意', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義3-2のコンテンツブロック投入（パッケージの作成）
INSERT INTO lecture_content_blocks (id, chapter_id, block_type, title, content, sort_order, created_at, updated_at) VALUES
(26, 17, 'OVERVIEW', '概要', 'package文を使用して実際にパッケージを作成する方法を学習します。ディレクトリ構造の作成から、package文の記述、コンパイルと実行までの一連の流れを実践的に理解します。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 17, 'CODE', 'パッケージ作成の実践例', '■ ディレクトリ構造の作成
```bash
# プロジェクト構造を作成
mkdir -p myproject/src/com/mycompany/calculator
mkdir -p myproject/classes
cd myproject
```

■ パッケージ内のクラス作成
```java
// src/com/mycompany/calculator/Calculator.java
package com.mycompany.calculator;

/**
 * 基本的な計算機能を提供するクラス
 */
public class Calculator {

    /**
     * 二つの数値を加算
     * @param a 第一オペランド
     * @param b 第二オペランド
     * @return 加算結果
     */
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * 二つの数値を減算
     * @param a 第一オペランド
     * @param b 第二オペランド
     * @return 減算結果
     */
    public double subtract(double a, double b) {
        return a - b;
    }

    /**
     * 二つの数値を乗算
     * @param a 第一オペランド
     * @param b 第二オペランド
     * @return 乗算結果
     */
    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * 二つの数値を除算
     * @param a 第一オペランド（被除数）
     * @param b 第二オペランド（除数）
     * @return 除算結果
     * @throws IllegalArgumentException ゼロ除算の場合
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("ゼロで除算することはできません");
        }
        return a / b;
    }
}

// src/com/mycompany/calculator/CalculatorApp.java
package com.mycompany.calculator;

/**
 * Calculator クラスを使用するメインアプリケーション
 */
public class CalculatorApp {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("=== 計算機アプリケーション ===");

        double a = 10.0;
        double b = 3.0;

        System.out.println("数値A: " + a);
        System.out.println("数値B: " + b);
        System.out.println();

        System.out.println("加算: " + calc.add(a, b));
        System.out.println("減算: " + calc.subtract(a, b));
        System.out.println("乗算: " + calc.multiply(a, b));
        System.out.println("除算: " + calc.divide(a, b));
    }
}
```', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 17, 'CODE', 'コンパイルと実行', '■ コンパイル手順
```bash
# パッケージを含むクラスのコンパイル
javac -d classes src/com/mycompany/calculator/*.java

# 生成されるディレクトリ構造の確認
tree classes/
# classes/
# └── com/
#     └── mycompany/
#         └── calculator/
#             ├── Calculator.class
#             └── CalculatorApp.class
```

■ 実行手順
```bash
# クラスパスを指定して実行
java -cp classes com.mycompany.calculator.CalculatorApp

# 期待される出力
=== 計算機アプリケーション ===
数値A: 10.0
数値B: 3.0

加算: 13.0
減算: 7.0
乗算: 30.0
除算: 3.3333333333333335
```

■ JARファイルの作成
```bash
# JARファイルの作成
jar cvf calculator.jar -C classes .

# JARファイルからの実行
java -cp calculator.jar com.mycompany.calculator.CalculatorApp
```', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, 17, 'NOTE', 'package文の記述ルール', '■ package文の基本ルール
1. **ファイルの先頭に記述**: package文はソースファイルの最初（コメントを除く）に記述
2. **セミコロンで終了**: package文は必ずセミコロンで終了
3. **1ファイル1パッケージ**: 1つのソースファイルは1つのパッケージにのみ所属
4. **ディレクトリ構造と一致**: パッケージ名はディレクトリ構造と完全に一致する必要

■ 正しい記述例
```java
// 正しい例
package com.mycompany.util;

import java.util.List;
import java.util.ArrayList;

public class StringUtils {
    // クラスの実装
}
```

■ 間違った記述例
```java
// 間違い1: package文の前にimport文
import java.util.List;
package com.mycompany.util;  // エラー

// 間違い2: セミコロンなし
package com.mycompany.util   // エラー

// 間違い3: 複数のpackage文
package com.mycompany.util;
package com.mycompany.helper;  // エラー
```

■ パッケージなしクラス（デフォルトパッケージ）
- package文を記述しない場合、クラスはデフォルトパッケージに所属
- 本格的な開発では推奨されない
- 他のパッケージからインポートできない制限あり', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 講義3-3のコンテンツブロック投入（importステートメント）
INSERT INTO lecture_content_blocks (id, chapter_id, block_type, title, content, sort_order, created_at, updated_at) VALUES
(30, 18, 'OVERVIEW', '概要', 'import文を使用して他のパッケージのクラスを利用する方法を学習します。単一クラスのインポート、パッケージ全体のインポート、静的インポートの使い方と、それぞれの適切な使用場面を理解します。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 18, 'CODE', 'import文の基本的な使い方', '■ 単一クラスのインポート（推奨）
```java
// com/mycompany/main/Application.java
package com.mycompany.main;

// 特定のクラスをインポート
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import com.mycompany.calculator.Calculator;

public class Application {
    public static void main(String[] args) {
        // インポートしたクラスを直接使用可能
        List<String> messages = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Calculator calc = new Calculator();

        messages.add("アプリケーション開始: " + now);

        double result = calc.add(10.5, 20.3);
        messages.add("計算結果: " + result);

        for (String message : messages) {
            System.out.println(message);
        }
    }
}
```

■ パッケージ全体のインポート（使用注意）
```java
package com.mycompany.main;

// パッケージ全体をインポート（*を使用）
import java.util.*;
import java.time.*;

public class WildcardImportExample {
    public static void main(String[] args) {
        // パッケージ内のすべてのクラスが利用可能
        List<String> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Map<String, Object> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        System.out.println("現在時刻: " + now);
        System.out.println("今日の日付: " + today);
    }
}
```', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 18, 'CODE', 'FQCN（完全修飾クラス名）の使用', '■ インポートなしでクラスを使用
```java
package com.mycompany.main;

/**
 * インポート文を使わずにFQCNで直接クラスを指定する例
 */
public class FQCNExample {
    public static void main(String[] args) {
        // FQCN（完全修飾クラス名）で直接指定
        java.util.List<String> messages = new java.util.ArrayList<>();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        messages.add("FQCN使用例");
        messages.add("現在時刻: " + now.toString());

        // 異なるパッケージの同名クラスを区別する場合に有効
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

        System.out.println("java.util.Date: " + utilDate);
        System.out.println("java.sql.Date: " + sqlDate);

        for (String message : messages) {
            System.out.println(message);
        }
    }
}
```

■ 名前の衝突を解決する例
```java
package com.mycompany.main;

// 片方のクラスのみインポート
import java.util.Date;

public class NameConflictResolution {
    public static void main(String[] args) {
        // インポートしたクラスは直接使用
        Date utilDate = new Date();

        // もう一方はFQCNで指定
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

        System.out.println("Util Date: " + utilDate);
        System.out.println("SQL Date: " + sqlDate);
    }
}
```', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 18, 'NOTE', 'import文のベストプラクティス', '■ 推奨される使い方
1. **明示的インポート**: 使用するクラスを個別に指定
   ```java
   import java.util.ArrayList;
   import java.util.List;
   ```

2. **ワイルドカードの制限的使用**: 同一パッケージから多数のクラスを使用する場合のみ
   ```java
   import java.util.*;  // 5個以上のクラスを使用する場合
   ```

3. **静的インポートの慎重な使用**: 定数や静的メソッドを頻繁に使用する場合のみ

■ 避けるべき使い方
1. **不要なインポート**: 使用しないクラスのインポート
2. **過度なワイルドカード**: 名前の衝突リスクを高める
3. **java.lang パッケージのインポート**: 自動インポートされるため不要

■ IDEの支援機能
- **自動インポート**: 未定義クラス使用時の自動インポート提案
- **不要インポート除去**: 使用されていないインポート文の自動削除
- **インポート整理**: インポート文のアルファベット順ソート
- **ワイルドカード変換**: 同一パッケージから一定数以上のクラスを使用時の自動変換

■ コンパイル時の注意点
- インポートエラーはコンパイル時に検出
- クラスパスの設定が重要
- パッケージ構造とディレクトリ構造の一致確認', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

COMMIT;
