package pl.sda.repository.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.sda.repository.SdaUser;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class UserDao {

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
        }
        else
        {
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

        List<SdaUser> result = jdbcTemplate.query("select PESEL, NAME, ASSIGNED_COURSE, PRICE, PAYED from SDA_USER",
                (rs, num) -> new SdaUser(rs.getString("pesel"),
                        rs.getString("name"),
                        rs.getString("ASSIGNED_COURSE"),
                        rs.getDouble("price"),
                        rs.getBoolean("payed")));
        log.info("readAllUsers [{}]", result);
        return result;
    }

    public Optional<SdaUser> findUserByPesel(String userPesel) {
        log.info("Finding user with pesel: {}", userPesel);
        //SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("pesel", userPesel);
        Optional<SdaUser> result = Optional.ofNullable(jdbcTemplate.queryForObject("select PESEL, NAME, ASSIGNED_COURSE, PRICE, PAYED from SDA_USER where PESEL = ?", new Object[]{userPesel}, new UserRowMapper()));
        log.info("Found user: [{}]", result);
        return result;
    }

    public boolean removeUserByPesel(String userPesel) {
        log.info("Removing user with pesel: {}", userPesel);
        Optional<SdaUser> userToRemove = findUserByPesel(userPesel);
        if (userToRemove.isPresent()) {
            jdbcTemplate.update("delete from SDA_USER where PESEL = ?", userPesel);
            log.info("User with pesel [{}] removed from database!", userPesel);
            return true;
        }
        else
            log.warn("Nothing to remove!");
            return false;

    }
}
