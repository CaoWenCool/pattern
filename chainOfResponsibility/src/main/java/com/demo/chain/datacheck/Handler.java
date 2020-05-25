package com.demo.chain.datacheck;

/**
 * @author: Maniac Wen
 * @create: 2020/5/23
 * @update: 7:38
 * @version: V1.0
 * @detail:
 **/
public abstract class Handler<T> {
    protected Handler chainHandler;
    public  void next(Handler handler){
        this.chainHandler = handler;
    }
    public abstract void doHandler(User user);
    public static class Builder<T>{
        private Handler<T> head;
        private Handler<T> tail;
        public Builder<T> addHandler(Handler<T> handler){
            if(this.head == null){
                this.head = this.tail = handler;
                return this;
            }
            this.tail.next(handler);
            this.tail = handler;
            return this;
        }
        public Handler<T> build(){
            return this.head;
        }
    }
}
