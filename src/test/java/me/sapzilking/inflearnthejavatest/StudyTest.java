package me.sapzilking.inflearnthejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기 😜")
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    @Disabled //해당 테스트를 제외시킨다
    void create_new_study_again() {
        System.out.println("create1");
     }

    //모든 테스트가 실행되기 이전에 딱 한 번만 호출된다
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    //모든 테스트가 실행된 이후에 딱 한 번만 호출된다
    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    //모든 테스트를 실행하기 전에 호출된다
    @BeforeEach
    void beforeEach() {
        System.out.println("Before each");
    }


    //모든 테스트를 실행한 후에 호출된다
    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

}