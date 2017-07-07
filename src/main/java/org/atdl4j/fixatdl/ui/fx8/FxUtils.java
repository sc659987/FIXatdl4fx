package org.atdl4j.fixatdl.ui.fx8;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sainik on 3/29/17.
 */
public class FxUtils {

    public static List<ColumnConstraints> getTwoColumnSameWidthForGridPane() {
        final ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        final ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        column2.setMaxWidth(100);
        final List<ColumnConstraints> columnConstraintsList = new ArrayList<>();
        columnConstraintsList.add(column1);
        columnConstraintsList.add(column2);
        return columnConstraintsList;
    }

    public static List<ColumnConstraints> getOneColumnWidthForGridPane() {

        final ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);

        final List<ColumnConstraints> columnConstraintsList = new ArrayList<>();
        columnConstraintsList.add(column1);

        return columnConstraintsList;
    }

}
