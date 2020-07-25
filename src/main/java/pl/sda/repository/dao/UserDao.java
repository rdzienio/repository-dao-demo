package pl.sda.repository.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.sda.repository.SdaUser;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDao {

    private static final String SELECT_ALL_QUERY = "select PESEL, NAME, ASSIGNED_COURSE, PRICE, PAYED from SDA_USER";
    private static final String USER_SELECT_QUERY = SELECT_ALL_QUERY + " where PESEL = ?";

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //create / update
    public SdaUser saveUser(SdaUser sdaUser) {
        log.info("Save user [{}]", sdaUser);
        if (findUserByPesel(sdaUser.getPesel()).isEmpty()) {
            jdbcTemplate.update("INSERT INTO SDA_USER (PESEL, NAME, ASSIGNED_COURSE, PRICE, PAYED) VALUES (?,?,?,?,?)",
                    sdaUser.getPesel(),
                    sdaUser.getName(),
                    sdaUser.getAssignedCourse(),
                    sdaUser.getPesel(),
                    sdaUser.isPayed());
        } else {
            jdbcTemplate.update("update SDA_USER set NAME = ?, ASSIGNED_COURSE = ?, PRICE = ?, PAYED = ? where PESEL = ?",
                    sdaUser.getName(),
                    sdaUser.getAssignedCourse(),
                    sdaUser.getPesel(),
                    sdaUser.isPayed(),
                    sdaUser.getPesel());
            log.info("User updated!");
        }
        return sdaUser;
    }

    public List<SdaUser> readAllUsers() {
        List<SdaUser> result = jdbcTemplate.query(SELECT_ALL_QUERY, new UserRowMapper());
        log.info("readAllUsers [{}]", result);
        return result;
    }

    public Optional<SdaUser> findUserByPeselAndName(String userPesel, String userName) {
        var result = jdbcTemplate.queryForObject(USER_SELECT_QUERY + " and name = ?", new UserRowMapper(), userPesel, userName);
        return Optional.of(result);
    }

    public Optional<SdaUser> findUserByPesel(String userPesel) {
        log.info("Finding user with pesel: {}", userPesel);
        SdaUser result;
        try {
            result = jdbcTemplate.queryForObject(
                    USER_SELECT_QUERY,
                    new UserRowMapper(), userPesel);
        } catch (DataAccessException dae) {
            log.warn("User not found! {}", dae.getMessage());
            return Optional.empty();
        }
        log.info("Found user: [{}]", result);
        return Optional.ofNullable(result);
    }

    public boolean removeUserByPesel(String userPesel) {
        log.info("Removing user with pesel: {}", userPesel);
        Optional<SdaUser> userToRemove = findUserByPesel(userPesel);
        if (userToRemove.isPresent()) {
            jdbcTemplate.update("delete from SDA_USER where PESEL = ?", userPesel);
            log.info("User with pesel [{}] removed from database!", userPesel);
            return true;
        } else
            log.warn("Nothing to remove!");
        return false;

    }
}
