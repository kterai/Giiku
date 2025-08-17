package jp.co.apsa.giiku.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import jp.co.apsa.giiku.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
class DashboardServiceTest {

    @Test
    void countUsersReturnsRepositoryCount() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.count()).thenReturn(5L);
        DashboardService service = new DashboardService(userRepository);
        assertThat(service.countUsers()).isEqualTo(5L);
    }
}
