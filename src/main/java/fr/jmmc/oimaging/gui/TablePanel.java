/** *****************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 ***************************************************************************** */
package fr.jmmc.oimaging.gui;

import fr.jmmc.jmcs.gui.component.BasicTableSorter;
import fr.jmmc.jmcs.gui.util.SwingUtils;
import fr.jmmc.oimaging.model.ResultSetTableModel;
import fr.jmmc.oimaging.model.RatingCell;
import fr.jmmc.oimaging.services.ServiceResult;
import fr.jmmc.oitools.fits.FitsHeaderCard;
import fr.jmmc.oitools.image.ImageOiInputParam;
import fr.jmmc.oitools.image.ImageOiOutputParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author martin
 */
public class TablePanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * ResultSet table model
     */
    private final ResultSetTableModel resultSetTableModel;
    private final BasicTableSorter resultSetTableSorter;

    /**
     * Creates new form TablePanel
     */
    public TablePanel() {

        // Build ResultsTable
        resultSetTableModel = new ResultSetTableModel();

        initComponents();

        resultSetTableSorter = new BasicTableSorter(resultSetTableModel, jResultSetTable.getTableHeader());
        jResultSetTable.setModel(resultSetTableSorter);
        SwingUtils.adjustRowHeight(jResultSetTable);

        //jResultSetTable.setDefaultRenderer(jResultSetTable.getColumnClass(ResultSetTableModel.SUCCESS), new SuccessCell());

        final RatingCell ratingCell = new RatingCell();

        /*final TableColumn column = jResultSetTable.getColumn(resultSetTableModel.getColumnName(ResultSetTableModel.RATING));
        column.setCellRenderer(ratingCell);
        column.setCellEditor(ratingCell);*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jResultSetTable = new javax.swing.JTable();
        jPanelTableOptions = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jResultSetTable.setModel(resultSetTableModel);
        jScrollPane1.setViewportView(jResultSetTable);

        jSplitPane1.setRightComponent(jScrollPane1);

        jPanelTableOptions.setLayout(new javax.swing.BoxLayout(jPanelTableOptions, javax.swing.BoxLayout.PAGE_AXIS));
        jSplitPane1.setLeftComponent(jPanelTableOptions);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public void setResults(List<ServiceResult> results) {
        // Exit the method if the result set is empty
        if (results.isEmpty()) return;

        List<ResultSetTableModel.ColumnKeyword> tableColumnKeywords = new ArrayList<>();

        // For each result in the results set, populate all the table values and headers
        for (ServiceResult result : results) {

            // Get the location of the input and output keywords and header cards
            ImageOiInputParam input = result.getOifitsFile().getImageOiData().getInputParam();
            ImageOiOutputParam output = result.getOifitsFile().getImageOiData().getOutputParam();

            // Get the input headers
            List<ResultSetTableModel.ColumnKeyword> inputs = new ArrayList<>();
            for (String header : input.getKeywordsValue().keySet()) {
                inputs.add(new ResultSetTableModel.ColumnKeyword("INPUT", header));
            }
            for (FitsHeaderCard card : input.getHeaderCards()) {
                inputs.add(new ResultSetTableModel.ColumnKeyword("INPUT", card.getKey()));
            }

            // Get the output headers
            List<ResultSetTableModel.ColumnKeyword> outputs = new ArrayList<>();
            for (String header : output.getKeywordsValue().keySet()) {
                outputs.add(new ResultSetTableModel.ColumnKeyword("OUTPUT", header));
            }
            for (FitsHeaderCard card : output.getHeaderCards()) {
                outputs.add(new ResultSetTableModel.ColumnKeyword("OUTPUT", card.getKey()));
            }

            // Add all the headers collected in a new a list
            List<ResultSetTableModel.ColumnKeyword> newKeywords = new ArrayList<>();
            newKeywords.addAll(inputs);
            newKeywords.addAll(outputs);

            // Merge the previous combined headers with new ones without duplicates
            tableColumnKeywords = Stream.concat(tableColumnKeywords.stream(), newKeywords.stream()).distinct().collect(Collectors.toList());
        }

        getTableModel().setHeaders(tableColumnKeywords);
        getTableModel().setResults(results);
    }

    public ListSelectionModel getSelectionModel() {
        return getTable().getSelectionModel();
    }

    public int getSelectedRowsCount() {
        return getTable().getSelectedRowCount();
    }

    public List<ServiceResult> getSelectedRows() {
        List<ServiceResult> results = new ArrayList<>();

        for (int index : getTable().getSelectedRows()) {
            results.add(resultSetTableModel.getServiceResult(resultSetTableSorter.modelIndex(index)));
        }
        return results;
    }

    public ServiceResult getSelectedRow() {
        final List<ServiceResult> results = getSelectedRows();
        return (results.isEmpty()) ? null : results.get(0);
    }

    public void setSelectedRow(final int rowIndex) {
        final int index = resultSetTableSorter.viewIndex(rowIndex);
        getTable().setRowSelectionInterval(index, index);
    }

    private JTable getTable() {
        return this.jResultSetTable;
    }

    private ResultSetTableModel getTableModel() {
        return this.resultSetTableModel;
    }

    public void addControlComponent(JComponent component) {
        jPanelTableOptions.add(component);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelTableOptions;
    private javax.swing.JTable jResultSetTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables

}
