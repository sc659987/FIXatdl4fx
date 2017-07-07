package org.atdl4j.fixatdl.validator;

import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.utils.CachedMap;

import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParameterNameToConstValueCachedMap implements CachedMap {

    private Map<String, ParameterT> stringParameterTMap;

    @Override
    public Comparable get(String key) {
        if (key == null)
            return null;
        ParameterT parameterT = this.stringParameterTMap.get(key);
        try {
            Field field = parameterT.getClass().getDeclaredField("constValue");
            field.setAccessible(true);
            Object obj = field.get(parameterT);
            if (obj instanceof XMLGregorianCalendar)
                return new ComparableXMLGregorianCalender((XMLGregorianCalendar) obj);
            return (Comparable) obj;
        } catch (Exception e) {
            // NullPointerException e
            return null;
        }
    }


    class ComparableXMLGregorianCalender implements Comparable<ComparableXMLGregorianCalender> {

        private XMLGregorianCalendar calendar;

        ComparableXMLGregorianCalender(XMLGregorianCalendar xmlGregorianCalendar) {
            this.calendar = xmlGregorianCalendar;
        }

        @Override
        public int compareTo(ComparableXMLGregorianCalender otherCalender) {
            return calendar.compare(otherCalender.calendar);
        }
    }

    public ParameterNameToConstValueCachedMap(List<ParameterT> parameterTS) {
        stringParameterTMap = parameterTS
                .parallelStream()
                .collect(Collectors.toMap(ParameterT::getName, parameterT -> parameterT));
    }

}
