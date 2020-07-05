package com.trading.protrading.model;

import com.trading.protrading.data.strategy.Predicate;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConditionTest {
    public static final double ASSET_PRICE = 22.3;


//    @Test(expected = IllegalStateException.class)
//    public void testCheckPredicateWithInvalidPredicateValue(){
//        Condition condition = new Condition(22.3,null);
//        condition.checkPredicate(20);
//    }

    @Test
    public void testCheckPredicateWithPredicateLowerThan(){
        Condition condition = new Condition(ASSET_PRICE, Predicate.LESS_THAN);
        assertTrue(condition.checkPredicate(20));
        assertFalse(condition.checkPredicate(22.3));
    }

    @Test
    public void testCheckPredicateWithPredicateLowerThanOrEquals(){
        Condition condition = new Condition(ASSET_PRICE,Predicate.LESS_OR_EQUAL);
        assertTrue(condition.checkPredicate(20));
        assertTrue(condition.checkPredicate(22.3));
        assertFalse(condition.checkPredicate(22.35));
    }

    @Test
    public void testCheckPredicateWithPredicateHigherThan(){
        Condition condition = new Condition(ASSET_PRICE,Predicate.GREATER_THAN);
        assertTrue(condition.checkPredicate(22.4));
        assertFalse(condition.checkPredicate(20));
    }

    @Test
    public void testCheckPredicateWithPredicateHigherThanOrEquals(){
        Condition condition = new Condition(ASSET_PRICE,Predicate.GREATER_OR_EQUAL);
        assertFalse(condition.checkPredicate(20));
        assertTrue(condition.checkPredicate(22.4));
        assertTrue(condition.checkPredicate(22.4));
    }
}