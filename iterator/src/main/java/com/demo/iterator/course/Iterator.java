package com.demo.iterator.course;

/**
 * @author: Maniac Wen
 * @create: 2020/6/12
 * @update: 8:30
 * @version: V1.0
 * @detail:
 **/
public interface Iterator<E> {
    E next();
    boolean hasNext();
}
