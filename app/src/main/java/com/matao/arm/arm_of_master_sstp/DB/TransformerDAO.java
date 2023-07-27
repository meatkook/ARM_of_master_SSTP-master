package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.models.Transformer;
import com.matao.arm.arm_of_master_sstp.models.TransformerTypes;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TransformerDAO extends BaseDaoImpl<Transformer, Integer> {

   TransformerDAO(ConnectionSource connectionSource,
                  Class<Transformer> dataClass) throws SQLException {
       super(connectionSource, dataClass);
   }

   public List<Transformer> getAll() {
       try {
           return this.queryForAll();
       } catch (SQLException e) {
           e.printStackTrace();
           throw new Error(e);
       }
   }

    public Transformer getItem(Integer id) {
        try {

            return this.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Transformer> getFiltered(Date start, Date end, Integer lpsId) {
        PreparedQuery<Transformer> query = null;
        try {
            query = queryBuilder().orderBy(Transformer.PLAN_DATE, true).where()
                    .isNotNull(Transformer.PLAN_DATE)
                    .and().between(Transformer.PLAN_DATE, HelperFactory.dateFormat.format(start), HelperFactory.dateFormat.format(end))
                    .and().eq(Transformer.LPS_ID, lpsId)
                    .prepare();
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Transformer> getDinamicFiltered(String filter){
        PreparedQuery<Transformer> query = null;
        try {
            if(filter.isEmpty()){
                query = queryBuilder().orderBy(Transformer.LPS_ID, true)
                        .prepare();
                return query(query);
            }else {

                QueryBuilder<LPS, Integer> queryLps = HelperFactory.getHelper().getLpsDao().queryBuilder();
                QueryBuilder<TransformerTypes, Integer> queryType = HelperFactory.getHelper().getTransformerTypeDao().queryBuilder();

                queryLps.where().like(LPS.NAME, "%" + filter + "%");
                queryType.where().like(TransformerTypes.NAME, "%" + filter + "%");

                query = queryBuilder().joinOr(queryLps).joinOr(queryType).where()
                        .like(Transformer.NAME,"%"+filter+"%")
                        .or().like("ratio","%"+filter+"%")
                        .or().like("nomber","%"+filter+"%")
                        .prepare();
                return query(query);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new Error(e);
        }

    }
}