package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final long ID = 1L;
    public static final String VENDOR_NAME = "Home Vendor";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(VENDOR_NAME);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertNotNull(vendorDTO);
        assertEquals(Long.valueOf(ID), vendorDTO.getId());
        assertEquals(VENDOR_NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDtoToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(VENDOR_NAME);

        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        assertNotNull(vendor);
        assertEquals(Long.valueOf(ID), vendor.getId());
        assertEquals(VENDOR_NAME, vendor.getName());
    }
}
