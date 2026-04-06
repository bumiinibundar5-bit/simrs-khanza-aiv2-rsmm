/*
  Kontribusi Ikhsan Dari RS Tk.III dr. REKSODIWIRYO
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class RMObservasiPasienDialisis extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeRiwayatKehamilan, tabModeRiwayatKehamilan2;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, pstindakan;
    private ResultSet rs, rstindakan;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariPetugas petugas3 = new DlgCariPetugas(null, false);
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    private StringBuilder htmlContent;
    private String finger = "", kamar = "", namakamar = "", status = "", norawatibu = "", kelas = "", posisi = "";

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMObservasiPasienDialisis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        DlgObservasiDialisis.setSize(650, 192);

        tabMode = new DefaultTableModelImpl(null, new Object[]{
            "No.Rawat", "No RM", "Nama Pasien", "JK", "Tgl.Lahir", "Tanggal HD", "Kode Dokter", "Nama Dokter", "Sumber Data Pasien", "Kewarganegaraan", "Kesadaran", "GCS", "EWS",
            "TD", "Suhu", "Nadi", "RR", "Skala Nyeri", "Keluhan", "Alergi", "Ada Nyeri?", "Vaskuler", "BB Kering", "BB Sekarang", "Lokasi", "No Mesin", "Dializer", "Ke?", "Dialisat",
            "Lama HD", "Ultra Goal", "Total", "Bolus Awal", "Kontinyu", "BFR", "HEPARIN", "Obat Selama HD", "Keluhan Post HD", "Sisa Priming", "Transfusi", "Minum", "Jumlah", "TD Post HD",
            "Suhu Post HD", "Nadi Post HD", "RR Post HD", "BB Post HD", "Pendidikan", "Nip", "Nama Petugas", "Diagnosa"
        });

        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 45; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(105);
                    break;
                case 1:
                    column.setPreferredWidth(140);
                    break;
                case 2:
                    column.setPreferredWidth(100);
                    break;
                case 3:
                    column.setPreferredWidth(100);
                    break;
                case 4:
                    column.setPreferredWidth(80);
                    break;
                case 5:
                    column.setPreferredWidth(130);
                    break;
                case 6:
                    column.setPreferredWidth(60);
                    break;
                case 7:
                    column.setPreferredWidth(60);
                    break;
                case 8:
                    column.setPreferredWidth(50);
                    break;
                case 9:
                    column.setPreferredWidth(50);
                    break;
                case 10:
                    column.setPreferredWidth(50);
                    break;
                case 11:
                    column.setPreferredWidth(50);
                    break;
                case 12:
                    column.setPreferredWidth(100);
                    break;
                case 13:
                    column.setPreferredWidth(100);
                    break;
                case 14:
                    column.setPreferredWidth(70);
                    break;
                case 15:
                    column.setPreferredWidth(70);
                    break;
                case 16:
                    column.setPreferredWidth(100);
                    break;
                case 17:
                    column.setPreferredWidth(80);
                    break;
                case 18:
                    column.setPreferredWidth(60);
                    break;
                case 19:
                    column.setPreferredWidth(80);
                    break;
                case 20:
                    column.setPreferredWidth(60);
                    break;
                case 21:
                    column.setPreferredWidth(60);
                    break;
                case 22:
                    column.setPreferredWidth(60);
                    break;
                case 23:
                    column.setPreferredWidth(70);
                    break;
                case 24:
                    column.setPreferredWidth(60);
                    break;
                case 25:
                    column.setPreferredWidth(60);
                    break;
                case 26:
                    column.setPreferredWidth(100);
                    break;
                case 27:
                    column.setPreferredWidth(100);
                    break;
                case 28:
                    column.setPreferredWidth(50);
                    break;
                case 29:
                    column.setPreferredWidth(70);
                    break;
                case 30:
                    column.setPreferredWidth(70);
                    break;
                case 31:
                    column.setPreferredWidth(70);
                    break;
                case 32:
                    column.setPreferredWidth(70);
                    break;
                case 33:
                    column.setPreferredWidth(60);
                    break;
                case 34:
                    column.setPreferredWidth(60);
                    break;
                case 35:
                    column.setPreferredWidth(50);
                    break;
                case 36:
                    column.setPreferredWidth(70);
                    break;
                case 37:
                    column.setPreferredWidth(80);
                    break;
                case 38:
                    column.setPreferredWidth(100);
                    break;
                case 39:
                    column.setPreferredWidth(100);
                    break;
                case 40:
                    column.setPreferredWidth(100);
                    break;
                case 41:
                    column.setPreferredWidth(100);
                    break;
                case 42:
                    column.setPreferredWidth(100);
                    break;
                case 43:
                    column.setPreferredWidth(100);
                    break;
                case 44:
                    column.setPreferredWidth(100);
                    break;
                case 45:
                    column.setPreferredWidth(100);
                    break;
                default:
                    column.setPreferredWidth(50);
                    break;
            }
        }

        tbObat.setDefaultRenderer(Object.class,
                new WarnaTable());

        tabModeRiwayatKehamilan = new DefaultTableModel(null, new Object[]{
            "Jam", "TGL", "TD", "NADI", "QB", "VP", "UFG", "UF REMOVED", "MASALAH/TINDAKAN", "PETUGAS", "NAMA"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbRiwayatEdukasi.setModel(tabModeRiwayatKehamilan);

        tbRiwayatEdukasi.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRiwayatEdukasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbRiwayatEdukasi.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(160);
            } else if (i == 3) {
                column.setPreferredWidth(35);
            } else if (i == 4) {
                column.setPreferredWidth(20);
            } else if (i == 5) {
                column.setPreferredWidth(65);
            } else if (i == 6) {
                column.setPreferredWidth(65);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(65);
            } else if (i == 8) {
                column.setPreferredWidth(65);
            } else if (i == 8) {
                column.setPreferredWidth(65);
            }
        }
        tbRiwayatEdukasi.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeRiwayatKehamilan2 = new DefaultTableModel(null, new Object[]{
            "Jam", "TGL", "TD", "NADI", "QB", "VP", "UFG", "UF REMOVED", "MASALAH/TINDAKAN", "PETUGAS", "NAMA"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbRiwayatEdukasi2.setModel(tabModeRiwayatKehamilan2);

        tbRiwayatEdukasi2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRiwayatEdukasi2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbRiwayatEdukasi2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(160);
            } else if (i == 3) {
                column.setPreferredWidth(35);
            } else if (i == 4) {
                column.setPreferredWidth(20);
            } else if (i == 5) {
                column.setPreferredWidth(65);
            } else if (i == 6) {
                column.setPreferredWidth(65);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(65);
            } else if (i == 8) {
                column.setPreferredWidth(65);
            } else if (i == 8) {
                column.setPreferredWidth(65);
            }
        }
        tbRiwayatEdukasi2.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(
                new batasInput((byte) 17).getKata(TNoRw)
        );
        TCari.setDocument(
                new batasInput((int) 100).getKata(TCari)
        );

        if (koneksiDB.CARICEPAT()
                .equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        dokter.addWindowListener(
                new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e
            ) {
            }

            @Override
            public void windowClosing(WindowEvent e
            ) {
            }

            @Override
            public void windowClosed(WindowEvent e
            ) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                }
            }

            @Override
            public void windowIconified(WindowEvent e
            ) {
            }

            @Override
            public void windowDeiconified(WindowEvent e
            ) {
            }

            @Override
            public void windowActivated(WindowEvent e
            ) {
            }

            @Override
            public void windowDeactivated(WindowEvent e
            ) {
            }
        });
        petugas.addWindowListener(
                new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e
            ) {
            }

            @Override
            public void windowClosing(WindowEvent e
            ) {
            }

            @Override
            public void windowClosed(WindowEvent e
            ) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    KdPetugas1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NmPetugas1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
            }

            @Override
            public void windowIconified(WindowEvent e
            ) {
            }

            @Override
            public void windowDeiconified(WindowEvent e
            ) {
            }

            @Override
            public void windowActivated(WindowEvent e
            ) {
            }

            @Override
            public void windowDeactivated(WindowEvent e
            ) {
            }
        });
        petugas3.addWindowListener(
                new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e
            ) {
            }

            @Override
            public void windowClosing(WindowEvent e
            ) {
            }

            @Override
            public void windowClosed(WindowEvent e
            ) {
                if (petugas3.getTable().getSelectedRow() != -1) {
                    NIP.setText(petugas3.getTable().getValueAt(petugas3.getTable().getSelectedRow(), 0).toString());
                    NamaPetugas.setText(petugas3.getTable().getValueAt(petugas3.getTable().getSelectedRow(), 1).toString());
                }
            }

            @Override
            public void windowIconified(WindowEvent e
            ) {
            }

            @Override
            public void windowDeiconified(WindowEvent e
            ) {
            }

            @Override
            public void windowActivated(WindowEvent e
            ) {
            }

            @Override
            public void windowDeactivated(WindowEvent e
            ) {
            }
        });

        HTMLEditorKit kit = new HTMLEditorKit();

        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();

        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();

        LoadHTML.setDocument(doc);

        LoadHTML.setEditable(
                true);

        ChkAccor.setSelected(
                false);
        isMenu();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        DlgObservasiDialisis = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        BtnKeluarKehamilan = new widget.Button();
        BtnSimpanRiwayatKehamilan = new widget.Button();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        UFR = new widget.TextBox();
        jLabel77 = new widget.Label();
        HR = new widget.TextBox();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        VP = new widget.TextBox();
        jLabel80 = new widget.Label();
        TD1 = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel83 = new widget.Label();
        QB = new widget.TextBox();
        jLabel84 = new widget.Label();
        jLabel87 = new widget.Label();
        UFG = new widget.TextBox();
        jLabel88 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        TMasalah = new widget.TextArea();
        Tanggal = new widget.Tanggal();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        Scroll6 = new widget.ScrollPane();
        tbRiwayatEdukasi = new widget.Table();
        BtnTambahMasalah = new widget.Button();
        BtnHapusRiwayatPersalinan = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        label28 = new widget.Label();
        Ruangan = new widget.TextBox();
        label29 = new widget.Label();
        ChbWarganegara = new widget.ComboBox();
        label30 = new widget.Label();
        Agama = new widget.TextBox();
        ChbSumberData = new widget.ComboBox();
        label31 = new widget.Label();
        TAlamat = new widget.TextBox();
        label32 = new widget.Label();
        jLabel55 = new widget.Label();
        cmbRwyAlergi = new widget.ComboBox();
        jLabel38 = new widget.Label();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel100 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel39 = new widget.Label();
        jLabel12 = new widget.Label();
        BBKering = new widget.TextBox();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel29 = new widget.Label();
        Nyeri = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel42 = new widget.Label();
        ews = new widget.TextBox();
        Keluhan = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel47 = new widget.Label();
        cmbVaskuler = new widget.ComboBox();
        jLabel48 = new widget.Label();
        TLokasi = new widget.TextBox();
        jLabel50 = new widget.Label();
        TNoMesin = new widget.TextBox();
        jLabel51 = new widget.Label();
        cmbDializer = new widget.ComboBox();
        jLabel52 = new widget.Label();
        dializerke = new widget.TextBox();
        jLabel14 = new widget.Label();
        BBSekarang = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel24 = new widget.Label();
        TDialisat = new widget.TextBox();
        TFlowRate = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel95 = new widget.Label();
        TKontinyu = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel102 = new widget.Label();
        cmdHeparin = new widget.ComboBox();
        jLabel54 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        TLamaHD = new widget.TextBox();
        TUltraGoal = new widget.TextBox();
        TTotal = new widget.TextBox();
        TBolusAwal = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        scrollPane16 = new widget.ScrollPane();
        Obat = new widget.TextArea();
        jLabel82 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        TTD = new widget.TextBox();
        jLabel44 = new widget.Label();
        TJumlah = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        TPriming = new widget.TextBox();
        TTranfusi = new widget.TextBox();
        TMinum = new widget.TextBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel53 = new widget.Label();
        TRRPost = new widget.TextBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        TSuhuPost = new widget.TextBox();
        TNasiPost = new widget.TextBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        TBBPost = new widget.TextBox();
        jLabel74 = new widget.Label();
        label15 = new widget.Label();
        KdPetugas1 = new widget.TextBox();
        NmPetugas1 = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        label33 = new widget.Label();
        TAlamat1 = new widget.TextBox();
        TDiagnosa = new widget.TextBox();
        label34 = new widget.Label();
        jLabel89 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        FormMasalahRencana = new widget.PanelBiasa();
        scrollPane9 = new widget.ScrollPane();
        tbRiwayatEdukasi2 = new widget.Table();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        DlgObservasiDialisis.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgObservasiDialisis.setName("DlgObservasiDialisis"); // NOI18N
        DlgObservasiDialisis.setUndecorated(true);
        DlgObservasiDialisis.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102), 3), "::[ Data On HD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        BtnKeluarKehamilan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarKehamilan.setMnemonic('U');
        BtnKeluarKehamilan.setText("Tutup");
        BtnKeluarKehamilan.setToolTipText("Alt+U");
        BtnKeluarKehamilan.setName("BtnKeluarKehamilan"); // NOI18N
        BtnKeluarKehamilan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarKehamilan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarKehamilanActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarKehamilan);
        BtnKeluarKehamilan.setBounds(310, 190, 100, 30);

        BtnSimpanRiwayatKehamilan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanRiwayatKehamilan.setMnemonic('S');
        BtnSimpanRiwayatKehamilan.setText("Simpan");
        BtnSimpanRiwayatKehamilan.setToolTipText("Alt+S");
        BtnSimpanRiwayatKehamilan.setName("BtnSimpanRiwayatKehamilan"); // NOI18N
        BtnSimpanRiwayatKehamilan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanRiwayatKehamilanActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanRiwayatKehamilan);
        BtnSimpanRiwayatKehamilan.setBounds(200, 190, 100, 30);

        jLabel75.setText("Tanggal :");
        jLabel75.setName("jLabel75"); // NOI18N
        jLabel75.setVerifyInputWhenFocusTarget(false);
        panelBiasa2.add(jLabel75);
        jLabel75.setBounds(-20, 10, 80, 23);

        jLabel76.setText("Petugas :");
        jLabel76.setName("jLabel76"); // NOI18N
        panelBiasa2.add(jLabel76);
        jLabel76.setBounds(230, 70, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        panelBiasa2.add(NIP);
        NIP.setBounds(300, 70, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        panelBiasa2.add(NamaPetugas);
        NamaPetugas.setBounds(400, 70, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        panelBiasa2.add(btnPetugas);
        btnPetugas.setBounds(590, 70, 28, 23);

        UFR.setFocusTraversalPolicyProvider(true);
        UFR.setName("UFR"); // NOI18N
        UFR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UFRKeyPressed(evt);
            }
        });
        panelBiasa2.add(UFR);
        UFR.setBounds(100, 70, 60, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("x/menit");
        jLabel77.setName("jLabel77"); // NOI18N
        panelBiasa2.add(jLabel77);
        jLabel77.setBounds(270, 40, 50, 23);

        HR.setFocusTraversalPolicyProvider(true);
        HR.setName("HR"); // NOI18N
        HR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRKeyPressed(evt);
            }
        });
        panelBiasa2.add(HR);
        HR.setBounds(220, 40, 40, 23);

        jLabel78.setText("HR :");
        jLabel78.setName("jLabel78"); // NOI18N
        panelBiasa2.add(jLabel78);
        jLabel78.setBounds(170, 40, 40, 23);

        jLabel79.setText("VP :");
        jLabel79.setName("jLabel79"); // NOI18N
        panelBiasa2.add(jLabel79);
        jLabel79.setBounds(510, 40, 40, 23);

        VP.setFocusTraversalPolicyProvider(true);
        VP.setName("VP"); // NOI18N
        VP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VPKeyPressed(evt);
            }
        });
        panelBiasa2.add(VP);
        VP.setBounds(560, 40, 50, 23);

        jLabel80.setText("TD :");
        jLabel80.setName("jLabel80"); // NOI18N
        panelBiasa2.add(jLabel80);
        jLabel80.setBounds(0, 40, 40, 23);

        TD1.setFocusTraversalPolicyProvider(true);
        TD1.setName("TD1"); // NOI18N
        TD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TD1KeyPressed(evt);
            }
        });
        panelBiasa2.add(TD1);
        TD1.setBounds(40, 40, 70, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("mmHg");
        jLabel81.setName("jLabel81"); // NOI18N
        panelBiasa2.add(jLabel81);
        jLabel81.setBounds(120, 40, 40, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("mL/menit");
        jLabel83.setName("jLabel83"); // NOI18N
        panelBiasa2.add(jLabel83);
        jLabel83.setBounds(450, 40, 50, 23);

        QB.setFocusTraversalPolicyProvider(true);
        QB.setName("QB"); // NOI18N
        QB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QBKeyPressed(evt);
            }
        });
        panelBiasa2.add(QB);
        QB.setBounds(380, 40, 60, 23);

        jLabel84.setText("QB :");
        jLabel84.setName("jLabel84"); // NOI18N
        panelBiasa2.add(jLabel84);
        jLabel84.setBounds(330, 40, 40, 23);

        jLabel87.setText("UFG :");
        jLabel87.setName("jLabel87"); // NOI18N
        panelBiasa2.add(jLabel87);
        jLabel87.setBounds(150, 70, 40, 23);

        UFG.setFocusTraversalPolicyProvider(true);
        UFG.setName("UFG"); // NOI18N
        UFG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UFGActionPerformed(evt);
            }
        });
        UFG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UFGKeyPressed(evt);
            }
        });
        panelBiasa2.add(UFG);
        UFG.setBounds(200, 70, 40, 23);

        jLabel88.setText("UF REMOVED :");
        jLabel88.setName("jLabel88"); // NOI18N
        panelBiasa2.add(jLabel88);
        jLabel88.setBounds(10, 70, 80, 23);

        scrollPane3.setName("scrollPane3"); // NOI18N

        TMasalah.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MASALAH/TINDAKAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        TMasalah.setColumns(20);
        TMasalah.setRows(5);
        TMasalah.setName("TMasalah"); // NOI18N
        TMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TMasalahKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TMasalah);

        panelBiasa2.add(scrollPane3);
        scrollPane3.setBounds(20, 100, 590, 80);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-04-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelBiasa2.add(Tanggal);
        Tanggal.setBounds(70, 10, 160, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        panelBiasa2.add(Jam);
        Jam.setBounds(250, 10, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        panelBiasa2.add(Menit);
        Menit.setBounds(320, 10, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        panelBiasa2.add(Detik);
        Detik.setBounds(400, 10, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        panelBiasa2.add(ChkKejadian);
        ChkKejadian.setBounds(470, 10, 23, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgObservasiDialisis.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Observasi Pasien Dialisis  ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 450));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(1100, 920));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(70, 40, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(310, 40, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(210, 40, 90, 23);

        label14.setText("Kewarganegaraan :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(640, 100, 100, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(110, 70, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        NmPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmPetugasActionPerformed(evt);
            }
        });
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(220, 70, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(400, 70, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 40, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(640, 40, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(780, 40, 110, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 40, 70, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(430, 70, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 40, 30, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-04-2025 12:11:03" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(490, 70, 150, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 160, 960, 1);

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbRiwayatEdukasi.setName("tbRiwayatEdukasi"); // NOI18N
        Scroll6.setViewportView(tbRiwayatEdukasi);

        FormInput.add(Scroll6);
        Scroll6.setBounds(290, 460, 670, 210);

        BtnTambahMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah.setMnemonic('3');
        BtnTambahMasalah.setToolTipText("Alt+3");
        BtnTambahMasalah.setName("BtnTambahMasalah"); // NOI18N
        BtnTambahMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalahActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahMasalah);
        BtnTambahMasalah.setBounds(260, 460, 28, 23);

        BtnHapusRiwayatPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusRiwayatPersalinan.setMnemonic('3');
        BtnHapusRiwayatPersalinan.setToolTipText("Alt+3");
        BtnHapusRiwayatPersalinan.setName("BtnHapusRiwayatPersalinan"); // NOI18N
        BtnHapusRiwayatPersalinan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapusRiwayatPersalinan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusRiwayatPersalinanActionPerformed(evt);
            }
        });
        FormInput.add(BtnHapusRiwayatPersalinan);
        BtnHapusRiwayatPersalinan.setBounds(260, 490, 28, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 680, 960, 3);

        label28.setText("Dokter :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(0, 70, 100, 23);

        Ruangan.setHighlighter(null);
        Ruangan.setName("Ruangan"); // NOI18N
        FormInput.add(Ruangan);
        Ruangan.setBounds(110, 100, 290, 23);

        label29.setText("Agama :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(380, 100, 110, 23);

        ChbWarganegara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "WNI", "WNA" }));
        ChbWarganegara.setName("ChbWarganegara"); // NOI18N
        ChbWarganegara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChbWarganegaraKeyPressed(evt);
            }
        });
        FormInput.add(ChbWarganegara);
        ChbWarganegara.setBounds(750, 100, 140, 23);

        label30.setText("Pendidikan :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(0, 130, 100, 23);

        Agama.setEditable(false);
        Agama.setHighlighter(null);
        Agama.setName("Agama"); // NOI18N
        FormInput.add(Agama);
        Agama.setBounds(500, 100, 140, 23);

        ChbSumberData.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga" }));
        ChbSumberData.setName("ChbSumberData"); // NOI18N
        ChbSumberData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChbSumberDataKeyPressed(evt);
            }
        });
        FormInput.add(ChbSumberData);
        ChbSumberData.setBounds(750, 70, 140, 23);

        label31.setText("Sumber Data :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(640, 70, 100, 23);

        TAlamat.setEditable(false);
        TAlamat.setHighlighter(null);
        TAlamat.setName("TAlamat"); // NOI18N
        FormInput.add(TAlamat);
        TAlamat.setBounds(320, 130, 270, 23);

        label32.setText("Ruang :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(0, 100, 100, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("A. IDENTITAS PASIEN");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 10, 300, 23);

        cmbRwyAlergi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        cmbRwyAlergi.setName("cmbRwyAlergi"); // NOI18N
        cmbRwyAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbRwyAlergiKeyPressed(evt);
            }
        });
        FormInput.add(cmbRwyAlergi);
        cmbRwyAlergi.setBounds(120, 300, 128, 23);

        jLabel38.setText("Kesadaran :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(40, 190, 70, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 290, 960, 1);

        jLabel37.setText("Bila IYA Sebutkan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(230, 300, 140, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(380, 300, 510, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("B. KEADAAN UMUM");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 160, 180, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(520, 220, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(460, 220, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(410, 220, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(280, 220, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(330, 220, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(110, 220, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(150, 220, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(380, 220, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(230, 220, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(650, 220, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(600, 220, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(560, 220, 40, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KesadaranActionPerformed(evt);
            }
        });
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(120, 190, 130, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Vital Sign :");
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(50, 220, 70, 23);

        jLabel12.setText("BB Kering :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(260, 330, 70, 23);

        BBKering.setFocusTraversalPolicyProvider(true);
        BBKering.setName("BBKering"); // NOI18N
        BBKering.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBKeringActionPerformed(evt);
            }
        });
        BBKering.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeringKeyPressed(evt);
            }
        });
        FormInput.add(BBKering);
        BBKering.setBounds(340, 330, 50, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(260, 190, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(340, 190, 100, 23);

        jLabel29.setText("Keluhan :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(340, 250, 110, 23);

        Nyeri.setFocusTraversalPolicyProvider(true);
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(120, 250, 250, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("EWS :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(460, 190, 50, 23);

        jLabel42.setText(":");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 220, 102, 23);

        ews.setFocusTraversalPolicyProvider(true);
        ews.setName("ews"); // NOI18N
        ews.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ewsKeyPressed(evt);
            }
        });
        FormInput.add(ews);
        ews.setBounds(500, 190, 110, 23);

        Keluhan.setFocusTraversalPolicyProvider(true);
        Keluhan.setName("Keluhan"); // NOI18N
        Keluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanKeyPressed(evt);
            }
        });
        FormInput.add(Keluhan);
        Keluhan.setBounds(460, 250, 430, 23);

        jLabel32.setText("Skala Nyeri :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 250, 110, 23);

        jLabel43.setText("Ke : ");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(200, 390, 70, 23);

        jLabel47.setText("Riwayat Alergi :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(-10, 300, 120, 23);

        cmbVaskuler.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "AV (CIMINO)", "Femoral", "Double Lumen Chateter" }));
        cmbVaskuler.setName("cmbVaskuler"); // NOI18N
        cmbVaskuler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbVaskulerKeyPressed(evt);
            }
        });
        FormInput.add(cmbVaskuler);
        cmbVaskuler.setBounds(120, 330, 128, 23);

        jLabel48.setForeground(new java.awt.Color(255, 0, 0));
        jLabel48.setText("<Ketika Ganti/Update Pastikan Tanggal Sesuai dengan data yang Di Ganti/Update");
        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(890, 70, 480, 23);

        TLokasi.setFocusTraversalPolicyProvider(true);
        TLokasi.setName("TLokasi"); // NOI18N
        TLokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLokasiKeyPressed(evt);
            }
        });
        FormInput.add(TLokasi);
        TLokasi.setBounds(120, 360, 330, 23);

        jLabel50.setText("Lokasi :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 360, 110, 23);

        TNoMesin.setFocusTraversalPolicyProvider(true);
        TNoMesin.setName("TNoMesin"); // NOI18N
        TNoMesin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoMesinKeyPressed(evt);
            }
        });
        FormInput.add(TNoMesin);
        TNoMesin.setBounds(550, 360, 340, 23);

        jLabel51.setText("No. Mesin :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(410, 360, 130, 23);

        cmbDializer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baru", "Reause" }));
        cmbDializer.setName("cmbDializer"); // NOI18N
        cmbDializer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDializerKeyPressed(evt);
            }
        });
        FormInput.add(cmbDializer);
        cmbDializer.setBounds(120, 390, 90, 23);

        jLabel52.setText("Dialiset :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(430, 390, 110, 23);

        dializerke.setFocusTraversalPolicyProvider(true);
        dializerke.setName("dializerke"); // NOI18N
        dializerke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dializerkeKeyPressed(evt);
            }
        });
        FormInput.add(dializerke);
        dializerke.setBounds(270, 390, 180, 23);

        jLabel14.setText("BB Sekarang :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(430, 330, 70, 23);

        BBSekarang.setFocusTraversalPolicyProvider(true);
        BBSekarang.setName("BBSekarang"); // NOI18N
        BBSekarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBSekarangKeyPressed(evt);
            }
        });
        FormInput.add(BBSekarang);
        BBSekarang.setBounds(510, 330, 50, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Kg");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(570, 330, 30, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Kg");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(400, 330, 30, 23);

        TDialisat.setFocusTraversalPolicyProvider(true);
        TDialisat.setName("TDialisat"); // NOI18N
        TDialisat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDialisatKeyPressed(evt);
            }
        });
        FormInput.add(TDialisat);
        TDialisat.setBounds(550, 390, 340, 23);

        TFlowRate.setName("TFlowRate"); // NOI18N
        TFlowRate.setPreferredSize(new java.awt.Dimension(207, 23));
        TFlowRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFlowRateKeyPressed(evt);
            }
        });
        FormInput.add(TFlowRate);
        TFlowRate.setBounds(140, 610, 60, 23);

        jLabel41.setText("Kontinyu :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(20, 580, 90, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("D. DATA ON HD");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(290, 440, 180, 23);

        TKontinyu.setFocusTraversalPolicyProvider(true);
        TKontinyu.setName("TKontinyu"); // NOI18N
        TKontinyu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKontinyuKeyPressed(evt);
            }
        });
        FormInput.add(TKontinyu);
        TKontinyu.setBounds(120, 580, 60, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("IU");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(200, 580, 30, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("C. INSTRUKSI HD (DI ISI OLEH DOKTER)");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 440, 210, 23);

        cmdHeparin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Reguler", "Minimal", "Free" }));
        cmdHeparin.setName("cmdHeparin"); // NOI18N
        cmdHeparin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmdHeparinKeyPressed(evt);
            }
        });
        FormInput.add(cmdHeparin);
        cmdHeparin.setBounds(140, 640, 110, 23);

        jLabel54.setText("Heparin :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 640, 120, 23);

        jLabel56.setText("Ultra Goal :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(20, 490, 90, 23);

        jLabel57.setText("Total :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(20, 520, 90, 23);

        jLabel58.setText("Bolus Awal :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(20, 550, 90, 23);

        TLamaHD.setFocusTraversalPolicyProvider(true);
        TLamaHD.setName("TLamaHD"); // NOI18N
        TLamaHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLamaHDKeyPressed(evt);
            }
        });
        FormInput.add(TLamaHD);
        TLamaHD.setBounds(120, 460, 60, 23);

        TUltraGoal.setFocusTraversalPolicyProvider(true);
        TUltraGoal.setName("TUltraGoal"); // NOI18N
        TUltraGoal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TUltraGoalActionPerformed(evt);
            }
        });
        TUltraGoal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUltraGoalKeyPressed(evt);
            }
        });
        FormInput.add(TUltraGoal);
        TUltraGoal.setBounds(120, 490, 60, 23);

        TTotal.setFocusTraversalPolicyProvider(true);
        TTotal.setName("TTotal"); // NOI18N
        TTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTotalKeyPressed(evt);
            }
        });
        FormInput.add(TTotal);
        TTotal.setBounds(120, 520, 60, 23);

        TBolusAwal.setFocusTraversalPolicyProvider(true);
        TBolusAwal.setName("TBolusAwal"); // NOI18N
        TBolusAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBolusAwalKeyPressed(evt);
            }
        });
        FormInput.add(TBolusAwal);
        TBolusAwal.setBounds(120, 550, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Jam");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(190, 460, 30, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("ml/menit");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(210, 610, 50, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("IU");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(200, 520, 30, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("IU");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(200, 550, 30, 23);

        jLabel59.setText("Lama HD :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(20, 460, 90, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("ml");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(200, 490, 30, 23);

        jLabel60.setText("Blood Flow Rate  (qb) : ");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(10, 610, 120, 23);

        jLabel61.setText("Dializer :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 390, 110, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 430, 960, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("E. DATA POST HD");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 690, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Diagnosis);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(40, 800, 400, 43);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Obat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Obat.setColumns(20);
        Obat.setRows(3);
        Obat.setName("Obat"); // NOI18N
        Obat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Obat);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(40, 730, 400, 43);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Keluhan :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(40, 780, 150, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Cairan yang masuik selama HD :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(450, 710, 170, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Obat yang diberikan selama HD :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(40, 710, 170, 23);

        TTD.setName("TTD"); // NOI18N
        TTD.setPreferredSize(new java.awt.Dimension(207, 23));
        TTD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTDKeyPressed(evt);
            }
        });
        FormInput.add(TTD);
        TTD.setBounds(720, 730, 60, 23);

        jLabel44.setText("Jumlah :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(450, 820, 90, 23);

        TJumlah.setFocusTraversalPolicyProvider(true);
        TJumlah.setName("TJumlah"); // NOI18N
        TJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TJumlahKeyPressed(evt);
            }
        });
        FormInput.add(TJumlah);
        TJumlah.setBounds(550, 820, 60, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("cc");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(620, 820, 30, 23);

        jLabel62.setText("TD :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(640, 730, 70, 23);

        jLabel63.setText("Tranfusi :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(450, 760, 90, 23);

        jLabel64.setText("Minum :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(450, 790, 90, 23);

        TPriming.setFocusTraversalPolicyProvider(true);
        TPriming.setName("TPriming"); // NOI18N
        TPriming.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPrimingKeyPressed(evt);
            }
        });
        FormInput.add(TPriming);
        TPriming.setBounds(550, 730, 60, 23);

        TTranfusi.setFocusTraversalPolicyProvider(true);
        TTranfusi.setName("TTranfusi"); // NOI18N
        TTranfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTranfusiKeyPressed(evt);
            }
        });
        FormInput.add(TTranfusi);
        TTranfusi.setBounds(550, 760, 60, 23);

        TMinum.setFocusTraversalPolicyProvider(true);
        TMinum.setName("TMinum"); // NOI18N
        TMinum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TMinumKeyPressed(evt);
            }
        });
        FormInput.add(TMinum);
        TMinum.setBounds(550, 790, 60, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("cc");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(620, 730, 30, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("mmHg");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(790, 730, 50, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("cc");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(620, 790, 30, 23);

        jLabel65.setText("Sisa Priming :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(450, 730, 90, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("cc");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(620, 760, 30, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Vital Sign :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(660, 710, 120, 23);

        jLabel53.setText("RR :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(620, 820, 90, 23);

        TRRPost.setFocusTraversalPolicyProvider(true);
        TRRPost.setName("TRRPost"); // NOI18N
        TRRPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRRPostKeyPressed(evt);
            }
        });
        FormInput.add(TRRPost);
        TRRPost.setBounds(720, 820, 60, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("x/menit");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(790, 820, 50, 23);

        jLabel69.setText("Suhu :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(620, 760, 90, 23);

        jLabel70.setText("Nadi :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(620, 790, 90, 23);

        TSuhuPost.setFocusTraversalPolicyProvider(true);
        TSuhuPost.setName("TSuhuPost"); // NOI18N
        TSuhuPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuPostKeyPressed(evt);
            }
        });
        FormInput.add(TSuhuPost);
        TSuhuPost.setBounds(720, 760, 60, 23);

        TNasiPost.setFocusTraversalPolicyProvider(true);
        TNasiPost.setName("TNasiPost"); // NOI18N
        TNasiPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNasiPostKeyPressed(evt);
            }
        });
        FormInput.add(TNasiPost);
        TNasiPost.setBounds(720, 790, 60, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("x/menit");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(790, 790, 40, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("°C");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(790, 760, 30, 23);

        jLabel73.setText("BB Post HD:");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(620, 850, 90, 23);

        TBBPost.setFocusTraversalPolicyProvider(true);
        TBBPost.setName("TBBPost"); // NOI18N
        TBBPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBBPostKeyPressed(evt);
            }
        });
        FormInput.add(TBBPost);
        TBBPost.setBounds(720, 850, 60, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Kg");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(790, 850, 50, 23);

        label15.setText("Perawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(20, 860, 70, 23);

        KdPetugas1.setEditable(false);
        KdPetugas1.setName("KdPetugas1"); // NOI18N
        KdPetugas1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas1);
        KdPetugas1.setBounds(100, 860, 90, 23);

        NmPetugas1.setEditable(false);
        NmPetugas1.setName("NmPetugas1"); // NOI18N
        NmPetugas1.setPreferredSize(new java.awt.Dimension(207, 23));
        NmPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmPetugas1ActionPerformed(evt);
            }
        });
        FormInput.add(NmPetugas1);
        NmPetugas1.setBounds(190, 860, 230, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        BtnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter2KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(420, 860, 28, 23);

        label33.setText("Diagnosa :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(560, 130, 90, 23);

        TAlamat1.setHighlighter(null);
        TAlamat1.setName("TAlamat1"); // NOI18N
        FormInput.add(TAlamat1);
        TAlamat1.setBounds(110, 130, 150, 23);

        TDiagnosa.setHighlighter(null);
        TDiagnosa.setName("TDiagnosa"); // NOI18N
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(660, 130, 230, 23);

        label34.setText("Alamat :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(210, 130, 100, 23);

        jLabel89.setText("Akses Vaskuler :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 330, 110, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Data", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-04-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-04-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel34.setText("Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabel34);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(200, 23));
        FormMenu.add(TPasien1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Cetak");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(75, 23));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPrint1);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        FormMasalahRencana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormMasalahRencana.setName("FormMasalahRencana"); // NOI18N
        FormMasalahRencana.setLayout(new java.awt.GridLayout(3, 0, 1, 1));

        scrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Riwayat ON HD :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane9.setName("scrollPane9"); // NOI18N

        tbRiwayatEdukasi2.setName("tbRiwayatEdukasi2"); // NOI18N
        scrollPane9.setViewportView(tbRiwayatEdukasi2);

        FormMasalahRencana.add(scrollPane9);
        scrollPane9.getAccessibleContext().setAccessibleName("Data on HD :");

        PanelAccor.add(FormMasalahRencana, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Observasi", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else if (KdPetugas1.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Petugas");
        } else {
            simpan();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
//            Valid.pindah(evt,Tindakan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString())) {
                    hapus();
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Petugas");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    ganti();
                } else {
                    if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString())) {
                        ganti();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint1);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                ChkAccor.setSelected(true);
                isMenu();
                tampilEdukasi2();
            } catch (java.lang.NullPointerException e) {
            }
            if ((evt.getClickCount() == 2) && (tbObat.getSelectedColumn() == 0)) {
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    tampilEdukasi2();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilEdukasi();
        tampilEdukasi2();
    }//GEN-LAST:event_formWindowOpened

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            isMenu();
        } else {
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "' order by tgl_masuk desc limit 1");
            if (!kamar.equals("")) {
                namakamar = kamar + ", " + Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "
                        + " where kamar.kd_kamar='" + kamar + "' ");
                kamar = "Kamar";
            } else if (kamar.equals("")) {
                kamar = "Poli";
                namakamar = Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
                        + "where reg_periksa.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            param.put("alamat", Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("agama", Sequel.cariIsi("select agama from pasien where no_rkm_medis=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("pekerjaan", Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("dpjp", Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString()));
            param.put("namapet", Sequel.cariIsi("SELECT nama FROM petugas WHERE nip = ?", tbObat.getValueAt(tbObat.getSelectedRow(), 48).toString()));
            param.put("diagnosa", Sequel.cariIsi("SELECT diagnosa FROM observasi_dialisis_pasien WHERE no_rawat = ?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()));
            param.put("kamar", kamar);
            param.put("namakamar", namakamar);
            String pas = " and reg_periksa.no_rkm_medis like '%" + TCari.getText() + "%' ";
            String tgl = tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString();
            try ( PreparedStatement ps = koneksi.prepareStatement("SELECT * FROM catatan_observasi_dialisis WHERE no_rawat = ? and tgl_perawatan = ?")) {
                ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                ps.setString(2, tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());

                try ( ResultSet rs = ps.executeQuery()) {
                    int i = 1;
                    while (rs.next()) {
                        param.put("jam" + i, rs.getString("jam_rawat"));
                        param.put("td" + i, rs.getString("td"));
                        param.put("nadi" + i, rs.getString("hr"));
                        param.put("qb" + i, rs.getString("qb"));
                        param.put("vp" + i, rs.getString("vp"));
                        param.put("ufg" + i, rs.getString("ufg"));
                        param.put("ufr" + i, rs.getString("ufremov"));
                        param.put("masalah" + i, rs.getString("masalah_tindakan"));
                        param.put("petugas" + i, rs.getString("nip"));
                        param.put("nama_petugas" + i, rs.getString("nama"));
                        i++;
                    }
                } catch (SQLException e) {
                    System.out.println("Error while processing ResultSet: " + e.getMessage());
                }
            } catch (SQLException e) {
                System.out.println("Error while preparing statement: " + e.getMessage());
            }
            //finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            //param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString() + "\nID " + (finger.equals("") ? tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString() : finger) + "\n" + Tanggal.getSelectedItem());
            Valid.MyReportqry("rptCetakObservasiPasienDislisis.jasper", "report", "::[ Observasi Pasien Hemodialisa ]::",
                    "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, "
                    + "IF(pasien.jk='L','Laki-Laki','Perempuan') AS jk, pasien.tgl_lahir, "
                    + "observasi_dialisis_pasien.tanggal, observasi_dialisis_pasien.kd_dokter, "
                    + "observasi_dialisis_pasien.sumberdata, observasi_dialisis_pasien.kewarganegaraan, "
                    + "observasi_dialisis_pasien.kesadaran, observasi_dialisis_pasien.gcs, "
                    + "observasi_dialisis_pasien.ews, observasi_dialisis_pasien.td, observasi_dialisis_pasien.suhu, "
                    + "observasi_dialisis_pasien.nadi, observasi_dialisis_pasien.rr, observasi_dialisis_pasien.nyeri, "
                    + "observasi_dialisis_pasien.keluhan, observasi_dialisis_pasien.alergi, "
                    + "observasi_dialisis_pasien.adanyeri, observasi_dialisis_pasien.vaskuler, "
                    + "observasi_dialisis_pasien.bbkering, observasi_dialisis_pasien.bbsekarang, "
                    + "observasi_dialisis_pasien.lokasi, observasi_dialisis_pasien.mesin, "
                    + "observasi_dialisis_pasien.dializer, observasi_dialisis_pasien.ke, "
                    + "observasi_dialisis_pasien.dialisat, observasi_dialisis_pasien.lamahd, "
                    + "observasi_dialisis_pasien.ultragoal, observasi_dialisis_pasien.total, "
                    + "observasi_dialisis_pasien.bolusawal, observasi_dialisis_pasien.kontinyu, "
                    + "observasi_dialisis_pasien.bfr, observasi_dialisis_pasien.heparin, "
                    + "observasi_dialisis_pasien.obat_selama_hd, observasi_dialisis_pasien.keluahan_post_hd, "
                    + "observasi_dialisis_pasien.sisa_priming, observasi_dialisis_pasien.transfusi, "
                    + "observasi_dialisis_pasien.minum, observasi_dialisis_pasien.jumlah, "
                    + "observasi_dialisis_pasien.td_pos, observasi_dialisis_pasien.suhu_pos, "
                    + "observasi_dialisis_pasien.nadi_pos, observasi_dialisis_pasien.rr_pos, "
                    + "observasi_dialisis_pasien.bb_pos, observasi_dialisis_pasien.pendidikan, "
                    + "observasi_dialisis_pasien.nip "
                    + "FROM reg_periksa "
                    + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                    + "INNER JOIN observasi_dialisis_pasien ON reg_periksa.no_rawat = observasi_dialisis_pasien.no_rawat "
                    + "LEFT JOIN dokter ON observasi_dialisis_pasien.kd_dokter = dokter.kd_dokter "
                    + "LEFT JOIN petugas ON observasi_dialisis_pasien.nip = petugas.nip "
                    + "WHERE observasi_dialisis_pasien.tanggal='" + tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString() + "'", param);
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        if (TNoRM.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih terlebih dahulu pasien yang mau dimasukkan data kelarihannya...");
        } else {
            emptTeksPersalinan();
            DlgObservasiDialisis.setLocationRelativeTo(internalFrame1);
            DlgObservasiDialisis.setSize(640, 250);
            DlgObservasiDialisis.setVisible(true);
        }
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnHapusRiwayatPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusRiwayatPersalinanActionPerformed
        if (tbRiwayatEdukasi.getSelectedRow() > -1) {
            Sequel.meghapus("catatan_observasi_dialisis", "no_rawat", "jam_rawat", TNoRw.getText(), tbRiwayatEdukasi.getValueAt(tbRiwayatEdukasi.getSelectedRow(), 0).toString());
            tampilEdukasi();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil dihapus.");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnHapusRiwayatPersalinanActionPerformed

    private void NmPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasActionPerformed

    private void ChbWarganegaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChbWarganegaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChbWarganegaraKeyPressed

    private void ChbSumberDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChbSumberDataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChbSumberDataKeyPressed

    private void cmbRwyAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbRwyAlergiKeyPressed
        Valid.pindah(evt, cmbRwyAlergi, Alergi);
    }//GEN-LAST:event_cmbRwyAlergiKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt, Alergi, cmbVaskuler);
    }//GEN-LAST:event_AlergiKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt, Nadi, RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt, Suhu, Nadi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt, TD, Suhu);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt, RR, Nyeri);
    }//GEN-LAST:event_RRKeyPressed

    private void KesadaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KesadaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KesadaranActionPerformed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt, Alergi, TD);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void BBKeringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeringKeyPressed
        Valid.pindah(evt, BBKering, BBSekarang);
    }//GEN-LAST:event_BBKeringKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt, GCS, ews);
    }//GEN-LAST:event_GCSKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt, Nyeri, Keluhan);
    }//GEN-LAST:event_NyeriKeyPressed

    private void ewsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ewsKeyPressed
        Valid.pindah(evt, ews, TD);
    }//GEN-LAST:event_ewsKeyPressed

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        Valid.pindah(evt, Keluhan, cmbRwyAlergi);
    }//GEN-LAST:event_KeluhanKeyPressed

    private void cmbVaskulerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbVaskulerKeyPressed
        Valid.pindah(evt, cmbVaskuler, BBKering);
    }//GEN-LAST:event_cmbVaskulerKeyPressed

    private void TLokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TLokasiKeyPressed
        Valid.pindah(evt, TLokasi, TNoMesin);
    }//GEN-LAST:event_TLokasiKeyPressed

    private void TNoMesinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoMesinKeyPressed
        Valid.pindah(evt, TNoMesin, cmbDializer);
    }//GEN-LAST:event_TNoMesinKeyPressed

    private void cmbDializerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDializerKeyPressed
        Valid.pindah(evt, cmbDializer, dializerke);
    }//GEN-LAST:event_cmbDializerKeyPressed

    private void dializerkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dializerkeKeyPressed
        Valid.pindah(evt, dializerke, TDialisat);
    }//GEN-LAST:event_dializerkeKeyPressed

    private void BBSekarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBSekarangKeyPressed
        Valid.pindah(evt, BBSekarang, TLokasi);
    }//GEN-LAST:event_BBSekarangKeyPressed

    private void TDialisatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDialisatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDialisatKeyPressed

    private void TFlowRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFlowRateKeyPressed
        Valid.pindah(evt, TFlowRate, cmdHeparin);
    }//GEN-LAST:event_TFlowRateKeyPressed

    private void TKontinyuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKontinyuKeyPressed
        Valid.pindah(evt, TKontinyu, TFlowRate);
    }//GEN-LAST:event_TKontinyuKeyPressed

    private void cmdHeparinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmdHeparinKeyPressed
        Valid.pindah(evt, cmdHeparin, Obat);
    }//GEN-LAST:event_cmdHeparinKeyPressed

    private void TLamaHDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TLamaHDKeyPressed
        Valid.pindah(evt, TLamaHD, TUltraGoal);
    }//GEN-LAST:event_TLamaHDKeyPressed

    private void TUltraGoalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUltraGoalKeyPressed
        Valid.pindah(evt, TUltraGoal, TTotal);
    }//GEN-LAST:event_TUltraGoalKeyPressed

    private void TTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTotalKeyPressed
        Valid.pindah(evt, TTotal, TBolusAwal);
    }//GEN-LAST:event_TTotalKeyPressed

    private void TBolusAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBolusAwalKeyPressed
        Valid.pindah(evt, TBolusAwal, TKontinyu);
    }//GEN-LAST:event_TBolusAwalKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah(evt, Diagnosis, TPriming);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void ObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatKeyPressed
        Valid.pindah(evt, Obat, Diagnosis);
    }//GEN-LAST:event_ObatKeyPressed

    private void TTDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTDKeyPressed
        Valid.pindah(evt, TTD, TSuhuPost);
    }//GEN-LAST:event_TTDKeyPressed

    private void TJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TJumlahKeyPressed
        Valid.pindah(evt, TJumlah, TTD);
    }//GEN-LAST:event_TJumlahKeyPressed

    private void TPrimingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPrimingKeyPressed
        Valid.pindah(evt, TPriming, TTranfusi);
    }//GEN-LAST:event_TPrimingKeyPressed

    private void TTranfusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTranfusiKeyPressed
        Valid.pindah(evt, TTranfusi, TMinum);
    }//GEN-LAST:event_TTranfusiKeyPressed

    private void TMinumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TMinumKeyPressed
        Valid.pindah(evt, TMinum, TJumlah);
    }//GEN-LAST:event_TMinumKeyPressed

    private void TRRPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRRPostKeyPressed
        Valid.pindah(evt, TRRPost, TBBPost);
    }//GEN-LAST:event_TRRPostKeyPressed

    private void TSuhuPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuPostKeyPressed
        Valid.pindah(evt, TSuhuPost, TNasiPost);
    }//GEN-LAST:event_TSuhuPostKeyPressed

    private void TNasiPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNasiPostKeyPressed
        Valid.pindah(evt, TNasiPost, TRRPost);
    }//GEN-LAST:event_TNasiPostKeyPressed

    private void TBBPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBBPostKeyPressed
        Valid.pindah(evt, TBBPost, NmPetugas1);
    }//GEN-LAST:event_TBBPostKeyPressed

    private void NmPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmPetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugas1ActionPerformed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void BtnKeluarKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarKehamilanActionPerformed
        DlgObservasiDialisis.dispose();
    }//GEN-LAST:event_BtnKeluarKehamilanActionPerformed

    private void BtnSimpanRiwayatKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanRiwayatKehamilanActionPerformed

        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NIP.getText().trim().equals("") || NamaPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIP, "Petugas");
        } else {
            String[] params = new String[]{
                TNoRw.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem().toString()),
                Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                TD1.getText(), HR.getText(), QB.getText(), VP.getText(), UFG.getText(), UFR.getText(), TMasalah.getText(), NIP.getText(), NamaPetugas.getText()
            };

            if (Sequel.menyimpantf("catatan_observasi_dialisis", "?,?,?,?,?,?,?,?,?,?,?,?", "Data", 12, params)) {
                emptTeksPersalinan();
                tampilEdukasi();
                tampilEdukasi2();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_BtnSimpanRiwayatKehamilanActionPerformed

    private void TUltraGoalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TUltraGoalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TUltraGoalActionPerformed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed

    }//GEN-LAST:event_NIPKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas3.emptTeks();
        petugas3.isCek();
        petugas3.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas3.setLocationRelativeTo(internalFrame1);
        petugas3.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed

    }//GEN-LAST:event_btnPetugasKeyPressed

    private void UFRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UFRKeyPressed
        Valid.pindah(evt, UFR, UFG);
    }//GEN-LAST:event_UFRKeyPressed

    private void HRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HRKeyPressed
        Valid.pindah(evt, TD, QB);
    }//GEN-LAST:event_HRKeyPressed

    private void VPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VPKeyPressed
        Valid.pindah(evt, VP, UFR);
    }//GEN-LAST:event_VPKeyPressed

    private void TD1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TD1KeyPressed
        Valid.pindah(evt, UFR, HR);
    }//GEN-LAST:event_TD1KeyPressed

    private void QBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QBKeyPressed
        Valid.pindah(evt, HR, VP);
    }//GEN-LAST:event_QBKeyPressed

    private void UFGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UFGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UFGActionPerformed

    private void UFGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UFGKeyPressed
        Valid.pindah(evt, VP, BtnSimpan);
    }//GEN-LAST:event_UFGKeyPressed

    private void TMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TMasalahKeyPressed

    }//GEN-LAST:event_TMasalahKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, TCari, Jam);
    }//GEN-LAST:event_TanggalKeyPressed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt, Tanggal, Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt, Jam, Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt, Menit, btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void BBKeringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBKeringActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BBKeringActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMObservasiPasienDialisis dialog = new RMObservasiPasienDialisis(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Agama;
    private widget.TextBox Alergi;
    private widget.TextBox BBKering;
    private widget.TextBox BBSekarang;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusRiwayatPersalinan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarKehamilan;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanRiwayatKehamilan;
    private widget.Button BtnTambahMasalah;
    private widget.ComboBox ChbSumberData;
    private widget.ComboBox ChbWarganegara;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.TextArea Diagnosis;
    private javax.swing.JDialog DlgObservasiDialisis;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox GCS;
    private widget.TextBox HR;
    private widget.ComboBox Jam;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPetugas1;
    private widget.TextBox Keluhan;
    private widget.ComboBox Kesadaran;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private widget.TextBox NIP;
    private widget.TextBox Nadi;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas1;
    private widget.TextBox Nyeri;
    private widget.TextArea Obat;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox QB;
    private widget.TextBox RR;
    private widget.TextBox Ruangan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.TextBox Suhu;
    private widget.TextBox TAlamat;
    private widget.TextBox TAlamat1;
    private widget.TextBox TBBPost;
    private widget.TextBox TBolusAwal;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TD1;
    private widget.TextBox TDiagnosa;
    private widget.TextBox TDialisat;
    private widget.TextBox TFlowRate;
    private widget.TextBox TJumlah;
    private widget.TextBox TKontinyu;
    private widget.TextBox TLamaHD;
    private widget.TextBox TLokasi;
    private widget.TextArea TMasalah;
    private widget.TextBox TMinum;
    private widget.TextBox TNasiPost;
    private widget.TextBox TNoMesin;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.TextBox TPriming;
    private widget.TextBox TRRPost;
    private widget.TextBox TSuhuPost;
    private widget.TextBox TTD;
    private widget.TextBox TTotal;
    private widget.TextBox TTranfusi;
    private widget.TextBox TUltraGoal;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox UFG;
    private widget.TextBox UFR;
    private widget.TextBox VP;
    private widget.Button btnPetugas;
    private widget.ComboBox cmbDializer;
    private widget.ComboBox cmbRwyAlergi;
    private widget.ComboBox cmbVaskuler;
    private widget.ComboBox cmdHeparin;
    private widget.TextBox dializerke;
    private widget.TextBox ews;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel95;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator5;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    private widget.Table tbRiwayatEdukasi;
    private widget.Table tbRiwayatEdukasi2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, "
                    + "IF(pasien.jk='L','Laki-Laki','Perempuan') AS jk, pasien.tgl_lahir, "
                    + "observasi_dialisis_pasien.tanggal, observasi_dialisis_pasien.kd_dokter, "
                    + "observasi_dialisis_pasien.sumberdata, observasi_dialisis_pasien.kewarganegaraan, "
                    + "observasi_dialisis_pasien.kesadaran, observasi_dialisis_pasien.gcs, "
                    + "observasi_dialisis_pasien.ews, observasi_dialisis_pasien.td, observasi_dialisis_pasien.suhu, "
                    + "observasi_dialisis_pasien.nadi, observasi_dialisis_pasien.rr, observasi_dialisis_pasien.nyeri, "
                    + "observasi_dialisis_pasien.keluhan, observasi_dialisis_pasien.alergi, "
                    + "observasi_dialisis_pasien.adanyeri, observasi_dialisis_pasien.vaskuler, "
                    + "observasi_dialisis_pasien.bbkering, observasi_dialisis_pasien.bbsekarang, "
                    + "observasi_dialisis_pasien.lokasi, observasi_dialisis_pasien.mesin, "
                    + "observasi_dialisis_pasien.dializer, observasi_dialisis_pasien.ke, "
                    + "observasi_dialisis_pasien.dialisat, observasi_dialisis_pasien.lamahd, "
                    + "observasi_dialisis_pasien.ultragoal, observasi_dialisis_pasien.total, "
                    + "observasi_dialisis_pasien.bolusawal, observasi_dialisis_pasien.kontinyu, "
                    + "observasi_dialisis_pasien.bfr, observasi_dialisis_pasien.heparin, "
                    + "observasi_dialisis_pasien.obat_selama_hd, observasi_dialisis_pasien.keluahan_post_hd, "
                    + "observasi_dialisis_pasien.sisa_priming, observasi_dialisis_pasien.transfusi, "
                    + "observasi_dialisis_pasien.minum, observasi_dialisis_pasien.jumlah, "
                    + "observasi_dialisis_pasien.td_pos, observasi_dialisis_pasien.suhu_pos, "
                    + "observasi_dialisis_pasien.nadi_pos, observasi_dialisis_pasien.rr_pos, "
                    + "observasi_dialisis_pasien.bb_pos, observasi_dialisis_pasien.pendidikan,observasi_dialisis_pasien.diagnosa, "
                    + "observasi_dialisis_pasien.nip "
                    + "FROM reg_periksa "
                    + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                    + "INNER JOIN observasi_dialisis_pasien ON reg_periksa.no_rawat = observasi_dialisis_pasien.no_rawat "
                    + "LEFT JOIN dokter ON observasi_dialisis_pasien.kd_dokter = dokter.kd_dokter "
                    + "LEFT JOIN petugas ON observasi_dialisis_pasien.nip = petugas.nip "
                    + "WHERE reg_periksa.no_rawat LIKE ? "
                    + (TCari.getText().trim().equals("") ? "" : "AND (reg_periksa.no_rawat LIKE ? OR pasien.no_rkm_medis LIKE ? OR pasien.nm_pasien LIKE ?) ")
                    + "ORDER BY observasi_dialisis_pasien.tanggal");

            ps.setString(1, "%" + TCari.getText() + "%");

            if (!TCari.getText().equals("")) {
                ps.setString(2, "%" + TCari.getText() + "%");
                ps.setString(3, "%" + TCari.getText() + "%");
                ps.setString(4, "%" + TCari.getText() + "%");
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                String namadok = Sequel.cariIsi("SELECT nm_dokter FROM dokter WHERE kd_dokter = ?", rs.getString("kd_dokter"));
                String namapet = Sequel.cariIsi("SELECT nama FROM petugas WHERE nip = ?", rs.getString("nip"));
                tabMode.addRow(new String[]{
                    rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                    rs.getString("jk"), rs.getString("tgl_lahir"), rs.getString("tanggal"),
                    rs.getString("kd_dokter"), namadok, rs.getString("sumberdata"), rs.getString("kewarganegaraan"),
                    rs.getString("kesadaran"), rs.getString("gcs"), rs.getString("ews"), rs.getString("td"),
                    rs.getString("suhu"), rs.getString("nadi"), rs.getString("rr"), rs.getString("nyeri"),
                    rs.getString("keluhan"), rs.getString("alergi"), rs.getString("adanyeri"),
                    rs.getString("vaskuler"), rs.getString("bbkering"), rs.getString("bbsekarang"),
                    rs.getString("lokasi"), rs.getString("mesin"), rs.getString("dializer"),
                    rs.getString("ke"), rs.getString("dialisat"), rs.getString("lamahd"),
                    rs.getString("ultragoal"), rs.getString("total"), rs.getString("bolusawal"),
                    rs.getString("kontinyu"), rs.getString("bfr"), rs.getString("heparin"),
                    rs.getString("obat_selama_hd"), rs.getString("keluahan_post_hd"), rs.getString("sisa_priming"),
                    rs.getString("transfusi"), rs.getString("minum"), rs.getString("jumlah"),
                    rs.getString("td_pos"), rs.getString("suhu_pos"), rs.getString("nadi_pos"),
                    rs.getString("rr_pos"), rs.getString("bb_pos"), rs.getString("pendidikan"), rs.getString("nip"), namapet, rs.getString("diagnosa")
                });
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        Kesadaran.setSelectedIndex(0);
        GCS.setText("");
        ews.setText("");
        TD.setText("");
        Suhu.setText("");
        Nadi.setText("");
        RR.setText("");
        Nyeri.setText("");
        Keluhan.setText("");
        cmbRwyAlergi.setSelectedIndex(0);
        Alergi.setText("");
        cmbVaskuler.setSelectedIndex(0);
        BBKering.setText("");
        BBSekarang.setText("");
        TLokasi.setText("");
        TNoMesin.setText("");
        cmbDializer.setSelectedIndex(0);
        dializerke.setText("");
        TDialisat.setText("");
        TLamaHD.setText("");
        TUltraGoal.setText("");
        TTotal.setText("");
        TBolusAwal.setText("");
        TKontinyu.setText("");
        TFlowRate.setText("");
        cmdHeparin.setSelectedIndex(0);
        Obat.setText("");
        Diagnosis.setText("");
        TPriming.setText("");
        TTranfusi.setText("");
        TMinum.setText("");
        TJumlah.setText("");
        TTD.setText("");
        TSuhuPost.setText("");
        TNasiPost.setText("");
        TRRPost.setText("");
        TBBPost.setText("");
        TAlamat1.setText("");
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            Valid.SetTgl2(TglAsuhan, tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            NmPetugas.setText(Sequel.cariIsi("select ifnull(nama,'') from pegawai where nik='" + KdDokter.getText() + "'"));
            ChbSumberData.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            ChbWarganegara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            ews.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Nyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            Keluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            cmbRwyAlergi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            cmbVaskuler.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            BBKering.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            BBSekarang.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            TLokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            TNoMesin.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            cmbDializer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            dializerke.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            TDialisat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
            TLamaHD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            TUltraGoal.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            TTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
            TBolusAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            TKontinyu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
            TFlowRate.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());
            cmdHeparin.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());
            Obat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());
            TPriming.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 38).toString());
            TTranfusi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 39).toString());
            TMinum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 40).toString());
            TJumlah.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 41).toString());
            TTD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 42).toString());
            TSuhuPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 43).toString());
            TNasiPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 44).toString());
            TRRPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 45).toString());
            TBBPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 46).toString());
            KdPetugas1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 48).toString());
            NmPetugas1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 49).toString());
            TDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 50).toString());
            tampilEdukasi();
            tampilEdukasi2();
        }
    }

    private void isRawat() {
        if (posisi.equals("Ranap")) {
            norawatibu = Sequel.cariIsi("select ranap_gabung.no_rawat from ranap_gabung where ranap_gabung.no_rawat2=?", TNoRw.getText());

            if (!norawatibu.equals("")) {
                kelas = Sequel.cariIsi(
                        "select kamar.kelas from kamar inner join kamar_inap "
                        + "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "
                        + "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", norawatibu);
            } else {
                kelas = Sequel.cariIsi(
                        "select kamar.kelas from kamar inner join kamar_inap "
                        + "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "
                        + "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", TNoRw.getText());
            }
        } else if (posisi.equals("Ralan")) {
            kelas = "Rawat Jalan";
        }
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis, pasien.nm_pasien, "
                    + "if(pasien.jk='L','Laki-Laki','Perempuan') as jk, "
                    + "pasien.tgl_lahir, pasien.agama, pasien.alamat, pasien.pekerjaan, pasien.pnd, "
                    + "bahasa_pasien.nama_bahasa, cacat_fisik.nama_cacat, reg_periksa.tgl_registrasi "
                    + "from reg_periksa "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "
                    + "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "
                    + "where reg_periksa.no_rawat=?"
            );

            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TAlamat1.setText(rs.getString("pnd"));
                    TAlamat.setText(rs.getString("alamat"));
                    Agama.setText(rs.getString("agama"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        tampilEdukasi();
        tampilEdukasi2();
    }

    // Tambahkan di sini 31 Maret 2026 👇
    public void loadAwal(){
        tampil();
    }



    public void setNoRm2(String norwt, String norm, String nama, String lokasi, String posisi) {
        TNoRw.setText(norwt);
        Ruangan.setText(lokasi);
        this.posisi = posisi;
        TCari.setText(norwt);
        isRawat();
        isPsien();
    }

    public void setNoRm3(String norwt, String norm, String nama, String lokasi, String posisi) {
        TNoRw.setText(norwt);
        Ruangan.setText(lokasi);
        this.posisi = posisi;
        TCari.setText(norwt);
        isRawat();
        isCekDPJP();
    }

    public void isCek() {

    }

    public void isCekDPJP() {
        try {
            pstindakan = koneksi.prepareStatement(
                    "SELECT dpjp_ranap.kd_dokter, dokter.nm_dokter FROM dpjp_ranap INNER JOIN dokter ON dpjp_ranap.kd_dokter = dokter.kd_dokter WHERE no_rawat=?");
            try {
                pstindakan.setString(1, TNoRw.getText());
                rstindakan = pstindakan.executeQuery();
                while (rstindakan.next()) {
                    KdDokter.setText(rstindakan.getString("kd_dokter"));
                    NmPetugas.setText(rstindakan.getString("nm_dokter"));

                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    public void setTampil() {
        TabRawat.setSelectedIndex(1);
    }

    private void isMenu() {
        if (ChkAccor.isSelected() == true) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470, HEIGHT));
            FormMenu.setVisible(true);
            FormMasalahRencana.setVisible(true);
            ChkAccor.setVisible(true);
        } else if (ChkAccor.isSelected() == false) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15, HEIGHT));
            FormMenu.setVisible(false);
            FormMasalahRencana.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    private void isPsien() {
        try {
            pstindakan = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,reg_periksa.kd_pj,reg_periksa.kd_dokter,dokter.nm_dokter,pasien.nm_pasien,pasien.jk,pasien.umur,"
                    + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "
                    + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "
                    + "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "
                    + "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where no_rawat=?");
            try {
                pstindakan.setString(1, TNoRw.getText());
                rstindakan = pstindakan.executeQuery();
                while (rstindakan.next()) {
                    KdDokter.setText(rstindakan.getString("kd_dokter"));
                    NmPetugas.setText(rstindakan.getString("nm_dokter"));

                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void emptTeksPersalinan() {
//        Materi.setText("");
//        Judul.setText("");
//        kd_pemberi.setText("");
//        penerima.setText("");
//        TglAsesmen1.setDate(new Date());
//        TglAsesmen.setDate(new Date());
//        Materi.requestFocus();
    }

    private void tampilEdukasi() {
        Valid.tabelKosong(tabModeRiwayatKehamilan);
        String query = "SELECT * FROM catatan_observasi_dialisis WHERE no_rawat = ?";

        try ( PreparedStatement ps = koneksi.prepareStatement(query)) {
            String noRawat = TNoRw.getText().trim();
            ps.setString(1, noRawat);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tabModeRiwayatKehamilan.addRow(new String[]{
                        rs.getString(3), rs.getString(2),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getString(11), rs.getString(12)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilEdukasi2() {
        Valid.tabelKosong(tabModeRiwayatKehamilan2);

        int selectedRow = tbObat.getSelectedRow();
        if (selectedRow != -1) {
            TNoRM1.setText(tbObat.getValueAt(selectedRow, 1).toString());
            TPasien1.setText(tbObat.getValueAt(selectedRow, 2).toString());
        } else {
            System.out.println("No row selected.");
            return;
        }

        String query = "SELECT * FROM catatan_observasi_dialisis WHERE no_rawat = ?";

        try ( PreparedStatement ps = koneksi.prepareStatement(query)) {
            String noRawat = TNoRw.getText().trim();
            ps.setString(1, noRawat);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String col3 = rs.getString(3);
                    if (col3 != null) {
                        tabModeRiwayatKehamilan2.addRow(new String[]{
                            col3, rs.getString(2),
                            rs.getString(4), rs.getString(5), rs.getString(6),
                            rs.getString(7), rs.getString(8), rs.getString(9),
                            rs.getString(10), rs.getString(11), rs.getString(12)
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Notif : " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Notifikasi : " + e.getMessage());
        }
    }

    private void hapus() {
        String noRawat = tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString();
        String tanggal = tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString();
        if (Sequel.queryu2tf("delete from observasi_dialisis_pasien where no_rawat=? and tanggal=?", 2, new String[]{noRawat, tanggal})) {
            TNoRM1.setText("");
            TPasien1.setText("");
            ChkAccor.setSelected(false);
            isMenu();
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void ganti() {
        //String selectedDate = Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19);
        String selectedDate = Valid.SetTgl(TglAsuhan.getSelectedItem() + "");
        //System.out.println("Tanggal yang dikirim: " + selectedDate);
        String queryCheck = "SELECT COUNT(*) FROM observasi_dialisis_pasien WHERE no_rawat=? AND tanggal=?";
        int recordCount = 0;
        try {
            PreparedStatement pst = koneksi.prepareStatement(queryCheck);
            pst.setString(1, TNoRw.getText());
            pst.setString(2, selectedDate);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                recordCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (recordCount > 0) {
            boolean isUpdated = Sequel.mengedittf("observasi_dialisis_pasien",
                    "no_rawat=? AND tanggal=?",
                    "no_rawat=?, tanggal=?, kd_dokter=?, sumberdata=?, kewarganegaraan=?, kesadaran=?, gcs=?, ews=?, td=?, suhu=?, nadi=?, rr=?, nyeri=?, keluhan=?, "
                    + "alergi=?, adanyeri=?, vaskuler=?, bbkering=?, bbsekarang=?, lokasi=?, mesin=?, dializer=?, ke=?, dialisat=?, lamahd=?, ultragoal=?, "
                    + "total=?, bolusawal=?, kontinyu=?, bfr=?, heparin=?, obat_selama_hd=?, keluahan_post_hd=?, sisa_priming=?, transfusi=?, minum=?, "
                    + "jumlah=?, td_pos=?, suhu_pos=?, nadi_pos=?, rr_pos=?, bb_pos=?, pendidikan=?, diagnosa=?, nip=?",
                    47,
                    new String[]{
                        TNoRw.getText(),
                        selectedDate,
                        KdDokter.getText(),
                        ChbSumberData.getSelectedItem().toString(),
                        ChbWarganegara.getSelectedItem().toString(),
                        Kesadaran.getSelectedItem().toString(),
                        GCS.getText(),
                        ews.getText(),
                        TD.getText(),
                        Suhu.getText(),
                        Nadi.getText(),
                        RR.getText(),
                        Nyeri.getText(),
                        Keluhan.getText(),
                        cmbRwyAlergi.getSelectedItem().toString(),
                        Alergi.getText(),
                        cmbVaskuler.getSelectedItem().toString(),
                        BBKering.getText(),
                        BBSekarang.getText(),
                        TLokasi.getText(),
                        TNoMesin.getText(),
                        cmbDializer.getSelectedItem().toString(),
                        dializerke.getText(),
                        TDialisat.getText(),
                        TLamaHD.getText(),
                        TUltraGoal.getText(),
                        TTotal.getText(),
                        TBolusAwal.getText(),
                        TKontinyu.getText(),
                        TFlowRate.getText(),
                        cmdHeparin.getSelectedItem().toString(),
                        Obat.getText(),
                        Diagnosis.getText(),
                        TPriming.getText(),
                        TTranfusi.getText(),
                        TMinum.getText(),
                        TJumlah.getText(),
                        TTD.getText(),
                        TSuhuPost.getText(),
                        TNasiPost.getText(),
                        TRRPost.getText(),
                        TBBPost.getText(),
                        TAlamat1.getText(),
                        TDiagnosa.getText(),
                        KdPetugas1.getText(),
                        TNoRw.getText(),
                        selectedDate
                    });

            if (isUpdated) {
                tampilEdukasi2();
                tampil();
                emptTeks();
                TabRawat.setSelectedIndex(1);
                JOptionPane.showMessageDialog(null, "Data berhasil di Update", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Data gagal di Update", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data dengan tanggal tersebut tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpan() {
        try {
            if (Sequel.menyimpantf("observasi_dialisis_pasien", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 45, new String[]{
                TNoRw.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19),
                KdDokter.getText(), ChbSumberData.getSelectedItem().toString(), ChbWarganegara.getSelectedItem().toString(), Kesadaran.getSelectedItem().toString(),
                GCS.getText(), ews.getText(), TD.getText(), Suhu.getText(), Nadi.getText(), RR.getText(), Nyeri.getText(), Keluhan.getText(), cmbRwyAlergi.getSelectedItem().toString(),
                Alergi.getText(), cmbVaskuler.getSelectedItem().toString(), BBKering.getText(), BBSekarang.getText(), TLokasi.getText(), TNoMesin.getText(), cmbDializer.getSelectedItem().toString(),
                dializerke.getText(), TDialisat.getText(), TLamaHD.getText(), TUltraGoal.getText(), TTotal.getText(), TBolusAwal.getText(), TKontinyu.getText(), TFlowRate.getText(), cmdHeparin.getSelectedItem().toString(),
                Obat.getText(), Diagnosis.getText(), TPriming.getText(), TTranfusi.getText(), TMinum.getText(), TJumlah.getText(), TTD.getText(), TSuhuPost.getText(), TNasiPost.getText(), TRRPost.getText(), TBBPost.getText(),
                TAlamat1.getText(), TDiagnosa.getText(), KdPetugas1.getText()

            })) {
                emptTeks();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampil();
            } else {
                System.out.println("Data Gagal Disimpan.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Pesan Error: " + e.getMessage());

        }
    }

    private static class DefaultTableModelImpl extends DefaultTableModel {

        public DefaultTableModelImpl(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    }
}
