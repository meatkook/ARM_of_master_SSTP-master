package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.TransformerTypes;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TransformerTypeDAO extends BaseDaoImpl<TransformerTypes, Integer> {

    TransformerTypeDAO(ConnectionSource connectionSource,
           Class<TransformerTypes> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<TransformerTypes> getAllTransformerTypes() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}