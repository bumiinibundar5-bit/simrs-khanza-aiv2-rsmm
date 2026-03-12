/*
 * Form Pemeriksaan EEG - Lab Neurofisiologi / Poliklinik Saraf
 * SIMRS Khanza - Rekam Medis
 * RS. Dr. Wahidin Sudirohusodo / RSUD
 *
 * Dibuat berdasarkan struktur form fisik Laboratorium EEG
 * Subdivisi Neurofisiologi - Poliklinik Brain Centre
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import kepegawaian.DlgCariDokter;

/**
 * Form Pemeriksaan EEG
 * @author IT RSUD
 */
public final class RMPemeriksaanEEG extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariDokter dokterPemeriksa = new DlgCariDokter(null, false);
    private String TANGGALMUNDUR = "yes";
    private StringBuilder htmlContent;

    public RMPemeriksaanEEG(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // === Setup Tabel Data ===
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Tgl.Lahir",
            "Tgl.Rekaman", "Diagnosa", "Keadaan Rekaman",
            "Bervoltase", "Frekuensi",
            "Gel.Patologis", "Gel.Lambat", "GL Voltase", "GL Frekuensi",
            "GL Loc Tipe", "GL Loc Daerah", "GL Gen Tipe",
            "IED", "IED Voltase", "IED Bentuk", "IED Ket",
            "IED Loc Tipe", "IED Loc Daerah", "IED Gen Tipe",
            "Hips Aritmia", "Vertex Transient", "Sleep Spindle",
            "Artefak", "Art.Kontraksi", "Art.Denyutan", "Art.Keringat",
            "Art.Kedipan", "Art.Gerak Mata", "Art.Mek",
            "HV/PS", "HV Ket", "Respon Mata",
            "Kesimpulan", "Kes.A IED", "Kes.A SW", "Kes.A Daerah",
            "Kes.B IED", "Kes.B SW",
            "Usul Komentar",
            "Kd.Dokter", "Nm.Dokter", "NIP Operator", "Nm.Operator"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbData.setModel(tabMode);
        tbData.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Set lebar kolom tabel
        int[] colWidths = {
            105, 70, 160, 40, 80,
            140, 160, 120,
            80, 100,
            90, 80, 80, 100,
            90, 120, 90,
            60, 80, 120, 150,
            90, 120, 90,
            80, 90, 80,
            80, 80, 80, 80,
            80, 90, 80,
            120, 200, 90,
            130, 90, 90, 200,
            90, 90,
            200,
            80, 160, 80, 160
        };
        for (i = 0; i < colWidths.length && i < tabMode.getColumnCount(); i++) {
            TableColumn col = tbData.getColumnModel().getColumn(i);
            col.setPreferredWidth(colWidths[i]);
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());

        // === Batas input field ===
        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        TDiagnosa.setDocument(new batasInput((int) 200).getKata(TDiagnosa));
        TFreqDominasi.setDocument(new batasInput((int) 50).getKata(TFreqDominasi));
        TFreqMinimal.setDocument(new batasInput((int) 50).getKata(TFreqMinimal));
        TGLFrekuensi.setDocument(new batasInput((int) 50).getKata(TGLFrekuensi));
        TGLLocDaerah.setDocument(new batasInput((int) 100).getKata(TGLLocDaerah));
        TIEDKet.setDocument(new batasInput((int) 100).getKata(TIEDKet));
        TIEDLocDaerah.setDocument(new batasInput((int) 100).getKata(TIEDLocDaerah));
        THVKet.setDocument(new batasInput((int) 200).getKata(THVKet));
        TKesADaerah.setDocument(new batasInput((int) 150).getKata(TKesADaerah));
        TUsulKomentar.setDocument(new batasInput((int) 500).getKata(TUsulKomentar));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        // === Listener Petugas (Operator) ===
        petugas.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    KdOperator.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NmOperator.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        // === Listener Dokter Pemeriksa ===
        dokterPemeriksa.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (dokterPemeriksa.getTable().getSelectedRow() != -1) {
                    KdDokterPemeriksa.setText(dokterPemeriksa.getTable().getValueAt(dokterPemeriksa.getTable().getSelectedRow(), 0).toString());
                    NmDokterPemeriksa.setText(dokterPemeriksa.getTable().getValueAt(dokterPemeriksa.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        try {
            TANGGALMUNDUR = koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR = "yes";
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        // === Root frame ===
        internalFrame1 = new widget.InternalFrame();
        panelTombol = new widget.panelisi();
        TabUtama = new javax.swing.JTabbedPane();

        // === Tab Input ===
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();

        // === Tab Data ===
        internalFrame3 = new widget.InternalFrame();
        scrollData = new widget.ScrollPane();
        tbData = new widget.Table();
        panelCari = new widget.panelisi();

        // ==== WIDGET DECLARATIONS ====

        // Header pasien
        TNoRw      = new widget.TextBox();
        TNoRM      = new widget.TextBox();
        TPasien    = new widget.TextBox();
        TJK        = new widget.TextBox();
        TTglLahir  = new widget.TextBox();
        TglRekaman = new widget.Tanggal();
        TDiagnosa  = new widget.TextBox();
        DokterPengirim = new widget.TextBox();

        // Operator & Dokter Pemeriksa
        KdOperator         = new widget.TextBox();
        NmOperator         = new widget.TextBox();
        BtnCariOperator    = new widget.Button();
        KdDokterPemeriksa  = new widget.TextBox();
        NmDokterPemeriksa  = new widget.TextBox();
        BtnCariDokterPemeriksa = new widget.Button();

        // Interpretasi
        CmbKeadaanRekaman = new widget.ComboBox();
        CmbBervoltase     = new widget.ComboBox();
        CmbFrekuensi      = new widget.ComboBox();
        TFreqDominasi     = new widget.TextBox();
        TFreqMinimal      = new widget.TextBox();

        // Temuan gelombang
        CmbGelPatologis   = new widget.ComboBox();
        CmbGelLambat      = new widget.ComboBox();
        CmbGLVoltase      = new widget.ComboBox();
        TGLFrekuensi      = new widget.TextBox();
        CmbGLLocTipe      = new widget.ComboBox();
        TGLLocDaerah      = new widget.TextBox();
        CmbGLGenTipe      = new widget.ComboBox();

        // IED
        CmbIED            = new widget.ComboBox();
        CmbIEDVoltase     = new widget.ComboBox();
        CmbIEDBentuk      = new widget.ComboBox();
        TIEDKet           = new widget.TextBox();
        CmbIEDLocTipe     = new widget.ComboBox();
        TIEDLocDaerah     = new widget.TextBox();
        CmbIEDGenTipe     = new widget.ComboBox();

        // Hips, Vertex, Sleep
        CmbHipsAritmia    = new widget.ComboBox();
        CmbVertexTransient = new widget.ComboBox();
        CmbSleepSpindle   = new widget.ComboBox();

        // Artefak
        CmbArtefak         = new widget.ComboBox();
        CmbArtKontraksi    = new widget.ComboBox();
        CmbArtDenyutan     = new widget.ComboBox();
        CmbArtKeringat     = new widget.ComboBox();
        CmbArtKedipan      = new widget.ComboBox();
        CmbArtGerakMata    = new widget.ComboBox();
        CmbArtMekanis      = new widget.ComboBox();

        // HV/PS
        CmbHVPS   = new widget.ComboBox();
        THVKet    = new widget.TextBox();

        // Respon mata
        CmbResponMata = new widget.ComboBox();

        // Kesimpulan
        CmbKesimpulan  = new widget.ComboBox();
        CmbKesAIED     = new widget.ComboBox();
        CmbKesASW      = new widget.ComboBox();
        TKesADaerah    = new widget.TextBox();
        CmbKesBIED     = new widget.ComboBox();
        CmbKesBSW      = new widget.ComboBox();
        TUsulKomentar  = new widget.TextBox();

        // Tombol
        BtnSimpan  = new widget.Button();
        BtnBaru    = new widget.Button();
        BtnHapus   = new widget.Button();
        BtnEdit    = new widget.Button();
        BtnCetak   = new widget.Button();
        BtnSemua   = new widget.Button();
        BtnKeluar  = new widget.Button();
        BtnPreview = new widget.Button();

        // Cari
        DTPCari1 = new widget.Tanggal();
        DTPCari2 = new widget.Tanggal();
        TCari    = new widget.TextBox();
        BtnCari  = new widget.Button();
        LCount   = new widget.Label();

        // Labels
        lNoRw          = new widget.Label();
        lNoRM          = new widget.Label();
        lPasien        = new widget.Label();
        lJK            = new widget.Label();
        lTglLahir      = new widget.Label();
        lTglRekaman    = new widget.Label();
        lDiagnosa      = new widget.Label();
        lDokterPengirim = new widget.Label();
        lOperator      = new widget.Label();
        lDokterPemeriksa = new widget.Label();
        lSep1 = new javax.swing.JSeparator();
        lSep2 = new javax.swing.JSeparator();
        lSep3 = new javax.swing.JSeparator();
        lSep4 = new javax.swing.JSeparator();
        lSep5 = new javax.swing.JSeparator();
        lSep6 = new javax.swing.JSeparator();
        lJudulInterpretasi = new widget.Label();
        lJudulTemuan       = new widget.Label();
        lJudulKesimpulan   = new widget.Label();

        // ============================================================
        // LAYOUT
        // ============================================================

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
            "::[ Pemeriksaan EEG - Lab Neurofisiologi ]::",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Tahoma", 0, 11),
            new java.awt.Color(50, 50, 50)));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        // ===== PANEL TOMBOL =====
        panelTombol.setPreferredSize(new java.awt.Dimension(44, 54));
        panelTombol.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(evt -> BtnSimpanActionPerformed(evt));
        panelTombol.add(BtnSimpan);

        BtnBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        BtnBaru.setMnemonic('B');
        BtnBaru.setText("Baru");
        BtnBaru.setToolTipText("Alt+B");
        BtnBaru.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBaru.addActionListener(evt -> emptTeks());
        panelTombol.add(BtnBaru);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(evt -> BtnHapusActionPerformed(evt));
        panelTombol.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(evt -> BtnEditActionPerformed(evt));
        panelTombol.add(BtnEdit);

        BtnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnCetak.setMnemonic('T');
        BtnCetak.setText("Cetak");
        BtnCetak.setToolTipText("Alt+T");
        BtnCetak.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCetak.addActionListener(evt -> BtnCetakActionPerformed(evt));
        panelTombol.add(BtnCetak);

        BtnSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnSemua.setMnemonic('M');
        BtnSemua.setText("Semua");
        BtnSemua.setToolTipText("Alt+M");
        BtnSemua.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSemua.addActionListener(evt -> { TCari.setText(""); tampil(); });
        panelTombol.add(BtnSemua);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(evt -> dispose());
        panelTombol.add(BtnKeluar);

        BtnPreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnPreview.setMnemonic('P');
        BtnPreview.setText("Preview");
        BtnPreview.setToolTipText("Alt+P - Cetak Preview HTML");
        BtnPreview.setPreferredSize(new java.awt.Dimension(110, 30));
        BtnPreview.addActionListener(evt -> {
            if (tbData.getSelectedRow() > -1) {
                cetakPreview();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data terlebih dahulu..!!");
            }
        });
        panelTombol.add(BtnPreview);

        internalFrame1.add(panelTombol, java.awt.BorderLayout.PAGE_END);

        // ===== TAB PANE =====
        TabUtama.setBackground(new java.awt.Color(254, 255, 254));
        TabUtama.setForeground(new java.awt.Color(50, 50, 50));
        TabUtama.setFont(new java.awt.Font("Tahoma", 0, 11));

        // ===== TAB INPUT =====
        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setPreferredSize(new java.awt.Dimension(900, 1050));
        FormInput.setLayout(null);

        // ---- Baris 1: Identitas Pasien ----
        lNoRw.setText("No.Rawat :");
        FormInput.add(lNoRw); lNoRw.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) isRawat();
                else Valid.pindah(evt, TCari, TDiagnosa);
            }
        });
        FormInput.add(TNoRw); TNoRw.setBounds(74, 10, 131, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        FormInput.add(TNoRM); TNoRM.setBounds(214, 10, 100, 23);

        lNoRM.setText("No.RM :");
        FormInput.add(lNoRM); lNoRM.setBounds(207, 10, 0, 0);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        FormInput.add(TPasien); TPasien.setBounds(322, 10, 200, 23);

        lPasien.setText("Nama :");
        FormInput.add(lPasien); lPasien.setBounds(320, 10, 0, 0);

        lJK.setText("J.K. :");
        FormInput.add(lJK); lJK.setBounds(530, 10, 35, 23);

        TJK.setEditable(false);
        FormInput.add(TJK); TJK.setBounds(565, 10, 60, 23);

        lTglLahir.setText("Tgl.Lahir :");
        FormInput.add(lTglLahir); lTglLahir.setBounds(632, 10, 65, 23);

        TTglLahir.setEditable(false);
        FormInput.add(TTglLahir); TTglLahir.setBounds(700, 10, 90, 23);

        // ---- Baris 2 ----
        lTglRekaman.setText("Tgl.Rekaman :");
        FormInput.add(lTglRekaman); lTglRekaman.setBounds(0, 40, 90, 23);

        TglRekaman.setForeground(new java.awt.Color(50, 70, 50));
        TglRekaman.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));
        TglRekaman.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglRekaman.setOpaque(false);
        TglRekaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TNoRw, TDiagnosa);
            }
        });
        FormInput.add(TglRekaman); TglRekaman.setBounds(95, 40, 155, 23);

        lDiagnosa.setText("Diagnosa :");
        FormInput.add(lDiagnosa); lDiagnosa.setBounds(260, 40, 65, 23);

        TDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TglRekaman, CmbKeadaanRekaman);
            }
        });
        FormInput.add(TDiagnosa); TDiagnosa.setBounds(328, 40, 300, 23);

        lDokterPengirim.setText("Dokter Pengirim :");
        FormInput.add(lDokterPengirim); lDokterPengirim.setBounds(0, 68, 110, 23);

        DokterPengirim.setEditable(false);
        FormInput.add(DokterPengirim); DokterPengirim.setBounds(113, 68, 250, 23);

        lOperator.setText("Operator :");
        FormInput.add(lOperator); lOperator.setBounds(375, 68, 65, 23);

        KdOperator.setEditable(false);
        FormInput.add(KdOperator); KdOperator.setBounds(443, 68, 80, 23);

        NmOperator.setEditable(false);
        FormInput.add(NmOperator); NmOperator.setBounds(527, 68, 160, 23);

        BtnCariOperator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnCariOperator.setMnemonic('2');
        BtnCariOperator.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariOperator.addActionListener(evt -> {
            petugas.isCek();
            petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            petugas.setLocationRelativeTo(internalFrame1);
            petugas.setVisible(true);
        });
        FormInput.add(BtnCariOperator); BtnCariOperator.setBounds(691, 68, 28, 23);

        lDokterPemeriksa.setText("Dokter Pemeriksa :");
        FormInput.add(lDokterPemeriksa); lDokterPemeriksa.setBounds(0, 95, 115, 23);

        KdDokterPemeriksa.setEditable(false);
        FormInput.add(KdDokterPemeriksa); KdDokterPemeriksa.setBounds(118, 95, 80, 23);

        NmDokterPemeriksa.setEditable(false);
        FormInput.add(NmDokterPemeriksa); NmDokterPemeriksa.setBounds(202, 95, 220, 23);

        BtnCariDokterPemeriksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnCariDokterPemeriksa.setMnemonic('3');
        BtnCariDokterPemeriksa.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariDokterPemeriksa.addActionListener(evt -> {
            dokterPemeriksa.isCek();
            dokterPemeriksa.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dokterPemeriksa.setLocationRelativeTo(internalFrame1);
            dokterPemeriksa.setVisible(true);
        });
        FormInput.add(BtnCariDokterPemeriksa); BtnCariDokterPemeriksa.setBounds(426, 95, 28, 23);

        // ---- SEPARATOR 1 ----
        lSep1.setBackground(new java.awt.Color(239, 244, 234));
        lSep1.setForeground(new java.awt.Color(239, 244, 234));
        lSep1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(lSep1); lSep1.setBounds(0, 125, 900, 1);

        // ---- JUDUL INTERPRETASI ----
        lJudulInterpretasi.setFont(new java.awt.Font("Tahoma", 1, 11));
        lJudulInterpretasi.setText("INTERPRETASI EEG");
        FormInput.add(lJudulInterpretasi); lJudulInterpretasi.setBounds(10, 130, 200, 23);

        // Keadaan Rekaman
        widget.Label lKR = new widget.Label();
        lKR.setText("Rekaman dibuat dalam keadaan :");
        FormInput.add(lKR); lKR.setBounds(10, 158, 220, 23);

        CmbKeadaanRekaman.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Sadar", "Tidur Ringan", "Tidur Dalam", "Dengan Premedikasi"
        }));
        CmbKeadaanRekaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TDiagnosa, CmbBervoltase);
            }
        });
        FormInput.add(CmbKeadaanRekaman); CmbKeadaanRekaman.setBounds(234, 158, 200, 23);

        // Bervoltase
        widget.Label lBV = new widget.Label();
        lBV.setText("Latar Belakang - Bervoltase :");
        FormInput.add(lBV); lBV.setBounds(10, 188, 195, 23);

        CmbBervoltase.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Rendah", "Sedang", "Tinggi"}));
        CmbBervoltase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbKeadaanRekaman, CmbFrekuensi);
            }
        });
        FormInput.add(CmbBervoltase); CmbBervoltase.setBounds(208, 188, 110, 23);

        // Frekuensi
        widget.Label lFQ = new widget.Label();
        lFQ.setText("Frekuensi :");
        FormInput.add(lFQ); lFQ.setBounds(330, 188, 70, 23);

        CmbFrekuensi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "< 7 Hz", "8-13 Hz", "14-30 Hz", "Bercampur"
        }));
        CmbFrekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbBervoltase, TFreqDominasi);
            }
        });
        FormInput.add(CmbFrekuensi); CmbFrekuensi.setBounds(403, 188, 120, 23);

        widget.Label lFQD = new widget.Label();
        lFQD.setText("Dominasi alfa/beta :");
        FormInput.add(lFQD); lFQD.setBounds(530, 188, 125, 23);
        TFreqDominasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbFrekuensi, TFreqMinimal);
            }
        });
        FormInput.add(TFreqDominasi); TFreqDominasi.setBounds(658, 188, 100, 23);

        widget.Label lFQM = new widget.Label();
        lFQM.setText("Minimal :");
        FormInput.add(lFQM); lFQM.setBounds(764, 188, 55, 23);
        TFreqMinimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TFreqDominasi, CmbGelPatologis);
            }
        });
        FormInput.add(TFreqMinimal); TFreqMinimal.setBounds(820, 188, 70, 23);

        // ---- SEPARATOR 2 ----
        lSep2.setBackground(new java.awt.Color(239, 244, 234));
        lSep2.setForeground(new java.awt.Color(239, 244, 234));
        lSep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(lSep2); lSep2.setBounds(0, 218, 900, 1);

        // ---- JUDUL TEMUAN ----
        lJudulTemuan.setFont(new java.awt.Font("Tahoma", 1, 11));
        lJudulTemuan.setText("TEMUAN - TEMUAN YANG TAMPAK");
        FormInput.add(lJudulTemuan); lJudulTemuan.setBounds(10, 222, 270, 23);

        // Gelombang Patologis
        widget.Label lGP = new widget.Label();
        lGP.setText("Gelombang Patologis :");
        FormInput.add(lGP); lGP.setBounds(10, 250, 145, 23);

        CmbGelPatologis.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak Tampak", "Tampak"}));
        CmbGelPatologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TFreqMinimal, CmbGelLambat);
            }
        });
        FormInput.add(CmbGelPatologis); CmbGelPatologis.setBounds(158, 250, 130, 23);

        // Gelombang Lambat
        widget.Label lGL = new widget.Label();
        lGL.setText("Gelombang Lambat Bervoltase :");
        FormInput.add(lGL); lGL.setBounds(10, 278, 208, 23);

        CmbGelLambat.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak Ada", "Ada"}));
        CmbGelLambat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbGelPatologis, CmbGLVoltase);
            }
        });
        FormInput.add(CmbGelLambat); CmbGelLambat.setBounds(220, 278, 90, 23);

        CmbGLVoltase.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Rendah", "Sedang", "Tinggi"}));
        CmbGLVoltase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbGelLambat, TGLFrekuensi);
            }
        });
        FormInput.add(CmbGLVoltase); CmbGLVoltase.setBounds(316, 278, 110, 23);

        widget.Label lGLF = new widget.Label();
        lGLF.setText("Dengan Frekuensi :");
        FormInput.add(lGLF); lGLF.setBounds(435, 278, 120, 23);
        TGLFrekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbGLVoltase, CmbGLLocTipe);
            }
        });
        FormInput.add(TGLFrekuensi); TGLFrekuensi.setBounds(558, 278, 120, 23);

        // GL Localized
        widget.Label lGLLoc = new widget.Label();
        lGLLoc.setText("Localized :");
        FormInput.add(lGLLoc); lGLLoc.setBounds(10, 305, 70, 23);

        CmbGLLocTipe.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbGLLocTipe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TGLFrekuensi, TGLLocDaerah);
            }
        });
        FormInput.add(CmbGLLocTipe); CmbGLLocTipe.setBounds(83, 305, 110, 23);

        widget.Label lGLLD = new widget.Label();
        lGLLD.setText("di daerah :");
        FormInput.add(lGLLD); lGLLD.setBounds(200, 305, 65, 23);
        TGLLocDaerah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbGLLocTipe, CmbGLGenTipe);
            }
        });
        FormInput.add(TGLLocDaerah); TGLLocDaerah.setBounds(268, 305, 200, 23);

        // GL Generalized
        widget.Label lGLGen = new widget.Label();
        lGLGen.setText("Generalized :");
        FormInput.add(lGLGen); lGLGen.setBounds(10, 330, 85, 23);

        CmbGLGenTipe.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbGLGenTipe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TGLLocDaerah, CmbIED);
            }
        });
        FormInput.add(CmbGLGenTipe); CmbGLGenTipe.setBounds(98, 330, 110, 23);

        widget.Label lGLGenKet = new widget.Label();
        lGLGenKet.setText("pada kedua hemisfer");
        FormInput.add(lGLGenKet); lGLGenKet.setBounds(215, 330, 140, 23);

        // ---- SEPARATOR 3 ----
        lSep3.setBackground(new java.awt.Color(239, 244, 234));
        lSep3.setForeground(new java.awt.Color(239, 244, 234));
        lSep3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(lSep3); lSep3.setBounds(0, 360, 900, 1);

        // ---- IED ----
        widget.Label lIED = new widget.Label();
        lIED.setFont(new java.awt.Font("Tahoma", 1, 11));
        lIED.setText("IED (Interictal Epileptiform Discharges) :");
        FormInput.add(lIED); lIED.setBounds(10, 365, 290, 23);

        CmbIED.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak", "Ya"}));
        CmbIED.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbGLGenTipe, CmbIEDVoltase);
            }
        });
        FormInput.add(CmbIED); CmbIED.setBounds(303, 365, 80, 23);

        widget.Label lIEDV = new widget.Label();
        lIEDV.setText("Bervoltase :");
        FormInput.add(lIEDV); lIEDV.setBounds(395, 365, 75, 23);

        CmbIEDVoltase.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Rendah", "Sedang", "Tinggi"}));
        CmbIEDVoltase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbIED, CmbIEDBentuk);
            }
        });
        FormInput.add(CmbIEDVoltase); CmbIEDVoltase.setBounds(473, 365, 110, 23);

        widget.Label lIEDB = new widget.Label();
        lIEDB.setText("Bentuk :");
        FormInput.add(lIEDB); lIEDB.setBounds(10, 393, 60, 23);

        CmbIEDBentuk.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Sharp wave", "Poly sharp wave", "Spikes", "Spikes wave complex", "Poli wave complexes"
        }));
        CmbIEDBentuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbIEDVoltase, TIEDKet);
            }
        });
        FormInput.add(CmbIEDBentuk); CmbIEDBentuk.setBounds(73, 393, 180, 23);

        widget.Label lIEDK = new widget.Label();
        lIEDK.setText("Keterangan :");
        FormInput.add(lIEDK); lIEDK.setBounds(260, 393, 80, 23);
        TIEDKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbIEDBentuk, CmbIEDLocTipe);
            }
        });
        FormInput.add(TIEDKet); TIEDKet.setBounds(343, 393, 250, 23);

        // IED Localized
        widget.Label lIEDLoc = new widget.Label();
        lIEDLoc.setText("Localized :");
        FormInput.add(lIEDLoc); lIEDLoc.setBounds(10, 420, 70, 23);

        CmbIEDLocTipe.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbIEDLocTipe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TIEDKet, TIEDLocDaerah);
            }
        });
        FormInput.add(CmbIEDLocTipe); CmbIEDLocTipe.setBounds(83, 420, 110, 23);

        widget.Label lIEDLD = new widget.Label();
        lIEDLD.setText("di daerah :");
        FormInput.add(lIEDLD); lIEDLD.setBounds(200, 420, 65, 23);
        TIEDLocDaerah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbIEDLocTipe, CmbIEDGenTipe);
            }
        });
        FormInput.add(TIEDLocDaerah); TIEDLocDaerah.setBounds(268, 420, 200, 23);

        // IED Generalized
        widget.Label lIEDGen = new widget.Label();
        lIEDGen.setText("Generalized :");
        FormInput.add(lIEDGen); lIEDGen.setBounds(10, 448, 85, 23);

        CmbIEDGenTipe.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbIEDGenTipe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TIEDLocDaerah, CmbHipsAritmia);
            }
        });
        FormInput.add(CmbIEDGenTipe); CmbIEDGenTipe.setBounds(98, 448, 110, 23);

        widget.Label lIEDGenKet = new widget.Label();
        lIEDGenKet.setText("pada kedua hemisfer");
        FormInput.add(lIEDGenKet); lIEDGenKet.setBounds(215, 448, 140, 23);

        // ---- SEPARATOR 4 ----
        lSep4.setBackground(new java.awt.Color(239, 244, 234));
        lSep4.setForeground(new java.awt.Color(239, 244, 234));
        lSep4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(lSep4); lSep4.setBounds(0, 478, 900, 1);

        // ---- Hips, Vertex, Sleep ----
        widget.Label lH = new widget.Label();
        lH.setText("Hips Aritmia :");
        FormInput.add(lH); lH.setBounds(10, 483, 90, 23);

        CmbHipsAritmia.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak Ada", "Ada"}));
        CmbHipsAritmia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbIEDGenTipe, CmbVertexTransient);
            }
        });
        FormInput.add(CmbHipsAritmia); CmbHipsAritmia.setBounds(103, 483, 100, 23);

        widget.Label lVT = new widget.Label();
        lVT.setText("Vertex Transient :");
        FormInput.add(lVT); lVT.setBounds(215, 483, 115, 23);

        CmbVertexTransient.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak Ada", "Ada"}));
        CmbVertexTransient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbHipsAritmia, CmbSleepSpindle);
            }
        });
        FormInput.add(CmbVertexTransient); CmbVertexTransient.setBounds(333, 483, 100, 23);

        widget.Label lSS = new widget.Label();
        lSS.setText("Sleep Spindle :");
        FormInput.add(lSS); lSS.setBounds(445, 483, 95, 23);

        CmbSleepSpindle.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak Ada", "Ada"}));
        CmbSleepSpindle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbVertexTransient, CmbArtefak);
            }
        });
        FormInput.add(CmbSleepSpindle); CmbSleepSpindle.setBounds(543, 483, 100, 23);

        // ---- ARTEFAK ----
        widget.Label lAR = new widget.Label();
        lAR.setFont(new java.awt.Font("Tahoma", 1, 11));
        lAR.setText("Artefak :");
        FormInput.add(lAR); lAR.setBounds(10, 513, 65, 23);

        CmbArtefak.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak Tampak", "Tampak"}));
        CmbArtefak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbSleepSpindle, CmbArtKontraksi);
            }
        });
        FormInput.add(CmbArtefak); CmbArtefak.setBounds(78, 513, 130, 23);

        // Jenis artefak
        widget.Label lAK = new widget.Label(); lAK.setText("Kontraksi Otot :");
        FormInput.add(lAK); lAK.setBounds(10, 540, 105, 23);
        CmbArtKontraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak", "Ya"}));
        CmbArtKontraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) { Valid.pindah(evt, CmbArtefak, CmbArtDenyutan); }
        });
        FormInput.add(CmbArtKontraksi); CmbArtKontraksi.setBounds(118, 540, 80, 23);

        widget.Label lAD = new widget.Label(); lAD.setText("Denyutan Pembuluh Darah :");
        FormInput.add(lAD); lAD.setBounds(210, 540, 170, 23);
        CmbArtDenyutan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak", "Ya"}));
        CmbArtDenyutan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) { Valid.pindah(evt, CmbArtKontraksi, CmbArtKeringat); }
        });
        FormInput.add(CmbArtDenyutan); CmbArtDenyutan.setBounds(383, 540, 80, 23);

        widget.Label lAKg = new widget.Label(); lAKg.setText("Keringat :");
        FormInput.add(lAKg); lAKg.setBounds(475, 540, 65, 23);
        CmbArtKeringat.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak", "Ya"}));
        CmbArtKeringat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) { Valid.pindah(evt, CmbArtDenyutan, CmbArtKedipan); }
        });
        FormInput.add(CmbArtKeringat); CmbArtKeringat.setBounds(543, 540, 80, 23);

        widget.Label lAKd = new widget.Label(); lAKd.setText("Kedipan Mata :");
        FormInput.add(lAKd); lAKd.setBounds(10, 568, 100, 23);
        CmbArtKedipan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak", "Ya"}));
        CmbArtKedipan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) { Valid.pindah(evt, CmbArtKeringat, CmbArtGerakMata); }
        });
        FormInput.add(CmbArtKedipan); CmbArtKedipan.setBounds(113, 568, 80, 23);

        widget.Label lAGM = new widget.Label(); lAGM.setText("Gerakan Bola Mata :");
        FormInput.add(lAGM); lAGM.setBounds(205, 568, 130, 23);
        CmbArtGerakMata.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak", "Ya"}));
        CmbArtGerakMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) { Valid.pindah(evt, CmbArtKedipan, CmbArtMekanis); }
        });
        FormInput.add(CmbArtGerakMata); CmbArtGerakMata.setBounds(338, 568, 80, 23);

        widget.Label lAMek = new widget.Label(); lAMek.setText("Gangguan Mekanis :");
        FormInput.add(lAMek); lAMek.setBounds(430, 568, 125, 23);
        CmbArtMekanis.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tidak", "Ya"}));
        CmbArtMekanis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) { Valid.pindah(evt, CmbArtGerakMata, CmbHVPS); }
        });
        FormInput.add(CmbArtMekanis); CmbArtMekanis.setBounds(558, 568, 80, 23);

        // ---- HIPERVENTILASI / FOTIK STIMULASI ----
        lSep5.setBackground(new java.awt.Color(239, 244, 234));
        lSep5.setForeground(new java.awt.Color(239, 244, 234));
        lSep5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(lSep5); lSep5.setBounds(0, 598, 900, 1);

        widget.Label lHV = new widget.Label();
        lHV.setFont(new java.awt.Font("Tahoma", 1, 11));
        lHV.setText("Hiperventilasi / Fotik Stimulasi (HV/PS) :");
        FormInput.add(lHV); lHV.setBounds(10, 603, 280, 23);

        CmbHVPS.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak Ada", "Tidak Menyebabkan Perubahan", "Ada Perubahan"
        }));
        CmbHVPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbArtMekanis, THVKet);
            }
        });
        FormInput.add(CmbHVPS); CmbHVPS.setBounds(293, 603, 240, 23);

        widget.Label lHVK = new widget.Label(); lHVK.setText("Keterangan :");
        FormInput.add(lHVK); lHVK.setBounds(10, 630, 80, 23);
        THVKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbHVPS, CmbResponMata);
            }
        });
        FormInput.add(THVKet); THVKet.setBounds(93, 630, 500, 23);

        // Respon Mata
        widget.Label lRM = new widget.Label(); lRM.setText("Respon Tutup/Buka Mata :");
        FormInput.add(lRM); lRM.setBounds(10, 658, 165, 23);

        CmbResponMata.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak Dilakukan", "Normal", "Abnormal"
        }));
        CmbResponMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, THVKet, CmbKesimpulan);
            }
        });
        FormInput.add(CmbResponMata); CmbResponMata.setBounds(178, 658, 170, 23);

        // ---- SEPARATOR 5: KESIMPULAN ----
        lSep6.setBackground(new java.awt.Color(239, 244, 234));
        lSep6.setForeground(new java.awt.Color(239, 244, 234));
        lSep6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(lSep6); lSep6.setBounds(0, 688, 900, 1);

        lJudulKesimpulan.setFont(new java.awt.Font("Tahoma", 1, 11));
        lJudulKesimpulan.setText("KESIMPULAN");
        FormInput.add(lJudulKesimpulan); lJudulKesimpulan.setBounds(10, 693, 120, 23);

        CmbKesimpulan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "EEG Normal", "EEG Abnormal Tidak Spesifik", "EEG Abnormal"
        }));
        CmbKesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbResponMata, CmbKesAIED);
            }
        });
        FormInput.add(CmbKesimpulan); CmbKesimpulan.setBounds(135, 693, 230, 23);

        // Kesimpulan baris a
        widget.Label lKesA = new widget.Label();
        lKesA.setText("a.  IED :");
        FormInput.add(lKesA); lKesA.setBounds(10, 723, 60, 23);

        CmbKesAIED.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbKesAIED.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbKesimpulan, CmbKesASW);
            }
        });
        FormInput.add(CmbKesAIED); CmbKesAIED.setBounds(73, 723, 120, 23);

        widget.Label lKesASW = new widget.Label(); lKesASW.setText("Slow Wave :");
        FormInput.add(lKesASW); lKesASW.setBounds(203, 723, 75, 23);

        CmbKesASW.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbKesASW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbKesAIED, TKesADaerah);
            }
        });
        FormInput.add(CmbKesASW); CmbKesASW.setBounds(281, 723, 120, 23);

        widget.Label lKesAD = new widget.Label(); lKesAD.setText("di daerah :");
        FormInput.add(lKesAD); lKesAD.setBounds(410, 723, 68, 23);
        TKesADaerah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbKesASW, CmbKesBIED);
            }
        });
        FormInput.add(TKesADaerah); TKesADaerah.setBounds(481, 723, 300, 23);

        // Kesimpulan baris b
        widget.Label lKesB = new widget.Label();
        lKesB.setText("b.  IED :");
        FormInput.add(lKesB); lKesB.setBounds(10, 750, 60, 23);

        CmbKesBIED.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbKesBIED.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, TKesADaerah, CmbKesBSW);
            }
        });
        FormInput.add(CmbKesBIED); CmbKesBIED.setBounds(73, 750, 120, 23);

        widget.Label lKesBSW = new widget.Label(); lKesBSW.setText("Slow Wave :");
        FormInput.add(lKesBSW); lKesBSW.setBounds(203, 750, 75, 23);

        CmbKesBSW.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"-", "Intermitten", "Kontinyu"}));
        CmbKesBSW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbKesBIED, TUsulKomentar);
            }
        });
        FormInput.add(CmbKesBSW); CmbKesBSW.setBounds(281, 750, 120, 23);

        widget.Label lKesBH = new widget.Label(); lKesBH.setText("pada kedua hemisfer");
        FormInput.add(lKesBH); lKesBH.setBounds(410, 750, 140, 23);

        // Usul Komentar
        widget.Label lUK = new widget.Label();
        lUK.setFont(new java.awt.Font("Tahoma", 1, 11));
        lUK.setText("Usul Komentar :");
        FormInput.add(lUK); lUK.setBounds(10, 780, 110, 23);
        TUsulKomentar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, CmbKesBSW, BtnSimpan);
            }
        });
        FormInput.add(TUsulKomentar); TUsulKomentar.setBounds(123, 780, 560, 23);

        scrollInput.setViewportView(FormInput);
        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);
        TabUtama.addTab("Input EEG", internalFrame2);

        // ===== TAB DATA =====
        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        scrollData.setOpaque(true);
        scrollData.setPreferredSize(new java.awt.Dimension(452, 200));
        tbData.setToolTipText("Klik dua kali untuk edit data");
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    getData();
                    TabUtama.setSelectedIndex(0);
                }
            }
        });
        tbData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                    getData();
                    TabUtama.setSelectedIndex(0);
                }
            }
        });
        scrollData.setViewportView(tbData);
        internalFrame3.add(scrollData, java.awt.BorderLayout.CENTER);

        // Panel cari
        panelCari.setPreferredSize(new java.awt.Dimension(44, 44));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        widget.Label lCariTgl1 = new widget.Label();
        lCariTgl1.setText("Tgl.Rekaman :");
        lCariTgl1.setPreferredSize(new java.awt.Dimension(85, 23));
        panelCari.add(lCariTgl1);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCari.add(DTPCari1);

        widget.Label lSampai = new widget.Label();
        lSampai.setText("s.d.");
        lSampai.setPreferredSize(new java.awt.Dimension(30, 23));
        panelCari.add(lSampai);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCari.add(DTPCari2);

        widget.Label lKW = new widget.Label();
        lKW.setText("Key Word :");
        lKW.setPreferredSize(new java.awt.Dimension(70, 23));
        panelCari.add(lKW);

        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) tampil();
            }
        });
        panelCari.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(evt -> tampil());
        panelCari.add(BtnCari);

        widget.Label lRec = new widget.Label();
        lRec.setText("Record :");
        lRec.setPreferredSize(new java.awt.Dimension(55, 23));
        panelCari.add(lRec);

        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelCari.add(LCount);

        internalFrame3.add(panelCari, java.awt.BorderLayout.PAGE_END);
        TabUtama.addTab("Data EEG", internalFrame3);

        internalFrame1.add(TabUtama, java.awt.BorderLayout.CENTER);
        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }
    // </editor-fold>

    // ============================================================
    // EVENT HANDLERS
    // ============================================================

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        tampil();
    }

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRM.getText().trim().isEmpty()) {
            Valid.textKosong(TNoRw, "No. Rawat / Pasien");
        } else if (NmOperator.getText().trim().isEmpty()) {
            Valid.textKosong(BtnCariOperator, "Operator");
        } else if (NmDokterPemeriksa.getText().trim().isEmpty()) {
            Valid.textKosong(BtnCariDokterPemeriksa, "Dokter Pemeriksa");
        } else {
            if (akses.getkode().equals("Admin Utama")) {
                simpan();
            } else {
                simpan();
            }
        }
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbData.getSelectedRow() > -1) {
            hapus();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data terlebih dahulu..!!");
        }
    }

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRM.getText().trim().isEmpty()) {
            Valid.textKosong(TNoRw, "No. Rawat / Pasien");
        } else if (NmOperator.getText().trim().isEmpty()) {
            Valid.textKosong(BtnCariOperator, "Operator");
        } else if (NmDokterPemeriksa.getText().trim().isEmpty()) {
            Valid.textKosong(BtnCariDokterPemeriksa, "Dokter Pemeriksa");
        } else {
            if (tbData.getSelectedRow() > -1) {
                ganti();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data terlebih dahulu..!!");
            }
        }
    }

    private void BtnCetakActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbData.getSelectedRow() > -1) {
            cetakHTML();
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data terlebih dahulu..!!");
        }
    }

    // ============================================================
    // METHOD: TAMPIL DATA
    // ============================================================
    public void tampil() {
        Valid.tabelKosong(tabMode);
        String sql = "SELECT r.no_rawat, p.no_rkm_medis, p.nm_pasien, " +
            "IF(p.jk='L','Laki-laki','Perempuan') AS jk, p.tgl_lahir, " +
            "e.tanggal, e.diagnosa, e.keadaan_rekaman, " +
            "e.bervoltase, e.frekuensi, " +
            "e.gelombang_patologis, e.gelombang_lambat, e.gl_bervoltase, e.gl_frekuensi, " +
            "e.gl_localized_tipe, e.gl_localized_daerah, e.gl_generalized_tipe, " +
            "e.ied_ada, e.ied_bervoltase, e.ied_bentuk, e.ied_bentuk_ket, " +
            "e.ied_localized_tipe, e.ied_localized_daerah, e.ied_generalized_tipe, " +
            "e.hips_aritmia, e.vertex_transient, e.sleep_spindle, " +
            "e.artefak, e.artefak_kontraksi, e.artefak_denyutan, e.artefak_keringat, " +
            "e.artefak_kedipan, e.artefak_gerak_mata, e.artefak_gangguan_mekanis, " +
            "e.hv_ps, e.hv_ps_ket, e.respon_mata, " +
            "e.kesimpulan, e.kes_a_ied, e.kes_a_slow_wave, e.kes_a_daerah, " +
            "e.kes_b_ied, e.kes_b_slow_wave, " +
            "e.usul_komentar, " +
            "e.kd_dokter_pemeriksa, d.nm_dokter, e.nip_operator, pt.nama " +
            "FROM reg_periksa r " +
            "INNER JOIN pasien p ON r.no_rkm_medis = p.no_rkm_medis " +
            "INNER JOIN pemeriksaan_eeg e ON r.no_rawat = e.no_rawat " +
            "INNER JOIN dokter d ON e.kd_dokter_pemeriksa = d.kd_dokter " +
            "INNER JOIN petugas pt ON e.nip_operator = pt.nip " +
            "WHERE e.tanggal BETWEEN ? AND ?";

        if (!TCari.getText().isEmpty()) {
            sql += " AND (r.no_rawat LIKE ? OR p.no_rkm_medis LIKE ? OR p.nm_pasien LIKE ? OR d.nm_dokter LIKE ?)";
        }
        sql += " ORDER BY e.tanggal DESC";

        try {
            ps = koneksi.prepareStatement(sql);
            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
            if (!TCari.getText().isEmpty()) {
                String kw = "%" + TCari.getText() + "%";
                ps.setString(3, kw); ps.setString(4, kw);
                ps.setString(5, kw); ps.setString(6, kw);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                tabMode.addRow(new String[]{
                    rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                    rs.getString("nm_pasien"), rs.getString("jk"), rs.getString("tgl_lahir"),
                    rs.getString("tanggal"), rs.getString("diagnosa"), rs.getString("keadaan_rekaman"),
                    rs.getString("bervoltase"), rs.getString("frekuensi"),
                    rs.getString("gelombang_patologis"), rs.getString("gelombang_lambat"),
                    rs.getString("gl_bervoltase"), rs.getString("gl_frekuensi"),
                    rs.getString("gl_localized_tipe"), rs.getString("gl_localized_daerah"),
                    rs.getString("gl_generalized_tipe"),
                    rs.getString("ied_ada"), rs.getString("ied_bervoltase"),
                    rs.getString("ied_bentuk"), rs.getString("ied_bentuk_ket"),
                    rs.getString("ied_localized_tipe"), rs.getString("ied_localized_daerah"),
                    rs.getString("ied_generalized_tipe"),
                    rs.getString("hips_aritmia"), rs.getString("vertex_transient"),
                    rs.getString("sleep_spindle"),
                    rs.getString("artefak"), rs.getString("artefak_kontraksi"),
                    rs.getString("artefak_denyutan"), rs.getString("artefak_keringat"),
                    rs.getString("artefak_kedipan"), rs.getString("artefak_gerak_mata"),
                    rs.getString("artefak_gangguan_mekanis"),
                    rs.getString("hv_ps"), rs.getString("hv_ps_ket"), rs.getString("respon_mata"),
                    rs.getString("kesimpulan"), rs.getString("kes_a_ied"),
                    rs.getString("kes_a_slow_wave"), rs.getString("kes_a_daerah"),
                    rs.getString("kes_b_ied"), rs.getString("kes_b_slow_wave"),
                    rs.getString("usul_komentar"),
                    rs.getString("kd_dokter_pemeriksa"), rs.getString("nm_dokter"),
                    rs.getString("nip_operator"), rs.getString("nama")
                });
            }
        } catch (Exception e) {
            System.out.println("Notif tampil EEG: " + e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); } catch (Exception ex) {}
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    // ============================================================
    // METHOD: AMBIL DATA DARI TABEL KE FORM
    // ============================================================
    private void getData() {
        if (tbData.getSelectedRow() != -1) {
            int r = tbData.getSelectedRow();
            TNoRw.setText(tabMode.getValueAt(r, 0).toString());
            TNoRM.setText(tabMode.getValueAt(r, 1).toString());
            TPasien.setText(tabMode.getValueAt(r, 2).toString());
            TJK.setText(tabMode.getValueAt(r, 3).toString());
            TTglLahir.setText(tabMode.getValueAt(r, 4).toString());
            Valid.SetTgl2(TglRekaman, tabMode.getValueAt(r, 5).toString());
            TDiagnosa.setText(tabMode.getValueAt(r, 6).toString());
            CmbKeadaanRekaman.setSelectedItem(tabMode.getValueAt(r, 7).toString());
            CmbBervoltase.setSelectedItem(tabMode.getValueAt(r, 8).toString());
            CmbFrekuensi.setSelectedItem(tabMode.getValueAt(r, 9).toString());
            CmbGelPatologis.setSelectedItem(tabMode.getValueAt(r, 10).toString());
            CmbGelLambat.setSelectedItem(tabMode.getValueAt(r, 11).toString());
            CmbGLVoltase.setSelectedItem(tabMode.getValueAt(r, 12).toString());
            TGLFrekuensi.setText(tabMode.getValueAt(r, 13).toString());
            CmbGLLocTipe.setSelectedItem(tabMode.getValueAt(r, 14).toString());
            TGLLocDaerah.setText(tabMode.getValueAt(r, 15).toString());
            CmbGLGenTipe.setSelectedItem(tabMode.getValueAt(r, 16).toString());
            CmbIED.setSelectedItem(tabMode.getValueAt(r, 17).toString());
            CmbIEDVoltase.setSelectedItem(tabMode.getValueAt(r, 18).toString());
            CmbIEDBentuk.setSelectedItem(tabMode.getValueAt(r, 19).toString());
            TIEDKet.setText(tabMode.getValueAt(r, 20).toString());
            CmbIEDLocTipe.setSelectedItem(tabMode.getValueAt(r, 21).toString());
            TIEDLocDaerah.setText(tabMode.getValueAt(r, 22).toString());
            CmbIEDGenTipe.setSelectedItem(tabMode.getValueAt(r, 23).toString());
            CmbHipsAritmia.setSelectedItem(tabMode.getValueAt(r, 24).toString());
            CmbVertexTransient.setSelectedItem(tabMode.getValueAt(r, 25).toString());
            CmbSleepSpindle.setSelectedItem(tabMode.getValueAt(r, 26).toString());
            CmbArtefak.setSelectedItem(tabMode.getValueAt(r, 27).toString());
            CmbArtKontraksi.setSelectedItem(tabMode.getValueAt(r, 28).toString());
            CmbArtDenyutan.setSelectedItem(tabMode.getValueAt(r, 29).toString());
            CmbArtKeringat.setSelectedItem(tabMode.getValueAt(r, 30).toString());
            CmbArtKedipan.setSelectedItem(tabMode.getValueAt(r, 31).toString());
            CmbArtGerakMata.setSelectedItem(tabMode.getValueAt(r, 32).toString());
            CmbArtMekanis.setSelectedItem(tabMode.getValueAt(r, 33).toString());
            CmbHVPS.setSelectedItem(tabMode.getValueAt(r, 34).toString());
            THVKet.setText(tabMode.getValueAt(r, 35).toString());
            CmbResponMata.setSelectedItem(tabMode.getValueAt(r, 36).toString());
            CmbKesimpulan.setSelectedItem(tabMode.getValueAt(r, 37).toString());
            CmbKesAIED.setSelectedItem(tabMode.getValueAt(r, 38).toString());
            CmbKesASW.setSelectedItem(tabMode.getValueAt(r, 39).toString());
            TKesADaerah.setText(tabMode.getValueAt(r, 40).toString());
            CmbKesBIED.setSelectedItem(tabMode.getValueAt(r, 41).toString());
            CmbKesBSW.setSelectedItem(tabMode.getValueAt(r, 42).toString());
            TUsulKomentar.setText(tabMode.getValueAt(r, 43).toString());
            KdDokterPemeriksa.setText(tabMode.getValueAt(r, 44).toString());
            NmDokterPemeriksa.setText(tabMode.getValueAt(r, 45).toString());
            KdOperator.setText(tabMode.getValueAt(r, 46).toString());
            NmOperator.setText(tabMode.getValueAt(r, 47).toString());
            isRawat();
            TabUtama.setSelectedIndex(0);
        }
    }

    // ============================================================
    // METHOD: CEK DATA PASIEN DARI NO RAWAT
    // ============================================================
    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                "SELECT r.no_rkm_medis, p.nm_pasien, " +
                "IF(p.jk='L','Laki-laki','Perempuan') AS jk, " +
                "p.tgl_lahir, d.nm_dokter, r.tgl_registrasi " +
                "FROM reg_periksa r " +
                "INNER JOIN pasien p ON r.no_rkm_medis = p.no_rkm_medis " +
                "LEFT JOIN dokter d ON r.kd_dokter = d.kd_dokter " +
                "WHERE r.no_rawat = ?");
            ps.setString(1, TNoRw.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                TNoRM.setText(rs.getString("no_rkm_medis"));
                TPasien.setText(rs.getString("nm_pasien"));
                TJK.setText(rs.getString("jk"));
                TTglLahir.setText(rs.getString("tgl_lahir"));
                DokterPengirim.setText(rs.getString("nm_dokter") != null ? rs.getString("nm_dokter") : "-");
                DTPCari1.setDate(rs.getDate("tgl_registrasi"));
            }
        } catch (Exception e) {
            System.out.println("Notif isRawat EEG: " + e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); } catch (Exception ex) {}
        }
    }

    // ============================================================
    // METHOD: SIMPAN
    // ============================================================
    private void simpan() {
        String tglRekaman = Valid.SetTgl(TglRekaman.getSelectedItem() + "") + " " +
            TglRekaman.getSelectedItem().toString().substring(11, 19);

         if (Sequel.menyimpantf("pemeriksaan_eeg",
    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
    "No.Rawat", 44, new String[]{

                TNoRw.getText(), tglRekaman,
                KdDokterPemeriksa.getText(), KdOperator.getText(), TDiagnosa.getText(),
                CmbKeadaanRekaman.getSelectedItem().toString(),
                CmbBervoltase.getSelectedItem().toString(),
                CmbFrekuensi.getSelectedItem().toString(),
                TFreqDominasi.getText(), TFreqMinimal.getText(),
                CmbGelPatologis.getSelectedItem().toString(),
                CmbGelLambat.getSelectedItem().toString(),
                CmbGLVoltase.getSelectedItem().toString(), TGLFrekuensi.getText(),
                CmbGLLocTipe.getSelectedItem().toString(), TGLLocDaerah.getText(),
                CmbGLGenTipe.getSelectedItem().toString(),
                CmbIED.getSelectedItem().toString(),
                CmbIEDVoltase.getSelectedItem().toString(),
                CmbIEDBentuk.getSelectedItem().toString(), TIEDKet.getText(),
                CmbIEDLocTipe.getSelectedItem().toString(), TIEDLocDaerah.getText(),
                CmbIEDGenTipe.getSelectedItem().toString(),
                CmbHipsAritmia.getSelectedItem().toString(),
                CmbVertexTransient.getSelectedItem().toString(),
                CmbSleepSpindle.getSelectedItem().toString(),
                CmbArtefak.getSelectedItem().toString(),
                CmbArtKontraksi.getSelectedItem().toString(),
                CmbArtDenyutan.getSelectedItem().toString(),
                CmbArtKeringat.getSelectedItem().toString(),
                CmbArtKedipan.getSelectedItem().toString(),
                CmbArtGerakMata.getSelectedItem().toString(),
                CmbArtMekanis.getSelectedItem().toString(),
                CmbHVPS.getSelectedItem().toString(), THVKet.getText(),
                CmbResponMata.getSelectedItem().toString(),
                CmbKesimpulan.getSelectedItem().toString(),
                CmbKesAIED.getSelectedItem().toString(), CmbKesASW.getSelectedItem().toString(),
                TKesADaerah.getText(),
                CmbKesBIED.getSelectedItem().toString(), CmbKesBSW.getSelectedItem().toString(),
                // usul_komentar akan diupdate via addRow manual karena 43 field
                TUsulKomentar.getText()
            }) == true) {

            tabMode.addRow(new String[]{
                TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                TJK.getText(), TTglLahir.getText(),
                tglRekaman, TDiagnosa.getText(),
                CmbKeadaanRekaman.getSelectedItem().toString(),
                CmbBervoltase.getSelectedItem().toString(),
                CmbFrekuensi.getSelectedItem().toString(),
                CmbGelPatologis.getSelectedItem().toString(),
                CmbGelLambat.getSelectedItem().toString(),
                CmbGLVoltase.getSelectedItem().toString(), TGLFrekuensi.getText(),
                CmbGLLocTipe.getSelectedItem().toString(), TGLLocDaerah.getText(),
                CmbGLGenTipe.getSelectedItem().toString(),
                CmbIED.getSelectedItem().toString(),
                CmbIEDVoltase.getSelectedItem().toString(),
                CmbIEDBentuk.getSelectedItem().toString(), TIEDKet.getText(),
                CmbIEDLocTipe.getSelectedItem().toString(), TIEDLocDaerah.getText(),
                CmbIEDGenTipe.getSelectedItem().toString(),
                CmbHipsAritmia.getSelectedItem().toString(),
                CmbVertexTransient.getSelectedItem().toString(),
                CmbSleepSpindle.getSelectedItem().toString(),
                CmbArtefak.getSelectedItem().toString(),
                CmbArtKontraksi.getSelectedItem().toString(),
                CmbArtDenyutan.getSelectedItem().toString(),
                CmbArtKeringat.getSelectedItem().toString(),
                CmbArtKedipan.getSelectedItem().toString(),
                CmbArtGerakMata.getSelectedItem().toString(),
                CmbArtMekanis.getSelectedItem().toString(),
                CmbHVPS.getSelectedItem().toString(), THVKet.getText(),
                CmbResponMata.getSelectedItem().toString(),
                CmbKesimpulan.getSelectedItem().toString(),
                CmbKesAIED.getSelectedItem().toString(), CmbKesASW.getSelectedItem().toString(),
                TKesADaerah.getText(),
                CmbKesBIED.getSelectedItem().toString(), CmbKesBSW.getSelectedItem().toString(),
                TUsulKomentar.getText(),
                KdDokterPemeriksa.getText(), NmDokterPemeriksa.getText(),
                KdOperator.getText(), NmOperator.getText()
            });
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
            TabUtama.setSelectedIndex(1);
        }
    }

    // ============================================================
    // METHOD: UPDATE / GANTI
    // ============================================================
    private void ganti() {
        int row = tbData.getSelectedRow();
        String noRawatLama = tabMode.getValueAt(row, 0).toString();
        String tglRekaman = Valid.SetTgl(TglRekaman.getSelectedItem() + "") + " " +
            TglRekaman.getSelectedItem().toString().substring(11, 19);

        if (Sequel.mengedittf("pemeriksaan_eeg", "no_rawat=?",
            "tanggal=?,kd_dokter_pemeriksa=?,nip_operator=?,diagnosa=?,keadaan_rekaman=?," +
            "bervoltase=?,frekuensi=?,frekuensi_dominasi=?,frekuensi_minimal=?," +
            "gelombang_patologis=?,gelombang_lambat=?,gl_bervoltase=?,gl_frekuensi=?," +
            "gl_localized_tipe=?,gl_localized_daerah=?,gl_generalized_tipe=?," +
            "ied_ada=?,ied_bervoltase=?,ied_bentuk=?,ied_bentuk_ket=?," +
            "ied_localized_tipe=?,ied_localized_daerah=?,ied_generalized_tipe=?," +
            "hips_aritmia=?,vertex_transient=?,sleep_spindle=?," +
            "artefak=?,artefak_kontraksi=?,artefak_denyutan=?,artefak_keringat=?," +
            "artefak_kedipan=?,artefak_gerak_mata=?,artefak_gangguan_mekanis=?," +
            "hv_ps=?,hv_ps_ket=?,respon_mata=?," +
            "kesimpulan=?,kes_a_ied=?,kes_a_slow_wave=?,kes_a_daerah=?," +
            "kes_b_ied=?,kes_b_slow_wave=?,usul_komentar=?",
            44, new String[]{
                tglRekaman, KdDokterPemeriksa.getText(), KdOperator.getText(), TDiagnosa.getText(),
                CmbKeadaanRekaman.getSelectedItem().toString(),
                CmbBervoltase.getSelectedItem().toString(), CmbFrekuensi.getSelectedItem().toString(),
                TFreqDominasi.getText(), TFreqMinimal.getText(),
                CmbGelPatologis.getSelectedItem().toString(), CmbGelLambat.getSelectedItem().toString(),
                CmbGLVoltase.getSelectedItem().toString(), TGLFrekuensi.getText(),
                CmbGLLocTipe.getSelectedItem().toString(), TGLLocDaerah.getText(),
                CmbGLGenTipe.getSelectedItem().toString(),
                CmbIED.getSelectedItem().toString(), CmbIEDVoltase.getSelectedItem().toString(),
                CmbIEDBentuk.getSelectedItem().toString(), TIEDKet.getText(),
                CmbIEDLocTipe.getSelectedItem().toString(), TIEDLocDaerah.getText(),
                CmbIEDGenTipe.getSelectedItem().toString(),
                CmbHipsAritmia.getSelectedItem().toString(), CmbVertexTransient.getSelectedItem().toString(),
                CmbSleepSpindle.getSelectedItem().toString(),
                CmbArtefak.getSelectedItem().toString(), CmbArtKontraksi.getSelectedItem().toString(),
                CmbArtDenyutan.getSelectedItem().toString(), CmbArtKeringat.getSelectedItem().toString(),
                CmbArtKedipan.getSelectedItem().toString(), CmbArtGerakMata.getSelectedItem().toString(),
                CmbArtMekanis.getSelectedItem().toString(),
                CmbHVPS.getSelectedItem().toString(), THVKet.getText(),
                CmbResponMata.getSelectedItem().toString(),
                CmbKesimpulan.getSelectedItem().toString(),
                CmbKesAIED.getSelectedItem().toString(), CmbKesASW.getSelectedItem().toString(),
                TKesADaerah.getText(),
                CmbKesBIED.getSelectedItem().toString(), CmbKesBSW.getSelectedItem().toString(),
                TUsulKomentar.getText(),
                noRawatLama
            }) == true) {
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
            TabUtama.setSelectedIndex(1);
        }
    }

    // ============================================================
    // METHOD: HAPUS
    // ============================================================
    private void hapus() {
        if (Sequel.queryu2tf("DELETE FROM pemeriksaan_eeg WHERE no_rawat=?", 1,
            new String[]{ tabMode.getValueAt(tbData.getSelectedRow(), 0).toString() }) == true) {
            tabMode.removeRow(tbData.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data..!!");
        }
    }

    // ============================================================
    // METHOD: BERSIHKAN FORM
    // ============================================================
    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        TJK.setText("");
        TTglLahir.setText("");
        DokterPengirim.setText("");
        TglRekaman.setDate(new Date());
        TDiagnosa.setText("");
        CmbKeadaanRekaman.setSelectedIndex(0);
        CmbBervoltase.setSelectedIndex(0);
        CmbFrekuensi.setSelectedIndex(0);
        TFreqDominasi.setText(""); TFreqMinimal.setText("");
        CmbGelPatologis.setSelectedIndex(0);
        CmbGelLambat.setSelectedIndex(0);
        CmbGLVoltase.setSelectedIndex(0); TGLFrekuensi.setText("");
        CmbGLLocTipe.setSelectedIndex(0); TGLLocDaerah.setText("");
        CmbGLGenTipe.setSelectedIndex(0);
        CmbIED.setSelectedIndex(0);
        CmbIEDVoltase.setSelectedIndex(0);
        CmbIEDBentuk.setSelectedIndex(0); TIEDKet.setText("");
        CmbIEDLocTipe.setSelectedIndex(0); TIEDLocDaerah.setText("");
        CmbIEDGenTipe.setSelectedIndex(0);
        CmbHipsAritmia.setSelectedIndex(0);
        CmbVertexTransient.setSelectedIndex(0);
        CmbSleepSpindle.setSelectedIndex(0);
        CmbArtefak.setSelectedIndex(0);
        CmbArtKontraksi.setSelectedIndex(0); CmbArtDenyutan.setSelectedIndex(0);
        CmbArtKeringat.setSelectedIndex(0); CmbArtKedipan.setSelectedIndex(0);
        CmbArtGerakMata.setSelectedIndex(0); CmbArtMekanis.setSelectedIndex(0);
        CmbHVPS.setSelectedIndex(0); THVKet.setText("");
        CmbResponMata.setSelectedIndex(0);
        CmbKesimpulan.setSelectedIndex(0);
        CmbKesAIED.setSelectedIndex(0); CmbKesASW.setSelectedIndex(0);
        TKesADaerah.setText("");
        CmbKesBIED.setSelectedIndex(0); CmbKesBSW.setSelectedIndex(0);
        TUsulKomentar.setText("");
        KdDokterPemeriksa.setText(""); NmDokterPemeriksa.setText("");
        KdOperator.setText(""); NmOperator.setText("");
        TNoRw.requestFocus();
    }

    // ============================================================
    // METHOD: CETAK HTML
    // ============================================================
    private void cetakHTML() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            int row = tbData.getSelectedRow();
            htmlContent = new StringBuilder();
            htmlContent.append("<html><head>")
                .append("<style>body{font-family:Tahoma;font-size:10px;} ")
                .append("table{width:100%;border-collapse:collapse;} ")
                .append("td{padding:2px 4px;} ")
                .append(".hdr{background:#f0f5eb;font-weight:bold;} ")
                .append(".judul{font-size:13px;font-weight:bold;text-align:center;} ")
                .append(".garis{border-bottom:1px solid #888;} ")
                .append("</style></head><body>")
                .append("<p class='judul'>LABORATORIUM EEG</p>")
                .append("<p class='judul'>SUBDIVISI NEUROFISIOLOGI - POLIKLINIK BRAIN CENTRE</p>")
                .append("<p class='judul'>" + akses.getnamars() + "</p>")
                .append("<hr/>")
                .append("<table>")
                .append("<tr><td>Nama</td><td>: " + tabMode.getValueAt(row, 2) + "</td>")
                .append("<td>Jenis Kelamin</td><td>: " + tabMode.getValueAt(row, 3) + "</td>")
                .append("<td>Operator</td><td>: " + tabMode.getValueAt(row, 47) + "</td></tr>")
                .append("<tr><td>Tgl Perekaman</td><td>: " + tabMode.getValueAt(row, 5) + "</td>")
                .append("<td>Tgl Lahir</td><td>: " + tabMode.getValueAt(row, 4) + "</td>")
                .append("<td>No.RM</td><td>: " + tabMode.getValueAt(row, 1) + "</td></tr>")
                .append("<tr><td>Dokter Pengirim</td><td colspan='3'>: " + DokterPengirim.getText() + "</td>")
                .append("<td>Diagnosa</td><td>: " + tabMode.getValueAt(row, 6) + "</td></tr>")
                .append("</table><hr/>")
                // Interpretasi
                .append("<p><b>INTERPRETASI EEG :</b></p>")
                .append("<table>")
                .append("<tr><td>Rekaman dalam keadaan</td><td>: " + tabMode.getValueAt(row, 7) + "</td></tr>")
                .append("<tr><td>Latar Belakang - Bervoltase</td><td>: " + tabMode.getValueAt(row, 8) + "</td></tr>")
                .append("<tr><td>Frekuensi</td><td>: " + tabMode.getValueAt(row, 9) + "</td></tr>")
                .append("</table>")
                // Temuan
                .append("<p><b>TEMUAN - TEMUAN YANG TAMPAK :</b></p>")
                .append("<table>")
                .append("<tr><td>Gelombang Patologis</td><td>: " + tabMode.getValueAt(row, 10) + "</td></tr>")
                .append("<tr><td>Gelombang Lambat Bervoltase</td><td>: " + tabMode.getValueAt(row, 11) +
                    " (" + tabMode.getValueAt(row, 12) + ")" +
                    " Frekuensi: " + tabMode.getValueAt(row, 13) + "</td></tr>")
                .append("<tr><td>Localized</td><td>: " + tabMode.getValueAt(row, 14) +
                    " di daerah " + tabMode.getValueAt(row, 15) + "</td></tr>")
                .append("<tr><td>Generalized</td><td>: " + tabMode.getValueAt(row, 16) + " pada kedua hemisfer</td></tr>")
                .append("<tr><td>IED</td><td>: " + tabMode.getValueAt(row, 17) +
                    " | Voltase: " + tabMode.getValueAt(row, 18) +
                    " | Bentuk: " + tabMode.getValueAt(row, 19) +
                    (tabMode.getValueAt(row, 20).toString().isEmpty() ? "" : " (" + tabMode.getValueAt(row, 20) + ")") + "</td></tr>")
                .append("<tr><td>IED Localized</td><td>: " + tabMode.getValueAt(row, 21) +
                    " di daerah " + tabMode.getValueAt(row, 22) + "</td></tr>")
                .append("<tr><td>IED Generalized</td><td>: " + tabMode.getValueAt(row, 23) + " pada kedua hemisfer</td></tr>")
                .append("<tr><td>Hips Aritmia</td><td>: " + tabMode.getValueAt(row, 24) + "</td>")
                .append("<td>Vertex Transient</td><td>: " + tabMode.getValueAt(row, 25) + "</td>")
                .append("<td>Sleep Spindle</td><td>: " + tabMode.getValueAt(row, 26) + "</td></tr>")
                .append("<tr><td>Artefak</td><td>: " + tabMode.getValueAt(row, 27) +
                    " | Kontraksi: " + tabMode.getValueAt(row, 28) +
                    " | Denyutan: " + tabMode.getValueAt(row, 29) +
                    " | Keringat: " + tabMode.getValueAt(row, 30) + "</td></tr>")
                .append("<tr><td>&nbsp;</td><td>Kedipan: " + tabMode.getValueAt(row, 31) +
                    " | Gerak Mata: " + tabMode.getValueAt(row, 32) +
                    " | Mek: " + tabMode.getValueAt(row, 33) + "</td></tr>")
                .append("<tr><td>HV/PS</td><td>: " + tabMode.getValueAt(row, 34) +
                    (tabMode.getValueAt(row, 35).toString().isEmpty() ? "" : " - " + tabMode.getValueAt(row, 35)) + "</td></tr>")
                .append("<tr><td>Respon Tutup/Buka Mata</td><td>: " + tabMode.getValueAt(row, 36) + "</td></tr>")
                .append("</table>")
                // Kesimpulan
                .append("<p><b>KESIMPULAN :</b></p>")
                .append("<table>")
                .append("<tr><td><b>" + tabMode.getValueAt(row, 37) + "</b></td></tr>")
                .append("<tr><td>a. IED: " + tabMode.getValueAt(row, 38) +
                    " | Slow Wave: " + tabMode.getValueAt(row, 39) +
                    " di daerah " + tabMode.getValueAt(row, 40) + "</td></tr>")
                .append("<tr><td>b. IED: " + tabMode.getValueAt(row, 41) +
                    " | Slow Wave: " + tabMode.getValueAt(row, 42) + " pada kedua hemisfer</td></tr>")
                .append("</table>")
                .append("<p><b>Usul Komentar :</b> " + tabMode.getValueAt(row, 43) + "</p>")
                .append("<br/><table><tr><td width='70%'></td>")
                .append("<td align='center'>Yang Memeriksa,<br/><br/><br/>")
                .append("<b>Dr. " + tabMode.getValueAt(row, 45) + "</b></td></tr></table>")
                .append("</body></html>");

            File f = new File("HasilEEG_" + tabMode.getValueAt(row, 0).toString().replace("/", "-") + ".html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(htmlContent.toString());
            bw.close();
            Desktop.getDesktop().browse(f.toURI());

        } catch (Exception e) {
            System.out.println("Notif Cetak EEG: " + e);
            JOptionPane.showMessageDialog(null, "Gagal cetak: " + e.getMessage());
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    // ============================================================
    // METHOD: CETAK PREVIEW HTML (VERSI BARU - RAPI)
    // ============================================================
    private void cetakPreview() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            int row = tbData.getSelectedRow();

            String namaRs      = akses.getnamars();
            String noRawat     = tabMode.getValueAt(row, 0).toString();
            String noRM        = tabMode.getValueAt(row, 1).toString();
            String namaPasien  = tabMode.getValueAt(row, 2).toString();
            String jk          = tabMode.getValueAt(row, 3).toString();
            String tglLahir    = tabMode.getValueAt(row, 4).toString();
            String tglRekaman  = tabMode.getValueAt(row, 5).toString();
            String diagnosa    = tabMode.getValueAt(row, 6).toString();
            String keadaan     = tabMode.getValueAt(row, 7).toString();
            String bervoltase  = tabMode.getValueAt(row, 8).toString();
            String frekuensi   = tabMode.getValueAt(row, 9).toString();
            String gelPat      = tabMode.getValueAt(row, 10).toString();
            String gelLambat   = tabMode.getValueAt(row, 11).toString();
            String glVolt      = tabMode.getValueAt(row, 12).toString();
            String glFreq      = tabMode.getValueAt(row, 13).toString();
            String glLocTipe   = tabMode.getValueAt(row, 14).toString();
            String glLocDaerah = tabMode.getValueAt(row, 15).toString();
            String glGenTipe   = tabMode.getValueAt(row, 16).toString();
            String iedAda      = tabMode.getValueAt(row, 17).toString();
            String iedVolt     = tabMode.getValueAt(row, 18).toString();
            String iedBentuk   = tabMode.getValueAt(row, 19).toString();
            String iedKet      = tabMode.getValueAt(row, 20).toString();
            String iedLocTipe  = tabMode.getValueAt(row, 21).toString();
            String iedLocDaerah= tabMode.getValueAt(row, 22).toString();
            String iedGenTipe  = tabMode.getValueAt(row, 23).toString();
            String hips        = tabMode.getValueAt(row, 24).toString();
            String vertex      = tabMode.getValueAt(row, 25).toString();
            String sleep       = tabMode.getValueAt(row, 26).toString();
            String artefak     = tabMode.getValueAt(row, 27).toString();
            String artKont     = tabMode.getValueAt(row, 28).toString();
            String artDeny     = tabMode.getValueAt(row, 29).toString();
            String artKer      = tabMode.getValueAt(row, 30).toString();
            String artKed      = tabMode.getValueAt(row, 31).toString();
            String artGerak    = tabMode.getValueAt(row, 32).toString();
            String artMek      = tabMode.getValueAt(row, 33).toString();
            String hvps        = tabMode.getValueAt(row, 34).toString();
            String hvKet       = tabMode.getValueAt(row, 35).toString();
            String responMata  = tabMode.getValueAt(row, 36).toString();
            String kesimpulan  = tabMode.getValueAt(row, 37).toString();
            String kesAIED     = tabMode.getValueAt(row, 38).toString();
            String kesASW      = tabMode.getValueAt(row, 39).toString();
            String kesADaerah  = tabMode.getValueAt(row, 40).toString();
            String kesBIED     = tabMode.getValueAt(row, 41).toString();
            String kesBSW      = tabMode.getValueAt(row, 42).toString();
            String usulKom     = tabMode.getValueAt(row, 43).toString();
            String nmDokter    = tabMode.getValueAt(row, 45).toString();
            String nmOperator  = tabMode.getValueAt(row, 47).toString();
            String dokterPengirimVal = DokterPengirim.getText().isEmpty() ? "-" : DokterPengirim.getText();

            // --- Ambil tanggal bersih untuk tampilan ---
            String tglTampil = tglRekaman.length() >= 10 ? tglRekaman.substring(0, 10) : tglRekaman;

            // --- Generate QR Code Base64 ---
            String qrText   = "Ditandatangani secara elektronik oleh : " + nmDokter;
            String qrBase64 = generateQrBase64(qrText, 130);

            // --- Tentukan warna badge kesimpulan ---
            boolean isNormal   = kesimpulan.toLowerCase().contains("normal")
                              && !kesimpulan.toLowerCase().contains("abnormal");
            String  bdgBg      = isNormal ? "#d4edda" : "#f8d7da";
            String  bdgColor   = isNormal ? "#155724" : "#721c24";
            String  bdgBorder  = isNormal ? "#28a745" : "#dc3545";

            StringBuilder sb = new StringBuilder();

            // ================================================================
            // HEAD + CSS
            // ================================================================
            sb.append("<!DOCTYPE html>")
              .append("<html lang='id'><head>")
              .append("<meta charset='UTF-8'>")
              .append("<title>Hasil EEG - ").append(namaPasien).append("</title>")
              .append("<style>")
              // Reset & body
              .append("*{margin:0;padding:0;box-sizing:border-box;}")
              .append("body{font-family:Arial,sans-serif;font-size:11px;color:#111;background:#d0d0d0;}")
              // Halaman A4
              .append(".page{background:#fff;width:210mm;min-height:297mm;margin:8px auto;")
              .append("padding:12mm 14mm 14mm 14mm;box-shadow:0 3px 10px rgba(0,0,0,.35);}")
              // KOP
              .append(".kop{text-align:center;padding-bottom:6px;margin-bottom:6px;border-bottom:3px double #1a5c1a;}")
              .append(".kop-rs{font-size:15px;font-weight:bold;text-transform:uppercase;letter-spacing:.5px;color:#0d3b0d;}")
              .append(".kop-sub{font-size:11px;font-weight:bold;color:#1a5c1a;margin-top:1px;}")
              .append(".kop-poli{font-size:10px;color:#555;margin-top:1px;}")
              // Judul formulir
              .append(".judul{text-align:center;font-size:12px;font-weight:bold;letter-spacing:.5px;")
              .append("text-transform:uppercase;background:#1a5c1a;color:#fff;")
              .append("padding:5px 8px;margin-bottom:8px;border-radius:2px;}")
              // Section header
              .append(".sec-hd{background:#2e7d32;color:#fff;font-size:10px;font-weight:bold;")
              .append("text-transform:uppercase;padding:3px 7px;letter-spacing:.3px;}")
              // Tabel identitas pasien (border luar saja)
              .append("table.ident{width:100%;border-collapse:collapse;border:1px solid #999;margin-bottom:6px;}")
              .append("table.ident td{padding:3px 6px;font-size:11px;vertical-align:top;border-bottom:1px solid #e0e0e0;}")
              .append("table.ident td.L{font-weight:bold;color:#333;width:22%;white-space:nowrap;}")
              .append("table.ident td.S{width:8px;color:#666;}")
              .append("table.ident td.V{width:28%;}")
              // Tabel data (grid lengkap)
              .append("table.grid{width:100%;border-collapse:collapse;font-size:10.5px;margin-bottom:6px;}")
              .append("table.grid td{padding:3px 6px;border:1px solid #bbb;vertical-align:top;}")
              .append("table.grid td.H{background:#e8f5e9;font-weight:bold;color:#1b5e20;}")
              .append("table.grid td.W25{width:25%;}")
              .append("table.grid td.W20{width:20%;}")
              .append("table.grid td.W30{width:30%;}")
              .append("table.grid td.W50{width:50%;}")
              // Kotak kesimpulan
              .append(".kes-wrap{border:2px solid #2e7d32;border-radius:3px;padding:7px 10px;")
              .append("background:#f1f8e9;margin-bottom:6px;}")
              .append(".kes-badge{display:inline-block;padding:3px 14px;border-radius:12px;font-weight:bold;")
              .append("font-size:12px;border:2px solid ").append(bdgBorder).append(";")
              .append("background:").append(bdgBg).append(";color:").append(bdgColor).append(";}")
              // Usul komentar
              .append(".usul-box{border:1px solid #bbb;padding:6px 8px;min-height:28px;")
              .append("font-size:11px;background:#fafafa;border-radius:2px;margin-bottom:8px;}")
              // TTD
              .append(".ttd-area{margin-top:16px;width:100%;}")
              .append(".ttd-cell{width:45%;text-align:center;font-size:11px;vertical-align:top;}")
              .append(".ttd-qr{display:block;margin:4px auto;border:1px solid #ccc;}")
              .append(".ttd-name{font-weight:bold;font-size:11px;border-top:1px solid #333;")
              .append("padding-top:3px;display:inline-block;min-width:130px;margin-top:3px;}")
              // Tombol cetak
              .append(".btn-print{position:fixed;top:12px;right:12px;background:#1a5c1a;color:#fff;")
              .append("border:none;padding:9px 18px;font-size:12px;border-radius:4px;cursor:pointer;")
              .append("box-shadow:0 2px 6px rgba(0,0,0,.4);z-index:999;}")
              .append(".btn-print:hover{background:#0d3b0d;}")
              // Print media
              .append("@media print{")
              .append(".btn-print{display:none;}")
              .append("body{background:#fff;}")
              .append(".page{box-shadow:none;margin:0;width:100%;padding:10mm 12mm;}")
              .append("}")
              .append("</style></head><body>")

              // ================================================================
              // TOMBOL CETAK
              // ================================================================
              .append("<button class='btn-print' onclick='window.print()'>&#128438; Cetak</button>")
              .append("<div class='page'>")

              // ================================================================
              // KOP SURAT
              // ================================================================
              .append("<div class='kop'>")
              .append("<div class='kop-rs'>").append(namaRs).append("</div>")
              .append("<div class='kop-sub'>LABORATORIUM EEG &ndash; SUBDIVISI NEUROFISIOLOGI</div>")
              .append("<div class='kop-poli'>POLIKLINIK BRAIN CENTRE</div>")
              .append("</div>")

              // Judul formulir
              .append("<div class='judul'>HASIL PEMERIKSAAN ELEKTROENSEFALOGRAFI (EEG)</div>")

              // ================================================================
              // IDENTITAS PASIEN
              // ================================================================
              .append("<div class='sec-hd'>IDENTITAS PASIEN</div>")
              .append("<table class='ident'>")
              .append("<tr>")
              .append("<td class='L'>Nama Pasien</td><td class='S'>:</td>")
              .append("<td class='V'><b>").append(namaPasien).append("</b></td>")
              .append("<td class='L'>No. Rekam Medis</td><td class='S'>:</td>")
              .append("<td><b>").append(noRM).append("</b></td>")
              .append("</tr><tr>")
              .append("<td class='L'>Jenis Kelamin</td><td class='S'>:</td>")
              .append("<td class='V'>").append(jk).append("</td>")
              .append("<td class='L'>No. Rawat</td><td class='S'>:</td>")
              .append("<td>").append(noRawat).append("</td>")
              .append("</tr><tr>")
              .append("<td class='L'>Tanggal Lahir</td><td class='S'>:</td>")
              .append("<td class='V'>").append(tglLahir).append("</td>")
              .append("<td class='L'>Tgl. Rekaman</td><td class='S'>:</td>")
              .append("<td>").append(tglTampil).append("</td>")
              .append("</tr><tr>")
              .append("<td class='L'>Dokter Pengirim</td><td class='S'>:</td>")
              .append("<td class='V'>").append(dokterPengirimVal).append("</td>")
              .append("<td class='L'>Diagnosa</td><td class='S'>:</td>")
              .append("<td>").append(diagnosa.isEmpty() ? "-" : diagnosa).append("</td>")
              .append("</tr><tr>")
              .append("<td class='L'>Operator EEG</td><td class='S'>:</td>")
              .append("<td class='V'>").append(nmOperator).append("</td>")
              .append("<td class='L'>Dokter Pemeriksa</td><td class='S'>:</td>")
              .append("<td><b>").append(nmDokter).append("</b></td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // INTERPRETASI EEG
              // ================================================================
              .append("<div class='sec-hd'>INTERPRETASI EEG</div>")
              .append("<table class='grid'>")
              .append("<tr>")
              .append("<td class='H W25'>Keadaan Rekaman</td>")
              .append("<td class='W25'>").append(keadaan).append("</td>")
              .append("<td class='H W25'>Bervoltase</td>")
              .append("<td class='W25'>").append(bervoltase).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>Frekuensi</td>")
              .append("<td colspan='3'>").append(frekuensi).append("</td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // TEMUAN - TEMUAN YANG TAMPAK
              // ================================================================
              .append("<div class='sec-hd'>TEMUAN &ndash; TEMUAN YANG TAMPAK</div>")
              .append("<table class='grid'>")
              .append("<tr>")
              .append("<td class='H W25'>Gelombang Patologis</td>")
              .append("<td class='W25'>").append(gelPat).append("</td>")
              .append("<td class='H W25'>Gelombang Lambat</td>")
              .append("<td class='W25'>").append(gelLambat).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>GL Bervoltase</td>")
              .append("<td class='W25'>").append(glVolt).append("</td>")
              .append("<td class='H W25'>GL Frekuensi</td>")
              .append("<td class='W25'>").append(glFreq).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>GL Localized</td>")
              .append("<td class='W25'>").append(glLocTipe).append(" di daerah ").append(glLocDaerah.isEmpty() ? "-" : glLocDaerah).append("</td>")
              .append("<td class='H W25'>GL Generalized</td>")
              .append("<td class='W25'>").append(glGenTipe).append(" pada kedua hemisfer</td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // IED
              // ================================================================
              .append("<div class='sec-hd'>IED (INTERICTAL EPILEPTIFORM DISCHARGES)</div>")
              .append("<table class='grid'>")
              .append("<tr>")
              .append("<td class='H W25'>IED</td>")
              .append("<td class='W25'>").append(iedAda).append("</td>")
              .append("<td class='H W25'>Bervoltase</td>")
              .append("<td class='W25'>").append(iedVolt).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>Bentuk</td>")
              .append("<td class='W25'>").append(iedBentuk).append(iedKet.isEmpty() ? "" : " (" + iedKet + ")").append("</td>")
              .append("<td class='H W25'>IED Generalized</td>")
              .append("<td class='W25'>").append(iedGenTipe).append(" pada kedua hemisfer</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>IED Localized</td>")
              .append("<td colspan='3'>").append(iedLocTipe).append(" di daerah ").append(iedLocDaerah.isEmpty() ? "-" : iedLocDaerah).append("</td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // TEMUAN LAIN
              // ================================================================
              .append("<div class='sec-hd'>TEMUAN LAIN</div>")
              .append("<table class='grid'>")
              .append("<tr>")
              .append("<td class='H W25'>Hips Aritmia</td><td class='W25'>").append(hips).append("</td>")
              .append("<td class='H W25'>Vertex Transient</td><td class='W25'>").append(vertex).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>Sleep Spindle</td><td class='W25'>").append(sleep).append("</td>")
              .append("<td class='H W25'>Respon Tutup/Buka Mata</td><td class='W25'>").append(responMata).append("</td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // ARTEFAK
              // ================================================================
              .append("<div class='sec-hd'>ARTEFAK</div>")
              .append("<table class='grid'>")
              .append("<tr>")
              .append("<td class='H W25'>Artefak</td>")
              .append("<td colspan='3'>").append(artefak).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>Kontraksi Otot</td><td class='W25'>").append(artKont).append("</td>")
              .append("<td class='H W25'>Denyutan Pembuluh</td><td class='W25'>").append(artDeny).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>Keringat</td><td class='W25'>").append(artKer).append("</td>")
              .append("<td class='H W25'>Kedipan Mata</td><td class='W25'>").append(artKed).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>Gerakan Bola Mata</td><td class='W25'>").append(artGerak).append("</td>")
              .append("<td class='H W25'>Gangguan Mekanis</td><td class='W25'>").append(artMek).append("</td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // HV/PS
              // ================================================================
              .append("<div class='sec-hd'>HIPERVENTILASI / FOTIK STIMULASI (HV/PS)</div>")
              .append("<table class='grid'>")
              .append("<tr>")
              .append("<td class='H W25'>HV/PS</td><td class='W25'>").append(hvps).append("</td>")
              .append("<td class='H W25'>Keterangan</td><td class='W25'>").append(hvKet.isEmpty() ? "-" : hvKet).append("</td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // KESIMPULAN
              // ================================================================
              .append("<div class='sec-hd'>KESIMPULAN</div>")
              .append("<div class='kes-wrap'>")
              .append("<span class='kes-badge'>").append(kesimpulan).append("</span>")
              .append("</div>")
              .append("<table class='grid'>")
              .append("<tr>")
              .append("<td class='H W25'>a. IED</td><td class='W25'>").append(kesAIED.isEmpty() ? "-" : kesAIED).append("</td>")
              .append("<td class='H W25'>Slow Wave</td><td class='W25'>").append(kesASW.isEmpty() ? "-" : kesASW).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>di Daerah</td>")
              .append("<td colspan='3'>").append(kesADaerah.isEmpty() ? "-" : kesADaerah).append("</td>")
              .append("</tr><tr>")
              .append("<td class='H W25'>b. IED</td><td class='W25'>").append(kesBIED.isEmpty() ? "-" : kesBIED).append("</td>")
              .append("<td class='H W25'>Slow Wave</td><td class='W25'>").append(kesBSW.isEmpty() ? "-" : kesBSW).append(" pada kedua hemisfer</td>")
              .append("</tr>")
              .append("</table>")

              // ================================================================
              // USUL / KOMENTAR
              // ================================================================
              .append("<div class='sec-hd'>USUL / KOMENTAR</div>")
              .append("<div class='usul-box'>").append(usulKom.isEmpty() ? "-" : usulKom).append("</div>");

            // ================================================================
            // TANDA TANGAN + QR CODE
            // ================================================================
            sb.append("<table class='ttd-area'><tr>")
              .append("<td style='width:55%;'></td>")
              .append("<td class='ttd-cell'>")
              .append("Makassar, ").append(tglTampil).append("<br/>")
              .append("Yang Memeriksa,<br/><br/>");

            if (qrBase64 != null) {
                sb.append("<img class='ttd-qr' src='data:image/png;base64,")
                  .append(qrBase64)
                  .append("' width='110' height='110'")
                  .append(" title='").append(qrText).append("' /><br/>");
            } else {
                sb.append("<div style='width:110px;height:110px;border:1px dashed #999;")
                  .append("margin:0 auto 4px;display:flex;align-items:center;justify-content:center;")
                  .append("font-size:9px;color:#888;'>TTD Elektronik</div>");
            }

            sb.append("<span class='ttd-name'>").append(nmDokter).append("</span>")
              .append("</td></tr></table>")
              .append("</div>") // end .page
              .append("</body></html>");

            String namaFile = "PreviewEEG_" + noRawat.replace("/", "-") + ".html";
            File f = new File(System.getProperty("user.home"), namaFile);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(sb.toString());
            bw.close();
            Desktop.getDesktop().browse(f.toURI());

        } catch (Exception e) {
            System.out.println("Notif Preview EEG: " + e);
            JOptionPane.showMessageDialog(null, "Gagal membuka preview: " + e.getMessage());
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    // ============================================================
    // QR CODE GENERATOR (Pure Java - tidak butuh library eksternal)
    // Menghasilkan QR Code Matrix 2D sederhana sebagai Base64 PNG
    // Kompatibel dengan pola TTD elektronik di sistem Khanza
    // ============================================================
    private String generateQrBase64(String text, int size) {
        try {
            // Encode teks menjadi matrix QR sederhana menggunakan bit pattern
            // Implementasi QR-like visual menggunakan data matrix encoding
            int[][] matrix = buildQrMatrix(text);
            if (matrix == null) return null;

            int modules   = matrix.length;
            int cellSize  = Math.max(2, size / modules);
            int imgSize   = modules * cellSize;
            int quiet     = cellSize * 2; // quiet zone

            BufferedImage img = new BufferedImage(
                imgSize + quiet * 2,
                imgSize + quiet * 2,
                BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g = img.createGraphics();

            // Background putih
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, img.getWidth(), img.getHeight());

            // Gambar modul
            g.setColor(Color.BLACK);
            for (int r = 0; r < modules; r++) {
                for (int c = 0; c < modules; c++) {
                    if (matrix[r][c] == 1) {
                        g.fillRect(
                            quiet + c * cellSize,
                            quiet + r * cellSize,
                            cellSize, cellSize
                        );
                    }
                }
            }
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (Exception e) {
            System.out.println("QR generate error: " + e);
            return null;
        }
    }

    /**
     * Membangun matrix QR Code menggunakan ZXing jika tersedia,
     * atau fallback ke Data Matrix visual sederhana.
     * Struktur: finder patterns (sudut) + data bits dari teks
     */
    private int[][] buildQrMatrix(String text) {
        // Coba pakai ZXing jika ada di classpath Khanza
        try {
            Class<?> qrClass   = Class.forName("com.google.zxing.qrcode.QRCodeWriter");
            Class<?> bfClass   = Class.forName("com.google.zxing.BarcodeFormat");
            Class<?> htClass   = Class.forName("com.google.zxing.common.BitMatrix");
            Object   writer    = qrClass.newInstance();
            Object   format    = bfClass.getField("QR_CODE").get(null);
            Object   bitMatrix = qrClass.getMethod("encode",
                                     String.class,
                                     bfClass,
                                     int.class, int.class)
                                     .invoke(writer, text, format, 29, 29);

            int w = (int) htClass.getMethod("getWidth").invoke(bitMatrix);
            int h = (int) htClass.getMethod("getHeight").invoke(bitMatrix);
            int[][] m = new int[h][w];
            for (int r = 0; r < h; r++)
                for (int c = 0; c < w; c++)
                    m[r][c] = (boolean) htClass.getMethod("get", int.class, int.class)
                                               .invoke(bitMatrix, c, r) ? 1 : 0;
            return m;

        } catch (Exception ignored) {
            // ZXing tidak tersedia — gunakan fallback matrix visual
        }

        // ---- FALLBACK: visual QR-like matrix dari hash teks ----
        int N = 21; // ukuran 21x21 (QR Version 1 style)
        int[][] m = new int[N][N];

        // Finder pattern kiri-atas
        drawFinder(m, 0, 0);
        // Finder pattern kanan-atas
        drawFinder(m, 0, N - 7);
        // Finder pattern kiri-bawah
        drawFinder(m, N - 7, 0);

        // Timing pattern horizontal & vertikal
        for (int i = 8; i < N - 8; i++) {
            m[6][i] = (i % 2 == 0) ? 1 : 0;
            m[i][6] = (i % 2 == 0) ? 1 : 0;
        }

        // Format bits (hardcoded level L, mask 0)
        int[] fmtBits = {1,1,1,0,1,1,1,1,1,0,0,0,1,0,0};
        int fi = 0;
        for (int i = 0; i <= 5; i++) m[8][i]     = fmtBits[fi++];
        m[8][7] = fmtBits[fi++];
        m[8][8] = fmtBits[fi++];
        m[7][8] = fmtBits[fi++];
        for (int i = 5; i >= 0; i--) m[i][8]     = fmtBits[fi++];

        // Dark module wajib
        m[N - 8][8] = 1;

        // Data: encode teks menjadi bits berdasarkan hash karakter
        byte[] bytes;
        try { bytes = text.getBytes("UTF-8"); }
        catch (Exception e) { bytes = text.getBytes(); }

        // Isi data modules zig-zag dari kanan-bawah
        boolean[] dataBits = new boolean[150];
        int bi = 0;
        for (byte b : bytes) {
            if (bi >= dataBits.length) break;
            for (int bit = 7; bit >= 0; bit--) {
                if (bi >= dataBits.length) break;
                dataBits[bi++] = ((b >> bit) & 1) == 1;
            }
        }

        int idx = 0;
        boolean goUp = true;
        for (int col = N - 1; col >= 1; col -= 2) {
            if (col == 6) col = 5;
            for (int row = goUp ? N - 1 : 0; goUp ? row >= 0 : row < N; row += goUp ? -1 : 1) {
                for (int dc = 0; dc < 2; dc++) {
                    int c = col - dc;
                    if (isReserved(m, row, c, N)) continue;
                    m[row][c] = (idx < dataBits.length && dataBits[idx]) ? 1 : 0;
                    idx++;
                }
            }
            goUp = !goUp;
        }

        return m;
    }

    /** Gambar finder pattern 7x7 di posisi (startR, startC) */
    private void drawFinder(int[][] m, int r, int c) {
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                boolean border = (i == 0 || i == 6 || j == 0 || j == 6);
                boolean inner  = (i >= 2 && i <= 4 && j >= 2 && j <= 4);
                m[r + i][c + j] = (border || inner) ? 1 : 0;
            }
        // Separator (baris/kolom putih di luar finder)
        if (r + 7 < m.length)
            for (int j = c; j < c + 8 && j < m[0].length; j++) m[r + 7][j] = 0;
        if (c + 7 < m[0].length)
            for (int i = r; i < r + 8 && i < m.length; i++) m[i][c + 7] = 0;
    }

    /** Cek apakah posisi sudah terisi oleh finder/timing/format pattern */
    private boolean isReserved(int[][] m, int r, int c, int N) {
        // Finder patterns area (termasuk separator)
        if (r < 9 && c < 9) return true;
        if (r < 9 && c >= N - 8) return true;
        if (r >= N - 8 && c < 9) return true;
        // Timing patterns
        if (r == 6 || c == 6) return true;
        // Dark module
        if (r == N - 8 && c == 8) return true;
        return false;
    }

    // ============================================================
    // PUBLIC METHODS (dipanggil dari modul lain)
    // ============================================================
    public void setNoRm(String norwt, Date tgl) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl);
        isRawat();
        tampil();
    }

    public void isCek() {
        BtnSimpan.setEnabled(true);
        BtnHapus.setEnabled(true);
        BtnEdit.setEnabled(true);

        if (akses.getjml2() >= 1) {
            KdOperator.setEditable(false);
            BtnCariOperator.setEnabled(false);
            KdOperator.setText(akses.getkode());
            NmOperator.setText(petugas.tampil3(KdOperator.getText()));
            if (NmOperator.getText().isEmpty()) {
                KdOperator.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }

        if (TANGGALMUNDUR.equals("no")) {
            if (!akses.getkode().equals("Admin Utama")) {
                TglRekaman.setEditable(false);
                TglRekaman.setEnabled(false);
            }
        }
    }

    // ============================================================
    // VARIABLE DECLARATIONS
    // ============================================================
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.panelisi panelTombol;
    private widget.panelisi panelCari;
    private javax.swing.JTabbedPane TabUtama;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollData;
    private widget.PanelBiasa FormInput;
    private widget.Table tbData;

    // Header
    private widget.TextBox TNoRw, TNoRM, TPasien, TJK, TTglLahir;
    private widget.Tanggal TglRekaman;
    private widget.TextBox TDiagnosa, DokterPengirim;
    private widget.TextBox KdOperator, NmOperator;
    private widget.Button BtnCariOperator;
    private widget.TextBox KdDokterPemeriksa, NmDokterPemeriksa;
    private widget.Button BtnCariDokterPemeriksa;

    // Interpretasi
    private widget.ComboBox CmbKeadaanRekaman, CmbBervoltase, CmbFrekuensi;
    private widget.TextBox TFreqDominasi, TFreqMinimal;

    // Temuan
    private widget.ComboBox CmbGelPatologis, CmbGelLambat, CmbGLVoltase;
    private widget.TextBox TGLFrekuensi, TGLLocDaerah;
    private widget.ComboBox CmbGLLocTipe, CmbGLGenTipe;

    // IED
    private widget.ComboBox CmbIED, CmbIEDVoltase, CmbIEDBentuk;
    private widget.TextBox TIEDKet, TIEDLocDaerah;
    private widget.ComboBox CmbIEDLocTipe, CmbIEDGenTipe;

    // Lain-lain
    private widget.ComboBox CmbHipsAritmia, CmbVertexTransient, CmbSleepSpindle;
    private widget.ComboBox CmbArtefak;
    private widget.ComboBox CmbArtKontraksi, CmbArtDenyutan, CmbArtKeringat;
    private widget.ComboBox CmbArtKedipan, CmbArtGerakMata, CmbArtMekanis;
    private widget.ComboBox CmbHVPS;
    private widget.TextBox THVKet;
    private widget.ComboBox CmbResponMata;

    // Kesimpulan
    private widget.ComboBox CmbKesimpulan;
    private widget.ComboBox CmbKesAIED, CmbKesASW;
    private widget.TextBox TKesADaerah;
    private widget.ComboBox CmbKesBIED, CmbKesBSW;
    private widget.TextBox TUsulKomentar;

    // Tombol
    private widget.Button BtnSimpan, BtnBaru, BtnHapus, BtnEdit, BtnCetak, BtnSemua, BtnKeluar, BtnPreview;

    // Cari & Count
    private widget.Tanggal DTPCari1, DTPCari2;
    private widget.TextBox TCari;
    private widget.Button BtnCari;
    private widget.Label LCount;

    // Labels
    private widget.Label lNoRw, lNoRM, lPasien, lJK, lTglLahir;
    private widget.Label lTglRekaman, lDiagnosa, lDokterPengirim;
    private widget.Label lOperator, lDokterPemeriksa;
    private widget.Label lJudulInterpretasi, lJudulTemuan, lJudulKesimpulan;
    private javax.swing.JSeparator lSep1, lSep2, lSep3, lSep4, lSep5, lSep6;
}
