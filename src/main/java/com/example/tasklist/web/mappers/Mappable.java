package com.example.tasklist.web.mappers;

import java.util.List;

public interface Mappable<E, D> {
  D entityToDto(E entity);

  List<D> entityToDto(List<E> entity);

  E dtoToEntity(D dto);

  List<E> dtoToEntity(List<D> dto);
}
