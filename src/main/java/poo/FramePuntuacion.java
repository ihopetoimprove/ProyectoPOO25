package poo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FramePuntuacion extends SuperFrame {
    private BDJugador bdJugador;

    public FramePuntuacion(){
        super();
    }
    public FramePuntuacion(String puntuacion,BDJugador bdjugador){
        super(puntuacion);
        this.bdJugador = bdjugador;
        // creo el modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel();

        // Obtener las cabeceras y los datos de tu BDJugador
        List<String> columnNamesList = bdJugador.obtenerColumnasJugadores();
        tableModel.setColumnIdentifiers(columnNamesList.toArray());

        // Obtener los datos como List<List<Object>>
        List<List<Object>> dataList = bdJugador.obtenerTodosLosJugadores();

        // Añadir todas las filas al modelo
        for (List<Object> rowData : dataList) {
            tableModel.addRow(rowData.toArray()); // Convertir cada List<Object> a Object[]
        }


        JTable puntuacionTabla = new JTable(tableModel);
        puntuacionTabla.setFillsViewportHeight(true);
        JScrollPane scrollPanel = new JScrollPane(puntuacionTabla);
        add(scrollPanel, BorderLayout.CENTER);

        JButton botonActualizar = new JButton("Actualizar Puntuacion");
        botonActualizar.addActionListener(e -> {
            tableModel.setRowCount(0);

            List<List<Object>> newData = bdJugador.obtenerTodosLosJugadores();
            for (List<Object> rowData : newData) {
                tableModel.addRow(rowData.toArray());
            }
        });
        add(botonActualizar, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(220,250));
        pack();
        setLocationRelativeTo(null);
    }

}
