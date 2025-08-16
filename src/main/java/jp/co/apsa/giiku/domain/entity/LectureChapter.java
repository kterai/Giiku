package jp.co.apsa.giiku.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 講義チャプターエンティティ
 * 各講義内の章・セクション単位での学習コンテンツ管理
 * 
 * @author Giiku LMS Development Team
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "lecture_chapters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LectureChapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long chapterId;

    @NotNull(message = "講義IDは必須です")
    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @NotNull(message = "チャプター番号は必須です")
    @Min(value = 1, message = "チャプター番号は1以上である必要があります")
    @Max(value = 999, message = "チャプター番号は999以下である必要があります")
    @Column(name = "chapter_number", nullable = false)
    private Integer chapterNumber;

    @NotBlank(message = "チャプタータイトルは必須です")
    @Size(max = 200, message = "チャプタータイトルは200文字以内で入力してください")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Size(max = 1000, message = "チャプター説明は1000文字以内で入力してください")
    @Column(name = "description", length = 1000)
    private String description;

    @Min(value = 1, message = "所要時間は1分以上で設定してください")
    @Max(value = 480, message = "所要時間は480分以下で設定してください")
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Size(max = 2000, message = "教材情報は2000文字以内で入力してください")
    @Column(name = "materials", length = 2000)
    private String materials;

    @Size(max = 1500, message = "学習目標は1500文字以内で入力してください")
    @Column(name = "objectives", length = 1500)
    private String objectives;

    @NotBlank(message = "ステータスは必須です")
    @Size(max = 20, message = "ステータスは20文字以内で入力してください")
    @Column(name = "status", nullable = false, length = 20)
    private String status; // DRAFT, ACTIVE, INACTIVE, ARCHIVED

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "video_url")
    @Size(max = 500, message = "動画URLは500文字以内で入力してください")
    private String videoUrl;

    @Column(name = "document_url")
    @Size(max = 500, message = "資料URLは500文字以内で入力してください")
    private String documentUrl;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = true;

    // 監査フィールド
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", length = 100, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    // ビジネスロジックメソッド

    /**
     * チャプターがアクティブかどうかを判定
     * @return アクティブな場合true
     */
    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }

    /**
     * 必須チャプターかどうかを判定
     * @return 必須チャプターの場合true
     */
    public boolean isRequired() {
        return this.isRequired != null && this.isRequired;
    }

    /**
     * チャプター表示用タイトルを生成
     * @return フォーマット済みタイトル
     */
    public String getDisplayTitle() {
        return String.format("第%d章: %s", this.chapterNumber, this.title);
    }

    /**
     * 所要時間を時間分形式で取得
     * @return 時間分形式の文字列（例：1時間30分）
     */
    public String getFormattedDuration() {
        if (this.durationMinutes == null || this.durationMinutes <= 0) {
            return "未設定";
        }

        int hours = this.durationMinutes / 60;
        int minutes = this.durationMinutes % 60;

        if (hours > 0 && minutes > 0) {
            return String.format("%d時間%d分", hours, minutes);
        } else if (hours > 0) {
            return String.format("%d時間", hours);
        } else {
            return String.format("%d分", minutes);
        }
    }

    /**
     * チャプターの学習リソースが利用可能かチェック
     * @return リソースが利用可能な場合true
     */
    public boolean hasLearningResources() {
        return (this.videoUrl != null && !this.videoUrl.trim().isEmpty()) ||
               (this.documentUrl != null && !this.documentUrl.trim().isEmpty()) ||
               (this.materials != null && !this.materials.trim().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureChapter that = (LectureChapter) o;
        return Objects.equals(chapterId, that.chapterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapterId);
    }

    @Override
    public String toString() {
        return String.format("LectureChapter{id=%d, lectureId=%d, number=%d, title='%s', status='%s'}", 
                           chapterId, lectureId, chapterNumber, title, status);
    }
}