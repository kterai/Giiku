package jp.co.apsa.giiku.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.entity.ProgramSchedule;

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
                    }
                })
                .build();
    }
}

