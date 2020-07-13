package view;

import model.WarGame;
import shared.AttackSummary;
import shared.PlayerColor;
import shared.Point;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.GameController;
import controller.GameState;
import exceptions.IllegalPlayEnd;
import exceptions.InvalidAttack;
import exceptions.NotEnoughPlayers;
import exceptions.PlayerNotFound;
import listeners.IAttackListener;
import listeners.ICurrentPlayerChangeListener;
import listeners.IGameStateChangeListener;
import listeners.IVictoryListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Target;

public class GameView extends View implements ICurrentPlayerChangeListener, IAttackListener, IVictoryListener {

	private WarGame warGame;
	private WorldMap worldMap;
	private GameViewSidePanel sidePanel;
	
	private GameController gameController;
	public GameController getGameController() {
		return gameController;
	}
	
	private enum MapClickBehaviour {
		DisplayTerritoryInfo,
		SelectAttackSource,
		SelectAttackTarget,
		SelectMigrationSource,
		SelectMigrationTarget,
		PositionContinentalTroops,
		PositionGlobalTroops
	}
	private MapClickBehaviour currentMapClickBehaviour = MapClickBehaviour.DisplayTerritoryInfo;
	
	private enum CancelButtonBehaviour {
		CancelAttack,
		CancelGlobalTroopsPositioning,
		CancelContinentalTroopsPositioning,
		CancelMigration,
		None
	}
	private CancelButtonBehaviour currentCancelButtonBehaviour = CancelButtonBehaviour.None;
	
	private String selectedSourceMigrationTerritory;
	private String selectedSourceAttackTerritory;
		
