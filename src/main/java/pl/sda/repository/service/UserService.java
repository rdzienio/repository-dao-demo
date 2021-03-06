package pl.sda.repository.service;

import org.springframework.stereotype.Service;
import pl.sda.repository.SdaUser;
import pl.sda.repository.dao.UserDao;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<SdaUser> getAllUsers(){
        return userDao.readAllUsers();
    }

    public SdaUser getUserByPesel(String userPesel){
        return userDao.findUserByPesel(userPesel).orElse(new SdaUser());
    }

    public SdaUser saveUser(SdaUser sdaUser){
        return userDao.saveUser(sdaUser);
    }

    public SdaUser getUserByPeselAndName(String userPesel, String userName){
        return userDao.findUserByPeselAndName(userPesel, userName).orElse(new SdaUser());
    }


}
