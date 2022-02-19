package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.repos.DeliveredBaggageRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeliveredBaggageServiceTest {

    @Autowired
    private DeliveredBaggageService baggageService;

    @MockBean
    private DeliveredBaggageRepo deliveredBaggageRepo;

    private Map<Long, DeliveredBaggage> baggageStorage = new HashMap<>();

    public static Stream<Arguments> baggageCases() {
        return Stream.of(
                Arguments.of(DeliveredBaggage.builder()
                        .id(1L)
                        .weight(10D)
                        .volume(14000D)
                        .type(BaggageType.PERISHABLE).build()
            )
        );
    }

    public static Stream<Arguments> baggageFailSaveCases() {
        return Stream.of(
            Arguments.of(DeliveredBaggage.builder().volume(2000D).build()),
            Arguments.of(DeliveredBaggage.builder().weight(5D).build()),
            Arguments.of(DeliveredBaggage.builder().type(BaggageType.STANDART).build()),
            Arguments.of(DeliveredBaggage.builder().volume(1000000D).type(BaggageType.OVERSIZED).build())
        );
    }

    public static Stream<Arguments> updateBaggageTestCases() {
        return Stream.of(
                Arguments.of(
                        DeliveredBaggage.builder().id(1L).type(BaggageType.STANDART).weight(20d).volume(1600d).build(),
                        DeliveredBaggageRequest.builder().type(BaggageType.DANGEROUS).weight(15d).volume(1300d).build()
                ),
                Arguments.of(
                        DeliveredBaggage.builder().id(2L).type(BaggageType.STANDART).weight(2.3d).volume(260d).build(),
                        DeliveredBaggageRequest.builder().type(BaggageType.PERISHABLE).weight(3.4d).volume(220d).build()

                )
        );
    }

    public static Stream<Arguments> updateBaggageNullPointerFail() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, DeliveredBaggageRequest.builder().type(BaggageType.DANGEROUS).weight(15d).volume(1300d).build()),
                Arguments.of(DeliveredBaggage.builder().id(2L).type(BaggageType.STANDART).weight(2.3d).volume(260d).build(), null)
        );
    }

    @BeforeEach
    public void setUp(){
        mockDeliveredBaggageRepo(deliveredBaggageRepo);
    }

    @ParameterizedTest
    @MethodSource("baggageCases")
    void addBaggage(DeliveredBaggage baggage) {
        Assertions.assertTrue(baggageService.save(baggage));
        Assertions.assertEquals(baggage, baggageService.findById(baggage.getId()).orElseThrow());
    }

    @Test
    public void saveNull(){
        Assertions.assertFalse(baggageService.save(null));
    }

    @ParameterizedTest
    @MethodSource("baggageFailSaveCases")
    public void saveFail(DeliveredBaggage baggage){
        Assertions.assertThrows(IllegalArgumentException.class, ()->baggageService.save(baggage));
    }

    @ParameterizedTest
    @MethodSource("updateBaggageTestCases")
    void update(DeliveredBaggage baggage, DeliveredBaggageRequest updateBaggageRequest) {
        baggageStorage.put(baggage.getId(), baggage);

        DeliveredBaggage updated = baggageService.update(baggage, updateBaggageRequest);
        Assertions.assertEquals(updated, baggageService.findById(updated.getId()).orElseThrow());
    }

    @ParameterizedTest
    @MethodSource("updateBaggageTestCases")
    void updateMissingBaggage(DeliveredBaggage baggage, DeliveredBaggageRequest updateBaggageRequest){
        Assertions.assertThrows(IllegalArgumentException.class, ()->baggageService.update(baggage, updateBaggageRequest));
    }

    @ParameterizedTest
    @MethodSource("updateBaggageNullPointerFail")
    void updateNull(DeliveredBaggage baggage, DeliveredBaggageRequest updateBaggageRequest){
        Assertions.assertThrows(NullPointerException.class, ()->baggageService.update(baggage, updateBaggageRequest));

    }

    private Answer<Optional<DeliveredBaggage>> findByIdMock() {
        return invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0, Long.class);
            return Optional.ofNullable(baggageStorage.get(id));
        };
    }

    private Answer<DeliveredBaggage> saveMock() {
        return invocationOnMock -> {
            DeliveredBaggage baggage = invocationOnMock.getArgument(0, DeliveredBaggage.class);

            baggageStorage.put(baggage.getId(), baggage);
            return baggage;
        };
    }

    private void mockDeliveredBaggageRepo(DeliveredBaggageRepo repo) {
        Mockito.when(repo.save(Mockito.any()))
                .thenAnswer(saveMock());

        Mockito.when(repo.findById(Mockito.any()))
                .thenAnswer(findByIdMock());
    }
}