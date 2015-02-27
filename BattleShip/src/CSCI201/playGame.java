package CSCI201;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class playGame {
	
	private String [][] Ships;
	private JButton [][] Labels;
	
	private int AHits;
	private int BHits;
	private int CHits;
	private int DHits;
	private Boolean AIsSunk;
	private Boolean BIsSunk;
	private Boolean CIsSunk;
	private Boolean DIsSunk;
	
	private playGame computerGame;
	private Boolean [][] guessed;
	
	private ImageIcon A;
	private ImageIcon B;
	private ImageIcon C;
	private ImageIcon D;
	
	private JLabel log;
	private GameBoard gb;
	
	private ImageIcon Miss;
	private ImageIcon Hit;
	private ImageIcon End;

	public playGame(String[][] computerShips, JButton[][] computerLabels, ImageIcon A,
			ImageIcon B, ImageIcon C, ImageIcon D, playGame game, JLabel log, GameBoard gb) {
		
		computerGame = game;
		Ships = computerShips;
		Labels = computerLabels;
		
		guessed = new Boolean[11][11];
		for (int i = 1; i<11; i++) {
			for (int j = 0; j<10; j++) { guessed[i][j] = false; }
		}
		
		this.log = log;
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		
		this.gb = gb;
		
		AIsSunk = false;
		BIsSunk = false;
		CIsSunk = false;
		DIsSunk = false;
		
		AHits = 0;
		BHits = 0;
		CHits = 0;
		DHits = 0;
		
		makeIcons();
		
		for (int i = 1; i<11; i++){
			for (int j = 0; j<10; j++){
				computerLabels[i][j].addActionListener(new buttonActionListener(i, j));
			}
		}
	}
	//constructor for the computer to guess the player spots
	public playGame(String[][] playerShips, JButton [][] playerLabels, JLabel log, GameBoard gb) {
		Ships = playerShips;
		Labels = playerLabels;
		A = null;
		B = null;
		C = null;
		D = null;
		this.log = log;
		
		this.gb = gb;
		
		AIsSunk = false;
		BIsSunk = false;
		CIsSunk = false;
		DIsSunk = false;
		
		guessed = new Boolean[11][11];
		for (int i = 1; i<11; i++) {
			for (int j = 0; j<10; j++) { guessed[i][j] = false; }
		}
		
		AHits = 0;
		BHits = 0;
		CHits = 0;
		DHits = 0;
		makeIcons();
		computerGame = null;
	}

	
	public void update(String [][] ships, JButton [][] labels, playGame pg2) {
		Ships = ships;
		Labels = labels;
		computerGame = pg2;
	}
	
	private void makeIcons() {
		ImageIcon newIcon = new ImageIcon("Hit.png");
		Image img = newIcon.getImage();
		Image newimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Hit = new ImageIcon(newimg);
		
		ImageIcon newIcon1 = new ImageIcon("unhappy2.jpg");
		Image img1 = newIcon1.getImage();
		Image newimg1 = img1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Miss = new ImageIcon(newimg1);
		
		ImageIcon newIcon2 = new ImageIcon("theEnd.jpg");
		Image img2 = newIcon2.getImage();
		Image newimg2 = img2.getScaledInstance(120, 67, Image.SCALE_SMOOTH);
		End = new ImageIcon(newimg2);
	}
	
	private class buttonActionListener implements ActionListener {
		private int x;
		private int y;
		
		public buttonActionListener(int i, int j){
			x = i;
			y = j;
		}
		public void actionPerformed(ActionEvent e) {
			if (Ships[x][y] != null) {
				Labels[x][y].setIcon(getIcon(Ships[x][y]));
				String temp = Ships[x][y];
				increaseHits(temp);
			}
			else { Labels[x][y].setIcon(Miss); }
			String sPlay = GameBoard.getString(x-1)+(y+1);
			Labels[x][y].setEnabled(false);
			Labels[x][y].setDisabledIcon(Labels[x][y].getIcon());
			Boolean isWin = checkIfWinner();
			if (isWin == false) {
				String sComp = computerGame.guess();
				log.setText("log: Player: "+sPlay+" Computer: "+sComp);
				checkIfWinner();
			}
		}
		
	}
	
	private Boolean checkIfWinner()
	{
		if (wins()) {
			JOptionPane.showMessageDialog(null, 
			        "You won!", 
			        "You beat the Computer!", 
			        JOptionPane.INFORMATION_MESSAGE, End);
			resetGameBoard();
			return true;
		}
		else if (computerGame.wins()) {
			JOptionPane.showMessageDialog(null, 
			        "You lost..", 
			        "You lost to the Computer!", 
			        JOptionPane.INFORMATION_MESSAGE, End);
			resetGameBoard();
			return true;
		}
		return false;
	}
	
	public void resetGameBoard() { gb.reset(); }
	
	public void reset()
	{
		AIsSunk = false;
		BIsSunk = false;
		CIsSunk = false;
		DIsSunk = false;
		
		AHits = 0;
		BHits = 0;
		CHits = 0;
		DHits = 0;
		
		for (int i = 1; i<11; i++) {
			for (int j = 0; j<10; j++) { guessed[i][j] = false; }
		}
		
		Ships = null;
		Labels = null;
	}
	
	public Boolean wins() { return (AIsSunk && DIsSunk && BIsSunk && CIsSunk); }
	
	public String guess()
	{
		Random randomGenerator = new Random();
		int i = randomGenerator.nextInt(10)+1;
		int j = randomGenerator.nextInt(10);
		if (guessed[i][j] == true) { return guess(); }
		else{
			if (Ships[i][j] == null){
				Labels[i][j].setIcon(Miss);
				Labels[i][j].setDisabledIcon(Miss);
			}
			else{
				increaseHits(Ships[i][j]);
				Labels[i][j].setIcon(Hit);
				Labels[i][j].setDisabledIcon(Hit);
			}
			Labels[i][j].setEnabled(false);
			guessed[i][j] = true;
			String s = GameBoard.getString(i-1);
			s = s+(j+1);
			return s;
		}
	}
	
	private void increaseHits(String ship){
		if (ship.equals("A")){
			AHits++;
			if (AHits==5) { AIsSunk = true; }	
		}
		else if (ship.equals("B")){
			BHits++;
			if (BHits == 4) { BIsSunk = true; }
		}
		else if (ship.equals("C")){
			CHits++;
			if (CHits == 3) { CIsSunk = true; }
		}
		else if (ship.equals("D")){
			DHits++;
			if (DHits == 4) { DIsSunk = true; }
		}
	}
	
    private ImageIcon getIcon(String s){
    	if (s.equals("D")) { return D; }
    	else if (s.equals("C")) { return C; }
    	else if (s.equals("B")) { return B; }
    	else if (s.equals("A")) { return A; }
    	else { return new ImageIcon(); }
    }
}
