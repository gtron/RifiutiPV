package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.TableSorter;

/**
 * @author Gtron -
 *  
 */
public class JMainTable extends JPanel {

    private Vector elenco=null; //elenco di IListable
    
    private  int columnSort=0; //mi dice quale per quale colonna sara ordinata di default se isSorting è true in addElement
    
    private JTable table;

    int row;

    MyTableModel myTableModel;

    TableSorter sorter;
     
    private MyTableCellRenderer myRenderer=null;

	private JLabel statusBar_JLabel;
    
   
    
    public JMainTable(AbstractSwg swg, boolean isStatusBar ) {
        super(new BorderLayout());
        row = -1;
        myTableModel = new MyTableModel(swg.getElemento().getColumns());
        sorter = new TableSorter(myTableModel,swg);
        sorter.setTableHeader(getJTable().getTableHeader());
        JScrollPane scrollPane = new JScrollPane(getJTable());
        scrollPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        scrollPane.setAutoscrolls(true);
        add(scrollPane, BorderLayout.CENTER);
        if(isStatusBar)
        	add(getStatusBar(),BorderLayout.SOUTH);
        
        if ( swg != null ) {
	        swg.getFrame().setStatusBar(getJTable().getTableHeader(),"Cliccare per ordinare la tabella secondo questo campo");
	        swg.getFrame().setStatusBar(getJTable(),"Cliccare per accedere al dettaglio");
        }
    }
    
    public JMainTable() {
        this(null, true);
    }
    
    public JMainTable(AbstractSwg swg) {
    	this(swg,true);
    }
    
    

    
    public JTable getJTable() {
        if(table==null) {
            table = new JTable(sorter);
            table.setPreferredScrollableViewportSize(new Dimension(500, 600));
            table.setCursor(new Cursor(Cursor.HAND_CURSOR));
            table.getTableHeader().setCursor(new Cursor(Cursor.HAND_CURSOR));
            table.setDefaultRenderer(Object.class, getMyRenderer());
            getJTable().getSelectionModel().addListSelectionListener(myListSelectionListener);
        }
        return table;
    }
    
    public JLabel getStatusBar(){
    	if(statusBar_JLabel==null){
    		statusBar_JLabel= new JLabel();
    	}
    	return statusBar_JLabel;
    }
    
    public void setsStatusBar(int tot, int sel){
    	getStatusBar().setText(" elementi totali: " + tot + "                elementi selezionati: " + sel );
    }
    
    private MytableModelListener mytableModelListener= new MytableModelListener();
    private class MytableModelListener implements TableModelListener{

		public void tableChanged(TableModelEvent e) {
			setsStatusBar(getJTable().getRowCount(),getJTable().getSelectedRowCount());
			
		}

		
    	
    }
    
