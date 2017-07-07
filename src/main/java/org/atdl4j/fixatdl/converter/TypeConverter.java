package org.atdl4j.fixatdl.converter;

import org.atdl4j.fixatdl.model.core.EnumPairT;
import org.atdl4j.fixatdl.model.core.ParameterT;

import java.lang.reflect.Field;

/****
 *
 * @param <E>
 * @param <F>
 */
//TODO clean up when all bugs are fixed
public interface TypeConverter<E extends Comparable<?>, F extends ParameterT> {


    /***
     *
     * @return
     */
    F getParameter();

    /***
     *
     * @return
     */
    String convertParameterConstToFixWireValue();


    /***
     *
     * @param value
     * @return
     */
    Object convertControlValueToParameterValue(Object value);



    /***
     *
     * @param aString
     * @return
     */
    E convertStringToControlValue(String aString);


//    default Object adjustParameterValueForEnumRefValue(Object aParameterValue, ParameterT aParameter, ControlT aControl) {
//        //logger.debug("aParameterValue: " + aParameterValue + " aParameter: " + aParameter + " aControl: " + aControl);
//        if ((aParameterValue != null) && (aParameter != null) && (aControl != null)) {
//            if (aControl instanceof CheckBoxT) {
//                CheckBoxT tempCheckBox = (CheckBoxT) aControl;
//
//                EnumPairT tempCheckedEnumPair = getEnumPairForEnumID(aParameter, tempCheckBox.getCheckedEnumRef());
//                EnumPairT tempUncheckedEnumPair = getEnumPairForEnumID(aParameter, tempCheckBox.getUncheckedEnumRef());
//                String tempParameterValueString = aParameterValue.toString();
//                //logger.debug("tempParameterValueString: " + tempParameterValueString + " tempCheckedEnumPair: " + tempCheckedEnumPair + " tempUncheckedEnumPair: " + tempUncheckedEnumPair);
//
//                if ((tempCheckedEnumPair != null) && (tempParameterValueString.equals(tempCheckedEnumPair.getWireValue()))) {
//                    return Boolean.TRUE.toString();
//                } else if ((tempUncheckedEnumPair != null) && (tempParameterValueString.equals(tempUncheckedEnumPair.getWireValue()))) {
//                    return Boolean.FALSE.toString();
//                }
//            }
//        }
//
//        return aParameterValue;
//    }


    default EnumPairT getEnumPairForEnumID(ParameterT aParameter, String aEnumID) {
        for (EnumPairT enumPair : aParameter.getEnumPair()) {
            if (enumPair.getEnumID().equals(aEnumID)) {
                return enumPair;
            }
        }

        return null;
    }


    default Object getConstFieldOfParam() {
        try {
            Field field = getParameter().getClass().getDeclaredField("constValue");
            field.setAccessible(true);
            return field.get(getParameter());
        } catch (Exception e) {
            return null;
        }
    }

    default Class<?> getParameterDatatype(Class<?> classIfNull) {
        if (getParameter() != null) {

            ParameterT tempParameter = getParameter();

            if (tempParameter != null) {
                return TypeConverterRepo.getParameterDatatype(tempParameter);
            } else {
                return null;
            }
        } else {
            return classIfNull;
        }
    }
}
