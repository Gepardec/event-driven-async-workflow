package com.gepardec.example.statemachine.util;

import java.util.Comparator;
import java.util.List;

public class ListUtils {

  private ListUtils() {
    //no-op
  }

  public static <T> T getMax(List<T> list, Comparator<T> comparator) {
    return list.stream()
        .max(comparator)
        .orElse(null);
  }
}
