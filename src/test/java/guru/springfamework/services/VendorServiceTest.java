package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class VendorServiceTest {
    public static final long ID_1 = 1L;
    public static final String NAME_1 = "Vendor Name1";
    public static final long ID_2 = 2L;
    public static final String NAME_2 = "Vendor Name2";
    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
        given(vendorRepository.findAll()).willReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertEquals(2, vendorDTOS.size());
        // alternative: assertThat(vendorDTOS.size(), Matchers.is(Matchers.equalTo(2)));
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor = getVendor1();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID_1);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().findById(anyLong());

        assertEquals(Long.valueOf(ID_1), vendorDTO.getId());
    }

    @Test
    public void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));

        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(VendorController.BASE_URL + "1", savedDTO.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor savedVendor = getVendor1();

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        VendorDTO savedDTO = vendorService.saveVendorByDTO(ID_1, vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));

        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(VendorController.BASE_URL + "1", savedDTO.getVendorUrl());
    }

    @Test
    public void patchVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor savedVendor = getVendor1();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(savedVendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        VendorDTO savedDTO = vendorService.patchVendor(ID_1, vendorDTO);

        then(vendorRepository).should().findById(anyLong());
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedDTO.getVendorUrl(), Matchers.containsString("1"));
    }

    @Test
    public void deleteVendorById() {
        vendorRepository.deleteById(ID_1);

        then(vendorRepository).should().deleteById(anyLong());
    }

    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_2);
        vendor.setId(ID_2);
        return vendor;
    }
}