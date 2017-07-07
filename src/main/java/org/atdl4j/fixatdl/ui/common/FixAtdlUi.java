package org.atdl4j.fixatdl.ui.common;

import org.atdl4j.fixatdl.model.core.StrategiesT;
import org.atdl4j.fixatdl.model.core.StrategyT;

import javax.xml.bind.Unmarshaller;
import java.io.File;

/***
 * FIX Algorithmic Trading Definition Language UI panel interface
 */
public interface FixAtdlUi<T> {

    /***
     *
     */
    T createStrategySelectionPanel();

    /***
     *
     * @return
     */
    T createUi();

    /***
     *
     * @param file
     */
    void parseFixAtdlFile(File file);

    /***
     *
     * @return
     */
    StrategiesT getStrategies();

    /***
     *
     */
    void setSelectedStrategy(StrategyT strategyT);

    /***
     *
     * @return
     */
    StrategyT getSelectedStrategy();

    /***
     *
     * @return
     */
    Unmarshaller getUnmarshaller();

}
