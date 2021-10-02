package com.shaman.labka;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dictionary<X,Y> extends LinkedHashMap<X,Y> {

    public X getKeyAt(int index){

        LinkedHashMap <X,Y> hs = this;
        X result=null;
        int pos=0;
        for(Map.Entry<X, Y> entry : hs.entrySet())
        {
            if(index==pos){
                result= entry.getKey();
            }
            pos++;
        }
        return  result;
    }

    public Y getValueByKey( X key) {
        LinkedHashMap <X,Y> hs = this;
        Y result=null;
        for(Map.Entry<X, Y> entry : hs.entrySet())
        {
            if(key==entry.getKey()){
                result= entry.getValue();
            }
        }
        return  result;
    }
}
