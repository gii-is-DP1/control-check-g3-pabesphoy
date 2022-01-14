package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test1 {

    @Autowired(required = false)
    RecoveryRoomRepository rr;

    @Test
    public void test1(){
        repositoryExists();
        repositoryContainsMethod();
        testConstraints();
    }

    
    public void repositoryExists(){
        assertNotNull(rr,"The repository was not injected into the tests, its autowired value was null");
    }

    
    public void repositoryContainsMethod(){
        if(rr!=null){
            Optional<RecoveryRoom> v=rr.findById(12);
            assertFalse(v.isPresent(),"No result (null) should be returned for a vaccine that does not exist");
        }else
            fail("The repository was not injected into the tests, its autowired value was null");
    }

     void testConstraints(){
        RecoveryRoom room=new RecoveryRoom();
        room.setId(7);
        room.setName("Comfy open bed");
        room.setSize(-10.50);
        assertThrows(ConstraintViolationException.class,() -> rr.save(room),
        "You are not constraining "+
        "room size to positive values");
        room.setSize(10.0);
        room.setName("ja");
        assertThrows(ConstraintViolationException.class,() -> rr.save(room),
        "You are not constraining name size to more than 3 characters"
        );
        room.setName("En un lugar de la mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor.");
        assertThrows(ConstraintViolationException.class,() -> rr.save(room),
        "you are not constraining name size to less than 51 characters");
    }

}
