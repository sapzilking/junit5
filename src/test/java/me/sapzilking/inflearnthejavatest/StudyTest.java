package me.sapzilking.inflearnthejavatest;

import me.sapzilking.inflearnthejavatest.domain.Study;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(FindSlowTestExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    @DisplayName("스터디 만들기 fast")
    @Tag("fast")
    @Test
    void create_new_study() {
        /*Study study = new Study(-10);*/

        /*assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {

            @Override
            public String get() {
                return "스터디를 처음 만들면 DRAFT 상태다.";
            }
        });*/

        /**
         * 테스트 실패 시 작성하는 메세지를 람다식을 사용한것과 문자열을 그대로 적었을 때의 차이점
         * 문자열을 그대로 적은 코드는 테스트 성공/실패 여부와 상관없이 항상 문자열 연산을 한다
         * 람다를 사용한 코드는 테스트가 실패했을 때만 문자열 연산을 진행한다.
         * 문자열 연산의 비용이 조금 걱정이 된다면 람다식을 사용하는게 조금 더 성능을 신경쓰는 입장에서 유리할 수 있다.
         */
        /*assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다.");*/


        /*assertNotNull(study);
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다.");
        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");*/

        //첫번째 테스트가 실패하면 나머지 테스트를 검증할 수 없다. 이럴때 사용하는게 assertAll이다.
        /*assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );*/

        //------------------------------------------------------------------------------------------------------------------------------------------//

        /*assertThrows(IllegalArgumentException.class, () -> new Study(-10));*/ //이런식으로 예외가 발생하는지 확인할 수 있다

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10)); //이런식으로 내가 기대했던 메세지가 맞는지 확인할 수 있다.
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());

        //------------------------------------------------------------------------------------------------------------------------------------------//

        //아래의 테스트는 실제 코드가 끝날때 까지 기다려야한다. 즉 300ms를 기다려야하는 것이다
        /*assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });*/

        //100ms가 끝나면 어차피 테스트는 실패니까 테스트가 끝내고 싶으면 아래와 같이 assertTimeoutPreemptively를 사용하면 되는데, 주의할 점은 로직 내 에서 ThreadLocal을 사용한다면 rollback 되지 않고, 실제 DB에 반영이 될 수 있으므로 주의한다.
        /*assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });*/

        //Junit Third party 라이브러리 중 하나인 AssertJ를 이용
        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

//    @Test
//    @Disabled //해당 테스트를 제외시킨다
    @DisplayName("스터디 만들기 slow")
//    @Tag("slow")
    @SlowTest
    void create_new_study_again() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println("create1");
    }

    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions} ")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo.getCurrentRepetition() + "/" +
                repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
    @NullAndEmptySource
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest2(Integer limit) {
        System.out.println(limit);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest3(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("하나의 argument 받을 때")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest4(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    //하나의 argument를 받을 때 사용
    static class StudyConverter extends SimpleArgumentConverter { //하나의 argument를 다른 type으로 변환할 때 적용 됨
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass, "Can only convert to study");
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    //여러 argument를 받을 때 사용
    @DisplayName("여러개의 argument받을 때")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest5(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }
    @DisplayName("여러개의 argument받을 때")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest6(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
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