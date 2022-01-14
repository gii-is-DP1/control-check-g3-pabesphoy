package org.springframework.samples.petclinic.recoveryroom;

import java.util.List;
import java.util.Optional;

public interface RecoveryRoomRepository {
    List<RecoveryRoom> findAll();
    //List<RecoveryRoomType> findAllRecoveryRoomTypes();
    Optional<RecoveryRoom> findById(int id);
    RecoveryRoom save(RecoveryRoom p);
    //RecoveryRoomType getRecoveryRoomType(String name);
    //List<RecoveryRoom> findBySizeMoreThan(double size);
}
