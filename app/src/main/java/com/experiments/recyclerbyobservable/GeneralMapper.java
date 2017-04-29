package com.experiments.recyclerbyobservable;

/**
 * 4/27/17.
 */

/**
 * Maps an ObservableList of models to one in ViewModel and vice versa
 * Can be implemented for filtering (searching), sorting, etc...
 *
 * Example:
 *   0 - CAN
 *   1 - BAN
 *   2 - CEO
 *   3 - DAO
 *
 * When search for "O"
 *   0 => null
 *   1 => null
 *   2 => 0
 *   3 => 1
 */
public interface GeneralMapper {
    public Integer mapModelToView(int modelIndex);
    public Integer mapViewToModel(int viewIndex);
}
