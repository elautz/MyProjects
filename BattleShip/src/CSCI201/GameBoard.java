//Emma Lautz: 5040437826
//Assignment 2
package CSCI201;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

//this class controls the game board GUI
public class GameBoard extends JFrame{

	private static final long serialVersionUID = 1L;
	//contains all the labels for the coordinates
	private JButton [][] playerLabels = new JButton [11][11];
	
	private JButton [][] computerLabels = new JButton[11][11];
	
	private String [][] computerShips = new String[11][11];
	
	private String [][] playerShips = new String[11][11];
	
	private Border compound;
	private Border compound3;
	
	private JLabel log;
	
	private JButton start;
	
	private JButton selectFile;
	
	private JFileChooser jfl;
	
	private playGame pgPlayer;
	private playGame pgComp;
	
	private Boolean computerShipsSet;
	private Boolean playerShipsSet;
	
	private JLabel fileLabel;
	
	private File file;
	
	private Font font;
	
	private ImageIcon questionMark;
	private ImageIcon A;
	private ImageIcon B;
	private ImageIcon C;
	private ImageIcon D;
	
	private JDialog jd1;
	private JDialog jd2;
	
	private JMenuItem HowTo;
	private JMenuItem About;;
	
	private Set<Coordinates> AcoorPlayer;
	private Set<Coordinates> BcoorPlayer;
	private Set<Coordinates> CcoorPlayer;
	
	private Destroyer D1;
	private Destroyer D2;
	
