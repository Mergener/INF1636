package view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.GameController;
import model.WarGame;
import shared.PlayerColor;

public class PlayerCardTradeWindow extends Window {

	private GameController game;
	private Window previousWindow;
	private ArrayList<CardPanel> cardPanels = new ArrayList<CardPanel>();
	
	private class CardPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		public JLabel cardImage;
		public JLabel geometry;
		public JCheckBox checkBox;
		public int cardId;
		
		public CardPanel(Container c, int cardId, String imagePath) {
			this.cardId = cardId;
			checkBox = new JCheckBox(game.getWarGame().getTerritoryCardTerritory(cardId));
			
			ImageIcon image = new ImageIcon(this.getClass().getResource(imagePath));
			cardImage = new JLabel(image);
			
			add(cardImage);
			add(checkBox);
			c.add(this);
			setLayout(new GridLayout(3,1));
		}
		
	}
	
	private String generateImagePath(String territoryName) {
		
		String s = territoryName.replace(" ","").toLowerCase();
		
		String c = game.getWarGame().getTerritoryContinentName(territoryName);
		
		switch(c) {
		case "America do Sul":
			c = "asl";
			break;
		case "America do Norte":
			c = "an";
			break;
		case "Africa":
			c = "af";
			break;
		case "Oceania":
			c = "oc";
			break;
		case "Asia":
			c = "as";
			break;
		case "Europa":
			c = "eu";
			break;
		}
		
		String ret = String.format("../images/war_carta_%s_%s.png",c, s);
		System.out.println(ret);
		return ret;
	}
	
	public PlayerCardTradeWindow(GameController game, Window window) {
		this.game = game;
		this.previousWindow = window;
	}
	
	@Override
	protected void onStart() {
		
		JFrame frame = getFrame();
		
		frame.setLocationRelativeTo(null);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		int[] playerCardIds = null;
		
		try {	
			playerCardIds = game.getWarGame().getTerritoryCardsIdsOwnedByPlayer(game.getCurrentPlayerColor());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		for(int i = 0; i < playerCardIds.length; ++i) {
			CardPanel cardPanel = new CardPanel(contentPane, playerCardIds[i],generateImagePath(game.getWarGame().getTerritoryCardTerritory(playerCardIds[i])));
			cardPanels.add(cardPanel);
		}
		
		previousWindow.getFrame().setEnabled(false);
		
		JButton button = new JButton("Trocar cartas");
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ArrayList<Integer> selectedIds = new ArrayList<Integer>();
				
				for(int i = 0; i < cardPanels.size(); ++i) {
					if(cardPanels.get(i).checkBox.isSelected()) {
						selectedIds.add(cardPanels.get(i).cardId);
					}
				}
				
				if(selectedIds.size() != 3) {
					JOptionPane.showMessageDialog(getFrame(),"Você precisa selecionar exatamente três cartas para realizar a troca.");
					return;
				}
				
				try {
					game.performCardTrade(selectedIds.get(0),selectedIds.get(1),selectedIds.get(2));
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				close();
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
                previousWindow.getFrame().setEnabled(true);
            }
		});
		
		contentPane.add(button);
		
		frame.setResizable(false);
		
		frame.pack();
		
		frame.revalidate();
		
	}

}
