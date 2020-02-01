package com.lunatech.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FieldMapper {

    private Map<String,String> mapOfFormFieldWithValue ;

    public FieldMapper(){
        this.mapOfFormFieldWithValue = new HashMap<>();
    }

    public static FieldMapper parse(FormDTO formDTO){
        FieldMapper fm = new FieldMapper();
        fm.mapOfFormFieldWithValue.putAll(formDTO.getFieldValues());
        return fm;
    }

    public Optional<String> getValue(String fieldName){
        return Optional.ofNullable(mapOfFormFieldWithValue.get(fieldName));
    }
}