	private int Anum = 0;
	private int Bnum = 0;
	private int Cnum = 0;
	private int Dnum = 0;
	
	
	public GameBoard(){
		super("Battleship");
		playerShipsSet = false;
		computerShipsSet = false;
		font = new Font("Athelas", Font.BOLD, 14);
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		compound = BorderFactory.createCompoundBorder(
	            raisedbevel, loweredbevel);
		Border compound2 = BorderFactory.createCompoundBorder(compound, new EmptyBorder(10,10,10,10));
		compound3 = BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10), compound2);
		
		setImages();
		
		AcoorPlayer = new HashSet<Coordinates>(5);
		BcoorPlayer = new HashSet<Coordinates>(4);
		CcoorPlayer = new HashSet<Coordinates>(3);
		
		makeDialogs();
		JMenuBar mainMenu = new JMenuBar();
		mainMenu.setBackground(Color.MAGENTA);
		JMenu Info = new JMenu("Info");
		Info.setBackground(Color.MAGENTA);
		Info.setFont(font);
		HowTo = new JMenuItem("How To");
		HowTo.setFont(font);
		About = new JMenuItem("About");
		About.setFont(font);
		About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.META_MASK));
		HowTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.META_MASK));
		Info.setMnemonic('I');
		About.setMnemonic('A');
		HowTo.setMnemonic('H');
		Info.add(HowTo);
		Info.add(About);
		mainMenu.add(Info);
		
		setJMenuBar(mainMenu);
		
		D1 = null;
		D2 = null;

		UIManager.getDefaults().put("Button.disabledText",Color.BLACK);
		setLocation(100, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//sets the JLabels for the character coordinates
		jfl = new JFileChooser();
		start = new JButton("START");
		start.setFont(font);
		start.setEnabled(false);
		log = new JLabel("Log: You are in edit mode, click to place your ships");
		log.setFont(font);
		selectFile = new JButton("Select File");
		selectFile.setFont(font);
		fileLabel = new JLabel("file: ");
		fileLabel.setFont(font);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
	            "battle files (*.battle)", "battle");
	     	jfl.setFileFilter(filter);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(log);
		bottomPanel.add(selectFile);
		bottomPanel.add(fileLabel);
		bottomPanel.add(start);
		
		JPanel main = new JPanel( new GridLayout(1, 3));
		//the center JPanel will contain all the coordinates
		JPanel mainLeft = new JPanel(new BorderLayout());
		JPanel left = new JPanel( new GridLayout(11, 11));
		//the right JPanel will contain the high score information
		JPanel mainRight = new JPanel(new BorderLayout());
		JPanel right = new JPanel( new GridLayout(11, 11));
		
		setGridImages(left, right);
		enableEditButtons(false);
		mainLeft.add(left, BorderLayout.CENTER);
		mainRight.add(right, BorderLayout.CENTER);
		
		makePretty(mainLeft, mainRight);
		
		main.add(mainLeft);
		main.add(mainRight);
		
		add(main, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		pgComp = new playGame(playerShips, playerLabels, log, GameBoard.this);
		pgPlayer = new playGame(computerShips, computerLabels, 
				A, B, C, D, pgComp, log, GameBoard.this);
		
		addActionListeners();
		
		getContentPane().add(main);
		pack();
		showUser(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class editModeListener implements ActionListener{
		private int coor1;
		private int coor2;
		String coor3;
		
		public editModeListener(int i, int j, String s){
			coor1 = i;
			coor2 = j;
			coor3 = s;
		}
		public void actionPerformed(ActionEvent e) {
			if (playerShips[coor1][coor2] != null) {
				if (playerShips[coor1][coor2].equals("D")) {
					emptyDestroyer(coor1, coor2);
				}
				else {
					emptyShip(playerShips[coor1][coor2]);
				}
				//PopWindow pop = new PopWindow(coor1, coor2, coor3);
			}
			else if (playerShipsSet == false && playerShips[coor1][coor2] == null) {
				PopWindow pop = new PopWindow(coor1, coor2, coor3);
			}
		}
	}
	
	private void addActionListeners()
	{
		selectFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int temp1 = jfl.showOpenDialog(selectFile);
				if (temp1 == JFileChooser.APPROVE_OPTION)
				{
					file = jfl.getSelectedFile();
					if (file != null)
					{
						String temp = file.getName();
						int index = temp.indexOf('.');
						String append = temp.substring(0,  index);
						fileLabel.setText("file: "+append);
						try {
							setComputerShips();
							computerShipsSet = true;
							tryToStart();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(false);
				start.setVisible(false);
				jfl.setEnabled(false);
				jfl.setVisible(false);
				selectFile.setEnabled(false);
				selectFile.setVisible(false);
				fileLabel.setText("");
				log.setText("log: Player: N/A Computer: N/A");
				enableEditButtons(true);
				pgComp.update(playerShips, playerLabels, null);
				pgPlayer.update(computerShips, computerLabels, pgComp);
			}
			
		});
		
		About.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) { jd1.setVisible(true); }
		});
		
		HowTo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) { jd2.setVisible(true); }
		});
	}
	
	private void makeDialogs()
	{
		JMenuBar mainMenu = new JMenuBar();
		JMenu Info = new JMenu("Info");
		JMenuItem HowTo = new JMenuItem("How To");
		JMenuItem About = new JMenuItem("About");
		About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.META_MASK));
		HowTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.META_MASK));
		Info.setMnemonic('I');
		About.setMnemonic('A');
		HowTo.setMnemonic('H');
		Info.add(HowTo);
		Info.add(About);
		mainMenu.add(Info);
		
		jd1 = new JDialog();
		jd1.setTitle("About");
		jd1.setSize(400, 600);
		jd1.setLocationRelativeTo(GameBoard.this);
		jd1.setLayout(new BoxLayout (jd1.getContentPane(), BoxLayout.Y_AXIS));
		ImageIcon i = new ImageIcon("data/Emma.jpg");
		Image image = i.getImage();
		Image image2 = image.getScaledInstance(192, 256, Image.SCALE_SMOOTH);
		ImageIcon Em = new ImageIcon(image2);
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel(new BorderLayout());
		JPanel panel3 = new JPanel(new BorderLayout());
		JLabel label = new JLabel(Em);
		JLabel name = new JLabel("Created by Emma Lautz", SwingConstants.CENTER);
		name.setBorder(compound3);
		name.setFont(font);
		JLabel id = new JLabel ("USC ID: 5040437826", SwingConstants.CENTER);
		id.setBorder(compound3);
		id.setFont(font);
		JLabel assign = new JLabel ("Date created: 2/22/15", SwingConstants.CENTER);
		assign.setBorder(compound3);
		assign.setFont(font);
		JLabel date = new JLabel("CSCI201 USC: Assignment3", SwingConstants.CENTER);
		date.setBorder(compound3);
		date.setFont(font);
		label.setPreferredSize(new Dimension(192, 256));
		panel.setPreferredSize(new Dimension(292, 356));
		panel.setBorder(compound3);
		panel2.add(name, BorderLayout.NORTH);
		panel2.add(id, BorderLayout.SOUTH);
		panel3.add(assign, BorderLayout.NORTH);
		panel3.add(date, BorderLayout.SOUTH);
		panel.add(label, BorderLayout.CENTER);
		label.setBackground(Color.YELLOW);
		name.setBackground(Color.CYAN);
		id.setBackground(Color.CYAN);
		date.setBackground(Color.CYAN);
		assign.setBackground(Color.CYAN);
		jd1.add(Box.createGlue());
		jd1.add(panel2);
		jd1.add(Box.createGlue());
		jd1.add(panel);
		jd1.add(Box.createGlue());
		jd1.add(panel3);
		jd1.add(Box.createGlue());
		jd1.setModal(true);
		
		jd2 = new JDialog();
		jd2.setTitle("How To Play Battleship");
		jd2.setSize(400, 600);
		jd2.setLocationRelativeTo(GameBoard.this);
		JTextArea area = new JTextArea();
		area.setFont(font);
		area.setBorder(compound3);
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		String lines = "";
		try{
			FileReader fr = new FileReader("data/Instructions.txt");
			BufferedReader textReader = new BufferedReader(fr);
			String temp1 = textReader.readLine();
			lines = temp1;
			String temp = "";
			while (!temp.equals("DONE")) {
				lines = lines + "\n" + temp;
				temp = textReader.readLine();
			}
			textReader.close();
		}
		catch (IOException e) { System.out.println(e.getMessage()); }
		
		area.setText(lines);
		area.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(area);
		scrollPane.setBackground(Color.YELLOW);
		jd2.add(scrollPane);
		jd2.setModal(true);
		setJMenuBar(mainMenu);
	}
	public void reset() {
		start.setVisible(true);
		jfl.setEnabled(true);
		jfl.setVisible(true);
		selectFile.setEnabled(true);
		selectFile.setVisible(true);
		fileLabel.setText("");
		enableEditButtons(false);
		playerShipsSet = false;
		computerShipsSet = false;
		enableStart(false);
		Anum = 0;
		Bnum = 0;
		Cnum = 0;
		Dnum = 0;
		
		file = null;
		
		for (int i = 1; i<11; i++) {
			for (int j = 0; j<10; j++) {
				computerShips[i][j] = null;
				computerLabels[i][j].setIcon(questionMark);
				computerLabels[i][j].setDisabledIcon(questionMark);
				playerShips[i][j] = null;
				playerLabels[i][j].setIcon(questionMark);
				playerLabels[i][j].setDisabledIcon(questionMark);
			}
		}
		D1 = null;
		D2 = null;
		AcoorPlayer.clear();
		BcoorPlayer.clear();
		CcoorPlayer.clear();
		
		pgComp.reset();
		pgPlayer.reset();
		pgComp.update(playerShips, playerLabels, null);
		pgPlayer.update(computerShips, computerLabels, pgComp);
		log.setText("Log: You are in edit mode, click to place your ships");
	}
	
	public void playerShipsReset() { playerShipsSet = (Anum == 1 && Bnum == 1 && Cnum == 1 && Dnum ==2); }
	
	
	private void emptyDestroyer(int i, int j) {
		Coordinates one = null;
		Coordinates two = null;
		Dnum--;
		if (D1 == null)
		{
			if (D2.contains(i, j)) {
				one = D2.getX();
				two = D2.getY();
				D2 = null;
			}
		}
		else if (D2 == null)
		{
			if (D1.contains(i, j)) {
				one = D1.getX();
				two = D1.getY();
				D1 = null;
			}
		}
		else
		{
			if (D1.contains(i, j)) {
				one = D1.getX();
				two = D1.getY();
				D1 = null;
			}
			else if (D2.contains(i, j)) {
				one = D2.getX();
				two = D2.getY();
				D2 = null;
			}
		}
		playerShips[one.getX()] [one.getY()] = null;
		playerLabels[one.getX()][one.getY()].setIcon(questionMark);
		playerShips[two.getX()] [two.getY()] = null;
		playerLabels[two.getX()][two.getY()].setIcon(questionMark);
	}
	
	private void emptyShip(String s) {
		Iterator<Coordinates> it;
		if (s.equals("A")) {
			it = AcoorPlayer.iterator();
			Anum--;
		}
		else if (s.equals("B")) {
			it = BcoorPlayer.iterator();
			Bnum--;
		}
		else if (s.equals("C")) {
			it = CcoorPlayer.iterator();
			Cnum--;
		}
		else { it = null; }
		while(it.hasNext()) {
			System.out.println("in while loop");
			Coordinates temp = it.next();
			System.out.println(playerShips[temp.getX()][temp.getY()]);
			playerShips[temp.getX()] [temp.getY()] = null;
			playerLabels[temp.getX()][temp.getY()].setIcon(questionMark);
			it.remove();
		}
	}
	
	//makes the GUI visible or not
	public void showUser(Boolean t) { setVisible(t);}
	
	public void tryToStart(){
		if (playerShipsSet && computerShipsSet) { enableStart(true); }
	}
	
	private void enableStart(Boolean b){ start.setEnabled(b);}
	
	private void enableEditButtons(Boolean b){
		for (int i = 1; i<11; i++){
			for (int j = 0; j<10; j++){
				computerLabels[i][j].setEnabled(b);
				playerLabels[i][j].setEnabled(!b);
			}
		}
	}
	
	public static String getString(int i){
		switch (i){
		case 0 :
			return "A";
		case 1:
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		case 8:
			return "I";
		case 9:
			return "J";
		default:
			return "";
		}
	}
	
	
	private void setComputerShips() throws IOException
	{
		FileReader fr = new FileReader(file);
		BufferedReader textReader = new BufferedReader(fr);
		String lines;
		for (int i = 0; i< 10; i++) {
			lines = textReader.readLine();
			for (int j = 0; j<10; j++) {
				if (lines.charAt(j) != 'X')
					computerShips[i+1][j] = Character.toString(lines.charAt(j));
			}
		}
		textReader.close();
	}
	private void setImages(){
		ImageIcon newIcon = new ImageIcon("questionMark.png");
		Image img = newIcon.getImage();
		Image newimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		questionMark = new ImageIcon(newimg);
	
		ImageIcon newIcon2 = new ImageIcon("A.jpeg");
		Image img2 = newIcon2.getImage();
		Image newimg2 = img2.getScaledInstance(40, 40,Image.SCALE_SMOOTH);
		A = new ImageIcon(newimg2);
		
		ImageIcon newIcon3 = new ImageIcon("B.png");
		Image img3 = newIcon3.getImage();
		Image newimg3 = img3.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		B = new ImageIcon(newimg3);
		
		ImageIcon newIcon4 = new ImageIcon("C.jpeg");
		Image img4 = newIcon4.getImage();
		Image newimg4 = img4.getScaledInstance(40, 40,Image.SCALE_SMOOTH);
		C = new ImageIcon(newimg4);
		
		ImageIcon newIcon5 = new ImageIcon("D.jpeg");
		Image img5 = newIcon5.getImage();
		Image newimg5 = img5.getScaledInstance(40, 40,Image.SCALE_SMOOTH);
		D = new ImageIcon(newimg5);
	
	}
	
	private void setGridImages(JPanel left, JPanel right){
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		playerLabels[0][10] = new JButton("");
		computerLabels[0][10] = new JButton("");
	
		playerLabels[0][10].setBorder(emptyBorder);
		
		for (int b = 0; b<11; b++){
			if (b!=10){
				playerLabels[0][b] = new JButton(getString(b));
				playerLabels[0][b].setFont(font);
				playerLabels[0][b].setBorder(emptyBorder);
				computerLabels[0][b] = new JButton(getString(b));
				computerLabels[0][b].setFont(font);
				computerLabels[0][b].setBorder(emptyBorder);
			}
			playerLabels[0][b].setPreferredSize(new Dimension(40, 40));
			playerLabels[0][b].setBorder(emptyBorder);
			computerLabels[0][b].setPreferredSize(new Dimension(40,40));
		}
		
		left.add(playerLabels[0][0]);
		playerLabels[0][0].setBorder(emptyBorder);
		playerLabels[0][0].setEnabled(false);
		right.add(computerLabels[0][0]);
		for (int i = 1; i<11; i++){
			for (int j = 0; j<10; j++){
				playerLabels[i][j] = new JButton();
				playerLabels[i][j].setIcon(questionMark);
				playerLabels[i][j].setDisabledIcon(questionMark);
				playerLabels[i][j].setBorder(new LineBorder(Color.GRAY));
				playerLabels[i][j].addActionListener(new editModeListener(i, j, getString(i-1)));
				if (playerShipsSet) { playerLabels[i][j].setEnabled(false); }
				computerLabels[i][j] = new JButton();
				computerLabels[i][j].setIcon(questionMark);
				computerLabels[i][j].setDisabledIcon(questionMark);
				computerLabels[i][j].setBorder(new LineBorder(Color.GRAY));
				left.add(playerLabels[i][j]);
				right.add(computerLabels[i][j]);
				playerLabels[i][j].setPreferredSize(new Dimension(40,40));
				computerLabels[i][j].setPreferredSize(new Dimension(40,40));
			}
			//adds the letter coordinate for the next row
			left.add(playerLabels[0][i]);
			playerLabels[0][i].setBorder(emptyBorder);
			playerLabels[0][i].setEnabled(false);
			computerLabels[0][i].setEnabled(false);
			computerLabels[0][i].setBorder(emptyBorder);
			right.add(computerLabels[0][i]);
		}

		//adds all the number coordinates on the bottom row
		for (int k = 1; k<11; k++){
			playerLabels[k][10] = new JButton(Integer.toString(k));
			playerLabels[k][10].setFont(font);
			playerLabels[k][10].setBorder(emptyBorder);
			computerLabels[k][10] = new JButton(Integer.toString(k));
			computerLabels[k][10].setBorder(emptyBorder);
			computerLabels[k][10].setFont(font);
			left.add(playerLabels[k][10]);
			playerLabels[k][10].setEnabled(false);
			right.add(computerLabels[k][10]);
			playerLabels[k][10].setPreferredSize(new Dimension(40,40));
			computerLabels[k][10].setPreferredSize(new Dimension(40,40));
		}
	}

	private void makePretty(JPanel mainLeft, JPanel mainRight) {
		
		JPanel playerPanel = new JPanel(new BorderLayout());
		JLabel player = new JLabel("PLAYER", SwingConstants.CENTER);
		player.setFont(font);
		playerPanel.add(player, BorderLayout.CENTER);
		JPanel computerPanel = new JPanel(new BorderLayout());
		JLabel computer = new JLabel("COMPUTER", SwingConstants.CENTER);
		computer.setFont(font);
		computerPanel.add(computer, BorderLayout.CENTER);
		
		Font font = player.getFont();
		// same font but bold
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		player.setFont(boldFont);
		computer.setFont(boldFont);
		
		computerPanel.setBorder(compound);
		computerPanel.setBackground(Color.YELLOW);
		playerPanel.setBorder(compound);
		playerPanel.setBackground(Color.YELLOW);
		
		mainLeft.add(playerPanel, BorderLayout.NORTH);
		
		mainRight.add(computerPanel, BorderLayout.NORTH);
		
		mainRight.setBorder(BorderFactory.createLineBorder(Color.black));
		mainLeft.setBorder(BorderFactory.createLineBorder(Color.black));
		mainRight.setBackground(Color.CYAN);
		mainLeft.setBackground(Color.CYAN);
		
		mainLeft.setBorder(compound3);
		mainRight.setBorder(compound3);
	}
	
	private class PopWindow extends JFrame
	{
		//hold the column that was chosen
		private int i;
		//hold the row that was chosen
		private int j;
		//holds the direction that was chosen
		private int direction;
		//hold the ship options for the user
		private JComboBox<String> jComboBox;
		//contains the group of direction options
		private ButtonGroup group;
		//string value of the ship chosen
		private String ship;
		//int value of the ship chosen
		private int shipNum;
		//radiobuttons
		private JRadioButton option1;
		private JRadioButton option2;
		private JRadioButton option3;
		private JRadioButton option4;
		//the button that allows the user to set the ship if the positioning is valid
		private JButton setShips;
		//boolean that determines whether the positioning is valid
		private Boolean valid;
		
		//constructor
		public PopWindow(int i, int j, String coor) {
			super(coor+(j+1));
			this.i = i;
			this.j = j;
			setLocation(750, 50);
			setSize(300, 300);
			direction = 0;
			
			setShips = new JButton("Place Ship");
			setShips.setEnabled(true);
			
			//determining which ships have been set, in which case they will not be added to the combobox
			jComboBox = new JComboBox<String>();
			if (Dnum <2) { jComboBox.addItem("Destroyer"); }
			if(Cnum != 1) { jComboBox.addItem("Cruiser"); }
			if (Bnum != 1) { jComboBox.addItem("Battleship"); }
			if (Anum != 1) { jComboBox.addItem("Aircraft Carrier"); }
			
			//adds an actionlistener to the combobox
			jComboBox.addActionListener( new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		//getting the ship String chosen, and the ship numberValue
		    		ship = (String) jComboBox.getSelectedItem();
		    		shipNum = getShipNum(ship);
		    		//checks to see if both the direction and ship have been chosen yet
			    	if (shipNum != -1 && direction != -1){
			    		//checks whether the direction and ship are valid with the i and j coordinates
			    		valid = checkValidity();
			    		//if they are valid, the setShips button will be enabled, if not valid, it will be disabled
			    		if(valid) { setShips.setEnabled(true); }
			    		else { setShips.setEnabled(false); }
			    	}
		    	}
		    });
			//initializes the default value of the combobox to whatever is at index 0
			jComboBox.setSelectedItem(jComboBox.getItemAt(0));
			
			//setting all the direction radioButtons and adding actionListeners
			option1 = new JRadioButton("West", true);
			option1.setActionCommand("West");
			option1.addActionListener(new myActionListener());
	        option2 = new JRadioButton("East", false);
	        option2.setActionCommand("East");
			option2.addActionListener(new myActionListener());
	        option3 = new JRadioButton("North", false);
	        option3.setActionCommand("North");
			option3.addActionListener(new myActionListener());
	        option4 = new JRadioButton("South", false);
	        option4.setActionCommand("South");
			option4.addActionListener(new myActionListener());
	        
			//adding all the radioButtons to the group
	        group = new ButtonGroup();
	        group.add(option1);
	        group.add(option2);
	        group.add(option3);
	        group.add(option4);
	        
	        //a panel that contains all the radioButtons
			JPanel radioPanel = new JPanel();
			radioPanel.add(option1);
			radioPanel.add(option2);
			radioPanel.add(option3);
			radioPanel.add(option4);
			add(jComboBox, BorderLayout.NORTH);
			add(radioPanel, BorderLayout.CENTER);
			add(setShips, BorderLayout.SOUTH);
			
			//actionListener for setting a ship
			setShips.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					//setting all the ships in the GUI
					makeShips();
					//advances the counter of the appropriate ship
					setShipNum(shipNum);
					playerShipsReset();
					tryToStart();
					setVisible(false);
					dispose();
				}
			});
			setVisible(true);
			setResizable(false);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	    
	    private class myActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String temp = group.getSelection().getActionCommand();
				if (temp.equals("West")) { direction = 0;}
				else if (temp.equals("East")) { direction = 1; }
				else if (temp.equals("North")) { direction = 2; }
				else if (temp.equals("South")) { direction = 3; }
				else { direction = -1; }
		    	if (shipNum != -1 && direction != -1){
		    		valid = checkValidity();
		    		if (valid) { setShips.setEnabled(true); }
		    		else { setShips.setEnabled(false); }
		    	}
			}
	    }
	    
		private Boolean checkValidity(){
			if (direction == 0){
				if(shipNum == 3  && j<4) { return false; }
				else if (j<3 && shipNum == 2) { return false;}
				else if (j<2 && shipNum == 1) { return false; }
				else if (j<1 && shipNum == 0) { return false; }
				else {
					int temp = j;
					while (temp != j-shipNum-2){
						if (playerShips[i][temp] != null){
							return false;
						}
						temp--;
					}
					return true;
				}
			}
			else if (direction == 1) {
				if(shipNum == 3  && j>5) { return false; }
				else if (j>6 && shipNum == 2) { return false; }
				else if (j>7 && shipNum == 1) { return false; }
				else if (j>8 && shipNum == 0) { return false; }
				else {
					int temp = j;
					while (temp != j+shipNum+2){
						if (playerShips[i][temp] != null){
							return false;
						}
						temp++;
					}
					return true;
				}
			}
			else if (direction == 3) {
				if(shipNum == 3  && i>6) { return false; }
				else if (i>7 && shipNum == 2) { return false; }
				else if (i>8 && shipNum == 1) { return false; }
				else if (i>9 && shipNum == 0) { return false; }
				else{
					//System.out.println("in true");
					int temp = i;
					while (temp != i+shipNum+2){
						if (playerShips[temp][j] != null){
							return false;
						}
						temp++;
					}
					return true;
				}
			}
			else if (direction == 2) {
				if(shipNum == 3 && i<5) { return false; }
				else if (i<4 && shipNum == 2) { return false; }
				else if (i<3 && shipNum == 1) { return false; }
				else if (i<2 && shipNum == 0) { return false; }
				else{
					int temp = i;
					while (temp != i-shipNum-2){
						if (playerShips[temp][j] != null){
							return false;
						}
						temp--;
					}
					return true;
				}
			}
			else{ return true; }
	    }
		
		private void makeShips(){
			int temp;
			if (direction == 2 || direction == 3){
				int size = shipNum+2;
				temp = i;
				int it = 0;
				Coordinates one = null;
				Coordinates two = null;
				String s = getShip(shipNum);
				while(size != 0){
					playerShips[temp][j] = s;
					setShipCoor(new Coordinates(temp, j), shipNum);
					if (shipNum == 0) {
						if (it == 0) { one = new Coordinates(temp, j); }
						else { two = new Coordinates(temp, j); }
						it++;
					}
					playerLabels[temp][j].setIcon(getIcon(shipNum));
					playerLabels[temp][j].setDisabledIcon(getIcon(shipNum));
					if (direction == 2) { temp--; }
					else if (direction == 3) { temp++; }
					size--;
				}
				if (shipNum == 0) {
					if (D1 == null) { D1 = new Destroyer(one, two); }
					else { D2 = new Destroyer(one, two); }
				}
			}
			else if (direction == 1 || direction == 0){
				int size = shipNum+2;
				temp = j;
				int it = 0;
				Coordinates one = null;
				Coordinates two = null;
				String s = getShip(shipNum);
				while(size != 0){
					playerShips[i][temp] = s;
					playerLabels[i][temp].setIcon(getIcon(shipNum));
					if (shipNum == 0){
						if (shipNum == 0){
							if (it == 0) { one = new Coordinates(i, temp); }
							else { two = new Coordinates(i, temp); }
						}
						it++;
					}
					setShipCoor(new Coordinates(i, temp), shipNum);
					playerLabels[i][temp].setDisabledIcon(getIcon(shipNum));
					//playerLabels[i][temp].setEnabled(false);
					if (direction == 1) { temp++; }
					else if (direction == 0) { temp--;}
					size--;
				}
				if (shipNum == 0){
					if (D1 == null) { D1 = new Destroyer(one, two); }
					else { D2 = new Destroyer(one, two); }
				}
			}
		}
		
		private void setShipCoor(Coordinates c, int ship)
		{ 
			if (ship == 1) { CcoorPlayer.add(c); }
			else if (ship == 2) { BcoorPlayer.add(c); }
			else if (ship == 3) { AcoorPlayer.add(c); }
		}
	    
		private void setShipNum(int i){
			if (i == 0){ Dnum++; }
			else if (i == 1) { Cnum++;}
			else if (i == 2) { Bnum++;}
			else if (i == 3) { Anum++;}
		}
		
		private int getShipNum(String s){
			if (s.equals("Aircraft Carrier")) { return 3; }
			else if (s.equals("Battleship")) { return 2; }
			else if (s.equals("Cruiser")) { return 1; }
			else if (s.equals("Destroyer")) { return 0; }
			else { return -1; }
		}
	    private String getShip(int i){
	    	switch(i){
	    		case 0:
	    			return "D";
	    		case 1:
	    			return "C";
	    		case 2:
	    			return "B";
	    		case 3:
	    			return "A";
	    		default:
	    			return "";
	    	}
	    }

	    private ImageIcon getIcon(int i){
	    	switch(i){
	    	case 0:
	    		return D;
	    	case 1:
	    		return C;
	    	case 2:
	    		return B;
	    	case 3:
	    		return A;
	    	default:
	    		return new ImageIcon();
	    	}
	    }
	}
	
	public static void main (String [] args)
	{
		GameBoard b = new GameBoard();
	}
}

