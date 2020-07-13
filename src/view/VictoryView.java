package view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import exceptions.GameNotStarted;
import exceptions.PlayerNotFound;
import model.WarGame;
import shared.PlayerColor;

public class VictoryView extends View {

	private PlayerColor winnerColor;
	private WarGame game;
	
	public VictoryView(Window window, WarGame game, PlayerColor winnerColor) {
		super(window);
		this.winnerColor = winnerColor;
		this.game = game;
	}

	@Override
	protected void onEnter(View previousView) {
		JFrame frame = getWindow().getFrame();
		Container contentPane = frame.getContentPane();
				
		contentPane.setLayout(new GridLayout(4, 1));

		try {		
			JLabel endGameText = new JLabel("<html><div align=\"center\"><strong>FIM DO JOGO</strong></div></html>");
			contentPane.add(endGameText);
		
			JLabel winnerText = new JLabel(String.format("Parabéns, %s (%s)!", game.getPlayerName(winnerColor), winnerColor.toString()));
			contentPane.add(winnerText);

			String s = String.format("<html>Seu objetivo era:\n%s</html>", game.getPlayerObjectiveDescription(winnerColor));
			s = s.replace("\n", "<br>");
			JLabel objectiveText = new JLabel(s);
			
			contentPane.add(objectiveText);
			
			JButton returnButton = new JButton("Retornar ao menu principal");
			returnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getWindow().setCurrentView(new MainMenuView(getWindow()));
				}
			});
			contentPane.add(returnButton);
			
			frame.pack();
			frame.revalidate();
			
		} catch (PlayerNotFound | GameNotStarted e) {
			// Ignore
			e.printStackTrace();
		}
	}

	@Override
	protected void onExit(View nextView) {
	}
}
