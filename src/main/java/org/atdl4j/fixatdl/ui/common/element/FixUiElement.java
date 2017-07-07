package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.ControlT;
import javafx.beans.property.ObjectProperty;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public interface FixUiElement<T, K extends Comparable<?>> {

    String NULL_VALUE = "{NULL}";

    /***
     * create the Ui and return depending on type
     *
     * @return
     */
    T create();

    /***
     * Publish the changes by
     *
     * @return
     */
    ObjectProperty<String> listenChange();

    /***
     *
     * @return
     */
    K getValue();

    /***
     *
     * @param k
     */
    void setValue(K k);

    /***
     *
     * @param visible
     */
    void makeVisible(boolean visible);

    /***
     *
     * @param enable
     */
    void makeEnable(boolean enable);

    /***
     *
     *
     * @param parameterTList
     */
    void setParameters(List<ParameterT> parameterTList);

    /****
     *
     * @return
     */
    List<ParameterT> getParameters();

    /***
     *
     * @param <C>
     * @return
     */
    <C extends ControlT> C getControl();

    /***
     *
     */
    void initializeControl();

    /****
     *
     * @param names
     * @return
     */
    default List<ParameterT> findParameterByName(String... names) {
        return this.getParameters() != null ? this.getParameters()
                .stream()
                .filter(parameterT -> {
                    boolean matched = false;
                    // array is faster with for loop so will be used contrary to list
                    for (int i = 0; i < names.length; i++)
                        matched |= parameterT.getName().equals(names[i]);
                    return matched;
                }).collect(Collectors.toList()) : null;
    }


    default void setNullInConstant(ParameterT parameterT) {
        try {
            Field field = parameterT.getClass().getDeclaredField("constValue");
            field.setAccessible(true);
            field.set(parameterT, null);
        } catch (Exception e) {
        }
    }

    /****
     *
     * @param object
     * @param parameterT
     * @return
     */
    default void setFieldValueToParameter(final Object object, ParameterT parameterT) {
        if (getControlTTypeConverter() != null) {
            Object parameterValueAfterConversion = getControlTTypeConverter()
                    .convertControlValueToParameterValue(object);
            if (parameterT != null) {
                try {
                    Field field = parameterT.getClass().getDeclaredField("constValue");
                    field.setAccessible(true);
                    field.set(parameterT, parameterValueAfterConversion);
                } catch (Exception e) {
                    System.out.println("error in setting const");
                }
            }
        }
    }


    String BOOLEAN_FALSE = "N";
    String BOOLEAN_TRUE = "Y";

    SimpleDateFormat hMmSsFormat = new SimpleDateFormat("H:mm:ss");
    SimpleDateFormat hMmFormat = new SimpleDateFormat("H:mm");
    SimpleDateFormat mmDdYyyyFormat = new SimpleDateFormat("MM/dd/yyyy");

    default GregorianCalendar getValue(String string, SimpleDateFormat... args) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        for (SimpleDateFormat simpleDateFormat : args)
            try {
                gregorianCalendar.setTime(simpleDateFormat.parse((String) string));
                return gregorianCalendar;
            } catch (ParseException e) {
            }
        return null;
    }

    TypeConverter<?, ?> getControlTTypeConverter();

}
