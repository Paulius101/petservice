package com.example.petservice.converter;
import com.example.petservice.dto.PetServiceDTO;
import com.example.petservice.entity.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PetServiceConverterTest {

    private PetServiceConverter converter;

    @BeforeEach
    void setUp() {
        converter = new PetServiceConverter();
    }

    @Test
    void testToEntity_WithDescriptionList() {
        PetServiceDTO dto = new PetServiceDTO();
        dto.setName("SPA");
        dto.setPrice(49.99);
        dto.setDescription(List.of("Test1", "Test2", "Test3"));

        PetService entity = converter.toEntity(dto);

        assertEquals("SPA", entity.getName());
        assertEquals(49.99, entity.getPrice());
        assertEquals("Test1\nTest2\nTest3", entity.getDescription());
    }

    @Test
    void testToEntity_WithNullDescription() {
        PetServiceDTO dto = new PetServiceDTO();
        dto.setName("SPA");
        dto.setPrice(19.99);
        dto.setDescription(null);

        PetService entity = converter.toEntity(dto);

        assertEquals("SPA", entity.getName());
        assertEquals(19.99, entity.getPrice());
        assertNull(entity.getDescription());
    }

    @Test
    void testToEntity_WithEmptyDescription() {
        PetServiceDTO dto = new PetServiceDTO();
        dto.setName("SPA");
        dto.setPrice(99.99);
        dto.setDescription(List.of());

        PetService entity = converter.toEntity(dto);

        assertEquals("SPA", entity.getName());
        assertEquals(99.99, entity.getPrice());
        assertNull(entity.getDescription());
    }

    @Test
    void testToDTO_WithMultilineDescription() {
        PetService service = new PetService();
        service.setServiceId(1L);
        service.setName("SPA");
        service.setPrice(59.99);
        service.setDescription("TEST1\nTEST2\nTEST3");

        PetServiceDTO dto = converter.toDTO(service);

        assertEquals(1L, dto.getServiceId());
        assertEquals("SPA", dto.getName());
        assertEquals(59.99, dto.getPrice());
        assertEquals(List.of("TEST1", "TEST2", "TEST3"), dto.getDescription());
    }

    @Test
    void testToDTO_WithNullDescription() {
        PetService service = new PetService();
        service.setServiceId(2L);
        service.setName("SPA");
        service.setPrice(29.99);
        service.setDescription(null);

        PetServiceDTO dto = converter.toDTO(service);

        assertEquals(2L, dto.getServiceId());
        assertEquals("SPA", dto.getName());
        assertEquals(29.99, dto.getPrice());
        assertNull(dto.getDescription());
    }
}
