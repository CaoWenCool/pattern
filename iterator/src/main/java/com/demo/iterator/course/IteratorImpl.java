package com.demo.iterator.course;
import java.util.List;
/**
 * @author: Maniac Wen
 * @create: 2020/6/12
 * @update: 8:32
 * @version: V1.0
 * @detail:
 **/
public class IteratorImpl<E> implements Iterator<E> {
    private List<E> list;
    private int cursor;
    private E  element;

    public IteratorImpl(List<E> list) {
        this.list = list;
    }

    public E next() {
        System.out.print("当前位置" + cursor + ":");
        element = list.get(cursor);
        cursor ++;
        return element;
    }

    public boolean hasNext() {
        if(cursor > list.size() -1){
            return false;
        }
        return true;
    }
}
