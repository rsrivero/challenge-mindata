package com.project.challenge.application.exceptions;

public class HeroNotFound extends Exception{

    @Override
    public String getMessage(){
        return "Hero not found";
    }
}
