package com.ring.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ring.exceptions.RingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * class implementing TyrePressureDao.
 * 
 * @author ee209986
 *
 */

@Repository
public class TyrePressureDaoImpl implements TyrePressureDao {

  @Autowired
  MongoTemplate mongoTemplate;

  /**
   * method to get lastInflated days of each tyre .
   */
  @Override
  public List<DBObject> getLatestPressureData(int userId, int vehicleId)
      throws RingException {
    Aggregation aggregationOperations = newAggregation(
        match(where("userId").is(userId)
            .and("vehicleId").is(vehicleId)),
        group("tyreType").max("updatedTime").as("lastUpdatedTime"));
    return mongoTemplate.aggregate(aggregationOperations, "AirPressureData",
        DBObject.class).getMappedResults();

  }

  @Override
  public List<DBObject> getTyrePressureData(int userId, int vehicleId,
      long fromEpochDay, long toEpochDay, String tyreType) {
    Aggregation aggregationOperations = Aggregation.newAggregation(
        match(where("userId").is(userId).and("vehicleId")
            .is(vehicleId).and("tyreType").is(tyreType)),
        getGroupAggregationObject(),
        project().and("_id").as("date").andInclude(
            "inflationEndPressure",
            "inflationStartPressure").andExclude("_id"),
        match(where("date").gte(fromEpochDay).lte(toEpochDay)),
        sort(Direction.ASC, "date"));
    return mongoTemplate.aggregate(aggregationOperations, "AirPressureData",
        DBObject.class).getMappedResults();
  }

  private AggregationOperation getGroupAggregationObject() {
    DBObject groupAggExpObj = new BasicDBObject();

    DBObject groupAggComponent = new BasicDBObject();
    groupAggComponent.put("_id", new BasicDBObject("$floor", new BasicDBObject(
        "$divide",
        new Object[]{new BasicDBObject("$toLong", "$updatedTime"), 86400})));
    groupAggComponent.put("inflationStartPressure",
        new BasicDBObject("$min", "$currentPressureValue"));
    groupAggComponent.put("inflationEndPressure",
        new BasicDBObject("$max", "$currentPressureValue"));

    groupAggExpObj.put("$group", groupAggComponent);

    return (AggregationOperationContext context) -> groupAggExpObj;

  }

}
