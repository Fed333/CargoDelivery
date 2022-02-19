package com.epam.cargo.service;

import com.epam.cargo.entity.DimensionsFare;
import com.epam.cargo.repos.DimensionsFareRepo;
import com.epam.cargo.util.CsvFileDimensionsFareReader;
import com.epam.cargo.util.FareDataMock;
import com.epam.cargo.util.MockEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Stream;


@RunWith(SpringRunner.class)
@SpringBootTest
class DimensionsFareServiceTest {

    private static final String DIMENSIONS_FARES_TABLE_CASES = "src/test/resources/static/dimensions_fares_table.csv";

    @Autowired
    private DimensionsFareService fareService;

    @MockBean
    private DimensionsFareRepo dimensionsFareRepo;

    private static List<DimensionsFare> fares;

    @BeforeAll
    public static void init(){
        fares = CsvFileDimensionsFareReader.readDimensionsFaresCsv(DIMENSIONS_FARES_TABLE_CASES);
    }

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(0, 10.0),
                Arguments.of(1000, 10.0),
                Arguments.of(4000, 10.0),
                Arguments.of(4999, 10.0),
                Arguments.of(5000, 20.0),
                Arguments.of(20000, 35.0),
                Arguments.of(50000, 35.0),
                Arguments.of(100000, 60.0),
                Arguments.of(500000, 60.0),
                Arguments.of(1000000, 80.0),
                Arguments.of(2000000, 160.0),
                Arguments.of(2500000, 160.0),
                Arguments.of(3000000, 240.0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    void testGetPrice(Integer volume, Double price) {
        FareDataMock<DimensionsFare> dataMock = new FareDataMock<>(fares);

        MockEnvironment.mockDimensionsFareRepo(dimensionsFareRepo, volume, dataMock);

        Assertions.assertEquals(price, fareService.getPrice(volume));
    }

}
