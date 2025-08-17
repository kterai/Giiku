package jp.co.apsa.giiku.exception;

/**
 * 学生が見つからない場合に投げられる例外クラス
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class StudentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long studentId;
    private final String studentNumber;

    /**
     * デフォルトコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public StudentNotFoundException() {
        super("学生が見つかりません。");
        this.studentId = null;
        this.studentNumber = null;
    }

    /**
     * メッセージを指定するコンストラクタ
     * 
     * @param message エラーメッセージ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public StudentNotFoundException(String message) {
        super(message);
        this.studentId = null;
        this.studentNumber = null;
    }

    /**
     * メッセージと原因を指定するコンストラクタ
     * 
     * @param message エラーメッセージ
     * @param cause 原因となった例外
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.studentId = null;
        this.studentNumber = null;
    }

    /**
     * 学生IDを指定するコンストラクタ
     * 
     * @param studentId 見つからない学生のID
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public StudentNotFoundException(Long studentId) {
        super(String.format("ID: %d の学生が見つかりません。", studentId));
        this.studentId = studentId;
        this.studentNumber = null;
    }

    /**
     * 学生IDとメッセージを指定するコンストラクタ
     *
     * @param studentId 見つからない学生のID
     * @param message エラーメッセージ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public StudentNotFoundException(Long studentId, String message) {
        super(message);
        this.studentId = studentId;
        this.studentNumber = null;
    }

    /**
     * 学生番号とメッセージを指定するコンストラクタ
     * 
     * @param studentNumber 見つからない学生の学生番号
     * @param message エラーメッセージ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public StudentNotFoundException(String studentNumber, String message) {
        super(message);
        this.studentId = null;
        this.studentNumber = studentNumber;
    }

    /**
     * 学生IDとメッセージと原因を指定するコンストラクタ
     * 
     * @param studentId 見つからない学生のID
     * @param message エラーメッセージ
     * @param cause 原因となった例外
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public StudentNotFoundException(Long studentId, String message, Throwable cause) {
        super(message, cause);
        this.studentId = studentId;
        this.studentNumber = null;
    }

    /**
     * 見つからない学生のIDを取得
     * 
     * @return 学生ID（設定されていない場合はnull）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * 見つからない学生の学生番号を取得
     * 
     * @return 学生番号（設定されていない場合はnull）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * 詳細な情報を含む文字列表現を返す
     * 
     * @return 例外の詳細情報
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(": ").append(getMessage());

        if (studentId != null) {
            sb.append(" [studentId=").append(studentId).append("]");
        }

        if (studentNumber != null) {
            sb.append(" [studentNumber=").append(studentNumber).append("]");
        }

        return sb.toString();
    }

    /**
     * 静的ファクトリーメソッド - ID指定
     * 
     * @param studentId 見つからない学生のID
     * @return StudentNotFoundException インスタンス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static StudentNotFoundException byId(Long studentId) {
        return new StudentNotFoundException(studentId);
    }

    /**
     * 静的ファクトリーメソッド - 学生番号指定
     * 
     * @param studentNumber 見つからない学生の学生番号
     * @return StudentNotFoundException インスタンス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static StudentNotFoundException byStudentNumber(String studentNumber) {
        return new StudentNotFoundException(
                studentNumber,
                String.format("学生番号: %s の学生が見つかりません。", studentNumber)
        );
    }

    /**
     * 静的ファクトリーメソッド - カスタムメッセージ
     * 
     * @param message カスタムエラーメッセージ
     * @return StudentNotFoundException インスタンス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static StudentNotFoundException withMessage(String message) {
        return new StudentNotFoundException(message);
    }
}
