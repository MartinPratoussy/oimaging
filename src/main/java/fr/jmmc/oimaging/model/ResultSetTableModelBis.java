/*******************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 ******************************************************************************/
package fr.jmmc.oimaging.model;

import fr.jmmc.oimaging.services.ServiceResult;
import fr.jmmc.oitools.fits.FitsHeaderCard;
import fr.jmmc.oitools.fits.FitsTable;
import fr.jmmc.oitools.image.ImageOiInputParam;
import fr.jmmc.oitools.image.ImageOiOutputParam;
import fr.jmmc.oitools.meta.ColumnMeta;
import fr.nom.tam.fits.FitsException;
import org.apache.commons.fileupload.util.Streams;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author martin
 */
public class ResultSetTableModelBis extends DefaultTableModel {

    private final List<String> headers = new ArrayList<>();
    private final List<Map<String, Object>> values = new ArrayList<>();

    public ResultSetTableModelBis() {
        super();
    }

    /**
     * Redesign entirely the result table with the new headers and the new values
     *
     * @param headers The keywords shown in the headers
     * @param values The new set of values
     */
    public void populate(List<String> headers, List<Map<String, Object>> values) {

        // Clear everything
        this.headers.clear();
        this.headers.addAll(headers);

        this.values.clear();
        this.values.addAll(values);

        setColumnCount(headers.size());
        setColumnIdentifiers(headers.toArray());

        fireTableStructureChanged();
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers.clear();
        this.headers.addAll(headers);

        fireTableStructureChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return values != null && !values.isEmpty() ? values.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return headers != null && !headers.isEmpty() ? headers.size() : 0;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String header = getColumnName(columnIndex);
        return values.get(rowIndex).get(header);
    }
}
