package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.GameController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenuView extends View {

	@Override
	protected void onEnter(View previousView) {
		JFrame frame = getWindow().getFrame();
		Container contentPane = frame.getContentPane();
		
		contentPane.setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(300, 300));
		contentPane.add(panel);
		
		panel.setLayout(new GridLayout(3,1));

		ImageIcon image = new ImageIcon(this.getClass().getResource("../images/war_tabuleiro_bottom.png"));
		
		JLabel logo = new JLabel(image);
		JButton startGameButton = new JButton("Novo jogo");
		JButton loadGameButton = new JButton("Carregar jogo");
		
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				GameController controller = new GameController();
				setCurrentView(new PlayerRegistrationView(getWindow(), controller));
			}
		});
		
		loadGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fc = new JFileChooser();

		        fc.setAcceptAllFileFilterUsed(false);
				fc.setFileFilter(new FileNameExtensionFilter("Arquivos de save de war (*.warsave)", "warsave"));
				
				int retVal = fc.showOpenDialog(getWindow().getFrame());
				
				if (retVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            
		            try {
						FileInputStream stream = new FileInputStream(file);
						try {
							GameController controller = GameController.loadGameFromStream(stream);

							setCurrentView(new GameView(getWindow(), controller));
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(getWindow().getFrame(), String.format("Erro ao ler o arquivo %s:\n%s", 
									fc.getSelectedFile().getName(),
									ex.getMessage()));				
							ex.printStackTrace();
						}
						
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(getWindow().getFrame(), "Não foi possível encontrar o arquivo especificado.");
					}		            
		        }
			}
		});
		
		panel.add(logo);
		panel.add(startGameButton);
		panel.add(loadGameButton);
		
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.revalidate();
	}

	@Override
	protected void onExit(View nextView) {		
	}

	public MainMenuView(Window window) {
		super(window);
	}
}