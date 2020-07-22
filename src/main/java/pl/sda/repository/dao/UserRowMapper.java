package pl.sda.repository.dao;

import org.springframework.jdbc.core.RowMapper;
import pl.sda.repository.SdaUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<SdaUser> {
    @Override
    public SdaUser mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return new SdaUser(
                resultSet.getString("PESEL"),
                resultSet.getString("NAME"),
                resultSet.getString("ASSIGNED_COURSE"),
                resultSet.getDouble("PRICE"),
                resultSet.getBoolean("PAYED"));
    }
}
