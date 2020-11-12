package com.jrk.mood4food.model.local;

import android.content.Context;

import java.util.Set;

public class ExampleEntity extends LocalEntity{

    // Attributes have to be protected
    // There are just a few possible DataTypes (See: LocalEntity.validDataTypes)

    protected String exampleString;
    protected int exampleInt;
    protected boolean exampleBoolean;
    protected long exampleLong;
    protected float exampleFloat;
    protected Set<String> exampleStringSet;

    public ExampleEntity(Context context) {
        super(context, ExampleEntity.class, true);
    }

    public String getExampleString() {
        return exampleString;
    }

    public void setExampleString(String exampleString) {
        this.exampleString = exampleString;
    }

    public int getExampleInt() {
        return exampleInt;
    }

    public void setExampleInt(int exampleInt) {
        this.exampleInt = exampleInt;
    }

    public boolean isExampleBoolean() {
        return exampleBoolean;
    }

    public void setExampleBoolean(boolean exampleBoolean) {
        this.exampleBoolean = exampleBoolean;
    }

    public long getExampleLong() {
        return exampleLong;
    }

    public void setExampleLong(long exampleLong) {
        this.exampleLong = exampleLong;
    }

    public float getExampleFloat() {
        return exampleFloat;
    }

    public void setExampleFloat(float exampleFloat) {
        this.exampleFloat = exampleFloat;
    }

    public Set<String> getExampleStringSet() {
        return exampleStringSet;
    }

    public void setExampleStringSet(Set<String> exampleStringSet) {
        this.exampleStringSet = exampleStringSet;
    }
}