	@Override
	protected void onEnter(View previousView) {
		try {			
			// Setup window layout
			JFrame frame = getWindow().getFrame();
			Container contentPane = frame.getContentPane();
			
			frame.setLayout(new FlowLayout());	
			
			// Generate world and map
			if (!gameController.getWarGame().hasStarted()) {
				gameController.startGame();	
			}
			
			worldMap = new WorldMap(warGame);
			
			// Generate side panel
			sidePanel = new GameViewSidePanel(gameController);
			
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
			
			// Add hooks
			gameController.addCurrentPlayerChangeListener(this);
			gameController.addAttackListener(this);
			gameController.getWarGame().addVictoryListener(this);
			
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
					if (gameController.cardsTradeRequired()) {
						JOptionPane.showMessageDialog(getWindow().getFrame(), "É necessário realizar uma troca de cartas antes de um novo ataque.");
						return;
					}
					
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
				}
			});
			
			sidePanel.getPositionContinentalTroopsButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					onPositionContinentalTroopsButtonClicked();
				}
			});
			
			sidePanel.getPositionGlobalTroopsButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					onPositionGlobalTroopsButtonClicked();
				}
			});
			
			sidePanel.getFinishTurnButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					try {
						gameController.finishCurrentPlay();
					} catch (IllegalPlayEnd e) {
						JOptionPane.showMessageDialog(getWindow().getFrame(), e.getMessage());
					}
				}
			});
			
			sidePanel.getTradeCardsButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					TerritoryCardWindow territoryCardWindow = new TerritoryCardWindow(gameController, getWindow());
					
					territoryCardWindow.start();
				}
			});
			
			sidePanel.getMoveTroopsButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					onMoveTroopsButtonClicked();
				}
			});
			
			sidePanel.getSaveGameButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					saveButtonClicked();
				}
			});			
			
			contentPane.add(sidePanel);
			
			frame.revalidate();
			frame.repaint();
			
			frame.pack();	
			frame.setLocationRelativeTo(null);
			
			sidePanel.onGameStateChanged(gameController.getGameState());
			sidePanel.onCurrentPlayerChanged(gameController.getCurrentPlayerColor());
			onCurrentPlayerChanged(gameController.getCurrentPlayerColor());		
			
		} catch (IOException ex) {
			System.err.println("Erro ao tentar carregar background.");
			ex.printStackTrace();
			
		} catch (NotEnoughPlayers e) {
			// Ignore
			e.printStackTrace();
		}
	}
	
	private void saveButtonClicked() {
		JFileChooser fc = new JFileChooser();

        fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("Arquivos de save de war (*.warsave)", "warsave"));
		
		int retVal = fc.showSaveDialog(getWindow().getFrame());
		
		File file = fc.getSelectedFile();
		
		
		if (!file.getName().endsWith(".warsave")) {
			file = new File(file.getPath() + ".warsave");
		}
		
		if (retVal == JFileChooser.APPROVE_OPTION) {
            if (file.exists()) {
            	int confirm = JOptionPane.showConfirmDialog(getWindow().getFrame(), "O arquivo especificado já existe. Deseja sobrescrevê-lo?");
            	
            	if (confirm != JOptionPane.YES_OPTION) {
            		// User does not want to overwrite file, exit save menu.
            		return;
            	}
            } else {
            	try {
					file.createNewFile();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("Não foi possível criar o arquivo requisitado:\n%s", e.getMessage()));
				}
            }
        }
		
		finishSave(file);
	}
	
	private void finishSave(File file) {		
		try {
			FileOutputStream stream = new FileOutputStream(file);
			
			try {
				gameController.saveToStream(stream);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("Erro ao tentar salvar jogo:\n%s", e.getMessage()));
				e.printStackTrace();
			} finally {
				stream.close();
			}
			
		} catch (FileNotFoundException e) {
			// Ignore
			e.printStackTrace();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("Erro:\n%s", ex.getMessage()));
			ex.printStackTrace();
		}
	}
	
	private void displayTerritoryInfo(String territoryName) {
		JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("%s\nNúmero de exércitos: %d\nDono: %s\n",territoryName,
		warGame.getTerritorySoldierCount(territoryName), warGame.getTerritoryOwnerColor(territoryName)));
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
	
	private void onGlobalTroopsPositioningFinished() {
		currentMapClickBehaviour = MapClickBehaviour.DisplayTerritoryInfo;
		sidePanel.setActionModeEnabled(false);
	}
	
	private void onContinentalTroopsPositioningFinished() {
		currentMapClickBehaviour = MapClickBehaviour.DisplayTerritoryInfo;
		sidePanel.setActionModeEnabled(false);
	}
	
	private void onCancelButtonClicked() {
		sidePanel.setActionModeEnabled(false);
		
		switch (currentCancelButtonBehaviour) {
		
		case CancelAttack:
			onAttackFinished();
			break;
			
		case CancelGlobalTroopsPositioning:
			onGlobalTroopsPositioningFinished();
			break;
			
		case CancelContinentalTroopsPositioning:
			onContinentalTroopsPositioningFinished();
			break;
			
		case CancelMigration:
			onMoveTroopsFinished();
			break;
			
		default:
			break;
			
		}
	}
	
	private void onMoveTroopsButtonClicked() {
		sidePanel.setActionModeEnabled(true);
		JOptionPane.showMessageDialog(getWindow().getFrame(), "Clique em um territórios para retirar tropas.");
		currentMapClickBehaviour = MapClickBehaviour.SelectMigrationSource;
	}
	
	private void onPositionContinentalTroopsButtonClicked() {
		sidePanel.setActionModeEnabled(true);
		JOptionPane.showMessageDialog(getWindow().getFrame(), "Clique em um território para posicionar tropas continentais nele.");
		currentMapClickBehaviour = MapClickBehaviour.PositionContinentalTroops;
	}
	
	private void onPositionGlobalTroopsButtonClicked() {
		sidePanel.setActionModeEnabled(true);
		JOptionPane.showMessageDialog(getWindow().getFrame(), "Clique em um território para posicionar tropas globais nele.");
		currentMapClickBehaviour = MapClickBehaviour.PositionGlobalTroops;
	}
	
	private void onMoveTroopsSourceSelected(String territoryName) {
		if (warGame.getMaximumEligibleMigrationTroopsCount(territoryName) <= 0) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), "O número de tropas migráveis no território especificado é zero. Selecione outro.\n\nObservação: Tropas são migráveis se não fizeram parte de uma migração na rodada atual e não são as únicas presentes em seu território.");
			return;
		}
		
		if (warGame.getTerritoryOwnerColor(territoryName) != gameController.getCurrentPlayerColor()) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), "Você não possui o território selecionado. Selecione outro.");
			return;
		}
		
		currentMapClickBehaviour = MapClickBehaviour.SelectMigrationTarget;
		this.selectedSourceMigrationTerritory = territoryName;
		JOptionPane.showMessageDialog(getWindow().getFrame(), "Selecione agora o território para enviar as tropas.");
	}
	
	private void onMoveTroopsTargetSelected(String territoryName) {
		if (warGame.getTerritoryOwnerColor(territoryName) != gameController.getCurrentPlayerColor()) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), "Você não possui o território selecionado. Selecione outro.");
			return;
		}
		
		if (!warGame.isTerritoryNeighbourOf(selectedSourceMigrationTerritory, territoryName)) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("O território selecionado não é conectado a %s. Selecione outro.", selectedSourceMigrationTerritory));
			return;
		}
		
		TroopMigrationWindow window = new TroopMigrationWindow(selectedSourceMigrationTerritory, territoryName, gameController, getWindow());
		window.start();
		onMoveTroopsFinished();
	}
	
	private void onMoveTroopsFinished() {
		currentMapClickBehaviour = MapClickBehaviour.DisplayTerritoryInfo;
		sidePanel.setActionModeEnabled(false);
	}
	
	private void onPositionGlobalTroopsTargetSelected(String territoryName) {
		GlobalTroopsPositioningWindow window = new GlobalTroopsPositioningWindow(territoryName, gameController, getWindow());
		window.start();
		onGlobalTroopsPositioningFinished();
	}
	
	private void onPositionContinentalTroopsTargetSelected(String territoryName) {
		ContinentalTroopsPositioningWindow window = new ContinentalTroopsPositioningWindow(territoryName, gameController, getWindow());
		window.start();
		onContinentalTroopsPositioningFinished();
	}
	
	protected void onMapTerritoryClicked(String territoryName) {
		switch (currentMapClickBehaviour) {	
			
		case SelectAttackSource:
			onAttackSourceSelected(territoryName);
			break;
			
		case SelectAttackTarget:
			onAttackTargetSelected(territoryName);
			break;
			
		case PositionContinentalTroops:
			onPositionContinentalTroopsTargetSelected(territoryName);
			break;
			
		case PositionGlobalTroops:
			onPositionGlobalTroopsTargetSelected(territoryName);
			break;

		case SelectMigrationSource:
			onMoveTroopsSourceSelected(territoryName);
			break;
			
		case SelectMigrationTarget:
			onMoveTroopsTargetSelected(territoryName);
			break;	
			
		default:	
		case DisplayTerritoryInfo:
			displayTerritoryInfo(territoryName);
			break;		
		}
	}

	@Override
	protected void onExit(View nextView) {
	}

	public GameView(Window window, GameController gc) {
		super(window);
		this.gameController = gc;
		this.warGame = gc.getWarGame();
	}

	
	@Override
	public void onCurrentPlayerChanged(PlayerColor newPlayerColor) {
		try {
			JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("Vez de %s (%s).", newPlayerColor.toString(), warGame.getPlayerName(newPlayerColor)));
		} catch (PlayerNotFound e) {
			// Ignore
			e.printStackTrace();
		}
	}

	@Override
	public void onAttackPerformed(AttackSummary args) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Ataque realizado\n");
		sb.append(String.format("Origem do ataque: %s\n", args.sourceTerritoryName));
		sb.append(String.format("Alvo do ataque: %s\n", args.targetTerritoryName));
		
		sb.append("Dados da defesa: ");
		for (int i = 0; i < args.defenseDices.length; ++i) {
			sb.append(args.defenseDices[i]);
			
			if (i < args.defenseDices.length - 1) {
				sb.append(", ");
			} else {
				sb.append("\n");
			}
		}
		
		sb.append("Dados do ataque: ");
		for (int i = 0; i < args.attackDices.length; ++i) {
			sb.append(args.attackDices[i]);
			
			if (i < args.attackDices.length - 1) {
				sb.append(", ");
			} else {
				sb.append("\n");
			}
		}
		
		sb.append(String.format("\nPerdas da defesa: %d soldados", args.getDefenseLoss()));
		sb.append(String.format("\nPerdas do ataque: %d soldados", args.getAttackLoss()));
		
		PlayerColor attacker = gameController.getWarGame().getTerritoryOwnerColor(args.sourceTerritoryName);
		if (args.territoryWasTaken()) {
			try {
				sb.append(String.format("\n\nApós o árduo combate, %s foi conquistado pelas tropas de %s (%s)", 
						args.targetTerritoryName, 
						gameController.getWarGame().getPlayerName(attacker),
						attacker.toString()
						));
			} catch (PlayerNotFound e) {
				// Ignore
				e.printStackTrace();
			}
		} else {		
			sb.append(String.format("\n\nAs tropas de %s resistiram ao ataque!", 
					args.targetTerritoryName
					));		
		}
		
		JOptionPane.showMessageDialog(getWindow().getFrame(), sb.toString());
		
		if (gameController.cardsTradeRequired()) {
			JOptionPane.showMessageDialog(getWindow().getFrame(), "Você obteve 5 cartas de territórios: é necessário realizar uma troca de cartas antes de finalizar a jogada ou realizar outro ataque.");
		}
		
		if (args.isPreviousOwnerEliminated()) {
			sb.setLength(0);
			sb.append(String.format("O jogador %s foi eliminado pelo jogador %s", args.getPreviousOwnerColor(),attacker));
			JOptionPane.showMessageDialog(getWindow().getFrame(),sb.toString());
		}
	}

	@Override
	public void onVictory(PlayerColor winnerColor) {
		this.setCurrentView(new VictoryView(getWindow(), warGame, winnerColor));
	}
}