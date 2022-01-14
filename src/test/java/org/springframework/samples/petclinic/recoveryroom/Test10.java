package org.springframework.samples.petclinic.recoveryroom;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RecoveryRoomController.class,
		includeFilters = @ComponentScan.Filter(value = RecoveryRoomTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class Test10 {
    @MockBean
    RecoveryRoomService recoveryRoomService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void configureMock() {
        String roomTypeName="room";
        RecoveryRoomType roomType=new RecoveryRoomType();
        roomType.setName(roomTypeName);
        Mockito.when(recoveryRoomService.save(any(RecoveryRoom.class))).thenReturn(null);
        Mockito.when(recoveryRoomService.getRecoveryRoomType(roomTypeName)).thenReturn(roomType);
        List<RecoveryRoomType> roomTypes=new ArrayList<RecoveryRoomType>();
        roomTypes.add(roomType);
        Mockito.when(recoveryRoomService.getAllRecoveryRoomTypes()).thenReturn(roomTypes);
    }

    @WithMockUser(value = "spring", authorities = {"admin"})
    @Test
    void test10()  throws Exception {
        testRecoveryRoomCreationControllerOK();                
        testRecoveryRoomCreationControllerWrongRoomName();
        testRecoveryRoomCreationControllerWrongSize();
    }

	void testRecoveryRoomCreationControllerOK() throws Exception {
        mockMvc.perform(post("/recoveryroom/create")
                            .with(csrf())
                            .param("name", "Ultralux room")
                            .param("roomType", "room")
                            .param("size", "8.5"))
                .andExpect(status().isOk())
				.andExpect(view().name("welcome"));
    }	
    
    void testRecoveryRoomCreationControllerWrongSize() throws Exception {
        mockMvc.perform(post("/recoveryroom/create")
                            .with(csrf())
                            .param("name", "Ultralux room")
                            .param("roomType", "room")
                            .param("size", "-2.5"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("recoveryRoom"))
				.andExpect(view().name("recoveryroom/createOrUpdateRecoveryRoomForm"));
    }

    
	void testRecoveryRoomCreationControllerWrongRoomName() throws Exception {
        mockMvc.perform(post("/recoveryroom/create")
                            .with(csrf())
                            .param("name", "")
                            .param("roomType", "room")
                            .param("size", "2.5"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("recoveryRoom"))
				.andExpect(view().name("recoveryroom/createOrUpdateRecoveryRoomForm"));
    }
}
