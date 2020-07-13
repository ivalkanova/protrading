package com.trading.protrading.model;

import com.trading.protrading.data.strategy.Predicate;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "conditions")
public class Condition {

    @Id
    @GeneratedValue
    private Long id;
    private double assetPrice;
    private Predicate predicate;

    public Condition() {}

    public Condition(double assetPrice, Predicate predicate) {
        this.assetPrice = assetPrice;
        this.predicate = predicate;
    }

    public boolean checkPredicate(double price) {
        switch (predicate) {
            case LESS_THAN:
                return price < assetPrice;
            case LESS_OR_EQUAL:
                return price <= assetPrice;
            case GREATER_THAN:
                return price > assetPrice;
            case GREATER_OR_EQUAL:
                return price >= assetPrice;
            default:
                throw new IllegalStateException("Unexpected value: " + predicate);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return Double.compare(condition.assetPrice, assetPrice) == 0 &&
                Objects.equals(id, condition.id) &&
                predicate == condition.predicate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assetPrice, predicate);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", assetPrice=" + assetPrice +
                ", predicate=" + predicate +
                '}';
    }
}