package com.nullwert.annilyser.model.datastructures;

import java.util.List;

public interface IRelatable<T extends IRelation> {
    List<IRelation> getRelations();
}
