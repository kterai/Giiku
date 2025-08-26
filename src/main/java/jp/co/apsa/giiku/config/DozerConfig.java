package jp.co.apsa.giiku.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.entity.ProgramSchedule;
import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.entity.StudentEnrollment;
import jp.co.apsa.giiku.domain.entity.Quiz;
import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.domain.entity.Instructor;
import jp.co.apsa.giiku.domain.entity.UserRole;
import jp.co.apsa.giiku.domain.entity.MockTest;

/**
 * Dozer の設定を提供するコンフィグレーションクラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Configuration
public class DozerConfig {

    /**
     * 監査情報やIDを除外した Dozer Mapper を生成する。
     *
     * @return Dozer Mapper
     */
    @Bean
    public Mapper dozerMapper() {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(new BeanMappingBuilder() {
                    @Override
                    protected void configure() {
                        mapping(ProgramSchedule.class, ProgramSchedule.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("version")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                        mapping(LectureChapter.class, LectureChapter.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("version")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                        mapping(StudentProfile.class, StudentProfile.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("version")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                        mapping(StudentEnrollment.class, StudentEnrollment.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("version")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                        mapping(Quiz.class, Quiz.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("createdAt")
                                .exclude("updatedAt");
                        mapping(QuestionBank.class, QuestionBank.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                        mapping(Instructor.class, Instructor.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("version")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                        mapping(UserRole.class, UserRole.class, TypeMappingOptions.oneWay())
                                .exclude("id")
                                .exclude("version")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                        mapping(MockTest.class, MockTest.class, TypeMappingOptions.oneWay())
                                .exclude("testId")
                                .exclude("createdBy")
                                .exclude("createdAt")
                                .exclude("updatedBy")
                                .exclude("updatedAt");
                    }
                })
                .build();
    }
}

