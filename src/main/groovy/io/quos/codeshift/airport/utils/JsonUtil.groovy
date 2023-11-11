package io.quos.codeshift.airport.utils

import com.fasterxml.jackson.databind.ObjectMapper

class JsonUtil {
    public static Object toObjectFromString(String json, Class clazz){
        ObjectMapper om = new ObjectMapper()
        return om.readValue(json,clazz)
    }

    public static String toStringFromObject(Object obj){
        ObjectMapper om = new ObjectMapper()
        return om.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
    }
}
