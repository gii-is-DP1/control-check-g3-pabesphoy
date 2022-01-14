package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test5 {

    @Autowired(required = false)
    RecoveryRoomService rs;

    @Test public void test5(){

        recoveryRoomServiceIsInjected();
        recoveryRoomServiceCanGetRooms();
    }
    
    public void recoveryRoomServiceIsInjected()
    {
        assertNotNull(rs,"RecoveryRoomService was not injected by spring");       
    }

    public void recoveryRoomServiceCanGetRooms(){
        assertNotNull(rs,"RecoveryRoomService was not injected by spring");
        List<RecoveryRoom> rooms=rs.getAll();
        assertNotNull(rooms,"The list of rooms found by the roomservice was null");
        assertFalse(rooms.isEmpty(),"The list of rooms found by the roomservice was empty");       
    }
}
