package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test3 {
    @Autowired
    RecoveryRoomRepository rrr;
    
    @Test
    public void test3(){
        testInitialRecoveryRooms();
        testInitialRecoveryRoomTypes();
    }
    
    public void testInitialRecoveryRooms(){
        List<RecoveryRoom> rooms=rrr.findAll();
        assertTrue(rooms.size()==2, "Exactly two rooms should be present in the DB");

        Optional<RecoveryRoom> p1=rrr.findById(1);
        assertTrue(p1.isPresent(),"There should exist a room with id:1");
        assertEquals(p1.get().getName(),"Big room for dangerous animals","The name of the room with id:1 should be 'Big room for dangerous animals'");
        assertEquals(p1.get().getSize(),6.50,"The size of the room with id:1 should be 6.50");
        assertTrue(p1.get().isSecure(), "The room with id:1 should be secure");

        Optional<RecoveryRoom> p2=rrr.findById(2);
        assertTrue(p2.isPresent(),"There should exist a room with id:2");
        assertEquals(p2.get().getName(),"Medium box","The name of the room with id:2 should be 'Medium box'");
        assertEquals(p2.get().getSize(),1.50,"The size of the room with id:2 should be 1.50");
        assertFalse(p2.get().isSecure(), "The room with id:2 should not be secure");
    }

    public void testInitialRecoveryRoomTypes()
    {
        List<RecoveryRoomType> roomTypes=rrr.findAllRecoveryRoomTypes();
        assertTrue(roomTypes.size()==2,"Exactly two room types should be present in the DB");

        for(RecoveryRoomType v:roomTypes)
        {
            if(v.getId()==1){
                assertEquals(v.getName(),"room","The name of the room type with id:1 should be 'room'");
            }else if(v.getId()==2){
                assertEquals(v.getName(),"box","The name of the room type with id:2 should be 'box'");
            }else
                fail("The id of the room types should be either 1 or 2");
        }
    }
}
