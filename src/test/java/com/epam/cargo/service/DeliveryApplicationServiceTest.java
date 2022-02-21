package com.epam.cargo.service;

import com.epam.cargo.dto.AddressRequest;
import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.entity.*;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.mock.*;
import com.epam.cargo.repos.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.epam.cargo.service.CityZipcodesConstants.KYIV_ZIPCODE;
import static com.epam.cargo.service.CityZipcodesConstants.VINNYTSIA_ZIPCODE;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeliveryApplicationServiceTest {

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveredBaggageService deliveredBaggageService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressService addressService;

    @MockBean
    private DeliveryApplicationRepo deliveryApplicationRepo;

    @MockBean
    private DeliveredBaggageRepo deliveredBaggageRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private AddressRepo addressRepo;

    @MockBean
    private DeliveryCostCalculatorService costCalculatorService;

    @MockBean
    private DistanceFareRepo distanceFareRepo;

    @MockBean
    private WeightFareRepo weightFareRepo;

    @MockBean
    private DimensionsFareRepo dimensionsFareRepo;

    @BeforeEach
    public void setUp(){
        DeliveryApplicationMockEnvironment applicationMockEnvironment = new DeliveryApplicationMockEnvironment();
        applicationMockEnvironment.mockDeliveryApplicationRepoBean(deliveryApplicationRepo);

        UserMockEnvironment userMockEnvironment = new UserMockEnvironment();
        userMockEnvironment.mockUserRepoBean(userRepo);

        DeliveredBaggageMockEnvironment baggageMockEnvironment = new DeliveredBaggageMockEnvironment();
        baggageMockEnvironment.mockDeliveredBaggageRepoBean(deliveredBaggageRepo);

        CityMockEnvironment cityMockEnvironment = new CityMockEnvironment();
        cityMockEnvironment.mockCityRepoBean(cityRepo);

        AddressMockEnvironment addressMockEnvironment = new AddressMockEnvironment();
        addressMockEnvironment.mockAddressRepoBean(addressRepo);

    }

    public static Stream<Arguments> applicationsToSaveTestCases() {
        return Stream.of(
                Arguments.of(testCorrectDeliveryApplication())
        );
    }


    public static Stream<Arguments> applicationsToSaveFailTestCases() {
        return Stream.of(
                Arguments.of(
                        DeliveryApplication.builder()
                                .customer(null)
                                .deliveredBaggage(testBaggage())
                                .senderAddress(testSenderAddress())
                                .receiverAddress(testReceiverAddress())
                                .sendingDate(testSendingDate())
                                .receivingDate(testReceivingDate())
                                .price(100d).build(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        testCorrectDeliveryApplication(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        DeliveryApplication.builder()
                                .customer(testCustomer())
                                .deliveredBaggage(null)
                                .senderAddress(testSenderAddress())
                                .receiverAddress(testReceiverAddress())
                                .sendingDate(testSendingDate())
                                .receivingDate(testReceivingDate())
                                .price(100d).build(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()).andThen(prepareUser()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        DeliveryApplication.builder()
                                .customer(testCustomer())
                                .deliveredBaggage(testBaggage())
                                .senderAddress(testSenderAddress())
                                .receiverAddress(testReceiverAddress())
                                .sendingDate(null)
                                .receivingDate(null)
                                .price(100d).build(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()).andThen(prepareUser()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        testCorrectDeliveryApplication(),
                        prepareUser().andThen(prepareSenderAddress()),
                        NoExistingCityException.class
                ),
                Arguments.of(
                        testCorrectDeliveryApplication(),
                        prepareUser().andThen(prepareReceiverAddress()),
                        NoExistingCityException.class
                )
        );
    }

    private static LocalDate testSendingDate() {
        return LocalDate.now();
    }

    public static Stream<Arguments> sendApplicationTestCases() {
        return Stream.of(
          Arguments.of(
                  testCustomer(),
                  testCorrectDeliveryApplicationRequest(1L, 2L),
                  prepareUser(testCustomer()).andThen(prepareCity(testSenderCity())).andThen(prepareCity(testReceiverCity()))
          )
        );
    }

    @ParameterizedTest
    @MethodSource("applicationsToSaveTestCases")
    @SneakyThrows
    void saveApplication(DeliveryApplication application) {
        prepareMockEnvironment(application);

        applicationService.saveApplication(application);
        Mockito.verify(deliveryApplicationRepo, Mockito.times(1)).save(any(DeliveryApplication.class));
        Assertions.assertEquals(application, applicationService.findById(application.getId()));

    }

    @ParameterizedTest
    @MethodSource("applicationsToSaveFailTestCases")
    @SneakyThrows
    void saveApplicationFail(DeliveryApplication application, BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepare, Class<? extends Exception> clazz) {
        prepare.accept(application, this);
        Assertions.assertThrows(clazz, ()->applicationService.saveApplication(application));
        Mockito.verify(deliveryApplicationRepo, Mockito.times(0)).save(any(DeliveryApplication.class));
    }

    //TODO mock fares and directions as well
    @ParameterizedTest
    @MethodSource("sendApplicationTestCases")
    @SneakyThrows
    void sendApplication(
            User customer,
            DeliveryApplicationRequest request,
            Consumer<DeliveryApplicationServiceTest> prepared
    ) {
        prepared.accept(this);

        Mockito.when(costCalculatorService.calculateDistanceCost(any(Address.class), any(Address.class)))
                .thenReturn(0d);

        Mockito.when(costCalculatorService.calculateWeightCost(any(Double.class)))
                .thenReturn(0d);

        Mockito.when(costCalculatorService.calculateDimensionsCost(any(Double.class)))
                .thenReturn(0d);

        applicationService.sendApplication(customer, request);

        Mockito.verify(deliveryApplicationRepo, Mockito.times(1))
                .save(any(DeliveryApplication.class));
    }

    private DeliveredBaggage toDeliveredBaggage(DeliveredBaggageRequest request) {
        return DeliveredBaggage.builder()
                .weight(request.getWeight())
                .volume(request.getVolume())
                .description(request.getDescription())
                .type(request.getType()).build();
    }

    private void prepareMockEnvironment(DeliveryApplication application) throws NoExistingCityException {
        userRepo.save(application.getCustomer());
        cityService.addCity(application.getSenderAddress().getCity());
        cityService.addCity(application.getReceiverAddress().getCity());
    }

    private static Address testReceiverAddress() {
        return Address.builder().city(testReceiverCity()).street("Chreschatyk").houseNumber("1").build();
    }

    private static City testReceiverCity() {
        return new City("Kyiv", KYIV_ZIPCODE);
    }

    private static Address testSenderAddress() {
        return Address.builder().city(testSenderCity()).street("Podilya").houseNumber("1").build();
    }

    private static City testSenderCity() {
        return new City("Vinnytsia", VINNYTSIA_ZIPCODE);
    }

    private static AddressRequest testSenderAddressRequest(Long id){
        return AddressRequest.builder().cityId(id).streetName("Podilya").houseNumber("1").build();
    }

    private static AddressRequest testReceiverAddressRequest(Long id){
        return AddressRequest.builder().cityId(id).streetName("Chreschatyk").houseNumber("1").build();
    }
    
    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareSenderAddress() {
        return prepareAddress(DeliveryApplication::getSenderAddress);
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareReceiverAddress() {
        return prepareAddress(DeliveryApplication::getReceiverAddress);
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareAddress(Function<DeliveryApplication, Address> getAddressFunction){
        return new BiConsumer<>() {
            @SneakyThrows
            @Override
            public void accept(DeliveryApplication a, DeliveryApplicationServiceTest t) {
                Address address = getAddressFunction.apply(a);
                t.cityService.addCity(address.getCity());
                t.addressService.addAddress(address);
            }
        };
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareUser(){
        return (a, t)->{
            try {
                t.userService.addUser(a.getCustomer());
            } catch (NoExistingCityException e) {
                e.printStackTrace();
            }
        };
    }

    private static Consumer<DeliveryApplicationServiceTest> prepareUser(User user) {
        return test -> {
            try {
                test.userService.addUser(user);
            } catch (NoExistingCityException e) {
                e.printStackTrace();
            }
        };
    }
    
    private static Consumer<DeliveryApplicationServiceTest> prepareCity(City city){
        return test -> {
            test.cityService.addCity(city);
        };
    }
    
    private static DeliveredBaggage testBaggage() {
        return DeliveredBaggage.builder()
                .volume(1000d)
                .weight(2d)
                .type(BaggageType.STANDART)
                .description("Test case baggage").build();
    }

    private static DeliveredBaggageRequest testBaggageRequest(){
        return DeliveredBaggageRequest.builder()
                .volume(1000d)
                .weight(2d)
                .type(BaggageType.STANDART)
                .description("Test case baggage").build();
    }
    
    private static DeliveryApplicationRequest testCorrectDeliveryApplicationRequest(Long senderCityId, Long receiverCityId) {
        return DeliveryApplicationRequest.builder()
                .deliveredBaggageRequest(testBaggageRequest())
                .senderAddress(testSenderAddressRequest(senderCityId))
                .receiverAddress(testReceiverAddressRequest(receiverCityId))
                .sendingDate(testSendingDate())
                .receivingDate(testReceivingDate()).build();
    }

    private static LocalDate testReceivingDate() {
        return LocalDate.now().plusDays(3);
    }


    private static User testCustomer() {
        return User.builder().name("Ivan").surname("Sipalka").login("Pips").password("Password123").phone("+380976543826").build();
    }

    private static UserRequest testCustomerRequest(){
        return UserRequest.builder().name("Ivan").surname("Sipalka").login("Pips").password("Password123").phone("+380976543826").build();
    }
    
    private static DeliveryApplication testCorrectDeliveryApplication() {
        return DeliveryApplication.builder()
                .customer(testCustomer())
                .deliveredBaggage(testBaggage())
                .senderAddress(testSenderAddress())
                .receiverAddress(testReceiverAddress())
                .sendingDate(testSendingDate())
                .receivingDate(testReceivingDate())
                .price(100d).build();
    }

}