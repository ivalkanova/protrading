package com.trading.protrading.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConditionTest {


    @Test(expected = IllegalStateException.class)
    public void testCheckPredicateWithInvalidPredicateValue(){
        Condition condition = new Condition(22.3,"@@");
        condition.checkPredicate(20);
    }

    @Test
    public void testCheckPredicateWithPredicateLowerThan(){
        Condition condition = new Condition(22.3,"<");
        assertTrue(condition.checkPredicate(20));
        assertFalse(condition.checkPredicate(22.4));
    }

    @Test
    public void testCheckPredicateWithPredicateLowerThanOrEquals(){
        Condition condition = new Condition(22.3,"<=");
        assertTrue(condition.checkPredicate(20));
        assertTrue(condition.checkPredicate(22.3));
        assertFalse(condition.checkPredicate(22.35));
    }

    @Test
    public void testCheckPredicateWithPredicateHigherThan(){
        Condition condition = new Condition(22.3,">");
        assertTrue(condition.checkPredicate(22.4));
        assertFalse(condition.checkPredicate(20));
    }

    @Test
    public void testCheckPredicateWithPredicateHigherThanOrEquals(){
        Condition condition = new Condition(22.3,">=");
        assertFalse(condition.checkPredicate(20));
        assertTrue(condition.checkPredicate(22.4));
        assertTrue(condition.checkPredicate(22.4));
    }
}