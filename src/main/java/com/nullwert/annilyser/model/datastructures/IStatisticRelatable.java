package com.nullwert.annilyser.model.datastructures;

import java.util.List;

public interface IStatisticRelatable extends IStatistic {
    List<IStatistic> getRelations();
}
