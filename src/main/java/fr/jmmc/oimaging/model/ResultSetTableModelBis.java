/*******************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 ******************************************************************************/
package fr.jmmc.oimaging.model;

import fr.jmmc.oimaging.services.ServiceResult;
import fr.jmmc.oitools.fits.FitsHeaderCard;
import fr.nom.tam.fits.FitsException;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class ResultSetTableModelBis extends DefaultTableModel {

    private final List<List<FitsHeaderCard>> fitsHeadersCards = new ArrayList<List<FitsHeaderCard>>();
    private final List<ServiceResult> results = new ArrayList<>();
    private List<String> headers = new ArrayList<>();

    public ResultSetTableModelBis() {
        super();
    }

    /**
     * Redesign entirely the result table with the new headers and the new values
     * @param results The result set
     */
    public void populate(List<ServiceResult> results) {
        fitsHeadersCards.clear();
        this.results.clear();
        this.results.addAll(results);
        this.results.forEach(result -> {
            try {
                fitsHeadersCards.add(result.getOifitsFile().getImageOiData().getOutputParam().getHeaderCards());
            } catch (IOException | FitsException e) {
                e.printStackTrace();
            }
        });

        // Merge the results parameters into one list with all the parameters
        headers = fitsHeadersCards
                .stream()
                .flatMap(List::stream)
                .map(FitsHeaderCard::getKey)
                .distinct()
                .collect(Collectors.toList());

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
        return fitsHeadersCards.get(rowIndex).get(columnIndex).getValue();
    }
}
