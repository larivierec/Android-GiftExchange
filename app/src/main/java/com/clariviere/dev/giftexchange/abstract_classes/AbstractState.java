package com.clariviere.dev.giftexchange.abstract_classes;

import android.content.Context;

import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

import java.util.LinkedList;

public abstract class AbstractState{
    protected Context con;
    protected String textFile = "GiftExchangeSaveState.st";
    protected PersonMapping personMapping;
    protected LinkedList<Person> listOfPeeps;

    public AbstractState(PersonMapping personMapping, LinkedList<Person> p, Context context) {
        this.personMapping = personMapping;
        this.listOfPeeps = p;
        textFile = "GiftExchangeSaveState.st";
        this.con = context;
    }

    public abstract void execute();
}