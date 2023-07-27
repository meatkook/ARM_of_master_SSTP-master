package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.Extinguisher;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ExtinguisherDAO extends BaseDaoImpl<Extinguisher, Integer> {

   ExtinguisherDAO(ConnectionSource connectionSource,
                   Class<Extinguisher> dataClass) throws SQLException {
       super(connectionSource, dataClass);
   }

    public Extinguisher getItem(Integer id) {
        try {
            return this.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

   public List<Extinguisher> getAll() {
       try {
           return this.queryForAll();
       } catch (SQLException e) {
           e.printStackTrace();
           throw new Error(e);
       }
   }

    public List<Extinguisher> getFiltered(Date start, Date end, Integer lpsId) {
        PreparedQuery<Extinguisher> query = null;
        try {
            query = queryBuilder().orderBy(Extinguisher.PLAN_DATE, true).where()
                    .isNotNull(Extinguisher.PLAN_DATE)
                    .and().between(Extinguisher.PLAN_DATE, HelperFactory.dateFormat.format(start), HelperFactory.dateFormat.format(end))
                    .and().eq(Extinguisher.LPS_ID, lpsId)
                    .prepare();
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Extinguisher> getDinamicFiltered(String filter){
        PreparedQuery<Extinguisher> query = null;
        try {
            if(filter.isEmpty()){
                query = queryBuilder().orderBy("stock_number", true)
                        .prepare();
                return query(query);
            }else {

                QueryBuilder<LPS, Integer> queryLps = HelperFactory.getHelper().getLpsDao().queryBuilder();
                queryLps.where().like(LPS.NAME, "%" + filter + "%");

                query = queryBuilder().joinOr(queryLps).where()
                        .like("name", "%" + filter+"%")
                        .or().like("stock_number", "%" + filter + "%")
                        .or().like("verification", "%" + filter + "%")
                        .or().like("next_verification", "%" + filter + "%")
                        .prepare();
            }
            return query(query);
        } catch (SQLException e){
            e.printStackTrace();
            throw new Error(e);
        }
    }

}