package com.staekframework.di.classinfo;


public class FortyTwoStudent  extends Student implements Person {
    private int cardinalNumber;
    private String intraId;

    public FortyTwoStudent(int cardinalNumber, String intraId) {
        this.cardinalNumber = cardinalNumber;
        this.intraId = intraId;
    }

    public int getCardinalNumber() {
        return cardinalNumber;
    }

    public void setCardinalNumber(int cardinalNumber) {
        this.cardinalNumber = cardinalNumber;
    }

    public String getIntraId() {
        return intraId;
    }

    public void setIntraId(String intraId) {
        this.intraId = intraId;
    }
}
