package view;

import model.WarGame;
import shared.AttackSummary;
import shared.Point;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.GameController;
import exceptions.InvalidAttack;
import exceptions.NotEnoughPlayers;
import java.io.IOException;

public class GameView extends View {

	private WarGame warGame;
	private WorldMap worldMap;
	private GameViewSidePanel sidePanel;
	
	private GameController gameController;
	
	private enum MapClickBehaviour {
		DisplayTerritoryInfo,
		SelectAttackSource,
		SelectAttackTarget
	}
	private MapClickBehaviour currentMapClickBehaviour = MapClickBehaviour.DisplayTerritoryInfo;
	
	private enum CancelButtonBehaviour {
		CancelAttack,
		None
	}
	private CancelButtonBehaviour currentCancelButtonBehaviour = CancelButtonBehaviour.None;
	
	private String selectedSourceAttackTerritory;
		
	@Override
	protected void onEnter(View previousView) {
		try {
			gameController = new GameController(warGame);
			warGame.start();
			
			JFrame frame = getWindow().getFrame();
			Container contentPane = frame.getContentPane();
			
			frame.setLayout(new FlowLayout());
			
			sidePanel = new GameViewSidePanel(this);
			
			worldMap = new WorldMap(warGame);
			
			worldMap.attachTo(contentPane);
			worldMap.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					String tName = worldMap.getTerritoryNameInPoint(new Point(e.getX(), e.getY()));
					
					if (tName != null) {
						onMapTerritoryClicked(tName);
					}
				}
			});
			
			sidePanel.getObjectivesButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {					
					PlayerObjectivesWindow playerObjectivesWindow = new PlayerObjectivesWindow(warGame, getWindow());
					
					playerObjectivesWindow.start();
				}
			});
			
			sidePanel.getAttackButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					sidePanel.setActionModeEnabled(true);
					sidePanel.getContextLabel().setText("Atacando...");
					currentMapClickBehaviour = MapClickBehaviour.SelectAttackSource;
					currentCancelButtonBehaviour = CancelButtonBehaviour.CancelAttack;
					
					JOptionPane.showMessageDialog(getWindow().getFrame(), "Selecione um território possuido por você para iniciar um ataque.");
				}
			});
			
			sidePanel.getCancelButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					onCancelButtonClicked();
					sidePanel.setActionModeEnabled(false);
				}
			});
						
			contentPane.add(sidePanel);
			
			frame.revalidate();
			frame.repaint();
			
			frame.pack();			
			
		} catch (IOException ex) {
			System.err.println("Error loading game background image.");
			ex.printStackTrace();
			
		} catch (NotEnoughPlayers e) {
			// Nothing to be done
		}
	}
	
	private void displayTerritoryInfo(String territoryName) {
		JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("%s\nNúmero de exércitos: %d\nDono: %s\n",territoryName,
		warGame.getTerritorySoldierCount(territoryName),warGame.getTerritoryOwnerColor(territoryName)));
	}
	
	private void onAttackSourceSelected(String territoryName) {
		if (warGame.getTerritoryOwnerColor(territoryName) != gameController.getCurrentPlayerColor()) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), "Você não possui este território, selecione outro.");
			return;
		}
		
		JOptionPane.showMessageDialog(getWindow().getFrame(), "Selecione agora um território para atacar. Precisa estar conectado ao seu território por conexão marítima ou fronteira.");
		selectedSourceAttackTerritory = territoryName;
		currentMapClickBehaviour = MapClickBehaviour.SelectAttackTarget;
	}
	
	private void onAttackTargetSelected(String territoryName) {
		if (!warGame.isTerritoryNeighbourOf(selectedSourceAttackTerritory, territoryName)) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), "O território de destino selecionado não está conectado ao território de onde deseja-se partir o ataque.");
			return;
		}

		try {
			AttackSummary summary = gameController.requestAttack(selectedSourceAttackTerritory, territoryName);
			
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Ataque realizado!\nFonte: %s\nDestino: %s\nDados da defesa: ", selectedSourceAttackTerritory, territoryName));
			
			for (int i = 0; i < summary.defenseDices.length; ++i) {
				int d = summary.defenseDices[i];
				sb.append(d);
				
				if (i < summary.defenseDices.length - 1) {
					sb.append(", ");
				}
			}
			
			sb.append("\nDados do ataque: ");
			for (int i = 0; i < summary.attackDices.length; ++i) {
				int d = summary.attackDices[i];
				sb.append(d);
				
				if (i < summary.attackDices.length - 1) {
					sb.append(", ");
				}
			}
		} catch (InvalidAttack ia) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("O ataque não pôde ser realizado:\n%s", ia.getMessage()));
		} finally {
			onAttackFinished();
			sidePanel.setActionModeEnabled(false);
		}
		
		
	}
	
	private void onAttackFinished() {
		currentMapClickBehaviour = MapClickBehaviour.DisplayTerritoryInfo;
		sidePanel.getContextLabel().setText("");
	}
	
	protected void onCancelButtonClicked() {
		switch (currentCancelButtonBehaviour) {		
		case CancelAttack:
			onAttackFinished();
			break;
			
		default:
			break;			
		}
	}
	
	protected void onMapTerritoryClicked(String territoryName) {
		switch (currentMapClickBehaviour) {		
		case DisplayTerritoryInfo:
			displayTerritoryInfo(territoryName);
			break;
			
		case SelectAttackSource:
			onAttackSourceSelected(territoryName);
			break;
			
		case SelectAttackTarget:
			onAttackTargetSelected(territoryName);
			break;
			
		default:
			break;			
		}
	}

	@Override
	protected void onExit(View nextView) {
	}
	
	public GameView(Window window, WarGame game) {
		super(window);
		this.warGame = game;
	}
}