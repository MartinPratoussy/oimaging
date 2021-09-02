/**
 * ****************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 * ****************************************************************************
 */
package fr.jmmc.oimaging.model;

import fr.jmmc.oimaging.services.ServiceResult;
import fr.jmmc.oitools.fits.FitsHeaderCard;
import fr.jmmc.oitools.image.ImageOiInputParam;
import fr.jmmc.oitools.image.ImageOiOutputParam;

import java.util.*;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 * @author martin
 */
public class ResultSetTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private List<ColumnKeyword> columnKeywords = new ArrayList<>();
    private List<ServiceResult> results = new ArrayList<>();

    public ResultSetTableModel() {
        super();
    }
    
    public void setHeaders(List<ColumnKeyword> columnKeywords) {
        this.columnKeywords = columnKeywords;
        fireTableStructureChanged();
    }

    public void setResults(List<ServiceResult> results) {
        this.results = results;
        fireTableDataChanged();
    }

    public ServiceResult getServiceResult(final int rowIndex) {
        return this.results.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return (results != null) ? results.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return (columnKeywords != null) ? columnKeywords.size() : 0;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnKeywords.get(columnIndex).getValue();
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "SUCCESS":
                return boolean.class;
            case "RATING":
                return Integer.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "COMMENTS":
            case "RATING":
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {

        final ServiceResult result = getServiceResult(columnIndex);

        switch (getColumnName(columnIndex)) {
            case "RATING":
                result.setRating((int) value);
                break;
            case "COMMENTS":
                result.setComments((String) value);
                break;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        final ServiceResult result = getServiceResult(rowIndex);

        String source = "";
        for (ColumnKeyword columnKeyword : columnKeywords) {
            if (columnKeyword.getValue().equals(getColumnName(columnIndex))) {
                source = columnKeyword.getSource();
            }
        }

        switch (source) {
            case "RESULT":
                return ValueProvider.RESULT.getValue(result, getColumnName(columnIndex));
            case "INPUT":
                return ValueProvider.INPUT_PARAM.getValue(result, getColumnName(columnIndex));
            case "OUTPUT":
                return ValueProvider.OUTPUT_PARAM.getValue(result, getColumnName(columnIndex));
            default:
                return null;
        }
    }

    public List<String> getHeaders() {
        return columnKeywords.stream().map(ColumnKeyword::getValue).collect(Collectors.toList());
    }

    // use enum or just create ColumnProvider(name)
    // with 3 child classes (ServiceColumnProvider, InputColumnProvider or OutputColumnProvider)
    // having the appropriate getValue() implentation
    public enum ValueProvider {
        RESULT {
            public Object getValue(final ServiceResult result, final String columnKey) {
                switch (columnKey) {
                    case "FILE":
                        return result.getInputFile().getName();

                    case "TIMESTAMP":
                        return result.getEndTime();

                    case "ALGORITHM":
                        return result.getService().getProgram();

                    case "SUCCESS":
                        return result.isValid();

                    case "RATING":
                        return result.getRating();

                    case "COMMENTS":
                        return result.getComments();

                    default:
                        return null;
                }
            }
        },
        INPUT_PARAM {
            public Object getValue(final ServiceResult result, final String columnKey) {
                if (result.getOifitsFile() != null) {
                    final ImageOiInputParam param = result.getOifitsFile().getImageOiData().getInputParam();
                    // Keywords
                    if (param.hasKeywordMeta(columnKey)) {
                        return param.getKeywordValue(columnKey);
                    // Header card
                    } else if (param.hasHeaderCards()) {
                        for (FitsHeaderCard card : param.getHeaderCards()) {
                            if (card.getKey().equals(columnKey)) {
                                return card.getValue();
                            }
                        }
                        return null;
                    }
                    return null;
                }
                return null;
            }
        },
        OUTPUT_PARAM {
            public Object getValue(final ServiceResult result, final String columnKey) {
                if (result.getOifitsFile() != null) {
                    final ImageOiOutputParam param = result.getOifitsFile().getImageOiData().getOutputParam();
                    // Keywords
                    if (param.hasKeywordMeta(columnKey)) {
                        return param.getKeywordValue(columnKey);
                    // Header card
                    }  else if (param.hasHeaderCards()) {
                        for (FitsHeaderCard card : param.getHeaderCards()) {
                            if (card.getKey().equals(columnKey)) {
                                return card.getValue();
                            }
                        }
                        return null;
                    }
                    return null;
                }
                return null;
            }
        };

        public abstract Object getValue(ServiceResult result, final String column);
    }

    /**
     * Associate a keyword with its source in the OiFits file
     */
    public static class ColumnKeyword {

        String source;
        String value;

        public ColumnKeyword(String source, String value) {
            this.source = source;
            this.value = value;
        }

        public String getSource() {
            return source;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ColumnKeyword columnKeyword = (ColumnKeyword) o;
            return Objects.equals(source, columnKeyword.source) && Objects.equals(value, columnKeyword.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, value);
        }
    }
}
