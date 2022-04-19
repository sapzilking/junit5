package me.sapzilking.inflearnthejavatest.study;

import me.sapzilking.inflearnthejavatest.domain.Member;
import me.sapzilking.inflearnthejavatest.domain.Study;
import me.sapzilking.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @DisplayName(value = "mock객체 없이 만들기")
    @Test
    void createStudyServiceNoMock() {
        MemberService memberService = new MemberService() {
            @Override
            public Optional<Member> findById(Long memberId) {
                return Optional.empty();
            }

            @Override
            public void validate(Long memberId) {

            }

            @Override
            public void notify(Study newstudy) {

            }

            @Override
            public void notify(Member member) {

            }
        };

        StudyRepository studyRepository = new StudyRepository() {
            @Override
            public List<Study> findAll() {
                return null;
            }

            @Override
            public List<Study> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Study> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends Study> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Study> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Study> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Study> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Study getOne(Long aLong) {
                return null;
            }

            @Override
            public Study getById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Study> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Study> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Study> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Study> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Study> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Study entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Study> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Study> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Study> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Study> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Study> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Study, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };

        StudyService studyService = new StudyService(memberService, studyRepository); //MemberService와 StudyRepository 둘다 인터페이스만 있고 구현체가 없는 상황이다. 이런 경우가 mock을 사용하기 아주 적절한 경우이다.

        assertNotNull(studyService);
    }

    @DisplayName(value = "Mockito.mock() 메소드로 만드는 방법")
    @Test
    void createStudyService() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }


    //class에 @ExtendWith(MockitoExtension.class)를 선언해 준 뒤 @Mock로 목 객체를 생성 할 수 있다.
    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    @DisplayName(value = "어노테이션을 이용해 목 객체 생성")
    @Test
    void createStudyServiceWithAnnotation() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    //annotation을 이용한 목 객체 생성은 위의 테스트처럼 선언하여 여러 테스트에서 사용할 수도 있고, 아래 테스트 처럼 parameter로 받아서 특정 테스트에서만 사용하도록 할 수도 있다.
    @DisplayName(value = "어노테이션을 이용해 목 객체 생성2")
    @Test
    void createStudyServiceWithAnnotation2(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @DisplayName(value = "Mock 객체 Stubbing")
    @Test
    void mockStubbing(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

//        when(memberService.findById(any())).thenReturn(Optional.of(member)); //stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member)); //stubbing

        Study study = new Study(10, "java");

        assertEquals("test@email.com", memberService.findById(1L).get().getEmail());
//        assertEquals("test@email.com", memberService.findById(2L).get().getEmail());


        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });
        memberService.validate(2L);


//        studyService.createNewStudy(1L, study);

    }

    @DisplayName(value = "Mock 객체 Stubbing 연습")
    @Test
    void stubbingTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());
        assertThrows(IllegalArgumentException.class, () -> studyService.createNewStudy(2L, study));
    }
}