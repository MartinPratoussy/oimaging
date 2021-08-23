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

    private final List<ServiceResult> results = new ArrayList<>();

    private final List<Object> outputHeaders = new ArrayList<>();
    private final List<Object> inputHeaders = new ArrayList<>();

    private ImageOiInputParam inputParam = new ImageOiInputParam();
    private ImageOiOutputParam outputParam = new ImageOiOutputParam();

    private final List<ImageOiInputParam> inputParams = new ArrayList<>();
    private final List<ImageOiOutputParam> outputParams = new ArrayList<>();

    private final List<Object> headers = new ArrayList<>();
    private final List<FitsTable> params = new ArrayList<>();

    private final List<Map<String, Object>> values = new ArrayList<>();


    public ResultSetTableModelBis() {
        super();
    }

    /**
     * Redesign entirely the result table with the new headers and the new values
     *
     * @param results The result set
     */
    public void populate(List<ServiceResult> results) {

        // Clear everything
        inputHeaders.clear();
        outputHeaders.clear();
        headers.clear();
        values.clear();
        this.results.clear();

        // For each result in the results set, populate all the table values and headers
        this.results.addAll(results);
        this.results.forEach(result -> {
            try {
                Map<String, Object> inputKeywordsValue = result.getOifitsFile().getImageOiData().getInputParam().getKeywordsValue();
                Map<String, Object> outputKeywordsValue = result.getOifitsFile().getImageOiData().getOutputParam().getKeywordsValue();

                inputHeaders.addAll(inputKeywordsValue.keySet());
                outputHeaders.addAll(outputKeywordsValue.keySet());

                values.add(new HashMap<>());
                values.get(values.size() - 1).putAll(inputKeywordsValue);
                values.get(values.size() - 1).putAll(outputKeywordsValue);

                List<Object> newHeaders = Stream.concat(inputHeaders.stream(), outputHeaders.stream()).distinct().collect(Collectors.toList());
                List<Object> tempHeaders = new ArrayList<>(headers);

                headers.clear();
                headers.addAll(Stream.concat(tempHeaders.stream(), newHeaders.stream()).distinct().collect(Collectors.toList()));

            } catch (IOException | FitsException e) {
                e.printStackTrace();
            }
        });

        setColumnCount(headers.size());
        setColumnIdentifiers(headers.toArray());

        fireTableStructureChanged();
    }

    @Override
    public int getRowCount() {
        return (results != null) ? results.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String header = getColumnName(columnIndex);
        return values.get(rowIndex).get(header);
    }
}
