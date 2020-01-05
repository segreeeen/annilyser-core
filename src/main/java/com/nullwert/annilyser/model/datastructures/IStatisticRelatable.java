package com.nullwert.annilyser.model.datastructures;

import java.util.List;

public interface IStatisticRelatable extends IStatistic {
    default List<IStatistic> getRelations() {
        return null;
    }
}
