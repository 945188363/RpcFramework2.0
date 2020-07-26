package com.njh.rpc.RpcServer.Server;

import lombok.Data;

import java.util.List;

/**
 * @ClassName:JavaBean
 * @Author:njh
 * @Description:TODO
 */
@Data
public class JavaBean {

    private String test;
    private String aaa;

    private static int[][] as;

    private static List<Character> charList;

}


class Dog implements Animal{
    private Animal animal;
    public static void main(String[] args) {
        Animal dog = new Dog();

        dog.Jump();
    }

    @Override
    public void Jump() {
        this.animal.Jump();
    }
}

interface Animal{
    void Jump();
}

class Cat implements Animal{

    @Override
    public void Jump() {
        System.out.println("Cat Jump");
    }
}
