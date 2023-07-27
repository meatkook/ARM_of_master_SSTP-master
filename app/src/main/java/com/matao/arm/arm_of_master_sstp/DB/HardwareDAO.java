package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.models.Transformer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HardwareDAO extends BaseDaoImpl<Hardware, Integer> {

    HardwareDAO(ConnectionSource connectionSource,
                Class<Hardware> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Hardware> getAllHardware() {
        PreparedQuery<Hardware> query = null;
        try {
            query = queryBuilder().orderBy(Hardware.LPS_ID, true)
                    .prepare();
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public Hardware getItem(Integer id) {
        try {
            return this.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }


    public List<Hardware> getHardwarePlan(Date start, Date end, Integer lpsId) {
        PreparedQuery<Hardware> query = null;
        try {
            query = queryBuilder().where()
                    .isNotNull(Hardware.PLAN_DATE)
                    .and().between(Hardware.PLAN_DATE, HelperFactory.dateFormat.format(start), HelperFactory.dateFormat.format(end))
                    .and().eq(Hardware.LPS_ID, lpsId)
                    .prepare();
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Hardware> getHardwareLps(Integer lpsId) {
        PreparedQuery<Hardware> query = null;
        try {
            query = queryBuilder().where()
                    .eq(Hardware.LPS_ID, lpsId)
                    .prepare();
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Hardware> getDinamicFiltered(String filter){
        PreparedQuery<Hardware> query = null;
        try {
            if(filter.isEmpty()){
                query = queryBuilder().orderBy(Hardware.LPS_ID, true)
                        .prepare();
                return query(query);
            }else {
                QueryBuilder<LPS, Integer> queryLps = HelperFactory.getHelper().getLpsDao().queryBuilder();
                queryLps.where().like(LPS.NAME, "%" + filter + "%");

                query = queryBuilder().joinOr(queryLps).where()
                        .like(Transformer.NAME, "%"+filter+"%").prepare();
                return query(query);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new Error(e);
        }

    }
}