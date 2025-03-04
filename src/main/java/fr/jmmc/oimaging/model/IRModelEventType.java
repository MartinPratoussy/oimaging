/*******************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 ******************************************************************************/
package fr.jmmc.oimaging.model;

/**
 * This enumeration defines all types of IRModelEvent
 */
public enum IRModelEventType {

    /** IRModel changed */
    IRMODEL_CHANGED,
    /** last event type = ready */
    READY,
    /** IRModel get a new service result */
    IRMODEL_UPDATED
}