    private MyListSelectionListener myListSelectionListener= new MyListSelectionListener();
    private class MyListSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			setsStatusBar(getJTable().getRowCount(),getJTable().getSelectedRowCount());
		}
    	
    }
    
    
    
    public MyTableCellRenderer getMyRenderer() {
        if(myRenderer==null) {
            myRenderer= new MyTableCellRenderer();
        }
        return myRenderer;
    }

    
    public void addItems(Vector items){
    	Iterator i=items.iterator();
    	while(i.hasNext()){
    		this.addElement((IListable)i.next());
    	}
    	getTableSorter().sortTable(getColumnSort());
    }
    
    public void removeItems(Vector items){
    	Iterator i=items.iterator();
    	while(i.hasNext()){
    		removeElement((IListable)i.next());
    	}
    
    }
    
    public Vector getSelectedItems(){
    	Vector elementiSelezionati=new Vector();
    	int[] selectedRows=getJTable().getSelectedRows();
        int[] modelIndex = getTableSorter().getIndexModel();
        for(int i=0; i<selectedRows.length;i++) {
        	elementiSelezionati.add(getElenco().get(modelIndex[selectedRows[i]]));
        }
        return elementiSelezionati;	
    }
    
    /**
     * prende gli elementi selezionati dalla JMainTable dattacome paramatro e gli aggiunge 
     * alla JMaintable corrente
     * @param jMainTable
     */
    public void addSelectedItem(JMainTable jMainTable) {
        int[] selectedRows=jMainTable.getJTable().getSelectedRows();
        int[] modelIndex = jMainTable.getTableSorter().getIndexModel();
        for(int i=0; i<selectedRows.length;i++) {
            IListable element=(IListable)jMainTable.getElenco().get(modelIndex[selectedRows[i]]);
            this.addElement(element,true);
        }
    	//getMyTableModel().fireTableDataChanged();
    }
    
    public void removeSelectedItem() {
        int[] selectedRows=getJTable().getSelectedRows();
        int[] modelIndex = getTableSorter().getIndexModel();
        for(int i=0; i<selectedRows.length;i++) {
            IListable element=(IListable) getElenco().get(modelIndex[selectedRows[i]]);
            this.removeElement(element);
            for(int j=i+1;j<selectedRows.length;j++)
                selectedRows[j]--;
                    
        }  
     //   getMyTableModel().fireTableDataChanged(); //rende il tutto pi� veloce ma non � tanto corretto
    }
    
    private void removeElementToTableModel(int row) {
        for (int i = 0; i < getMyTableModel().getColumnCount(); i++) 
            myTableModel.removeValueAt(row, i);
        this.row=this.row-1;
    }
  
    public void removeAllItems() {
        setElenco(null);     
    }
    
    public void addElement(IListable elemento, boolean isSorting) {
        try {
            getElenco().add(elemento);
            addElementToTableModel(elemento);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(isSorting)
        	getTableSorter().sortTable(getColumnSort());
    }
    
    public void addElement(IListable elemento){
    	addElement(elemento, false);
    }
    
    private void addElementToTableModel(IListable element) {
        try {
            row = row + 1;
            myTableModel.addRow(element.getVValues()) ;
            /*for (int i = 0; i < element.getValues().length; i++) {
                myTableModel.setValueAt(element.getValues()[i], row, i);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMyTableModel().fireTableRowsInserted(row,row);
    }
    
    private void addElementToTableModel_burst(IListable element) {
        
        try {
            row = row + 1;
            myTableModel.addRow(element.getVValues()) ;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     *seleziona l'elemento nella jTable, 
     *se non esiste non seleziona nessun elemento  
     * @param elemento
     */
    public void selectElement(IListable elemento){   	
    	
    	int[] modelIndex = getTableSorter().getModelToView();
        
    	for(int i=0; i<getElenco().size();i++) {
            IListable element=(IListable)getElenco().get(i);
            if(element.equals(elemento)){
            	getJTable().setRowSelectionInterval(modelIndex[i],modelIndex[i]);
            	return;
            }
    	}
    	
    }
    
    public void removeElement(IListable elemento) {
        try {
            int index=getElenco().indexOf(elemento);
            if(index==-1)
                return;
            removeElementToTableModel(index);
            getElenco().remove(elemento);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMyTableModel().fireTableDataChanged();
        new TableModelEvent(getMyTableModel());
    }
    
    public void removeElement(int index){
    	try {
            removeElementToTableModel(index);
            getElenco().removeElementAt(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMyTableModel().fireTableDataChanged(); 	
    }

    public void buildJTable(Vector el) {

        
        int size = 0 ;
        if ( el != null && el.size() > 0 )
            size = ((IListable)el.firstElement()).getColumns().length ; 
        
        getMyTableModel().removeAll_burst( size );

        if ( el.size() > 0 ) {
            for (Iterator i = el.iterator(); i.hasNext() ; ) {
                try {
                    addElementToTableModel_burst( (IListable) i.next());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getMyTableModel().fireTableDataChanged();
    }
    
    
    public void setElenco (Vector elenco) {
        this.elenco=elenco;
        buildJTable(getElenco());
    }
    
    public Vector getElenco() {
        if(elenco==null)
            elenco=new Vector();
        return elenco;
    }
    
   /*
    public void setMaxWidthColumnsTable(int[] percentuali) {
        
        for(int i=0;i<percentuali.length;i++) {            
            int width2 = getJTable().getWidth();
            double j = (percentuali[i]*0.01);
            int aux= (int) (j* width2); 
            if(aux!=0)
        	    getJTable().getColumnModel().getColumn(i).setMaxWidth(aux);
        }         
    }
    */
    
    
    public TableSorter getTableSorter() {
        return sorter;
    }
    
    
    /**
     * @return Restituisce il valore myTableModel.
     */
    public MyTableModel getMyTableModel() {
        return myTableModel;
    }

    
    
    

    private JMainTable getThis() {
        return this;
    }

    /**
     * @return Restituisce il valore row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row
     *            Il valore row da impostare.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @param table
     *            Il valore table da impostare.
     */
    public void setTable(JTable table) {
        this.table = table;
    }

    
    private class MyTableCellRenderer extends DefaultTableCellRenderer {
        
        public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
		{
        	JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			c.setHorizontalAlignment(JLabel.CENTER);
			return c;
		}
        
    }
    
    
    public class MyTableModel extends AbstractTableModel {
        private String[] columnNames;
        
        
        private int col;

        private Vector[] data;

        public MyTableModel(String[] columnNames) {
            this(1,columnNames.length,columnNames);
        }
        
        public MyTableModel(int col, String[] columnNames) {
            this(1,col,columnNames);
        }

        public MyTableModel(int row, int col, String[] columnNames) {
            this.col = col;
            data = new Vector[col];
            this.columnNames = columnNames;
            initDataArray( columnNames.length ) ;
            addTableModelListener(mytableModelListener);
        }
        
        private void initDataArray( int cols ) {
            
            if ( data == null )
                data = new Vector[cols] ;

            for (int i = 0; i < cols ; i++)
                data[i] = new Vector() ;
        }

        public void addRow( Vector d ) {
            
            if ( data == null )
                initDataArray( d.size() ) ;
            
            Object[] o = d.toArray();
            
            for( int i = 0 ; i< d.size() ; i++ ) {
                data[i].add( o[i] ) ;
            }
        }
        
        private void setData(Vector[] data2) {
            for (int i = 0; i < columnNames.length; i++)
                this.data[i] = (Vector) data2[i].clone();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            if ( data == null || data[0] == null)
                return 0;
            else
                return data[0].size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            try {
                return data[col].elementAt(row);
            } catch (Exception e) {
                return null;
            }
        }

        /*
         * JTable uses this method to determine the default renderer/ editor for
         * each cell. If we didn't implement this method, then the last column
         * would contain text ("true"/"false"), rather than a check box.
         */
        public Class getColumnClass(int c) {
            if(getValueAt(0, c)!=null)
                return getValueAt(0, c).getClass();
            else
                return String.class;
        }

        /*
         * Don't need to implement this method unless your table's editable.
         */
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        private void removeAll_old() {
            for (int i = 0; i < columnNames.length; i++)
                data[i] = new Vector();
            row=-1;
            fireTableDataChanged();
        }
        
        private void removeAll() {
            data = null ;
            fireTableDataChanged();
        }
        
        private void removeAll_burst( int size ) {
            if ( size > 0 ) {
                data = new Vector[size] ;
                
                for ( int i = 0 ; i < size ; i++ ) {
                    data[i] = new Vector() ;
                }
            }
            else
                data = null ;
            //fireTableDataChanged();
        }
        

        /*
         * Don't need to implement this method unless your table's data can
         * change.
         */
        public void setValueAt(Object value, int row, int col) {
            if ( data[col] == null )
                data[col] = new Vector() ;
            
            data[col].add(row, value);
          //  fireTableDataChanged();
           // fireTableCellUpdated(row, col);
        }
        
        public void removeValueAt(int row, int col) {
            data[col].remove(row);
            //fireTableDataChanged();
           // fireTableCellUpdated(row, col);
        }

        /**
         * @return
         */
        public Vector[] getData() {
            return this.data;
        }

        /**
         * @param data2
         */
        public void setTableModel(Vector[] data) {
            this.data = data;
            fireTableCellUpdated(0, 0);
        }
      
    }

	public int getColumnSort() {
		return columnSort;
	}

	public void setColumnSort(int columnSort) {
		this.columnSort = columnSort;
	}
       
}

