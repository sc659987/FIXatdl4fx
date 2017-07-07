package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.EnumPairT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.ListItemT;
import org.atdl4j.fixatdl.model.layout.SingleSelectListT;
import org.atdl4j.fixatdl.ui.common.element.FixSingleSelectListUiElement;
import org.atdl4j.fixatdl.ui.fx8.FxUtils;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class FxFixSingleSelectListUiElement
        implements FixSingleSelectListUiElement<Pane, String> {

    private GridPane gridPane;
    private ListView<ListItemT> singleSelectListView;
    private SingleSelectListT singleSelectListT;
    private ParameterT parameterT;
    private int nextRow = 0;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private TypeConverter<?, ?> controlTTypeConverter;

    @Override
    public Pane create() {
        if (this.singleSelectListT != null) {
            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            this.gridPane = new GridPane();
            if (!Utils.isEmptyString(this.singleSelectListT.getLabel())) {
                this.gridPane.getColumnConstraints().addAll(FxUtils.getOneColumnWidthForGridPane());
                this.gridPane.add(new Label(this.singleSelectListT.getLabel()), 0, this.nextRow++);
            }
            this.singleSelectListView = new ListView<>(FXCollections
                    .observableArrayList(this.singleSelectListT.getListItem()));
            this.singleSelectListView.getSelectionModel()
                    .setSelectionMode(SelectionMode.SINGLE);

            // initialize the single select list with initialize value
            initializeControl();

            this.singleSelectListView.setOnMouseClicked(event -> {
                setValue(this.singleSelectListView.getItems().get(
                        singleSelectListView.getSelectionModel().getSelectedIndices().get(0)).getEnumID());
                controlIdEmitter.set(singleSelectListT.getID() + ":" + getValue());
            });
            this.gridPane.add(this.singleSelectListView, 0, this.nextRow);
            return this.gridPane;
        }
        return null;
    }

    @Override
    public void initializeControl() {
        if (Utils.isNonEmptyString(this.singleSelectListT.getInitValue()))
            setValue(this.singleSelectListT.getInitValue());
    }

    @Override
    public void setSingleSelectList(SingleSelectListT singleSelectListT) {
        this.singleSelectListT = singleSelectListT;
    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        if (parameterTList != null && parameterTList.size() > 0)
            this.parameterT = parameterTList.get(0);
    }

    @Override
    public List<ParameterT> getParameters() {
        List<ParameterT> parameterTS = Collections.emptyList();
        parameterTS.add(this.parameterT);
        return parameterTS;
    }

    @Override
    public ObjectProperty<String> listenChange() {
        return this.controlIdEmitter;
    }

    @Override
    public SingleSelectListT getControl() {
        return this.singleSelectListT;
    }

    @Override
    public String getValue() {
        return singleSelectListView.getItems().get(
                singleSelectListView
                        .getSelectionModel()
                        .getSelectedIndices()
                        .get(0))
                .getEnumID();
    }

    @Override
    public void setValue(String enumID) {
        IntStream.range(0, singleSelectListT.getListItem().size())
                .filter(value -> singleSelectListT.getListItem().get(value).getEnumID().equals(enumID)).findFirst().ifPresent(value -> {
            singleSelectListView.getSelectionModel().select(value);
            setFieldValueToParameter(tryToConvertEnumIdToWireValue(enumID), this.parameterT);
        });
    }

    /***
     * converts the enumId to wire value using parameter if presents
     * @param enumID
     * @return
     */
    private String tryToConvertEnumIdToWireValue(String enumID) {
        return parameterT == null ? null : parameterT.getEnumPair()
                .parallelStream()
                .filter(enumPairT -> enumPairT.getEnumID().equals(enumID))
                .findFirst()
                .orElse(new EnumPairT())
                .getWireValue();
    }

    @Override
    public void makeVisible(boolean visible) {
        this.singleSelectListView.setVisible(visible);
    }

    @Override
    public void makeEnable(boolean enable) {
        this.singleSelectListView.setDisable(!enable);
        if (enable)
            initializeControl();
    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
