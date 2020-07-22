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


}
