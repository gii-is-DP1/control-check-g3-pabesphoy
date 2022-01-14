package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class Test2 {
    @Autowired(required = false)
    RecoveryRoomRepository rrr;
    @Autowired(required = false)
    EntityManager em;
    
    @Test
    public void test2(){
        entityExists();
        repositoryContainsMethod();
        testConstraints();
    }

   
    public void entityExists()
    {
        RecoveryRoomType p=new RecoveryRoomType();
        p.setId(7);
        p.setName("Ginormous Room");        
    }  

    
    public void repositoryContainsMethod(){
        try {
            Method findAllRecoveryRoomTypes = RecoveryRoomRepository.class.getDeclaredMethod("findAllRecoveryRoomTypes");
            if(rrr!=null){
                List<RecoveryRoomType> pts= (List<RecoveryRoomType>) findAllRecoveryRoomTypes.invoke(rrr);
                assertNotNull(pts,"We can not query all the recovery room types through the repository");
            }else
                fail("The repository was not injected into the tests, its autowired value was null");
        } catch(NoSuchMethodException e) {
            fail("There is no method findAllRecoveryRoomTypes in RecoveryRoomRepository", e);
        } catch (IllegalAccessException e) {
            fail("There is no public method findAllAllRecoveryRoomTypes in RecoveryRoomRepository", e);
        } catch (IllegalArgumentException e) {
            fail("There is no method findAllAllRecoveryRoomTypes() in RecoveryRoomRepository", e);
        } catch (InvocationTargetException e) {
            fail("There is no method findAllAllRecoveryRoomTypes() in RecoveryRoomRepository", e);
        }
        if(rrr!=null){
            List<RecoveryRoomType> pts=rrr.findAllRecoveryRoomTypes();
            assertNotNull(pts,"We can not query all the recovery room types through the repository");
        }else
            fail("The repository was not injected into the tests, its autowired value was null");
    }

    
    public void testConstraints(){
        
        RecoveryRoomType roomType=new RecoveryRoomType();       
        roomType.setName("ja");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        assertFalse(validator.validate(roomType).isEmpty());
        roomType.setName("En un lugar de la mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor.");
        assertFalse(validator.validate(roomType).isEmpty());
        roomType.setName("prueba");
        em.persist(roomType);
        
        RecoveryRoomType rrt2=new RecoveryRoomType();       
        rrt2.setName("prueba");
        assertThrows(Exception.class,()->em.persist(rrt2),"Debería lanzarse una excepción al intentar grabar un tipo de habitación con nombre ya existente");
        
    }

}
