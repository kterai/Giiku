package jp.co.apsa.giiku.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * 学生DTO
 * 学生の基本情報を転送するためのデータ転送オブジェクト
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    /**
     * 学生ID
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private Long id;

    /**
     * 学生コード
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "学生コードは必須です")
    @Size(max = 20, message = "学生コードは20文字以内で入力してください")
    private String studentCode;

    /**
     * 氏名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "氏名は必須です")
    @Size(max = 100, message = "氏名は100文字以内で入力してください")
    private String fullName;

    /**
     * 氏名（カナ）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "氏名（カナ）は100文字以内で入力してください")
    private String fullNameKana;

    /**
     * メールアドレス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Email(message = "有効なメールアドレスを入力してください")
    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください")
    private String email;

    /**
     * 電話番号
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 20, message = "電話番号は20文字以内で入力してください")
    private String phoneNumber;

    /**
     * 生年月日
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private LocalDate birthDate;

    /**
     * 郵便番号
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 10, message = "郵便番号は10文字以内で入力してください")
    private String postalCode;

    /**
     * 住所
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 500, message = "住所は500文字以内で入力してください")
    private String address;

    /**
     * 緊急連絡先氏名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "緊急連絡先氏名は100文字以内で入力してください")
    private String emergencyContactName;

    /**
     * 緊急連絡先電話番号
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 20, message = "緊急連絡先電話番号は20文字以内で入力してください")
    private String emergencyContactPhone;

    /**
     * アクティブフラグ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private Boolean active = true;
}
