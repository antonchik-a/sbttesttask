package ru.sbt.testtask.android.models;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Valute")
public class Valute {

    @Element(name="Name")
    private String name;

    @Element(name="CharCode")
    private String charCode;

    @Element(name="NumCode")
    private int numCode;

    @Element(name = "Nominal")
    private int nominal;

    @Element(name = "Value")
    private String value;

    public String getName() {
        return name;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNumCode() {
        return numCode;
    }

    public int getNominal() {
        return nominal;
    }

    public Float getValue() {
        return new Float(value.replace(",","."));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Valute createRubleValute(){
        Valute valute = new Valute();
        valute.setName("Российский рубль");
        valute.setNominal(1);
        valute.setValue("1.0");
        valute.setCharCode("RUB");
        valute.setNumCode(743);
        return valute;
    }

    @Override
    public int hashCode() {
        return numCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Valute){
            Valute valute = (Valute) obj;
            return valute.getNumCode() == numCode;
        }
        return super.equals(obj);
    }
}
