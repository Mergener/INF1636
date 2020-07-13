package view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import exceptions.IllegalCardsTrade;
import model.WarGame;
import shared.PlayerColor;

public class TerritoryCardWindow extends Window {

	private GameController controller;
	private Window previousWindow;
	private ArrayList<CardPanel> cardPanels = new ArrayList<CardPanel>();
	
	private JButton tradeCardsButton;
	
	private int selectedPanelCount = 0;
	
	private class CardPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		public JLabel cardImage;
		public JLabel geometry;
		public JCheckBox checkBox;
		public int cardId;
		
		public CardPanel(Container c, int cardId, String imagePath) {
			this.cardId = cardId;
			checkBox = new JCheckBox(controller.getWarGame().getTerritoryCardTerritory(cardId));
			
			ImageIcon image = new ImageIcon(this.getClass().getResource(imagePath));
			cardImage = new JLabel(image);
			
			add(cardImage);
			add(checkBox);
			c.add(this);
			setLayout(new GridLayout(3,1));
			
			checkBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent evt) {
					selectedPanelCount += 1 * (evt.getStateChange() == ItemEvent.SELECTED ? 1 : -1);
					tradeCardsButton.setEnabled(selectedPanelCount == 3);					
				}
			});
		}
		
	}
	
	private String generateImagePath(String territoryName) {
		
		String s = territoryName.replace(" ","").toLowerCase();
		
		String c = controller.getWarGame().getTerritoryContinentName(territoryName);
		
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
		
		return ret;
	}
	
	public TerritoryCardWindow(GameController game, Window window) {
		this.controller = game;
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
			playerCardIds = controller.getWarGame().getTerritoryCardsIdsOwnedByPlayer(controller.getCurrentPlayerColor());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		tradeCardsButton = new JButton("Trocar cartas selecionadas");
		
		tradeCardsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				ArrayList<Integer> selectedIds = new ArrayList<Integer>();
				
				for (int i = 0; i < cardPanels.size(); ++i) {
					if(cardPanels.get(i).checkBox.isSelected()) {
						selectedIds.add(cardPanels.get(i).cardId);
					}
				}
				
				if (selectedIds.size() != 3) {
					JOptionPane.showMessageDialog(getFrame(),"Você precisa selecionar exatamente três cartas para realizar a troca.");
					return;
				}
				
				try {
					controller.performCardTrade(selectedIds.get(0), selectedIds.get(1), selectedIds.get(2));
				} catch (IllegalCardsTrade e) {
					JOptionPane.showMessageDialog(getFrame(), e.getMessage());
				}
					
				close();
			}
		});
		
		tradeCardsButton.setEnabled(false);
		
		for(int i = 0; i < playerCardIds.length; ++i) {
			System.out.printf("%d\n", playerCardIds[i]);
			CardPanel cardPanel;
			
			if(controller.getWarGame().isTerritoryCardJoker(playerCardIds[i])) {
				cardPanel = new CardPanel(contentPane,playerCardIds[i],"../images/war_carta_coringa.png");
			}
			else {
				cardPanel = new CardPanel(contentPane, playerCardIds[i],generateImagePath(controller.getWarGame().getTerritoryCardTerritory(playerCardIds[i])));
			}
			
			cardPanels.add(cardPanel);
		}
		
		previousWindow.getFrame().setEnabled(false);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
                previousWindow.getFrame().setEnabled(true);
            }
		});
		
		contentPane.add(tradeCardsButton);
		
		frame.setResizable(false);
		
		frame.pack();
		
		frame.revalidate();
		
	}

}
