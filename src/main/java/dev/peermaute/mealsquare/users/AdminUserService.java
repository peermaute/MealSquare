package dev.peermaute.mealsquare.users;

import dev.peermaute.mealsquare.meals.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    private AdminUserRepository adminUserRepository;

    @Autowired
    public AdminUserService(AdminUserRepository adminUserRepository){
        this.adminUserRepository = adminUserRepository;
    }

    public boolean isAdminUser(String firebaseId){
        //TODO: Only Find by firebaseId - testing!
        List<AdminUser> adminUserList = adminUserRepository.findALlByFirebaseId(firebaseId);
        return adminUserList.size() > 0;
        /*
        for(AdminUser adminUser: adminUserList){
            if(adminUser.getFirebaseId().equals(firebaseId)){
                return true;
            }
        }
        return false;

         */
    }
}
