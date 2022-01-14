package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.persistence.ManyToOne;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.pet.Visit;
import org.springframework.samples.petclinic.pet.VisitRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test4 {
    @Autowired
    RecoveryRoomRepository rrr;
    @Autowired
    VisitRepository vr;
    

    @Test
    @DisplayName("The DB is initialized with two rooms associated to two room types and at least 1 visit")
    public void test4() {
        testAnnotations();
        testInitialRoomsLinked();
    }

        
    public void testInitialRoomsLinked(){
    
        Field roomType;
        RecoveryRoomType rt;

        try {
            roomType = RecoveryRoom.class.getDeclaredField("roomType");
            roomType.setAccessible(true);
            Optional<RecoveryRoom> v1=rrr.findById(1);
            assertTrue(v1.isPresent(),"RecoveryRoom with id:1 not found");
            rt = (RecoveryRoomType) roomType.get(v1.get());
            assertNotNull(rt,"The RecoveryRoom with id:1 has not a RecoveryRoomType associated");
            assertEquals("room",rt.getName(),"The name of the room type associated to the room with id:1 is not 'room'");

            Optional<RecoveryRoom> v2=rrr.findById(2);
            assertTrue(v2.isPresent(),"RecoveryRoom with id:2 not found");
            rt = (RecoveryRoomType) roomType.get(v2.get());
            assertNotNull(rt,"The RecoveryRoom with id:2 has not a RecoveryRoomType associated");
            assertEquals("box", rt.getName(),"The name of the room type associated to the room with id:2 is not 'box'");
        } catch (NoSuchFieldException e) {
            fail("The RecoveryRoom class should have an attribute called recoveryRoomType that is not present: "+e.getMessage());
        } catch (IllegalArgumentException e) {
            fail("The RecoveryRoom class should have an attribute called recoveryRoomType that is not present: "+e.getMessage());
        } catch (IllegalAccessException e) {
            fail("The RecoveryRoom class should have an attribute called recoveryRoomType that is not present: "+e.getMessage());
        }

        
        List<Visit> visits = vr.findAll();
        boolean hasRoomAssociated = false;
        for (Visit v : visits) {
            if (v.getRecoveryRoom()!=null) {
                hasRoomAssociated = true;
                break;
            }
        }
        assertTrue(hasRoomAssociated, "There is no visit associated with a room");
    }

    void testAnnotations(){
        try{
            Field roomType=RecoveryRoom.class.getDeclaredField("roomType");
            ManyToOne annotationManytoOne=roomType.getAnnotation(ManyToOne.class);
            assertNotNull(annotationManytoOne,"The roomType property is not properly annotated");
            assertFalse(annotationManytoOne.optional(),"The roomType property is not properly annotated");
        }catch(NoSuchFieldException ex){
            fail("The RecoveryRoom class should have a field that is not present: "+ex.getMessage());
        }
    }
}
