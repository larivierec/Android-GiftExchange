package com.clariviere.dev.giftexchange.model;

class PersonEvaluator {
    private Person mPerson = null;

    public PersonEvaluator(Person iPerson){
        mPerson = iPerson;
        evaluate();
    }

    private void evaluate(){
        if(mPerson.getCommunicationAddress().contains("@")){
            mPerson.setPersonMedium(Person.CommunicationMedium.eEmail);
        }
        else if(!mPerson.getCommunicationAddress().contains("@")){
            mPerson.setPersonMedium(Person.CommunicationMedium.eSMS);
        }
        else{
            mPerson.setPersonMedium(Person.CommunicationMedium.eNone);
        }
    }

}
