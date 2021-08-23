package fr.jmmc.oimaging.model;

import fr.jmmc.oimaging.services.ServiceResult;
import fr.jmmc.oitools.fits.FitsHeaderCard;
import fr.jmmc.oitools.image.ImageOiData;
import fr.nom.tam.fits.FitsException;
import org.apache.commons.fileupload.util.Streams;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResultSetTableModelBis extends DefaultTableModel {

    private final List<List<FitsHeaderCard>> fitsHeadersCards = new ArrayList<List<FitsHeaderCard>>();
    private List<ServiceResult> results = new ArrayList<>();
    private String[] headers;

    public ResultSetTableModelBis() {
        super();
    }

    public void populate(List<ServiceResult> results) {
        this.results = results;
        this.results.forEach(result -> {
            try {
                fitsHeadersCards.clear();
                fitsHeadersCards.add(result.getOifitsFile().getImageOiData().getOutputParam().getHeaderCards());
            } catch (IOException | FitsException e) {
                e.printStackTrace();
            }
        });

        System.out.println(Stream.of(fitsHeadersCards).flatMap(Collection::stream).collect(Collectors.toList()));

        // TODO: add cards keys in headers

        setColumnCount((headers != null) ? headers.length : 0);
        setColumnIdentifiers(headers);

        fireTableStructureChanged();
    }

    @Override
    public int getRowCount() {
        return (results != null) ? results.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return (headers != null) ? headers.length : 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return fitsHeadersCards.get(rowIndex).get(columnIndex).getValue();
    }
}
