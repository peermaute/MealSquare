package dev.peermaute.mealsquare.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserRepository extends MongoRepository<AdminUser, String> {
    List<AdminUser> findALlByFirebaseId(String firebaseId);
}
